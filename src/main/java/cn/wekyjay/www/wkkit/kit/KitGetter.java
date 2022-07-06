package cn.wekyjay.www.wkkit.kit;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import cn.wekyjay.www.wkkit.WkKit;
import cn.wekyjay.www.wkkit.config.LangConfigLoader;
import cn.wekyjay.www.wkkit.tool.WKTool;
/**
 * ������ȡ����󱣴������ȡ����(Ŀǰֻ�в˵�ʹ��)
 * @author Administrator
 *
 */
public class KitGetter{
	/**
	 * ��ȡ���
	 * @param kitname
	 * @param p
	 */
	public void getKit(Kit kit,Player p) {
		if(kit.getPermission() != null) {if(!this.runPermission(kit, p)) {return;}}
		if(kit.getItemStack() != null) {if(!this.runItem(kit, p)) {return;}}
		if(kit.getTimes() != null) {if(!this.runTimes(kit, p)) {return;}}
		// ����ִ��
		if(kit.getCommands() != null) {this.runCommands(kit, p);}
		this.getSuccess(kit, p);
	}
	
	//******************************�� �� ��*********************************//
	/**
	 * ����ָ��
	 * @param kitname
	 * @param playername
	 */
	public void runCommands(Kit kit,Player p) {
		List<String> commands = kit.getCommands();
		for(String str : commands) {
			String[] splitstr = str.split(":");
			String command = null;
			if(splitstr.length > 1) {//�ж��Ƿ���ָ����ָ��ͷ�ʽ
				command = WKTool.replacePlaceholder("player", p.getName(), splitstr[1]);
			}else {
				command = WKTool.replacePlaceholder("player", p.getName(), splitstr[0]);
			}
			//���ݲ�ͬ��ָ��ͷ�ʽ����
			if(splitstr[0].equalsIgnoreCase("cmd")) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
			}else if(splitstr[0].equalsIgnoreCase("op") && !p.isOp()) {
				p.setOp(true);
				Bukkit.dispatchCommand(p, command);
				p.setOp(false);
			}else {
				p.performCommand(command);
			}
		}
	}
	/**
	 * Ȩ�޼�飬������û��Ȩ���򷵻�false
	 * @param kitname
	 * @param p
	 * @return
	 */
	public Boolean runPermission(Kit kit,Player p) {
		return p.hasPermission(kit.getPermission())?true:false;
	}
	/**
	 * ��Ʒ��ȡ������
	 * @param kitname
	 * @param p
	 * @return
	 */
	public Boolean runItem(Kit kit,Player p) {
		if(!WKTool.hasSpace(p, kit)) {//�ж���û���㹻�ı����ռ�
			p.sendMessage(LangConfigLoader.getString("KIT_GET_FAILED"));
			return false;
		}
		return true;
	}
	
	/**
	 * ������ȡ����
	 * @param kitname
	 * @param p
	 * @return
	 */
	public Boolean runTimes(Kit kit,Player p) {
		//������û����ȡ�����ʹ���һ��
		String kitname = kit.getKitname();
		if(WkKit.getPlayerData().getKitTime(p.getName(), kitname) == null) {
			if(kit.getTimes() < 0 || kit.getTimes() == null) {
				WkKit.getPlayerData().setKitTime(p.getName(), kitname, -1);
				return true;
			}else {
				WkKit.getPlayerData().setKitTime(p.getName(), kitname, kit.getTimes());
				return true;
			}
			
		}
		int times = WkKit.getPlayerData().getKitTime(p.getName(),kitname);
		if(times != 0) return true;
		return false;
		
	}
	
	/**
	 * ��ȡ��ִ��
	 * @param kitname
	 * @param p
	 */
	private void getSuccess(Kit kit, Player p) {
		String kitname = kit.getKitname();
		int times = -1;
		if(WkKit.getPlayerData().getKitTime(p.getName(),kitname) != null) {
			times = WkKit.getPlayerData().getKitTime(p.getName(),kitname);
		}
		// ������ȡ״̬
		if(kit.getDocron() != null) {
			WkKit.getPlayerData().setKitData(p.getName(), kitname, "false");
		}
		// ������ȡ����
		if(times > 0)WkKit.getPlayerData().setKitTime(p.getName(), kitname, times - 1);
		// �����ȡ�������0�˾�Ҳ���false
		if(WkKit.getPlayerData().getKitTime(p.getName(),kitname) != null && WkKit.getPlayerData().getKitTime(p.getName(),kitname) == 0)WkKit.getPlayerData().setKitData(p.getName(), kitname, "false");
		// ������Ʒ
		PlayerInventory pinv = p.getInventory();//ʹ�÷�װ���getplayer������ȡ��ұ���
		ItemStack[] itemlist = kit.getItemStack();
		for(ItemStack item : itemlist) {
			pinv.addItem(item);//�����Ʒ������
		}
		// ������Ϣ
		p.sendMessage(LangConfigLoader.getStringWithPrefix("KIT_GET_SUCCESS",ChatColor.GREEN));
	}
}
