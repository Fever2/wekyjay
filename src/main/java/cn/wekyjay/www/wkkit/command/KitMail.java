package cn.wekyjay.www.wkkit.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import cn.wekyjay.www.wkkit.WkKit;
import cn.wekyjay.www.wkkit.config.LangConfigLoader;
import cn.wekyjay.www.wkkit.invholder.MailHolder;
import cn.wekyjay.www.wkkit.kit.Kit;
import cn.wekyjay.www.wkkit.tool.WKTool;
import cn.wekyjay.www.wkkit.tool.items.GlassPane;



public class KitMail{

	static WkKit wk = WkKit.getWkKit();// ��������ʵ��	

	public void onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player) sender;
		openKitMail(p,1);
	}
	
	
	
	/**
	 * Ϊ��Ҵ��������
	 * @param p
	 */
	public void openKitMail(Player p, int page) {
		String guiname = LangConfigLoader.getString("KITMAIL_TITLE");
		List<ItemStack> litem = new ArrayList<>();// kit list
		List<Inventory> linv = new ArrayList<>(); // inv list
		String pname = p.getName();
		List<String> kits = WkKit.getPlayerData().getMailKits(pname);
		// ���û�оͳ�ʼ��ֵ
		if(kits == null || kits.size() == 0) {
			kits = new ArrayList<>();
		}
		
		//��Ч�������
		int i = kits.size();
		
		
		//�жϿɴ�����gui����
		int guinum = 0;
		if(i % 36 == 0 && !(i == 0)) {
			guinum = i / 36;
		}else {
			guinum = (i / 36) + 1;
		}
		
		//�����Ʒ��litem
			//������
			for(String kitname : kits) {
				ItemStack is = Kit.getKit(kitname).getKitItem();
				if(WkKit.getPlayerData().getMailKitNum(pname, kitname) > 1) {
					is.setAmount(WkKit.getPlayerData().getMailKitNum(pname, kitname));
				}
				litem.add(is);
			}
			
			//����gui��linv
			for(int i1 = 1; i1 <= guinum; i1++) {
				Inventory inv;
				if(guinum == 1) {//���ֻ��һҳ�Ͳ���ҳ��
					
					inv = Bukkit.createInventory(new MailHolder(), 6*9, guiname); 
				}else {
					String pagetitle = WKTool.replacePlaceholder("page", i1+"", LangConfigLoader.getString("GUI_PAGETITLE"));
					inv = Bukkit.createInventory(new MailHolder(), 6*9, guiname + " - " + pagetitle); 
				}

				
				//�����Ʒ��������
				ItemStack item_mn;
				if(wk.getConfig().getString("GUI.MenuMaterial").equalsIgnoreCase("Default")){
					item_mn = GlassPane.DEFAULT.getItemStack();
				}else {
					item_mn = new ItemStack(Material.getMaterial(wk.getConfig().getString("GUI.MenuMaterial")));
				}
				ItemMeta im = item_mn.getItemMeta();
				im.setDisplayName(LangConfigLoader.getString("DO_NOT_TOUCH"));
				item_mn.setItemMeta(im);
				//��ӹ�������Ʒ����һҳ
				ItemStack item_pre = new ItemStack(Material.getMaterial(wk.getConfig().getString("GUI.TurnPageMaterial")));
				ItemMeta ip = item_pre.getItemMeta();
				ip.setDisplayName(LangConfigLoader.getString("PREVIOUS_PAGE"));
				item_pre.setItemMeta(ip);
				//��ӹ�������Ʒ����һҳ
				ItemStack item_next = new ItemStack(Material.getMaterial(wk.getConfig().getString("GUI.TurnPageMaterial")));
				ItemMeta in = item_next.getItemMeta();
				in.setDisplayName(LangConfigLoader.getString("NEXT_PAGE"));
				item_next.setItemMeta(in);
				
				for(int i0 = 0; i0 < 9; i0++) {//����һ��
					inv.setItem(i0, item_mn);
				}
				for(int i11 = 54 - 9; i11 < 54; i11++) {//����һ��
					inv.setItem(i11, item_mn);
				}
				
				
				if(kits.size() >= 1) {
					ItemStack item_getall;
					if(wk.getConfig().getString("GUI.GetAllMaterial").equalsIgnoreCase("Default")) {
						item_getall = GlassPane.GREEN.getItemStack();
					}else {
						item_getall = new ItemStack(Material.getMaterial(wk.getConfig().getString("GUI.GetAllMaterial")));
					}

					ItemMeta img = item_next.getItemMeta();
					img.setDisplayName(LangConfigLoader.getString("KITMAIL_GETALL"));
					item_getall.setItemMeta(img);
					inv.setItem(52, item_getall);
				}
				
				//�жϵ�ǰҳ����ӹ��ܰ�ť
				if(i1 > 1 && i1 < guinum) {
					inv.setItem(48, item_pre);
					inv.setItem(55, item_next);
				}
				
				if(i1 == guinum && i1 != 1) {
					inv.setItem(48, item_pre);
				}
				
				if(guinum > 1 && i1 == 1) {
					inv.setItem(50, item_next);
				}

				linv.add(inv);
			}
			

			//�����Ʒ��ָ����inv
			int itemnum = 0;
			for(int invnum = 0; invnum < guinum; invnum++) {
				for(int i2 = 9; i2 < 45; i2++) {
					if(itemnum >= litem.size() || litem.size() == 0 ) {
						break;
					}
					linv.get(invnum).setItem(i2, litem.get(itemnum));
					itemnum += 1;
				}
			}
			

			
			p.openInventory(linv.get(page -1));
		}
}
