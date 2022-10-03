package de.trollagent.trollbot.musik.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
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

public class SetVolumeCommand extends SlashCommand {

    public SetVolumeCommand(String name, String description, JDA jda) {
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
        musicController.getAudioPlayer().setVolume(event.getOption("volume").getAsInt());

        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setTitle("Volume is now " + event.getOption("volume").getAsInt());

        event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();

    }
}
