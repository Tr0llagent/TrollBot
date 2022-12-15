package de.trollagent.trollbot.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.HashMap;

public class GuildData {

    private String guildId;
    private HashMap<String, Object> data = new HashMap<>();

    public GuildData(String guildId) {
        this.guildId = guildId;
        save();
    }

    public GuildData() {

    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
        save();
    }

    private void save() {

        new File("guilddata/").mkdirs();

        Gson gson = new GsonBuilder().setPrettyPrinting().enableComplexMapKeySerialization().create();

        try (FileWriter fileWriter = new FileWriter("guilddata/" + guildId + ".json")) {
            gson.toJson(this, fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static GuildData getByGuildId(String guildId) {
        try (FileReader fileReader = new FileReader("guilddata/" + guildId + ".json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().enableComplexMapKeySerialization().create();
            return gson.fromJson(fileReader, GuildData.class);
        } catch (IOException e) {
            return new GuildData(guildId);
        }
    }

}
