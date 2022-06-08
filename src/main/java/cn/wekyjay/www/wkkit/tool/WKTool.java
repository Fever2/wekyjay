package cn.wekyjay.www.wkkit.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import cn.wekyjay.www.wkkit.config.MenuConfigLoader;
import cn.wekyjay.www.wkkit.kit.Kit;
import cn.wekyjay.www.wkkit.tool.items.PlayerHead;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;

public class WKTool{

	/**
	 * ���ָ������Ʒ��ָ������ұ���
	 * @param e - ָ�������
	 * @param i - ָ������Ʒ
	 */
	public static void addItem(Player e, ItemStack i) {
		InventoryHolder invholder = (InventoryHolder) e;
		Inventory inv = invholder.getInventory();
		PlayerInventory playerinv = (PlayerInventory) inv;
		playerinv.addItem(i);
	}
	
	/**
	 * �ж��Ƿ����㹻�Ŀռ���ȡָ�������
	 * @param p
	 * @param kit
	 * @return
	 */
	public static boolean hasSpace(Player p,Kit kit) {
		int nullNum = 0;
		int kitItemNum = 0;
		//����������ж��ٿռ�
		for(int i = 0;i < 36; i++) {
			try {
				p.getInventory().getItem(i).getType();
			}catch(NullPointerException e) {
				nullNum += 1;
			}
		}
		//�����������ж��ٿռ�
		for(int i = 0;i < kit.getItemStack().length;i++) {
			if(kit.getItemStack()[i] != null) {
				kitItemNum++;
			}
		}
		if(nullNum >= kitItemNum) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * �ж��Ƿ����㹻�Ŀռ���ȡ�㹻����������Ʒ
	 * @param p
	 * @param kit
	 * @return
	 */
	public static boolean hasSpace(Player p,int num) {
		int nullNum = 0;
		int ItemNum = num;
		//����������ж��ٿռ�
		for(int i = 0;i < 36; i++) {
			try {
				p.getInventory().getItem(i).getType();
			}catch(NullPointerException e) {
				nullNum += 1;
			}
		}
		if(nullNum >= ItemNum) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * ����ռλ��Ϊָ����ֵ
	 * @param key ռλ����key(����{})
	 * @param value Ҫ�滻��ֵ
	 * @param msg Ҫ�滻����Ϣ��
	 * @return
	 */
	public static String replacePlaceholder(String key,String value,String msg) {
	    msg = msg.replace("{"+ key +"}", value);
		return msg;
		
	}
	/**
	 * NBTת����ͷ­
	 * @param skullnbt
	 * @return ItemStack
	 */
	public static ItemStack nbtCovertoSkull(String skullnbt) {
		ItemStack head = PlayerHead.DEFAULT.getItemStack();//�½�һ��ͷ­��Ʒ
	    NBTItem nbti = new NBTItem(head);//����ͷ­��NBT��NBTItem
	    String nbt = skullnbt;
		NBTContainer c = new NBTContainer(nbt);//���һ��NBT����
	    nbti.mergeCompound(c);
	    head = nbti.getItem();
	    return head;
	}
	
	public static NBTItem getItemNBT(ItemStack is) {
		if(is == null) {
			return null;
		}else {
			NBTItem nbt = new NBTItem(is);
			return nbt;
		}
	}
	
	/**
	 * ��÷�����������ҵ����֣��������ߣ�
	 * @return
	 */
    public static List<String> getPlayerNames() {
    	List<String> players = new ArrayList<String>();
    	for(OfflinePlayer offp : Bukkit.getOfflinePlayers()) {
    		if(offp.getName() == null) {continue;}
    		players.add(offp.getName());
    	}
    	
    	return players;
    }
	
	/**
	 * ��ȡslot��λ��
	 * @param path
	 * @return
	 */
	public static List<Integer> getSlotNum(String path){
		String slotnum = MenuConfigLoader.getString(path);
		List<Integer> list = new ArrayList<>();
		if(slotnum.contains(",")) {// �зָ���
			for(String s : slotnum.split(",")) {
				if(s.contains("-") && s.split("-").length == 2) {
					String[] s1 = s.split("-");
					int begain = Integer.parseInt(s1[0]);
					int end = Integer.parseInt(s1[1]);
					// ������������
					for(int i = begain;i <= end;i++) {
						if(!list.contains(i)) {
							list.add(i);
						}
					}
				}else if(!list.contains(Integer.parseInt(s))) {// û�����������ֱ�Ӽ���
					list.add(Integer.parseInt(s));
				}
			}
		}else {// û�зָ���
			if(slotnum.contains("-") && slotnum.split("-").length == 2) {
				String[] s1 = slotnum.split("-");
				int begain = Integer.parseInt(s1[0]);
				int end = Integer.parseInt(s1[1]);
				// ������������
				for(int i = begain;i <= end;i++) {
					if(!list.contains(i)) {
						list.add(i);
					}
				}
			}else {// û�����������ֱ�Ӽ���
				if(!list.contains(Integer.parseInt(slotnum))) {
					list.add(Integer.parseInt(slotnum));
				}
			}
		}
		return list;
	}
	/**
	 * ��ȡ�������汾��
	 * @return x.xx.x
	 */
	public static int getVersion() {
		String[] versions = Bukkit.getBukkitVersion().split("\\.");//��ð汾�Ų��ҷָ��version�ַ�����
		int versionsnum = Integer.parseInt(versions[1]);//16
		return versionsnum;
	}
	
	/**
	 * ����Item��DisplayName
	 * @param is
	 * @param name
	 * @return
	 */
	public static ItemStack setItemName(ItemStack is,String name) {
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		is.setItemMeta(im);
		return is;
	}
	/**
	 * ����Item��Lore
	 * @param is
	 * @param lore
	 * @return
	 */
	public static ItemStack setItemLore(ItemStack is,List<String> lore) {
		ItemMeta im = is.getItemMeta();
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
	
	/**
	 * �ж��ı��Ƿ����������ʽ
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static Boolean ismatche(String str, String pattern) {
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(str);
		return m.matches();
	}
	
}
