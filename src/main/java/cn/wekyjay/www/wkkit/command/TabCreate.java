package cn.wekyjay.www.wkkit.command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;

public enum TabCreate {
	
    FIRST(Arrays.asList("create"),0,null,new int[]{1}),
	KIT_NAME(Arrays.asList("<KitName>"),1,"create",new int[]{2}),
	KIT_DISPLAYNAME(Arrays.asList("<DisplayName>"),1,"create",new int[]{3});
	
    private List<String> list;//���ص�List
    private int befPos;//Ӧ��ʶ�����һ��������λ��
    private String bef;//Ӧ��ʶ����ϸ�����������
    private int[] num;//����������Գ��ֵ�λ��
    
    private TabCreate(List<String> list,int befPos, String bef, int[] num){
        this.list = list;
        this.befPos = befPos;
        this.bef = bef;
        this.num = num.clone();
    }
    
    public String getBef() {
		return bef;
	}
    public int getBefPos() {
		return befPos;
	}
    public List<String> getList() {
    	return list;
	}
    public int[] getNum() {
		return num;
	}
    
    
    public static List<String> returnList(String[] Para, int curNum, CommandSender sender) {
        for(TabCreate tab : TabCreate.values() ){
            if(tab.getBefPos()-1 >= Para.length){
                continue;
            }
            if((tab.getBef() == null || tab.getBef().equalsIgnoreCase(Para[tab.getBefPos()-1])) && Arrays.binarySearch(tab.getNum(),curNum)>=0){
            	return tab.getList();
            }
        }
        return null;
    }
    
    
}
