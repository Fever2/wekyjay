package cn.wekyjay.www.wkkit.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cn.wekyjay.www.wkkit.WkKit;
import cn.wekyjay.www.wkkit.config.ConfigManager;
import cn.wekyjay.www.wkkit.config.LangConfigLoader;
import cn.wekyjay.www.wkkit.kit.Kit;

public class KitDelete {
	public void onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(args.length < 2) {
			sender.sendMessage(LangConfigLoader.getStringWithPrefix("Commands.delete", ChatColor.GREEN));
			return;
		}
		
		String kitname = args[1];
		String filename = ConfigManager.getKitconfig().getContainsFilename(kitname);
		
		if(Kit.getKit(kitname) != null) {//�ж��Ƿ���ڸ����
			// ����������ˢ���߳̾ͱ����ر�
			if(Kit.getKit(kitname).getDocron() != null) {
				for(String value : ConfigManager.tasklist.keySet()) {
					if(value.equalsIgnoreCase(kitname)) {
						ConfigManager.tasklist.get(value).cancel();
					}
				}
			}
			//ɾ�����
			ConfigManager.getKitconfig().set(kitname, null);
			ConfigManager.getKitconfig().getKitsList().remove(Kit.getKit(kitname));
			//ɾ����������е������¼
			OfflinePlayer[] s = Bukkit.getOfflinePlayers();
			for(OfflinePlayer offp : s) {
				String pname = offp.getName();
				if(WkKit.getPlayerData().contain_Kit(pname,kitname)) {
					WkKit.getPlayerData().delKitToFile(pname, kitname);
				}
				if(WkKit.getPlayerData().contain_Mail(pname,kitname)) {
					WkKit.getPlayerData().delMailToFile(pname, kitname);;
				}
			}

			//��������
			try {
				ConfigManager.getKitconfig().save(filename);
				sender.sendMessage(LangConfigLoader.getStringWithPrefix("KIT_DELETE_SUCCESS", ChatColor.GREEN));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}else {//���������ִ�еĴ���
			sender.sendMessage(LangConfigLoader.getStringWithPrefix("KIT_DELETE_NONEXIST", ChatColor.RED));
			return;
		}	
		
	}
}
