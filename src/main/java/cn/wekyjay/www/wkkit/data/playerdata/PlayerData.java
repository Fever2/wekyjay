package cn.wekyjay.www.wkkit.data.playerdata;

import java.util.List;

public interface PlayerData {
	/**
	 * ���û���ӵ��������
	 * @param kitname
	 * @param playername
	 * @param data
	 * @param time
	 */
	void setKitToFile(String playername, String kitname, String data,int time);
	
	/**
	 * ɾ����ҵ�ָ���������
	 * @param playername
	 * @param kitname
	 */
	void delKitToFile(String playername, String kitname);
	
	/**
	 * �������ָ��������ϴ���ȡ����
	 * @param playername
	 * @param kitname
	 * @param value
	 */
	void setKitData(String playername, String kitname, String value);
	/**
	 * �������ָ���������ȡ����
	 * @param playername
	 * @param kitname
	 * @param value
	 */
	void setKitTime(String playername, String kitname, int value);
	/**
	 * �����������ڵ����������
	 * @param playername
	 * @return
	 */
	List<String> getKits(String playername);
	
	/**
	 * ��ȡ����������������
	 * @param kitname
	 * @return
	 */
	String getKitData(String playername, String kitname);
	
	/**
	 * ��ȡ����������ȡ��������
	 * @param kitname
	 * @return
	 */
	Integer getKitTime(String playername, String kitname);
	
	/**
	 * �ж����ָ����������Ƿ����
	 * @param playername
	 * @param kitname
	 * @return
	 */
	Boolean contain_Kit(String playername, String kitname);
	
	/**
	 * �ж�����Ƿ������ݴ���
	 * @param playername
	 * @return
	 */
	Boolean contain_Kit(String playername);
	
	/**
	 * ���û������ҵ���������
	 * @param playername
	 * @param kitname
	 * @param num
	 */
	void setMailToFile(String playername, String kitname, int num);
	/**
	 * ɾ����ҵ�ָ����������
	 * @param playername
	 * @param kitname
	 */
	void delMailToFile(String playername, String kitname);
	/**
	 * �������������ָ������ĸ���
	 * @param playername
	 * @param kitname
	 * @param value
	 */
	void setMailNum(String playername, String kitname, int num);
	
	/**
	 * �����������ڵ����������
	 * @param playername
	 * @return
	 */
	List<String> getMailKits(String playername);
	
	/**
	 * ��ȡ����������������ȡ��������
	 * @param kitname
	 * @return
	 */
	Integer getMailKitNum(String playername, String kitname);
	
	/**
	 * �ж��������ָ����������Ƿ����
	 * @param playername
	 * @param kitname
	 * @return
	 */
	Boolean contain_Mail(String playername, String kitname);
	
	/**
	 * �ж�����������Ƿ������ݴ���
	 * @param playername
	 * @return
	 */
	Boolean contain_Mail(String playername);
}
