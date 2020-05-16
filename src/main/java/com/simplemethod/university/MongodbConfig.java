package com.simplemethod.university;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Component;

@Component
public class MongodbConfig {

    private MongoDatabase mongoDatabase;


    public void openConnection(String[] connection, String[] bucket)
    {
        MongoClient mongoClient = new MongoClient( connection[0] , 27017 );
        mongoDatabase = mongoClient.getDatabase(bucket[0] );
    }

    public MongoCollection<Document> openCollection(String collection)
    {
        return mongoDatabase.getCollection(collection);
    }

}
