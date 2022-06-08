package cn.wekyjay.www.wkkit.tool;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import cn.wekyjay.www.wkkit.WkKit;
import cn.wekyjay.www.wkkit.config.ConfigManager;
import cn.wekyjay.www.wkkit.kit.Kit;

public class KitRefresh {
		
	/**
	 * ��ʱ��ֵתΪ����
	 * @param value
	 * @return
	 */
	public long changeTomilli(String value,Calendar cal) {
		String[] part = value.split("(?<=\\d)(?=\\D)");
		int num = Integer.parseInt(part[0]);
		switch(part[1]) {
			case "d": cal.add(Calendar.DATE, num);break;
			case "w": cal.add(Calendar.WEEK_OF_MONTH, num);break;
			case "m": cal.add(Calendar.MONTH, num);break;
			case "y": cal.add(Calendar.YEAR, num);break;
		}
		return num;
	}
	
	/**
	 * ����ˢ��һ�����
	 * @param kitname
	 */
	public static void refreshNow(Kit kit) {
		String kitname = kit.getKitname();
		OfflinePlayer[] playerlist = Bukkit.getOfflinePlayers();
		for(OfflinePlayer player : playerlist) {
			String playername = player.getName();
			// ��������ݵľ�ˢ����ȡ״̬
			if(WkKit.getPlayerData().contain_Kit(playername, kitname)) {
				WkKit.getPlayerData().setKitData(playername, kitname, "true");
			}
		}
	}
	/**
	 * �����Զ�ˢ���߳�
	 * @param kit
	 */
	public static void refreshDay(Kit kit) {
		if(kit.getDocron() != null) {
			String kitname = kit.getKitname();
			WkKit.refreshCount++;
			String cron = kit.getDocron();
			Calendar cnext = Calendar.getInstance();//��ʼ��ʱ��
			cnext.setTime(CronManager.getNextExecution(cron)); // ��ʼ���´�ִ�е�ʱ��
			// ��������ڲ���
			ConfigManager.tasklist.put(kitname, 
				new BukkitRunnable() {
					@Override
					public void run() {
						Calendar cnow = Calendar.getInstance();//��ҵ�ǰʱ��
						// ʱ�䵽ִ��
						if(cnow.getTimeInMillis() >= cnext.getTimeInMillis()) {
							OfflinePlayer[] playerlist = Bukkit.getOfflinePlayers();
							for(OfflinePlayer player : playerlist) {
								if(player.getName() == null) continue; // �����ȡ�������������ȡ������ҵ�ˢ��
								String playername = player.getName();
								// ��������ݵľ�ˢ����ȡ״̬
								if(WkKit.getPlayerData().contain_Kit(playername, kitname)) {
									WkKit.getPlayerData().setKitData(playername, kitname, "true");
								}
							}
							cnext.setTime(CronManager.getNextExecution(cron)); // �����´�ִ�е�ʱ�䣨��ǰʱ�䣩
						}
					}
					
				}.runTaskTimerAsynchronously(WkKit.getWkKit(), 20, 20)
			);
		}
	}
	
	public void enable() {
		// it.next������ͬһ��ѭ���ڳ������Σ��ᵼ�����һ�ε��α�ָ���ֵ
		List<Kit> list = Kit.getKits();
		Iterator<Kit> it = list.iterator();
		while(it.hasNext()) {
			Kit kit = it.next();
			String shutdate = WkKit.getWkKit().getConfig().getString("Default.ShutDate");
			if(kit.getDocron() != null) {
				KitRefresh.refreshDay(kit);
				if(!shutdate.equalsIgnoreCase("None") && CronManager.isExecuted(shutdate,kit.getDocron())) {
					refreshNow(kit);
				}
			}
		}

	}
}
