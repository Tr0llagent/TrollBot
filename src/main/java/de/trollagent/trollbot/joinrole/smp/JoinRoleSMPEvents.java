package de.trollagent.trollbot.joinrole.smp;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JoinRoleSMPEvents extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        if (!event.getGuild().equals(event.getJDA().getGuildById("1026559416956354560"))) return;
        Member member = event.getMember();
        event.getGuild().addRoleToMember(member, event.getJDA().getRoleById("1026865336471015434")).queue();
    }
}
