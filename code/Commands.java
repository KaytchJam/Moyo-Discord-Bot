package kaytchjam.Moyo;

import java.util.function.Consumer;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class Commands extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		
		String msg = event.getMessage().getContentRaw();
		String activeRsp = "Moyo is active!";
		final String mactive = "``.mactive`` - Check if Moyo is active!";
		final String mdef = "``.mdef [word]`` - Brings up the Oxford definition of the chosen word";
		final String mwiki = "``.mwiki [term]`` - Brings up the Wikipedia page of the chosen term";
		final String mrps = "``.mrps`` - Play a game of Rock, Paper, Scissors!";
		
		if (event.getAuthor().isBot()) return;
		
		//Active Command
		if (msg.equalsIgnoreCase(".mactive")) {
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage(activeRsp).queue();
			return;
		}
		
		if (msg.toLowerCase().contains("moyo")) {
			event.getMessage().addReaction("❤️").queue();
			return;
		}
		
		if (msg.startsWith(Moyo.prefix)) {
			switch((msg.split(" ", 2)[0]).substring(2)) {
			
				case "help": // Basic Help command
					EmbedBuilder help = new EmbedBuilder();
					help.setTitle("Information");
					help.setDescription("Moyo is designed to help you quickly get Wikipedia and Oxford links for terms and words you want to learn about. Future mechanics to be determined...");
					help.addField("Creator", "KJam", false);
					help.addField("Want to add Moyo to your server?", "*Add Moyo:* https://tinyurl.com/moyoauth01", false);
					help.addField("Commands", mactive + "\n" + mdef + "\n" + mwiki + "\n" + mrps, false);
					help.setColor(0x663a82);
					
					event.getChannel().sendMessage(help.build()).queue();
					help.clear();
					break;
					
				case "def": // Oxford Dictionary Command
					event.getChannel()
					.sendMessage("https://www.oxfordlearnersdictionaries.com/us/definition/english/" + msg.substring(5)).queue();
					break;
					
				case "wiki": // Wikipedia Command
					String noSpace = msg.substring(6);
					event.getChannel().sendMessage("https://en.wikipedia.org/wiki/" + noSpace.replace(' ', '_')).queue();
					break;
					
				case "rps": // Activate Rock, Paper, Scissors game
					EmbedBuilder rps = new EmbedBuilder();
					rps.setTitle("Rock, Paper, Scissors game!");
					rps.addField("__Rules:__", "*Choose one of following reactions:*\n:punch: ``- Rock``\n:raised_hand: ``- Paper``\n:v: ``- Scissors``", false);
					rps.setColor(0x99ffd6);
					MessageEmbed mRPS = rps.build();
					//System.out.println(mRPS.getTitle());
					RestAction<Message> rpsRest = event.getChannel().sendMessage(mRPS);
					Consumer<Message> restReact = (message) -> {
						Message refMes = message;
						refMes.addReaction("👊").queue();
						refMes.addReaction("✋").queue();
						refMes.addReaction("✌️").queue();
					};
					rpsRest.queue(restReact);
					rps.clear();
					break;
					
				case "test": // Test
					MessageChannel testChan = event.getChannel();
					RestAction<Message> testMes = testChan.sendMessage("test statement");
					Consumer<Message> callback = (message) -> {
						Message m = message;
						m.editMessage("This message is successfully edited").queue();
						m.addReaction("💔").queue();
					};
					testMes.queue(callback);
					break;
			}
		}
	}
}
