package de.trollagent.trollbot.utils;

import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

@Getter
public abstract class SlashCommand extends ListenerAdapter {

    private String name;
    private String description;
    private JDA jda;

    private boolean onlyGuilds = true;

    private Collection<OptionData> optionData = new ArrayList<>();
    private Collection<SubcommandData> subcommandData = new ArrayList<>();

    public SlashCommand(String name, String description, JDA jda) {
        this.name = name;
        this.description = description;
        this.jda = jda;
    }

    public abstract void execute(SlashCommandInteractionEvent event);

    public void subCommands(SlashCommandInteractionEvent event) {}

    public void setAutoComplete(CommandAutoCompleteInteractionEvent event) {}

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getInteraction().getName().equals(name)) {

            //Only Guilds
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setColor(Color.RED);
            embedBuilder.setTitle("You cant use this Command here");
            if (onlyGuilds && !event.isFromGuild()) {
                event.replyEmbeds(embedBuilder.build()).queue();
                return;
            }

            //Execute
            execute(event);
            subCommands(event);
        }
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        if (event.getInteraction().getName().equals(name)) {
            setAutoComplete(event);
        }
    }

    public SlashCommand addOption(OptionType type, String name, String description, boolean required) {
        this.optionData.add(new OptionData(type, name, description, required));
        return this;
    }

    public SlashCommand addOption(OptionData optionData) {
        this.optionData.add(optionData);
        return this;
    }

    public SlashCommand addSubCommand(SubcommandData subcommandData) {
        this.subcommandData.add(subcommandData);
        return this;
    }

    public SlashCommand onlyGuilds(boolean onlyGuilds) {
        this.onlyGuilds = onlyGuilds;
        return this;
    }

    public void register(){
        jda.upsertCommand(name.toLowerCase(Locale.ROOT), description).addOptions(optionData).addSubcommands(subcommandData).queue();
        jda.addEventListener(this);
        System.out.println("Registert /" + name.toLowerCase(Locale.ROOT));
    }

}
