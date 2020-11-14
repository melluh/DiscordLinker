package tech.mistermel.discordlinker.discord;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;

public class DiscordHandler {
	
	public void connectBot(String token) {
		DiscordClient client = DiscordClient.create(token);
		
		GatewayDiscordClient gateway = client.login().doOnError(error -> {
			System.out.println("Failed to log in");
		}).block();
		
		gateway.on(MessageCreateEvent.class).subscribe(event -> {
			System.out.println(event.getMessage().getContent());
		});
	}
	
}
