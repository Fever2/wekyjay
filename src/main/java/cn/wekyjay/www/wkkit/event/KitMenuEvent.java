package cn.wekyjay.www.wkkit.event;

import static org.bukkit.event.inventory.InventoryAction.NOTHING;
import static org.bukkit.event.inventory.InventoryAction.UNKNOWN;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import cn.wekyjay.www.wkkit.WkKit;
import cn.wekyjay.www.wkkit.command.KitInfo;
import cn.wekyjay.www.wkkit.invholder.MenuHolder;
import cn.wekyjay.www.wkkit.kit.Kit;
import cn.wekyjay.www.wkkit.kit.KitGetter;
import cn.wekyjay.www.wkkit.menu.MenuOpenner;
import de.tr7zw.nbtapi.NBTItem;


public class KitMenuEvent implements Listener{

	public static List<String> menutitles = new ArrayList<>();
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerClick(InventoryClickEvent e) {

		if(e.getInventory().getHolder() instanceof MenuHolder) {
			e.setCancelled(true);// ȡ����Ʒ����ȡ
			if(e.getAction().equals(NOTHING) || e.getAction().equals(UNKNOWN)) {
				e.setCancelled(true);
		        return;
			}
			
			Player p = Bukkit.getPlayer(e.getWhoClicked().getName());
			//�������ǿո��Ӿ�ȡ���¼�
			try {
				new NBTItem(e.getCurrentItem());
			}catch(NullPointerException e1) {
				return;
			}
			
			NBTItem itemnbt = new NBTItem(e.getCurrentItem());//��ȡ������������NBT
			// �������key���ǿ��Խ��������ȡ����
			if(itemnbt.hasKey("wkkit")) {
				String kitname = itemnbt.getString("wkkit");
				// ������Ҽ���Ԥ�����
				if(e.getClick().isRightClick()) {
					new KitInfo().getKitInfo(kitname, p);
					return;
				}else {
				// ����������ļ�����ȡ���
					new KitGetter().getKit(Kit.getKit(kitname), p);
				}
			}else {
				return;
			}
			
			//��ȡ��Ʒ���Ƿ�ر�GUI
			if(WkKit.getWkKit().getConfig().getBoolean("GUI.ClickClose")) {
				p.closeInventory();
			}else {
				MenuHolder menuholder = (MenuHolder) e.getInventory().getHolder();
				p.closeInventory();
				new MenuOpenner().openMenu(menuholder.getMenuname(), p);
			}
		}
		
	}
}
