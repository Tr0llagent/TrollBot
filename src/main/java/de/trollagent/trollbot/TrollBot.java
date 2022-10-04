package de.trollagent.trollbot;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import de.trollagent.trollbot.joinrole.smp.JoinRoleSMPEvents;
import de.trollagent.trollbot.musik.commands.JoinCommand;
import de.trollagent.trollbot.musik.commands.PlayCommand;
import de.trollagent.trollbot.musik.commands.SetVolumeCommand;
import de.trollagent.trollbot.musik.commands.StopCommand;
import de.trollagent.trollbot.musik.utils.PlayerManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.Scanner;

public class TrollBot {

    public static AudioPlayerManager audioPlayerManager;
    public static PlayerManager playerManager;

    public static void main(String[] args) throws LoginException, InterruptedException {

        JDABuilder jdaBuilder =  JDABuilder.createDefault(args[0]);

        //Intents
        jdaBuilder.enableIntents(Arrays.asList(GatewayIntent.values()));
        jdaBuilder.setMemberCachePolicy(MemberCachePolicy.ALL);
        jdaBuilder.setChunkingFilter(ChunkingFilter.ALL);
        jdaBuilder.enableCache(Arrays.asList(CacheFlag.values()));

        //Events
        jdaBuilder.addEventListeners(
                new JoinRoleSMPEvents()
        );

        //Audio
        audioPlayerManager = new DefaultAudioPlayerManager();
        playerManager = new PlayerManager();

        //JDA
        JDA jda = jdaBuilder.build();
        jda.awaitReady();

        //Audio again
        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        audioPlayerManager.getConfiguration().setFilterHotSwapEnabled(true);

        //Commands
        //User Command
        new PlayCommand("play", "Play some musik", jda)
                .addOption(OptionType.STRING, "music", "YouTube url, Twitch url or search query", true)
                .onlyGuilds(true)
                .register();

        //User Command
        new SetVolumeCommand("volume", "Set the volume of the music Bot", jda)
                .addOption(OptionType.INTEGER, "volume", "The Volume in %", true)
                .onlyGuilds(true)
                .register();

        //Admin only Command
        new JoinCommand("join", "Join your VoiceChannel", jda).onlyGuilds(true).register();
        //User Command
        new StopCommand("stop", "Stop the music", jda).onlyGuilds(true).register();

        Scanner scan = new Scanner(System.in);
        String str = null;
        while (jda != null) {
            str = scan.nextLine();
            if (str.equals("stop")) {
                jda.shutdownNow();
                break;
            }
        }
        jda.shutdownNow();
        System.exit(0);

    }

}
