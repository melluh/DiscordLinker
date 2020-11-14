package tech.mistermel.discordlinker;

import org.bukkit.plugin.java.JavaPlugin;

import tech.mistermel.discordlinker.discord.DiscordHandler;

public class DiscordLinker extends JavaPlugin {

	private static DiscordLinker instance;
	
	public static DiscordLinker instance() {
		return instance;
	}
	
	
	private DiscordHandler discordHandler;
	
	@Override
	public void onEnable() {
		instance = this;
		
		this.discordHandler = new DiscordHandler();
		discordHandler.connectBot("test");
	}
	
}
