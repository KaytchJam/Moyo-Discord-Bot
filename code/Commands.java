package kaytchjam.Moyo;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter{
	
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event ) {
		
		String msg = event.getMessage().getContentRaw();
		String activeRsp = "Moyo is active!";
		
		if (event.getAuthor().isBot()) return;
		
		//Active Command
		if (msg.equalsIgnoreCase(".mactive")) {
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage(activeRsp).queue();
		}
		
		//Help Command
		if (msg.equalsIgnoreCase(Moyo.prefix + "help")) {
			EmbedBuilder help = new EmbedBuilder();
			help.setTitle("Information");
			help.setDescription("Moyo is designed to help you quickly get Wikipedia and Oxford links for terms and words you want to learn about. Future mechanics to be determined...");
			help.addField("Creator", "KJam", false);
			help.addField("Commands", ".mactive - Check if Moyo is active!" + "\n" + ".mdef [word] - Brings up the Oxford definition of the chosen word" + "\n" + ".mwiki [title] - Brings up the Wikipedia page of the chosen term", false);
			help.setColor(0x663a82);
			
			event.getChannel().sendMessage(help.build()).queue();
			help.clear();
			return; 
		} //Oxford Dictionary Command
			else if (msg.equalsIgnoreCase(Moyo.prefix + "def" + msg.substring(4))) {
				event.getChannel().sendMessage("https://www.oxfordlearnersdictionaries.com/us/definition/english/" + msg.substring(5)).queue();
				System.out.println("1");
				return; 
		} //Wikipedia Command
			else if (msg.equalsIgnoreCase(Moyo.prefix + "wiki" + msg.substring(5))) {
				String noSpace = msg.substring(6);
				event.getChannel().sendMessage("https://en.wikipedia.org/wiki/" + noSpace.replace(' ', '_')).queue();
				System.out.println("2");
				return;
		}
	}
}
