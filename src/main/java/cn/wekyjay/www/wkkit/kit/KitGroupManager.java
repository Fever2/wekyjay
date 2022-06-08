package cn.wekyjay.www.wkkit.kit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import cn.wekyjay.www.wkkit.WkKit;
import cn.wekyjay.www.wkkit.config.ConfigManager;

public class KitGroupManager{
	public KitGroupManager() {}
	public KitGroupManager(String groupname) {
		File file = new File(WkKit.getWkKit().getDataFolder().getAbsolutePath() + File.separator + "Kits" + File.separator + groupname + ".yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
				ConfigManager.getKitconfig().getFileList().add(file);
				ConfigManager.getKitconfig().getFileConfigMap().put(file.getName(),YamlConfiguration.loadConfiguration(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ��ȡĿǰ���ڵ������
	 * @return
	 */
	public static List<String> getGroups() {
		int num = ConfigManager.getKitconfig().getFileList().size();
		List<String> groups = new ArrayList<>();
		for(int i = 0; i < num; i++) {
			groups.add(ConfigManager.getKitconfig().getFileList().get(i).getName().split(".yml")[0].toString());
		}
		return groups;
	}
	
	/**
	 * �ж��Ƿ���ڸ������ļ�
	 * @param GroupName
	 * @return
	 */
	public static Boolean contains(String GroupName) {
		String filename = GroupName + ".yml";
		return ConfigManager.getKitconfig().getFileConfigMap().containsKey(filename);
	}
	
	/**
	 * ��������������ļ�
	 * @param GroupName
	 * @return FileConfiguration
	 */
	public static FileConfiguration getGroup(String GroupName) {
		return ConfigManager.getKitconfig().getFileConfigMap().get(GroupName + ".yml");
	}
	
	/**
	 * ��ȡһ�������������������
	 * @param GroupName
	 * @return
	 */
	public static List<String> getGroupKits(String GroupName) {
		List<String> list = new ArrayList<String>();
		for(String kitname : ConfigManager.getKitconfig().getFileConfigMap().get(GroupName + ".yml").getKeys(false)) {
			if(Kit.getKit(kitname) != null) list.add(kitname);
		}
		return list;
	}
	
	/**
	 * ת�����������һ����
	 * @param kitname
	 * @param GroupName
	 */
	public static void toGroup(Kit kit,String GroupName) {
		String kitname = kit.getKitname();
		ConfigurationSection cs =ConfigManager.getKitconfig().getConfigWithPath(kitname).getConfigurationSection(kitname);
		Map<String, Object> m = cs.getValues(true);
		String filename = ConfigManager.getKitconfig().getContainsFilename(kitname);
		cs.getParent().set(kitname, null);
		try {
			ConfigManager.getKitconfig().save(filename);
			ConfigManager.getKitconfig().getFileConfigMap().get(GroupName + ".yml").createSection(kitname, m);
			ConfigManager.getKitconfig().save(GroupName + ".yml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * �����������ȡ���ڵ������
	 * @param kitname
	 * @return
	 */
	public static String getContainName(Kit kit) {
		List<String> list = getGroups();
		for(String groupname : list) {
			if(getGroupKits(groupname).contains(kit.getKitname())) {
				return groupname;
			}
		}
		return null;
		
	}
}
