package com.github.manitscold123.philosophy_bot;

import java.util.*;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Channel;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Bot {

	private static final String botToken = "NzA5NTk4MTkyMTgxODM3ODU1.XrocPg.FYFgS23cZMqVptqRSidjQPU7sT8";
	private static final Map<String, Command> commands = new HashMap<>();
	
	interface Command {

		Mono<Void> execute(MessageCreateEvent event);
	
	}

	
	public static void main(String[] args) {
		
		
		final DiscordClient client = DiscordClientBuilder.create(botToken).build();
				
		client.getEventDispatcher().on(MessageCreateEvent.class)
	    .flatMap(event -> Mono.justOrEmpty(event.getMessage().getContent())
	        .flatMap(content -> Flux.fromIterable(commands.entrySet())
	            .filter(entry -> content.startsWith('.' + entry.getKey()))
	            .flatMap(entry -> entry.getValue().execute(event))
	            .next()))
	    .subscribe();
		
		client.login().block();
		
		
	}
	
	static {
	    commands.put("ping", command -> pong(command));
	    commands.put("zarathustra", command -> zarathustra(command) );
	}

	public static Mono<Void> pong(MessageCreateEvent command) {
		return command.getMessage().getChannel()
		        .flatMap(channel -> channel.createMessage("Pong!")).then();
	}
	
	public static Mono<Void> zarathustra(MessageCreateEvent command) {
		
		
		
		return command.getMessage().getChannel().flatMap(null).then();
	}
	
}
