package cn.wekyjay.www.wkkit.event;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import cn.wekyjay.www.wkkit.config.LangConfigLoader;
import cn.wekyjay.www.wkkit.kit.Kit;
import cn.wekyjay.www.wkkit.tool.CountDelayTime;
import cn.wekyjay.www.wkkit.tool.WKTool;
import de.tr7zw.nbtapi.NBTItem;
public class DropKitEvent implements Listener{

	@EventHandler
	public void onPlayerClickKit(PlayerInteractEvent e) {
		if(!e.hasItem()) {return;}//����û�ж����ͽ�������
		NBTItem nbti;
		try {nbti = new NBTItem(e.getItem());}catch(NullPointerException e2) {return;}// ��������ƷΪNULL��ȡ��
		if(WKTool.getVersion() > 9 && !e.getHand().equals(EquipmentSlot.HAND)) {// ����Ǹ߰汾���Ǹ��ֵĻ��ͷ���
			return;
		}
		if(nbti.hasKey("wkkit") ) {
			String kitname = nbti.getString("wkkit");
				if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
					PlayerInventory pinv = e.getPlayer().getInventory();
					Kit kit = Kit.getKit(kitname);
					// �ж��Ƿ���Ȩ��
					if(kit.getPermission() != null && !e.getPlayer().hasPermission(kit.getPermission())) {
						e.getPlayer().sendMessage(LangConfigLoader.getStringWithPrefix("NO_PERMISSION", ChatColor.RED));
						return;
					}
					// �ж��Ƿ�����ȴ
					if(kit.getDelay() != null) {
						if(CountDelayTime.getDelayInstance(e.getPlayer(), kit) == null) {
							CountDelayTime ct = new CountDelayTime(e.getPlayer(), kit);
							ct.isGet();
						}else {
							CountDelayTime ct = CountDelayTime.getDelayInstance(e.getPlayer(), kit);
							if(!ct.isGet()) {
								ct.whenGet();
								return;
							}
						}
					}
					
					if(WKTool.hasSpace(e.getPlayer(), Kit.getKit(kitname))) {
						int itemnum;
						if(WKTool.getVersion() >= 9) {
							itemnum = e.getPlayer().getInventory().getItemInMainHand().getAmount();//��ȡ������Ʒ����
							e.getPlayer().getInventory().getItemInMainHand().setAmount(itemnum - 1);//������Ʒ����-1
						}else {
							itemnum = e.getPlayer().getInventory().getItemInHand().getAmount();//��ȡ������Ʒ����
							e.getPlayer().getInventory().getItemInHand().setAmount(itemnum - 1);//������Ʒ����-1
							if(itemnum - 1 == 0) {
								e.getPlayer().getInventory().remove(e.getPlayer().getInventory().getItemInHand());
							}
						}

						//�ж��Ƿ���Commands
						if(kit.getCommands() != null){
							List<String> cmdlist = kit.getCommands();
							for(String str : cmdlist) {
								String[] splitstr = str.split(":");
								String command = null;
								if(splitstr.length > 1) {//�ж��Ƿ���ָ����ָ��ͷ�ʽ
									command = WKTool.replacePlaceholder("player", e.getPlayer().getName(), splitstr[1]);
								}else {
									command = WKTool.replacePlaceholder("player", e.getPlayer().getName(), splitstr[0]);
								}
								//���ݲ�ͬ��ָ��ͷ�ʽ����
								if(splitstr[0].equalsIgnoreCase("cmd")) {
									Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
								}else if(splitstr[0].equalsIgnoreCase("op") && !e.getPlayer().isOp()) {
									e.getPlayer().setOp(true);
									Bukkit.dispatchCommand(e.getPlayer(), command);
									e.getPlayer().setOp(false);
								}else {
									e.getPlayer().performCommand(command);
								}
							}
						}
						//������
						for(ItemStack item : kit.getItemStack()) {
							if(item != null) {
								pinv.addItem(item);//�����Ʒ������
							}

						}
						e.getPlayer().sendMessage(LangConfigLoader.getStringWithPrefix("KIT_GET_SUCCESS",ChatColor.GREEN));
					}else {
						e.getPlayer().sendMessage(LangConfigLoader.getStringWithPrefix("KIT_GET_FAILED",ChatColor.YELLOW));
					}
					
				}

		}
		return;
	}
	//����Ƿ�����������򲻿��Է���
	@EventHandler
	public void blockPlace(BlockPlaceEvent e) {
		NBTItem nbti = new NBTItem(e.getItemInHand());
		if(nbti.hasKey("wkkit")) {
			e.setCancelled(true);
		}
	}
	
	//���������ʵ���Ƿ��ǿɵ���ָ�������ʵ�壬�ǵĻ��͵���ָ�����
	@EventHandler
	public void ontest(EntityDeathEvent e) {
		List<Kit> kits = Kit.getKits();
		
		Iterator<Kit> it = kits.iterator();
		while(it.hasNext()) {
			Kit kit = it.next();
			if(kit.getDrop() != null) {
				List<String> droplist = kit.getDrop();
				for(int i = 0; i < droplist.size(); i++) {
					String[] s = droplist.get(i).split("->");
					String ename = s[0];
					float f = Float.parseFloat(s[1]);
					double d = Math.random();
					if(e.getEntity().getName().equals(ename) && d <= f) {
						e.getEntity().getWorld().dropItem(e.getEntity().getLocation(),kit.getKitItem());
					}
				}
			}
		}
		return;
	}
}
