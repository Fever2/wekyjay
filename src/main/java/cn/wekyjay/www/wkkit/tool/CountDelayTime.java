package cn.wekyjay.www.wkkit.tool;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import cn.wekyjay.www.wkkit.WkKit;
import cn.wekyjay.www.wkkit.config.LangConfigLoader;
import cn.wekyjay.www.wkkit.kit.Kit;
/**
 * ���ڲ��������ڵ�����
 * @author Administrator
 *
 */
public class CountDelayTime {
	// ��������ʵ��
	WkKit wk = WkKit.getWkKit();
	
	private long delay;//�����ȴʱ��
	private Calendar cala;
	private Player p;
	private Kit kit;
	private static List<CountDelayTime> ctlist = new ArrayList<CountDelayTime>();
	
	/**
	 * ʹ��ǰҪȷ����Kit��Delay����
	 * @param p
	 * @param kit
	 */
	public CountDelayTime(Player p,Kit kit) {
		cala = null;
		this.kit = kit;
		this.p = p;
		delay = kit.getDelay();
		ctlist.add(this);
	}
	
	
	public Kit getKit() {
		return kit;
	}
	public Player getPlayer() {
		return p;
	}
	/**
	 * �ж�����Ƿ�����ȡ�����
	 * @return
	 */
	public boolean isGet() {
		if(cala == null) {
			cala = Calendar.getInstance();
			return true;
		}
		// ����
		long lc = cala.getTimeInMillis();//��ȡ����ϴ�ʱ�������
		long ln = Calendar.getInstance().getTimeInMillis();//��ȡ��ҵ�ǰʱ�������
		
		if((ln - lc) >= (delay * 1000)) {
			System.out.println((ln - lc));
			cala = Calendar.getInstance();
			return true;
		}else {
			return false;
		}
	}
	
	
	/**
	 * ���������ȡ�������ʣ��Ĵ���ʱ��
	 */
	public void whenGet() {
		long lc = cala.getTimeInMillis();//��ȡ�������ʱ�������
		long ln = Calendar.getInstance().getTimeInMillis();//��ȡ��ǰʱ��
		long newlc = lc + delay * 1000;//����ʱ�������ȴʱ��֮���ʱ�������
		
		Calendar newcalc = Calendar.getInstance();//����һ��ʱ������
		newcalc.setTimeInMillis(newlc - ln);//��newlc�ĺ�����ת��Ϊʱ��
		
		//��ÿ�����ȡ�����ʱ���
		List<Integer> list = new ArrayList<Integer>();
		list.add(newcalc.get(Calendar.YEAR) - 1970);
		list.add((newcalc.get(Calendar.MONTH)) - 0);
		list.add(newcalc.get(Calendar.DATE) - 1);
		list.add(newcalc.get(Calendar.HOUR_OF_DAY) - 8);
		list.add(newcalc.get(Calendar.MINUTE) - 0);
		list.add(newcalc.get(Calendar.SECOND) - 0);
		
		//Ϊʱ�����ϵ�λ
		String lang = WkKit.getWkKit().getConfig().getString("Setting.Language");
		List<String> slist = new ArrayList<String>();
		if(!lang.equals("zh_CN")) {
			slist.add(list.get(0) + "Y");
			slist.add(list.get(1) + "M");
			slist.add(list.get(2) + "D");
			slist.add(list.get(3) + "H");
			slist.add(list.get(4) + "MIN");
			slist.add(list.get(5) + "S");
		}else {
			slist.add(list.get(0) + "��");
			slist.add(list.get(1) + "����");
			slist.add(list.get(2) + "��");
			slist.add(list.get(3) + "Сʱ");
			slist.add(list.get(4) + "����");
			slist.add(list.get(5) + "��");
		}

		
		//�ж��Ƿ�Ϊ��
		String str = "";
		for(int i = 0; i <= 5; i++) {
			if(!(list.get(i) <= 0)) {
				str += slist.get(i);
				break;
			}
		}
		String lefttime = WKTool.replacePlaceholder("lefttime", str, LangConfigLoader.getStringWithPrefix("KIT_GET_LEFTTIME",ChatColor.RED));
		p.sendMessage(lefttime);
	}
	
	/**
	 * ��ȡ������ȡ����ľ�������
	 * @return String
	 */
	public String getData(){
		long lc = cala.getTimeInMillis();//��ȡ�������ʱ�������
		long newlc = lc + delay * 1000;//����ʱ�������ȴʱ��֮���ʱ�������
		Calendar newcalc = Calendar.getInstance();//����һ��ʱ������
		newcalc.setTimeInMillis(newlc);//��newlc�ĺ�����ת��Ϊʱ��
		
		//��ÿ�����ȡ�����ʱ��
		List<Integer> list = new ArrayList<Integer>();
		list.add(newcalc.get(Calendar.YEAR));
		list.add(newcalc.get(Calendar.MONTH) + 1);
		list.add(newcalc.get(Calendar.DATE));
		list.add(newcalc.get(Calendar.HOUR_OF_DAY));
		list.add(newcalc.get(Calendar.MINUTE));
		list.add(newcalc.get(Calendar.SECOND));
		
		return String.join("/", list.get(0)+"",list.get(1)+"",list.get(2)+"",list.get(3)+"",list.get(4)+"",list.get(5)+"");
	}
	
	/**
	 * ��õ�ǰ��ʱ��Ϊ��ʽ��YY-MM-DD-HH-MM-SS��
	 * @return String
	 */
	public String getLocalTime(){
		Calendar caln = Calendar.getInstance();//��ҵ�ǰʱ��
		//��õ�ǰ��ʱ��Ϊ��ʽ��YY-MM-DD-HH-MM-SS��
		int year = caln.get(Calendar.YEAR);
		int mon = caln.get(Calendar.MONTH) + 1;
		int day = caln.get(Calendar.DATE);
		int hour = caln.get(Calendar.HOUR_OF_DAY);
		int min = caln.get(Calendar.MINUTE);
		int sec = caln.get(Calendar.SECOND);
		
		return String.join("-", year+"",mon+"",day+"",hour+"",min+"",sec+"");
	}
	
	/**
	 * ��õ�ǰ��ʱ��Ϊ��ʽ��YY-MM-DD-HH-MM-SS��
	 * @return String
	 */
	public static String toLocalTime(){
		Calendar caln = Calendar.getInstance();//��ҵ�ǰʱ��
		//��õ�ǰ��ʱ��Ϊ��ʽ��YY-MM-DD-HH-MM-SS��
		int year = caln.get(Calendar.YEAR);
		int mon = caln.get(Calendar.MONTH) + 1;
		int day = caln.get(Calendar.DATE);
		int hour = caln.get(Calendar.HOUR_OF_DAY);
		int min = caln.get(Calendar.MINUTE);
		int sec = caln.get(Calendar.SECOND);
		
		return String.join("-", year+"",mon+"",day+"",hour+"",min+"",sec+"");
	}
	
	/**
	 * ��ȡ��ȴ����Ķ����б�
	 * @return
	 */
	public static List<CountDelayTime> getDelaylist() {
		return ctlist;
	}
	public static CountDelayTime getDelayInstance(Player p, Kit kit) {
		for(CountDelayTime ct : getDelaylist()) {
			if(ct.getKit().equals(kit) && ct.getPlayer().equals(p)) {
				return ct;
			}
		}
		return null;
	}
}
