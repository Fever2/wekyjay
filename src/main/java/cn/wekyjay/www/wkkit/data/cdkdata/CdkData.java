package cn.wekyjay.www.wkkit.data.cdkdata;

import java.util.List;

public interface CdkData {
	/**
	 * ���CDK����
	 * @param CDK
	 * @param kits
	 * @param data
	 * @param mark
	 */
	void addCDKToFile(String CDK, String kits, String data,String mark);
	/**
	 * ����ָ��cdk��ʹ�����
	 * @param CDK
	 * @param playername
	 */
	void setCDKStatus(String CDK,String playername);
	/**
	 * �޸�ָ��mark��mark
	 * @param mark
	 * @param newmark
	 */
	void setCDKMark(String mark,String newmark);
	/**
	 * ɾ��markΪmark������CDK
	 * @param mark
	 */
	void delCDKOfMark(String mark);
	/**
	 * ɾ��ָ����CDK
	 * @param cdk
	 */
	void delCDK(String cdk);
	/**
	 * ������ָ��mark�Ķһ���
	 * @param mark
	 */
	List<String> findCDK(String mark);
	/**
	 * �ж��Ƿ����ָ����CDK
	 * @param CDK
	 * @return
	 */
	boolean Contain_CDK(String CDK);
	/**
	 * �ж��Ƿ����ָ����Mark
	 * @param mark
	 * @return
	 */
	boolean Contain_CDKMark(String mark);
	/**
	 * ��ȡָ��cdk��kits
	 * @param cdk
	 * @return
	 */
	String getCDKKits(String cdk);
	/**
	 * ��ȡָ��CDK��Date
	 * @param cdk
	 * @return
	 */
	String getCDKDate(String cdk);
	/**
	 * ��ȡָ��CDK��Status
	 * @param cdk
	 * @return
	 */
	String getCDKStatus(String cdk);
	/**
	 * ��ȡָ��CDK��Mark
	 * @param cdk
	 * @return
	 */
	String getCDKMark(String cdk);
}
