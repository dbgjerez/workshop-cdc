package dev.ryanezil.camel;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import dev.ryanezil.camel.model.CommonUser;

public class MergeStrategy implements AggregationStrategy {

    public Exchange aggregate(Exchange original, Exchange resource) {        
        

        CommonUser userFromDebezium = original.getIn().getBody(CommonUser.class);
        CommonUser userFromMongo = resource.getIn().getBody(CommonUser.class);

        if(userFromMongo != null) {

            /*
            * Apply your own mapping implementation for debeziun updates.
            * 
            * MapStruct component might be used with Camel for object mapping between POJOs
            */

            if(userFromDebezium.getRemoteId() != null) userFromMongo.setRemoteId(userFromDebezium.getRemoteId());
            if(userFromDebezium.getFirstName() != null) userFromMongo.setFirstName(userFromDebezium.getFirstName());
            if(userFromDebezium.getLastName() != null) userFromMongo.setLastName(userFromDebezium.getLastName());
            if(userFromDebezium.getEnriched() != null) userFromMongo.setEnriched(userFromDebezium.getEnriched());
            if(userFromDebezium.getEmail() != null) userFromMongo.setEmail(userFromDebezium.getEmail());
            if(userFromDebezium.getPhone() != null) userFromMongo.setPhone(userFromDebezium.getPhone());
            if(userFromDebezium.getGender() != null) userFromMongo.setGender(userFromDebezium.getGender());

            // Updated object from MongoDB is returned
            return resource;

        } else return original;

    }    
    
}
