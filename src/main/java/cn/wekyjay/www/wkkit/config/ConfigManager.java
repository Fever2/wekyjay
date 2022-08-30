package cn.wekyjay.www.wkkit.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitTask;

import cn.wekyjay.www.wkkit.WkKit;
import cn.wekyjay.www.wkkit.data.playerdata.PlayerData_MySQL;
import cn.wekyjay.www.wkkit.kit.KitGroupManager;
import cn.wekyjay.www.wkkit.menu.MenuManager;
import cn.wekyjay.www.wkkit.mysql.MySQLManager;
import cn.wekyjay.www.wkkit.tool.KitRefresh;

public class ConfigManager {
	public static Map<String,BukkitTask> tasklist = new HashMap<>();
	private static KitConfigLoader kitconfig = null;
	private static KitGroupManager kitGroupManager = null;
	
	// ����������
	public static KitConfigLoader getKitconfig() {
		return kitconfig == null ? kitconfig = new KitConfigLoader() : kitconfig;
	}
	
	// ��ò˵�����
	public static KitGroupManager getKitGroupManager() {
		return kitGroupManager;
	}
	
	public static void loadConfig() {
		getKitconfig().loadConfig();
		kitGroupManager = new KitGroupManager();
		MenuConfigLoader.loadConfig();
		
	}
	
	public static void reloadPlugin() {
		if(WkKit.getPlayerData() instanceof PlayerData_MySQL) MySQLManager.get().shutdown();
		WkKit.getWkKit().reloadConfig(); // ��������
		if(WkKit.getWkKit().getConfig().getBoolean("MySQL.Enable") == true) MySQLManager.get().enableMySQL();
		WkKit.playerConfig = YamlConfiguration.loadConfiguration(WkKit.playerConfigFile);
		WkKit.playerMailConfig = YamlConfiguration.loadConfiguration(WkKit.playerMailConfigFile);
		LangConfigLoader.reloadConfig();
		ConfigManager.reloadKit();
		ConfigManager.reloadMenu();
    	WkKit.getWkKit().saveConfig();
    	WkKit.getWkKit().enableAntiShutDown(); //��������¼�߳�����
	}
	
	/**
	 * ���ز˵�����
	 */
	public static void reloadMenu() {
		MenuManager.getInvs().clear();
		MenuManager.getMenus().clear();
		MenuConfigLoader.filelist.clear();
		MenuConfigLoader.map.clear();
		MenuConfigLoader.loadConfig();
	}
	/**
	 * �����������
	 */
	public static void reloadKit() {
		// ��������ʱ��
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	WkKit.getWkKit().getConfig().set("Default.ShutDate", sdf.format(new Date()));
    	kitconfig = new KitConfigLoader(); // ��������
    	getKitconfig().loadConfig(); // ��������
		// �ر�������ˢ���߳�
		for(String kitname : tasklist.keySet()) {
			tasklist.get(kitname).cancel();
		}
		// �������߳�
		new KitRefresh().enable();
	}

}
