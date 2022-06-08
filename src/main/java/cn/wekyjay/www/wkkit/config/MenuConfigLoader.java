package cn.wekyjay.www.wkkit.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import cn.wekyjay.www.wkkit.WkKit;
import cn.wekyjay.www.wkkit.menu.Menu;
import cn.wekyjay.www.wkkit.tool.WKTool;

public class MenuConfigLoader{
	public static List<File> filelist = new ArrayList<>();
	public static Map<String, FileConfiguration> map = new HashMap<>();
	
	/**
	 * �����ļ����е������ļ�
	 * @param file
	 */
	public static void loadConfig() {
		String path = WkKit.getWkKit().getDataFolder().getAbsolutePath() + File.separator + "Menus";
		File[] ff = new File(path).listFiles();
		 for(File fs : ff) {
			 if(fs.isFile() && fs.getName().endsWith(".yml")) {
				 filelist.add(fs);
				 map.put(fs.getName(),YamlConfiguration.loadConfiguration(fs));
			 }
		 }
		MenuConfigLoader.readMenuConfig();//��ȡ�����еĲ˵�
	}
	/**
	 * ���ش��ڸ�·�����ļ���
	 * @param path
	 * @return
	 */
	public static String getContainsFilename(String path) {
		for(File fl : filelist) {
			if(map.get(fl.getName()).contains(path)) {
				return fl.getName();
			}
		}
		return null;
	}
	
	
	/**
	 * �ж��Ƿ���ڸ�·��
	 * @param path
	 * @return
	 */
	public static Boolean contains(String path) {
		for(File fl : filelist) {
			if(map.get(fl.getName()).contains(path)) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * �ļ������ض���
	 * @param filename
	 * @return
	 */
	public static FileConfiguration getConfig(String filename) {
		return map.get(filename);
	}
	/**
	 * ·�����ض���
	 * @param filename
	 * @return
	 */
	public static FileConfiguration getConfigWithPath(String path) {
		String filename = getContainsFilename(path);
		return map.get(filename);
	}
	
	/**
	 * ����ļ��еĴ��ڵ������
	 * @return List<String>
	 */
	public static List<String> getMenus() {
		List<String> list = new ArrayList<>();
		for(File fl : filelist) {
			Set<String> set = map.get(fl.getName()).getKeys(false);
			for(String key : set) {
				if(!list.contains(key)) {
					list.add(key);
				}
			}
		}
		return list;
	}
	
	/**
	 * �ж��Ƿ�����ظ��Ĳ˵������ҷ���ֵ��
	 * @return ��������
	 */
	public static int checkRepeatMenus() {
		List<String> list = new ArrayList<>();
		int num = 0;
		for(File fl : filelist) {
			Set<String> set = map.get(fl.getName()).getKeys(false);
			for(String key : set) {
				if(!list.contains(key)) {
					list.add(key);
				}else {
					Bukkit.getLogger().warning("[WkKit]�����ظ��Ĳ˵� - " + key);
					num++;
				}
			}
		}
		return num;
	}
	
	
	// ��ȡ�����еĲ˵�����
	public static void readMenuConfig() {
		List<String> menulist = MenuConfigLoader.getMenus();
		int repeatnum = MenuConfigLoader.checkRepeatMenus();
		//�����ظ������
		if(repeatnum != 0) {
			WKTool.replacePlaceholder("num", repeatnum + "", LangConfigLoader.getString("LOADMENU_FAILED"));
			Bukkit.getLogger().warning("[WkKit]" + repeatnum + "���˵�����ʧ�ܣ�");
		}
		// ��Ӳ˵���Ϣ
		for(String menuname : menulist) {
			String title = MenuConfigLoader.getString(menuname + ".Title");
			String type = MenuConfigLoader.getString(menuname + ".Type");
			String permission = MenuConfigLoader.getString(menuname + ".Permission");
			int size = MenuConfigLoader.getInt(menuname + ".Size");
			Set<String> slots = MenuConfigLoader.getConfigurationSection(menuname + ".Slots").getKeys(false);
			new Menu(menuname, title, type, permission, size, new ArrayList<>(slots));
		}
	}
	// ��װBukkitAPI�ķ���
	public static ConfigurationSection getConfigurationSection(String path) {
		return getConfigWithPath(path).getConfigurationSection(path);
	}
	public static String getString(String path) {
		if(MenuConfigLoader.contains(path)) {
			return getConfigWithPath(path).getString(path);
		}
		return null;
	}
	public static List<String> getStringList(String path) {
		return getConfigWithPath(path).getStringList(path);
		
	}
	public static Integer getInt(String path) {
		if(getConfigWithPath(path).contains(path)) {
			return getConfigWithPath(path).getInt(path);
		}
		return null;
		
	}
	public static long getLong(String path) {
		return getConfigWithPath(path).getLong(path);
		
	}
	public static Boolean getBoolean(String path) {
		return getConfigWithPath(path).getBoolean(path);
		
	}
	public static void set(String path,Object value) {
		getConfigWithPath(path).set(path, value);
	}
	public static void save(String filename) throws IOException {
		String path = WkKit.getWkKit().getDataFolder().getAbsolutePath() + File.separator + "Menus"+ File.separator + filename;
		getConfig(filename).save(new File(path));
	}
}
