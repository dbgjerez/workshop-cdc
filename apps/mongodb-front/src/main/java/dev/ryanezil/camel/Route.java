package dev.ryanezil.camel;


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;


public class Route extends RouteBuilder {

    @Override
    public void configure() throws Exception {


        restConfiguration().bindingMode(RestBindingMode.json);

        rest("/citizens")
                .get()
                .to("direct:getCitizens");

        from("direct:getCitizens")
            .to("mongodb:camelMongoClient?database={{mongodb.database}}&collection={{mongodb.users.collection}}&operation=findAll")
            .log("Retrieved values from MongoDB >>>> ${body}");

    } //END configure()

}
