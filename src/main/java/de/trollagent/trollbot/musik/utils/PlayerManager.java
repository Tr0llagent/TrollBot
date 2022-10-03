package de.trollagent.trollbot.musik.utils;

import net.dv8tion.jda.api.entities.Guild;

import java.util.HashMap;

public class PlayerManager {

    public HashMap<Guild, MusicController> controller;

    public PlayerManager() {
        this.controller = new HashMap<>();
    }

    public MusicController getMusicController(Guild guild) {
        if (controller.containsKey(guild)) {
            return controller.get(guild);
        }
        MusicController musicController = new MusicController(guild);
        controller.put(guild, musicController);
        return musicController;
    }

}
