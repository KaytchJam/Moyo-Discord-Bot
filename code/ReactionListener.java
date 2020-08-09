package kaytchjam.Moyo;

import java.util.function.Consumer;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;

public class ReactionListener extends ListenerAdapter{
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		if (event.getMember().getUser().equals(event.getJDA().getSelfUser())) {
			if (event.getReactionEmote().getEmoji().equals("✌️")) {
				String rpsEmbID = event.getMessageId();
				System.out.println(rpsEmbID);
				MessageChannel channel = event.getChannel();
				Message rpsEmbMes = channel.getHistory().getMessageById(rpsEmbID);
				System.out.println(rpsEmbMes);
				System.out.println();
			}
			if (event.getReactionEmote().getEmoji().equals("👊")) {
				RestAction<Message> testResting = event.getChannel().retrieveMessageById(event.getMessageId());
				Consumer<Message> editFromReact = (mesa) -> {
					Message m = mesa;
					m.editMessage("Test succeeds once again!").queue();
					m.clearReactions("👊").queue();
				};
				testResting.queue(editFromReact);
				return;
			}
		}
	}
}
