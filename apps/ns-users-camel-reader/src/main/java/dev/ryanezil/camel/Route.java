package dev.ryanezil.camel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import dev.ryanezil.camel.model.Audit;
import dev.ryanezil.camel.model.CommonUser;

public class Route extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("kafka:{{kafka.dbz.topic.name}}" +
        // The consumer processor group ID
                "?groupId={{kafka.application.groupid}}" +
                // What to do when there is no initial offset in ZooKeeper or if an offset is
                // out of range
                // 'earliest' automatically reset the offset to the earliest offset
                "&autoOffsetReset=earliest" +
                // See this link:
                // https://www.apicur.io/registry/docs/apicurio-registry/2.4.x/getting-started/assembly-configuring-kafka-client-serdes.html#registry-serdes-types-avro_registry
                "&valueDeserializer=io.apicurio.registry.serde.avro.AvroKafkaDeserializer" +
                "&keyDeserializer=io.apicurio.registry.serde.avro.AvroKafkaDeserializer" +
                "&additionalProperties.apicurio.registry.url={{apicurio.registry}}" +
                "&additionalProperties.apicurio.registry.avro-datum-provider=io.apicurio.registry.serde.avro.ReflectAvroDatumProvider")
                .log("Message received from Kafka : ${body}")
                .log("    on the topic ${headers[kafka.TOPIC]}")
                .log("    on the partition ${headers[kafka.PARTITION]}")
                .log("    with the offset ${headers[kafka.OFFSET]}")
                .log("    with the key ${headers[kafka.KEY]}")
                .choice()
                /*
                 * A database DELETE operation causes Debezium to generate two Kafka records:
                 * 1. A record that contains "op": "d", the before row data, and some other
                 * fields.
                 * 2. A tombstone record that has the same key as the deleted row and a value of
                 * null.
                 * This record is a marker for Apache Kafka. It indicates that log compaction
                 * can
                 * remove all records that have this key.
                 */
                .when(body().isNull()).log("Record ignored: the message body is NULL")
                .otherwise().to("direct:process-record")
                .endChoice();

        from("direct:process-record")
                .convertBodyTo(String.class)
                .setHeader("dbz_operation").jsonpath("$.op")
                .setHeader("dbz_source_db").jsonpath("$.source.db")
                .setHeader("dbz_source_table").jsonpath("$.source.table")
                .setHeader("dbz_source_connector").jsonpath("$.source.connector")
                .setHeader("dbz_source_name").jsonpath("$.source.name")
                .setHeader("dbz_ts").jsonpath("$.source.ts_ms")
                .log("Debezium event info:")
                .log("  >> Debezium Operation = ${header.dbz_operation}")
                .log("  >> SourceDB = ${header.dbz_source_db}")
                .log("  >> SourceTABLE = ${header.dbz_source_table}")
                .log("  >> SourceCONNECTOR = ${header.dbz_source_connector}")
                .log("  >> SourceNAME = ${header.dbz_source_name}")
                .choice()
                .when(body().isNull())
                .log("The received message body is NULL")

                .when(header("dbz_operation").isEqualTo("c"))
                .log("DBZ op='c' - A record was created/inserted")
                .setBody().jsonpath("$.after").marshal().json(true)
                .log("The retrieved Debezium 'after' block is:\n${body}")
                .to("direct:upsert-record")

                .when(header("dbz_operation").isEqualTo("u"))
                .log("DBZ op='u' - A record was updated")
                .setBody().jsonpath("$.after").marshal().json(true)
                .log("The retrieved Debezium 'after' block is:\n${body}")
                .to("direct:upsert-record")

                .when(header("dbz_operation").isEqualTo("d"))
                .log("DBZ op='d' - A record was deleted")
                .setBody().jsonpath("$.before").marshal().json(true)
                .log("The retrieved Debezium 'before' block is:\n${body}")
                .to("direct:deleted-record")

                .when(header("dbz_operation").isEqualTo("r"))
                .log("DBZ op='r' - A record was read (Snapshot event): operation not implemented")

                .otherwise()
                .log("Unknown Debezium operation")

                .end();

        from("direct:upsert-record")
                // Mapping JSON format from DBZ to CommonUser
                .toD("atlasmap:atlasmap-mappings/{{atlasmap.mapper}}")
                .unmarshal().json(JsonLibrary.Jackson, CommonUser.class)
                .enrich("direct:enrich", new MergeStrategy())
                .process(new Processor() {
                    // Enriching the document setting the 'Document last update time' using a Camel
                    // Processor
                    public void process(Exchange exchange) throws Exception {
                        final Audit audit = new Audit();
                        String timestampString = exchange.getIn().getHeader("dbz_ts", String.class);
                        final Long timestampLong = Long.parseLong(timestampString);

                        // Convertir a Timestamp y formatear
                        final Timestamp timestamp = new Timestamp(timestampLong);
                        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                        final String formattedDate = sdf.format(timestamp);
                        audit.setDate(formattedDate);
                        audit.setOperation(exchange.getIn().getHeader("dbz_operation", String.class));
                        audit.setSource(exchange.getIn().getHeader("dbz_source_connector", String.class));
                        audit.setName(exchange.getIn().getHeader("dbz_source_name", String.class));
                        final CommonUser commonUser = exchange.getIn().getBody(CommonUser.class);
                        commonUser.getEnriched().add(audit);
                    }
                })
                // SEE operations here:
                // https://camel.apache.org/components/3.21.x/mongodb-component.html#_endpoint_query_option_operation
                .to("mongodb:camelMongoClient?database={{mongodb.database}}&collection={{mongodb.users.collection}}&operation=save")
                .log("SAVED MongoDB JSON ${body}");

        from("direct:enrich")
                // retrieves only one element from the collection whose _id field matches the
                // content of the IN message body
                .setBody(simple("${body.get_id()}"))
                .log("Looking for MongoDB document with _id=[${body}]")
                .to("mongodb:camelMongoClient?database={{mongodb.database}}&collection={{mongodb.users.collection}}&operation=findById")
                // The returned object is a BSON object. We marshall it to JSON and then
                // unmarshal the JSON to a CommonUser object.
                .marshal().json(JsonLibrary.Jackson)
                .unmarshal().json(JsonLibrary.Jackson, CommonUser.class);

        from("direct:deleted-record")
                // The IN message body will act as the removal filter query
                // Mapping JSON format from DBZ to CommonUser
                .toD("atlasmap:atlasmap-mappings/{{atlasmap.mapper}}")
                .log("DELETING body=[${body}]")
                // SEE operations here:
                // https://camel.apache.org/components/3.21.x/mongodb-component.html#_endpoint_query_option_operation
                .to("mongodb:camelMongoClient?database={{mongodb.database}}&collection={{mongodb.users.collection}}&operation=remove")
                .log("DELETED MongoDB JSON ${body}");

    } // END configure() method

}