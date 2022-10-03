package de.trollagent.trollbot.musik.utils;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;

public class AudioResultHandler implements AudioLoadResultHandler {

    private final String url;
    private final MusicController musicController;
    private SlashCommandInteractionEvent event;
    private AudioManager audioManager;
    private GuildVoiceState guildVoiceState;

    public AudioResultHandler(
                            String url,
                            MusicController musicController,
                            SlashCommandInteractionEvent event,
                            AudioManager audioManager,
                            GuildVoiceState guildVoiceState) {
        this.url = url;
        this.musicController = musicController;
        this.event = event;
        this.audioManager = audioManager;
        this.guildVoiceState = guildVoiceState;
    }

    @Override
    public void trackLoaded(AudioTrack audioTrack) {

        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setTitle("Now playing "+ url.replace("ytsearch: ", ""));

        event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();

        audioManager.openAudioConnection(guildVoiceState.getChannel());
        musicController.getAudioPlayer().playTrack(audioTrack);
    }

    @Override
    public void playlistLoaded(AudioPlaylist audioPlaylist) {

    }

    @Override
    public void noMatches() {
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setColor(Color.RED);
        embedBuilder.setTitle("Not Valid");

        event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
    }

    @Override
    public void loadFailed(FriendlyException e) {

    }
}
