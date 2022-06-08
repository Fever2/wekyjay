package cn.wekyjay.www.wkkit.event;

import org.bukkit.Statistic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import cn.wekyjay.www.wkkit.WkKit;
import cn.wekyjay.www.wkkit.kit.Kit;

public class NewComerEvent implements Listener {
	WkKit wk = WkKit.getWkKit();
	
	/*��ҵ�һ�ν����¼�*/
	@EventHandler
	public void onPlayerJion(PlayerJoinEvent e) {
	    String pname = e.getPlayer().getName();
	    boolean isnc = this.wk.getConfig().getBoolean("NewComer.Enable");//��������Ƿ���
	    boolean isauto = this.wk.getConfig().getBoolean("NewComer.Auto"); // �Ƿ��Զ�����
	    String nckitname = this.wk.getConfig().getString("NewComer.Kit");//���������
	    if (isnc && Kit.getKit(nckitname) != null && e.getPlayer().getStatistic(Statistic.LEAVE_GAME) == 0) {
	    	if(WkKit.getPlayerData().contain_Kit(pname, nckitname))return;
	    	if(isauto) {
	    		e.getPlayer().getInventory().addItem(Kit.getKit(nckitname).getKitItem());
		    	WkKit.getPlayerData().setKitToFile(pname, nckitname, "false", 0);
	    	}else {
		    	WkKit.getPlayerData().setKitToFile(pname, nckitname, "true", 1);
	    	}

	    } 
	    
	  }
}
