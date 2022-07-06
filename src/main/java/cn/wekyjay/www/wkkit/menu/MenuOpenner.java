package cn.wekyjay.www.wkkit.menu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import cn.wekyjay.www.wkkit.WkKit;
import cn.wekyjay.www.wkkit.config.LangConfigLoader;
import cn.wekyjay.www.wkkit.config.MenuConfigLoader;
import cn.wekyjay.www.wkkit.data.playerdata.PlayerData;
import cn.wekyjay.www.wkkit.invholder.MenuHolder;
import cn.wekyjay.www.wkkit.kit.Kit;
import cn.wekyjay.www.wkkit.tool.CronManager;
import cn.wekyjay.www.wkkit.tool.WKTool;
import de.tr7zw.nbtapi.NBTItem;
import me.clip.placeholderapi.PlaceholderAPI;


public class MenuOpenner {
	public void openMenu(String menuname,Player p) {
		if(!(MenuManager.getPermission(menuname) == null) && !p.hasPermission(MenuManager.getPermission(menuname))) {// ȱ��Ȩ��
			System.out.println(LangConfigLoader.getStringWithPrefix("MENU_NEED_PERMISSION", ChatColor.RED) + MenuManager.getPermission(menuname));
			return;
		} 
		String playername = p.getName();
		Inventory inv;
		if(MenuManager.getType(menuname).equals(InventoryType.CHEST)){
			inv = Bukkit.createInventory(new MenuHolder(menuname), MenuManager.getInvs().get(menuname).getSize(), MenuManager.getTitle(menuname));
		}else {
			inv = Bukkit.createInventory(new MenuHolder(menuname), MenuManager.getType(menuname), MenuManager.getTitle(menuname));
		}
		inv.setContents(MenuManager.getMenu(menuname).getStorageContents());
		List<String> kitlist = MenuManager.getSlotOfKit(menuname);
		
		// չ�����Ͳ˵�
		if(MenuConfigLoader.contains(menuname + ".Spread") && MenuConfigLoader.getBoolean(menuname + ".Spread") && kitlist.size() == 1) {
			// ��ȡ��ť
			if(MenuConfigLoader.contains(menuname + ".Slots.Get")){
				String id = MenuConfigLoader.getString(menuname + ".Slots.Get.id");

				String kitname = kitlist.get(0);
				Kit kit = Kit.getKit(kitname);
				// �ж��Ƿ����
				if(WkKit.getPlayerData().contain_Kit(playername, kitname)) {
					// �ж��Ƿ������ȡ
					if(WkKit.getPlayerData().getKitData(playername, kitname).equalsIgnoreCase("false") || WkKit.getPlayerData().getKitTime(playername, kitname) != null && WkKit.getPlayerData().getKitTime(playername, kitname) == 0) {
						for(int num : WKTool.getSlotNum(menuname + ".Slots.Get.slot")) {
							ItemStack item = new ItemStack(Material.BARRIER);
							// �����Զ���ͼ��
							if(MenuConfigLoader.contains(menuname + ".Slots." + kitname + ".offid")) {
								item = new ItemStack(Material.getMaterial(MenuConfigLoader.getString(menuname + ".Slots." + kitname + ".offid")));
							}
							ItemMeta meta = item.getItemMeta();
							
							List<String> list = new ArrayList<String>();
							// �����Զ���lore
							if(MenuConfigLoader.contains(menuname + ".Slots." + kitname + ".offlore")) {
								list = PlaceholderAPI.setPlaceholders(p, MenuConfigLoader.getStringList(menuname + ".Slots." + kitname + ".offlore"));
							}else {// û�����þ�Ĭ��
								list.add(LangConfigLoader.getString("CLICK_GET_NEXT_STATUS"));
								if(kit.getDocron() != null) list.add(LangConfigLoader.getString("CLICK_GET_NEXT_DATE") + "��e" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(CronManager.getNextExecution(kit.getDocron())));
								else list.add(LangConfigLoader.getString("CLICK_GET_NEXT_NODATE"));
								if(kit.getTimes() != null) list.add(LangConfigLoader.getString("CLICK_GET_NEXT_TIMES") + "��e" + WkKit.getPlayerData().getKitTime(playername, kitname));
								else list.add(LangConfigLoader.getString("CLICK_GET_NEXT_NOTIMES"));
							}
							meta.setLore(list);
							meta.setDisplayName(kit.getDisplayName());
							item.setItemMeta(meta);
							inv.setItem(num, item);
						}
					}else {
						ItemStack is = new ItemStack(Material.getMaterial(id));
						List<Integer> slotnum = WKTool.getSlotNum(menuname + ".Slots.Get.slot");
						for(int num : slotnum) {
							// �����Զ���lore
							if(MenuConfigLoader.contains(menuname + ".Slots." + kitname + ".lore")) {
								ItemMeta meta = is.getItemMeta();
								List<String> list = new ArrayList<String>();
								list = PlaceholderAPI.setPlaceholders(p, MenuConfigLoader.getStringList(menuname + ".Slots." + kitname + ".lore"));
								meta.setLore(list);
								meta.setDisplayName(kit.getDisplayName());
								is.setItemMeta(meta);
							}
							// ����NBT
							NBTItem nbti = new NBTItem(is);
							nbti.setString("wkkit", kitname);
							inv.setItem(num, nbti.getItem());
						}
					}
				}else {
					ItemStack is = new ItemStack(Material.getMaterial(id));
					List<Integer> slotnum = WKTool.getSlotNum(menuname + ".Slots.Get.slot");
					for(int num : slotnum) {
						// �����Զ���lore
						if(MenuConfigLoader.contains(menuname + ".Slots." + kitname + ".lore")) {
							ItemMeta meta = is.getItemMeta();
							List<String> list = new ArrayList<String>();
							list = PlaceholderAPI.setPlaceholders(p, MenuConfigLoader.getStringList(menuname + ".Slots." + kitname + ".lore"));
							meta.setLore(list);
							meta.setDisplayName(kit.getDisplayName());
							is.setItemMeta(meta);
						}
						// ����NBT
						NBTItem nbti = new NBTItem(is);
						nbti.setString("wkkit", kitname);
						inv.setItem(num, nbti.getItem());
					}
				}
				
			}
			// �������
			for(String kitname : kitlist) {
				Kit kit = Kit.getKit(kitname);
				int itemnum = (int) Stream.of(kit.getItemStack()).filter(item -> item != null).count();
				int nounnum = (int) Stream.of(inv.getContents()).filter(item -> item == null).count();
				// �Ƚϴ�С��������㹻�ռ�
				if(itemnum <= nounnum) {
					List<Integer> slotsIndex = new ArrayList<>();
					// ������Ʒ
					for(int i = 0; i < inv.getContents().length; i++) {
						if(inv.getItem(i) == null) slotsIndex.add(i);
					}
					// �����Ʒ
					for(int i = 0; i < kit.getItemStack().length; i++) {
						inv.setItem(slotsIndex.get(i), kit.getItemStack()[i]);
					}
				}
			}

			// �򿪲˵�
			p.openInventory(inv);
			return;
		}
		
		// ��ͨ���Ͳ˵�
		// �������
		for(String kitname : kitlist) {
			if(WkKit.getPlayerData().contain_Kit(playername, kitname)) {
				// ���������ȡ
				if(WkKit.getPlayerData().getKitData(playername, kitname).equalsIgnoreCase("false") || WkKit.getPlayerData().getKitTime(playername, kitname) != null && WkKit.getPlayerData().getKitTime(playername, kitname) == 0) {
						for(int num : WKTool.getSlotNum(menuname + ".Slots." + kitname + ".slot")) {
							ItemStack item = new ItemStack(Material.BARRIER);
							// �����Զ���ͼ��
							if(MenuConfigLoader.contains(menuname + ".Slots." + kitname + ".offid")) {
								item = new ItemStack(Material.getMaterial(MenuConfigLoader.getString(menuname + ".Slots." + kitname + ".offid")));
							}
							ItemMeta meta = item.getItemMeta();
							Kit kit = Kit.getKit(kitname);
							List<String> list = new ArrayList<String>();
							// �����Զ���lore
							if(MenuConfigLoader.contains(menuname + ".Slots." + kitname + ".offlore")) {
								list = PlaceholderAPI.setPlaceholders(p, MenuConfigLoader.getStringList(menuname + ".Slots." + kitname + ".offlore"));
							}else {// û�����þ�Ĭ��
								list.add(LangConfigLoader.getString("CLICK_GET_NEXT_STATUS"));
								if(kit.getDocron() != null) list.add(LangConfigLoader.getString("CLICK_GET_NEXT_DATE") + "��e" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(CronManager.getNextExecution(kit.getDocron())));
								else list.add(LangConfigLoader.getString("CLICK_GET_NEXT_NODATE"));
								if(kit.getTimes() != null) list.add(LangConfigLoader.getString("CLICK_GET_NEXT_TIMES") + "��e" + WkKit.getPlayerData().getKitTime(playername, kitname));
								else list.add(LangConfigLoader.getString("CLICK_GET_NEXT_NOTIMES"));
							}
							
							meta.setLore(list);
							meta.setDisplayName(kit.getDisplayName());
							item.setItemMeta(meta);
							NBTItem nbti = new NBTItem(item);
							nbti.removeKey("wkkit");
							inv.setItem(num, nbti.getItem());
						}
				}else {
					ItemStack is = Kit.getKit(kitname).getKitItem();
					List<Integer> slotnum = WKTool.getSlotNum(menuname + ".Slots." + kitname + ".slot");
					for(int num : slotnum) {
						// �����Զ���lore
						if(MenuConfigLoader.contains(menuname + ".Slots." + kitname + ".lore")) {
							ItemMeta meta = is.getItemMeta();
							List<String> list = new ArrayList<String>();
							list = PlaceholderAPI.setPlaceholders(p, MenuConfigLoader.getStringList(menuname + ".Slots." + kitname + ".lore"));
							meta.setLore(list);
							is.setItemMeta(meta);
						}
						inv.setItem(num, is);
					}
				}
			}else {
				ItemStack is = Kit.getKit(kitname).getKitItem();
				List<Integer> slotnum = WKTool.getSlotNum(menuname + ".Slots." + kitname + ".slot");
				for(int num : slotnum) {
					// �����Զ���lore
					if(MenuConfigLoader.contains(menuname + ".Slots." + kitname + ".lore")) {
						ItemMeta meta = is.getItemMeta();
						List<String> list = new ArrayList<String>();
						list = PlaceholderAPI.setPlaceholders(p, MenuConfigLoader.getStringList(menuname + ".Slots." + kitname + ".lore"));
						meta.setLore(list);
						is.setItemMeta(meta);
					}
					inv.setItem(num, is);
				}
			}
		}
		p.openInventory(inv);
		
	}
}
