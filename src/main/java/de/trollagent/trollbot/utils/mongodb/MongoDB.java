package de.trollagent.trollbot.utils.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDB {

    private final MongoClient mongoClient;
    private final MongoDatabase mongoDatabase;
    private final MongoCollection<Document> mongoCollection;

    public MongoDB() {
        mongoClient = new MongoClient(
                new MongoClientURI("<MongoDB URL ist hier nicht wegen datenschutzt>"));
        mongoDatabase = mongoClient.getDatabase("trollbot");
        mongoCollection = mongoDatabase.getCollection("guilddata");
    }

    public MongoCollection<Document> getMongoCollection() {
        return mongoCollection;
    }

}
