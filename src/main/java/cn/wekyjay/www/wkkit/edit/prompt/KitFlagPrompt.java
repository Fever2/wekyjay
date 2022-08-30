package cn.wekyjay.www.wkkit.edit.prompt;

import java.util.Arrays;

import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;

import cn.wekyjay.www.wkkit.WkKit;
import cn.wekyjay.www.wkkit.kit.Kit;

public class KitFlagPrompt {
	// ���Flag
	public static void setFlag(Player player, String kitname, String flag) {
		Conversation conversation = new ConversationFactory(WkKit.getWkKit())
				.withFirstPrompt(new KitFlagPrompt_SetFlag())
				.withTimeout(60)
				.buildConversation(player);
		conversation.getContext().setSessionData("kitname", kitname);
		conversation.getContext().setSessionData("flag", flag);
		conversation.begin();
	}
	// ɾ��Flag
	public static void deFlag(Player player, String kitname, String flag) {
		Conversation conversation = new ConversationFactory(WkKit.getWkKit())
				.withFirstPrompt(new KitFlagPrompt_DeFlag())
				.withTimeout(60)
				.buildConversation(player);
		conversation.getContext().setSessionData("kitname", kitname);
		conversation.getContext().setSessionData("flag", flag);
		conversation.begin();
	}

}
class KitFlagPrompt_SetFlag extends ValidatingPrompt{

	@Override
	public String getPromptText(ConversationContext context) {
		String flag = (String) context.getSessionData("flag");
		String kitname = (String) context.getSessionData("kitname");
		return "��a�����޸ġ�e" + kitname + "��a�ġ�e" + flag + "��a ,��������Ҫ�޸ĵ�ֵ(Cancelȡ��):";
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		String flag = (String) context.getSessionData("flag");
		if(flag.equals("Delay") && !input.matches("[0-9]+"))  return false;
		if(flag.equals("Times") && !input.matches("[0-9]+"))  return false;
		return !(input.length() <= 0)?true:false;
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, String input) {
		if(input.equalsIgnoreCase("Cancel")) {
			context.getForWhom().sendRawMessage("��e��ȡ���ˣ�");
			return Prompt.END_OF_CONVERSATION;
		}
		input = input.replace("&", "��");
		String flag = (String) context.getSessionData("flag");
		String kitname = (String) context.getSessionData("kitname");
		Kit kit = Kit.getKit(kitname);
		switch(flag) {
		case "DisplayName": kit.setDisplayName(input.replaceAll("&", "��"));break;
		case "Icon": kit.setIcon(input);break;
		case "Permission": kit.setPermission(input);break;
		case "Times": kit.setTimes(Integer.parseInt(input));break;
		case "Delay": kit.setDelay(Integer.parseInt(input));break;
		case "DoCron": kit.setDocron(input);break;
		case "Lore" : kit.setLore(Arrays.asList(input.replaceAll("&", "��").split(",")));break;
		case "Drop": kit.setDrop(Arrays.asList(input.split(",")));break;
		case "Commands": kit.setCommands(Arrays.asList(input.replaceAll("&", "��").split(",")));break;
		}
		kit.saveConfig(); // �����������
		context.getForWhom().sendRawMessage("��a�޸ĳɹ���");
		return Prompt.END_OF_CONVERSATION;
	}
	
}
class KitFlagPrompt_DeFlag extends ValidatingPrompt{

	@Override
	public String getPromptText(ConversationContext context) {
		String flag = (String) context.getSessionData("flag");
		String kitname = (String) context.getSessionData("kitname");
		return "��c��ȷ��Ҫɾ����e" + kitname + "��c�ġ�e" + flag + "��c��(Y/N)";
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		if(input.equalsIgnoreCase("Y") || input.equals("N")) return true; 
		return false; 
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, String input) {
		if(input.equalsIgnoreCase("N")) {
			context.getForWhom().sendRawMessage("��e��ȡ���ˣ�");
			return Prompt.END_OF_CONVERSATION;
		}
		String flag = (String) context.getSessionData("flag");
		String kitname = (String) context.getSessionData("kitname");
		Kit kit = Kit.getKit(kitname);
		switch(flag) {
		case "DisplayName": kit.setDisplayName(WkKit.getWkKit().getConfig().getString("Default.Name"));break;
		case "Icon": kit.setIcon(WkKit.getWkKit().getConfig().getString("Default.Icon"));break;
		case "Permission": kit.setPermission(null);break;
		case "Times": kit.setTimes(null);break;
		case "Delay": kit.setDelay(null);break;
		case "DoCron": kit.setDocron(null);break;
		case "Lore" : kit.setLore(null);break;
		case "Drop": kit.setDrop(null);break;
		case "Commands": kit.setCommands(null);break;
		}
		kit.saveConfig(); // �����������
		context.getForWhom().sendRawMessage("��aɾ���ɹ���");
		return Prompt.END_OF_CONVERSATION;
	}
	
}