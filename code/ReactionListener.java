package kaytchjam.Moyo;


import java.util.Random;
import java.util.function.Consumer;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;

public class ReactionListener extends ListenerAdapter{
	String ogMsgID;
	boolean rpsGameOn;
	String playerName;
	
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		final String paper = "✋";
		final String scissor = "✌️";
		
		if (event.getMember().getUser().equals(event.getJDA().getSelfUser())) {
			if (event.getReactionEmote().getEmoji().equals(scissor)) {
				RestAction<Message> rpsEmbed = event.getChannel().retrieveMessageById(event.getMessageId());
				Consumer<Message> callEmbed = (messa) -> {
					Message w = messa;
					ogMsgID = w.getId();
					//MessageEmbed rpsList = w.getEmbeds().get(0);
				};
				rpsEmbed.queue(callEmbed);
				rpsGameOn = true;
			}
			if (event.getReactionEmote().getEmoji().equals("💔")) {
				RestAction<Message> testResting = event.getChannel().retrieveMessageById(event.getMessageId());
				Consumer<Message> editFromReact = (mesa) -> {
					Message m = mesa;
					m.editMessage("Test succeeds once again!").queue();
					m.clearReactions("💔").queue();
				};
				testResting.queue(editFromReact);
				return;
			}
		} else if (!event.getMember().getUser().equals(event.getJDA().getSelfUser())) {
			//System.out.println("User emote identified");
			//System.out.println("Current message ID: " + event.getMessageId());
			//System.out.println("Original message ID: " + ogMsgID);
			//System.out.println("Is the RPS game on? " + rpsGameOn);
			
			if (rpsGameOn && event.getMessageId().equalsIgnoreCase(ogMsgID)) {
				//System.out.println("Reaction confirmed to be on same message.");
				RestAction<Message> getRest = event.getChannel().retrieveMessageById(event.getMessageId());

				switch (event.getReactionEmote().getEmoji()) {
					case"👊":
						//System.out.println("ROCK");
						rpsGameOn = false;
						compareAns(0, getRest, event.getUser().getName(), event.getUser().getAvatarUrl(), event.getJDA().getSelfUser().getAvatarUrl());
						break;
					case paper:
						//System.out.println("PAPER");
						rpsGameOn = false;;
						compareAns(1, getRest, event.getUser().getName(), event.getUser().getAvatarUrl(), event.getJDA().getSelfUser().getAvatarUrl());
						break;
					case scissor:
						//System.out.println("SCISSORS");
						rpsGameOn = false;
						compareAns(2, getRest, event.getUser().getName(), event.getUser().getAvatarUrl(), event.getJDA().getSelfUser().getAvatarUrl());
						break;
				}
			}
		}
	}
	
	public void compareAns(int p_pick, RestAction<Message> rA, String p1, String pfp1, String pfp2) {
		Random rand = new Random();
		boolean wins = false;
		
		int p_move = p_pick - 1;
		int cpu_move = rand.nextInt(2) - 1;
		int cpu_pick = cpu_move + 1;
		
		int m_product = p_move * cpu_move;
		if (m_product < 0) {
			if (p_move < cpu_move) { wins = true; }
			else if (p_move > cpu_move) { wins = false; }
		} else if (m_product >= 0) {
			if (p_move > cpu_move) { wins = true; }
			else if (p_move < cpu_move) { wins = false; }
		}
		
		boolean tie = false;
		if (p_move == cpu_move) { tie = true; }
		
		String[] p_options = {"__Rock__", "__Paper__", "__Scissors__"};
		String winningpfp = wins ? pfp1 : pfp2;
		String winningPlayer = wins ? p1 : "Moyo";
		String results = tie ? winningPlayer + " wins!" : "It's a **tie**!";
		
		Consumer<Message> eat = (messi) -> {
			messi.clearReactions().queue();
			MessageEmbed oldEmb = messi.getEmbeds().get(0);
			EmbedBuilder rps = new EmbedBuilder();
			rps.setTitle(oldEmb.getTitle());
			rps.addField("**CHOICES**", "*The player chose* " + p_options[p_pick] + ", *Moyo chose* " + p_options[cpu_pick] + ".", false);
			rps.addField("**RESULT**", results, false);
			rps.setImage(winningpfp);
			rps.setColor(0x8f00b3);
			oldEmb = rps.build();
			messi.editMessage(oldEmb).queue();
			rps.clear();
		};
		rA.queue(eat);
	}
}
