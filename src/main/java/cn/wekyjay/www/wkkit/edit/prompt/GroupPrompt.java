package cn.wekyjay.www.wkkit.edit.prompt;

import org.bukkit.Bukkit;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;

import cn.wekyjay.www.wkkit.WkKit;

public class GroupPrompt{
	
	public static void newConversation(Player player,String groupname) {
		Conversation conversation = new ConversationFactory(WkKit.getWkKit()) // ����һ���Ự
	            .withFirstPrompt(new GroupPrompt_1()) // �������������ɵĶԻ���ʹ�õĵ�һ����ʾ��
	            .withTimeout(60)
	            .buildConversation(player);
		conversation.getContext().setSessionData("groupname", groupname);
		conversation.begin();
	}

}

class GroupPrompt_1 extends ValidatingPrompt{
	// ѯ��Ҫ��ʲô
	@Override
	public String getPromptText(ConversationContext context) {
		return "���Ƿ�Ҫɾ��" + context.getSessionData("groupname") + "?(Y/N)";
	}

	// �������İ�ȫ���
	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		if(input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")) return true; 
		return false; 
	}

	// InputValid������Чʱִ��
	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, String input) {
		if(input.equalsIgnoreCase("N")) return Prompt.END_OF_CONVERSATION;
		return new GroupPrompt_2(); // ��һ���Ի�
	}
}
class GroupPrompt_2 extends ValidatingPrompt {

	
	@Override
	public String getPromptText(ConversationContext context) {
		return "�Ƿ�Ҳɾ��������ڵ����?(Y/N)";

	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		if(input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")) return true; 
		return false; 
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, String input) {
		if(input.equalsIgnoreCase("N")) Bukkit.dispatchCommand((Player)context.getForWhom(), "wk group remove " + context.getSessionData("groupname") + " true");
		else Bukkit.dispatchCommand((Player)context.getForWhom(), "wk group delete " + context.getSessionData("groupname"));
		return Prompt.END_OF_CONVERSATION;
	}
	
}
