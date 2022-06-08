package cn.wekyjay.www.wkkit.command;

import java.util.Iterator;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import cn.wekyjay.www.wkkit.WkKit;
import cn.wekyjay.www.wkkit.config.LangConfigLoader;

public class KitTransfer {
	public void onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 2 && WkKit.wkkit.getConfig().getString("MySQL.Enable").equalsIgnoreCase("true")) {
			// YAMLתMYSQL
			if(args[0].equalsIgnoreCase("transfer") && args[1].equalsIgnoreCase("mysql")) {
				Set<String> playerlist = WkKit.playerConfig.getKeys(false);
				Iterator<String> it = playerlist.iterator();// �����������
				while(it.hasNext()) {
					String playername = it.next().toString();
					ConfigurationSection cs = WkKit.playerConfig.getConfigurationSection(playername);
					for(String kitname : cs.getKeys(false)) {
						String data = cs.getString(kitname + ".data");
						int time = cs.getInt(playername + ".time");
						// ��������͸�������
						if(WkKit.getPlayerData().contain_Kit(playername, kitname)) {
							WkKit.getPlayerData().setKitTime(playername, kitname, time);
							WkKit.getPlayerData().setKitData(playername, kitname, data);
						}else {//�����ھ��������
							WkKit.getPlayerData().setKitToFile(playername, kitname, data, time);
						}
					}
				}
				while(it.hasNext()) {
					String playername = it.next().toString();
					ConfigurationSection cs = WkKit.playerConfig.getConfigurationSection(playername);
					for(String kitname : cs.getKeys(false)) {
						int num = cs.getInt(kitname);
						// ��������͸�������
						if(WkKit.getPlayerData().contain_Mail(playername, kitname)) {
							WkKit.getPlayerData().setMailNum(playername, kitname, num);
						}else {//�����ھ��������
							WkKit.getPlayerData().setMailToFile(playername, kitname, num);
						}
					}
				}
				sender.sendMessage(LangConfigLoader.getStringWithPrefix("KIT_TRANSFER_SUCCESS", ChatColor.GREEN));
			}
		}else if(!WkKit.wkkit.getConfig().getString("MySQL.Enable").equalsIgnoreCase("true")) {
			sender.sendMessage(LangConfigLoader.getStringWithPrefix("MYSQL_NOENABLE", ChatColor.RED));
		}
	}
}
