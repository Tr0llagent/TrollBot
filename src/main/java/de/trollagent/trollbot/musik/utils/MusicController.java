package de.trollagent.trollbot.musik.utils;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import de.trollagent.trollbot.TrollBot;
import net.dv8tion.jda.api.entities.Guild;

public class MusicController {

    private Guild guild;
    private AudioPlayer audioPlayer;

    public MusicController(Guild guild) {
        this.guild = guild;
        this.audioPlayer = TrollBot.audioPlayerManager.createPlayer();

        this.guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(audioPlayer));
        this.audioPlayer.setVolume(50);

    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    public Guild getGuild() {
        return guild;
    }

}
