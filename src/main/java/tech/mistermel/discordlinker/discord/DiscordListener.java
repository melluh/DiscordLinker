package tech.mistermel.discordlinker.discord;

import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import tech.mistermel.discordlinker.DiscordLinker;

public class DiscordListener extends ListenerAdapter {

	@Override
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
		if(!event.getReaction().getReactionEmote().getAsCodepoints().equals(DiscordHandler.VERIFY_REACTION_CODEPOINT)) {
			return;
		}
		
		if(!DiscordLinker.instance().getDiscordHandler().isVerifyChannel(event.getChannel())) {
			return;
		}
		
		event.retrieveMessage().complete().removeReaction(DiscordHandler.VERIFY_REACTION, event.getUser()).complete();
		
		int code = DiscordLinker.instance().generateVerifyCode(event.getGuild().getIdLong(), event.getUserIdLong());
		
		PrivateChannel channel = event.getUser().openPrivateChannel().complete();
		channel.sendMessage("Use **/verify " + code + "** in-game").complete();
	}
	
}
