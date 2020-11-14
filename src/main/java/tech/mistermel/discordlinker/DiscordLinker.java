package tech.mistermel.discordlinker;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.plugin.java.JavaPlugin;

import tech.mistermel.discordlinker.discord.DiscordHandler;

public class DiscordLinker extends JavaPlugin {

	private static DiscordLinker instance;
	
	public static DiscordLinker instance() {
		return instance;
	}
	
	private Map<Integer, PendingVerify> pendingVerifies = new HashMap<>();
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
	
	public DiscordHandler getDiscordHandler() {
		return discordHandler;
	}
	
	public int generateVerifyCode(long guildId, long userId) {
		int code = ThreadLocalRandom.current().nextInt(1000000);
		
		PendingVerify pendingVerify = new PendingVerify(guildId, userId, System.currentTimeMillis());
		pendingVerifies.put(code, pendingVerify);
		
		return code;
	}
	
	public static class PendingVerify {
		
		private long guildId, discordId;
		private long createTime;
		
		public PendingVerify(long guildId, long discordId, long createTime) {
			this.guildId = guildId;
			this.discordId = discordId;
			this.createTime = createTime;
		}
		
		public long getGuildId() {
			return guildId;
		}
		
		public long getDiscordId() {
			return discordId;
		}
		
		public long getCreateTime() {
			return createTime;
		}
		
	}
	
}
