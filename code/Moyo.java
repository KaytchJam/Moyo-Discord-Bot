package kaytchjam.Moyo;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;


public class Moyo {
	public static JDA jda;
	public static JDA build;
	public static String prefix = ".m";
	
	private Moyo() throws LoginException {
		jda = JDABuilder.createDefualt("Generic_Token").build();
		jda.getPresence().setStatus(OnlineStatus.IDLE);
		jda.getPresence().setActivity(Activity.playing(".mhelp"));
		jda.addEventListener(new Commands());
	}
	
	public static void main(String[] args) throws LoginException {
		new Moyo();
		
	}
}
