package cn.wekyjay.www.wkkit.tool;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import cn.wekyjay.www.wkkit.WkKit;
import cn.wekyjay.www.wkkit.handlerlist.PlayersReceiveKitEvent;

public class KitCache implements Listener{
	private static KitCache cache = null;
	private boolean isEnable = false;
	
	// 动态获取静态的Cache对象
	public static KitCache getCache() {
		return cache == null? cache = new KitCache() : cache;
	}
	
	public KitCache() {
		isEnable = WkKit.getWkKit().getConfig().getBoolean("Setting.Cache");
		if(isEnable) {
			WkKit.getWkKit().getLogger().info("启用日志管理.");
			Bukkit.getPluginManager().registerEvents(this, WkKit.getWkKit());
		}

	}
	
	@EventHandler
	public void onPlayerRececiveKit(PlayersReceiveKitEvent e) {
		WkKit.getWkKit().getLogger().info("玩家" + e.getPlayer().getName() + " 触发事件 " + e.getType().toString());
	}
}
