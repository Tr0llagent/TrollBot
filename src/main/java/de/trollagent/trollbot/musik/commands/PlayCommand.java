package de.trollagent.trollbot.musik.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import de.trollagent.trollbot.TrollBot;
import de.trollagent.trollbot.musik.utils.AudioResultHandler;
import de.trollagent.trollbot.musik.utils.MusicController;
import de.trollagent.trollbot.utils.SlashCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;

public class PlayCommand extends SlashCommand {

    public PlayCommand(String name, String description, JDA jda) {
        super(name, description, jda);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        Guild guild = event.getGuild();
        GuildVoiceState guildVoiceState = member.getVoiceState();

        if (guildVoiceState.getChannel() == null) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setColor(Color.RED);
            embedBuilder.setTitle("You have to be in a VoiceChannel");

            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }
        MusicController musicController = TrollBot.playerManager.getMusicController(guild);
        AudioPlayerManager audioPlayerManager = TrollBot.audioPlayerManager;
        AudioManager audioManager = guild.getAudioManager();

        if (audioManager.isConnected() && audioManager.getConnectedChannel() != guildVoiceState.getChannel()) {

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setColor(Color.RED);
            embedBuilder.setTitle("The Bot is already in another voice channel");

            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        String url = event.getOption("music").getAsString();

        audioPlayerManager.loadItem(url, new AudioResultHandler(
                                                            url,
                                                            musicController,
                                                            event,
                                                            audioManager,
                                                            guildVoiceState));

    }
}
