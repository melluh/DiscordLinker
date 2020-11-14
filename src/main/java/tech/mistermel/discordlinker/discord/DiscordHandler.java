package tech.mistermel.discordlinker.discord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import tech.mistermel.discordlinker.DiscordLinker;

public class DiscordHandler {
	
	public static final String VERIFY_REACTION = "\u2705", VERIFY_REACTION_CODEPOINT = "U+2705";
	
	private JDA jda;
	private Map<Guild, TextChannel> verifyChannels = new HashMap<>();
	
	private DiscordListener listener;
	
	public boolean connectBot(String token) {
		try {
			this.jda = JDABuilder.createDefault(token)
					.build();
			
			this.listener = new DiscordListener();
			jda.addEventListener(listener);
			
			jda.awaitReady();
			return true;
		} catch (LoginException | InterruptedException e) {
			DiscordLinker.instance().getLogger().severe("Failed to log in to Discord: " + e.getMessage());
			return false;
		}
	}
	
	public void createVerifyChannel(String name) {
		MessageEmbed embed = new EmbedBuilder()
				.setTitle("Verify your Minecraft account")
				.appendDescription("In order to continue, you need to link your Minecraft account to your Discord account. Click on the checkmark below to continue.")
				.build();
		
		for(Guild guild : jda.getGuilds()) {
			List<TextChannel> channels = guild.getTextChannelsByName(name, false);
			
			if(channels.size() != 0) {
				verifyChannels.put(guild, channels.get(0));
				continue;
			}
			
			TextChannel channel = guild.createTextChannel(name).complete();
			verifyChannels.put(guild, channel);
			
			Message msg = channel.sendMessage(embed).complete();
			msg.addReaction(VERIFY_REACTION).complete();
		}
	}
	
	public void disconnectBot() {
		if(jda != null) {
			jda.removeEventListener(listener);
			jda.shutdown();
		}
	}
	
	public boolean isVerifyChannel(TextChannel channel) {
		return channel.equals(verifyChannels.get(channel.getGuild()));
	}
	
}
