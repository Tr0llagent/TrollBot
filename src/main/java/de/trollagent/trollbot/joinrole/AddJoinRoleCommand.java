package de.trollagent.trollbot.joinrole;

import de.trollagent.trollbot.utils.GuildData;
import de.trollagent.trollbot.utils.SlashCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AddJoinRoleCommand extends SlashCommand {

    public AddJoinRoleCommand(String name, String description, JDA jda) {
        super(name, description, jda);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Role role = event.getOption("role").getAsRole();

        GuildData guildData = GuildData.getByGuildId(event.getGuild().getId());

        ArrayList<String> roles = new ArrayList<>();
        HashMap<String, Object> data = guildData.getData();

        if (data.containsKey("joinroles")) {
            roles = (ArrayList<String>) data.get("joinroles");
        }

        roles.add(role.getId());
        data.put("joinroles", roles);

        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setColor(Color.RED);
        embedBuilder.setTitle("\"" + role.getName() + "\"" + " was successfully added to the joinroles");

        event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();

        guildData.setData(data);

        String temp = "";

        roles = (ArrayList<String>) guildData.getData().get("joinroles");
        for (String role1 : roles) {
            temp += getJda().getRoleById(role1).getName() + " ";
        }

        event.getChannel().sendMessage(temp).queue();
    }
}
