package de.trollagent.trollbot.utils;

import de.trollagent.trollbot.utils.mongodb.MongoDB;
import org.bson.Document;

import java.util.HashMap;

public class GuildData {

    private String guildId;
    private HashMap<String, Object> data;
    private MongoDB mongoDB;

    public GuildData(String guildId, HashMap<String, Object> data) {
        this.guildId = guildId;
        this.data =data;
        mongoDB = new MongoDB();
        save();
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
        save();
    }

    private void save() {
        Document document = new Document().append("guildId", guildId).append("guildData", data);
        Document filterDocument = new Document("guildId", guildId);
        mongoDB.getMongoCollection().insertOne(document);
    }

    public static GuildData getByGuildId(String guildId) {

        MongoDB secondMongoDB = new MongoDB();

        Document document = new Document("guildId", guildId);

        if (secondMongoDB.getMongoCollection().find(document).first() != null) {
            return new GuildData(guildId, (HashMap<String, Object>) secondMongoDB.getMongoCollection().find(document).first().get("guildData"));
        }
        return new GuildData(guildId, new HashMap<>());
    }

}
