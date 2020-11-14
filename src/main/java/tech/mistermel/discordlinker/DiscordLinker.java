package tech.mistermel.discordlinker;

import java.io.File;

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
		
		if(!new File(this.getDataFolder(), "config.yml").exists())
			this.saveDefaultConfig();
		
		this.discordHandler = new DiscordHandler();
		if(!discordHandler.connectBot(this.getConfig().getString("token")))
			return;
		
		discordHandler.createVerifyChannel(this.getConfig().getString("verify-channel"));
	}
	
	@Override
	public void onDisable() {
		discordHandler.disconnectBot();
	}
	
}
