package de.trollagent.trollbot.musik.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import de.trollagent.trollbot.TrollBot;
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

public class StopCommand extends SlashCommand {

    public StopCommand(String name, String description, JDA jda) {
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

        if (member.getVoiceState().getChannel() != guild.getMemberById(event.getJDA().getSelfUser().getId()).getVoiceState().getChannel()) {
            EmbedBuilder embedBuilder = new EmbedBuilder();

            embedBuilder.setColor(Color.RED);
            embedBuilder.setTitle("You are not in the same channel as the Bot");

            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        MusicController musicController = TrollBot.playerManager.getMusicController(guild);
        AudioManager audioManager = guild.getAudioManager();
        audioManager.closeAudioConnection();
        musicController.getAudioPlayer().stopTrack();

        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setTitle("Successfully stopped the music");

        event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();

    }
}
