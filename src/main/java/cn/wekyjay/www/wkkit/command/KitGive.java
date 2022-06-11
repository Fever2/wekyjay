package cn.wekyjay.www.wkkit.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import cn.wekyjay.www.wkkit.WkKit;
import cn.wekyjay.www.wkkit.config.LangConfigLoader;
import cn.wekyjay.www.wkkit.kit.Kit;
import cn.wekyjay.www.wkkit.kit.KitGetter;
import cn.wekyjay.www.wkkit.tool.WKTool;

public class KitGive {
	static WkKit wk = WkKit.getWkKit();// ��������ʵ��		

	public Boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length < 3) {
			sender.sendMessage(LangConfigLoader.getStringWithPrefix("Commands.give", ChatColor.GREEN));
			return true;
		}
		Player p;
		if(args[2].equalsIgnoreCase("@Me")) {
			p = (Player)sender;
		}else {
			p = Bukkit.getPlayer(args[2]);//��ȡ���ʵ��	
			if(p == null) {
				sender.sendMessage(LangConfigLoader.getStringWithPrefix("NO_PLAYER", ChatColor.RED));
				return true;
			}
		}
		String kitname = args[1];
		Kit kit = Kit.getKit(kitname);
		this.ExcutionMode((Player)sender,p, kit, args.length>=4?args[3]:"1");
		return true;
		
	}
	public void ExcutionMode(Player sender,Player player, Kit kit, String mode) {
		PlayerInventory pinv = player.getInventory();//ʹ�÷�װ���getplayer������ȡ��ұ���
		ItemStack[] getItemList = kit.getItemStack();//��ȡKits.Item��list����
		switch(mode) {
			case "2":
				if(!WKTool.hasSpace(player, kit)) {//�ж��Ƿ����㹻�ı����ռ�
					sender.sendMessage(LangConfigLoader.getStringWithPrefix("KIT_GIVE_FAILED",ChatColor.RED));
					return;
				}
				for(ItemStack item : getItemList) {
					ItemStack i = item;//ͨ��NBT����һ��Item
					pinv.addItem(i);//�����Ʒ������
				}
				// ִ��ָ��
				if(kit.getCommands() != null) new KitGetter().runCommands(kit, player);
				break;
			case "3":
				if(!WKTool.hasSpace(player, 1)) {//�ж��Ƿ����㹻�ı����ռ�
					sender.sendMessage(LangConfigLoader.getStringWithPrefix("KIT_GIVE_FAILED",ChatColor.RED));
					return;
				}
				pinv.addItem(kit.getKitItem());
				break;
			default:
				if(!WKTool.hasSpace(player, kit)) {//�ж��Ƿ����㹻�ı����ռ�
					sender.sendMessage(LangConfigLoader.getStringWithPrefix("KIT_GIVE_FAILED",ChatColor.RED));
					return;
				}
				for(ItemStack item : getItemList) {
					ItemStack i = item;
					pinv.addItem(i);//�����Ʒ������
				}
		}
		
		sender.sendMessage(LangConfigLoader.getStringWithPrefix("KIT_GIVE_SUCCESS",ChatColor.GREEN));//������ͳɹ�
	}
}
