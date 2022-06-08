package cn.wekyjay.www.wkkit.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;

import cn.wekyjay.www.wkkit.menu.MenuManager;

public enum TabOpen {
	
    FIRST(Arrays.asList("open"),0,null,new int[]{1}),
	KIT_NAME(Arrays.asList("<KitMenu>"),1,"open",new int[]{2});
	
    private List<String> list;//返回的List
    private int befPos;//应该识别的上一个参数的位置
    private String bef;//应该识别的上个参数的内容
    private int[] num;//这个参数可以出现的位置
    
    private TabOpen(List<String> list,int befPos, String bef, int[] num){
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
    	if(num[0] == 2)return list = new ArrayList<>(MenuManager.getMenus().keySet());
    	return list;
	}
    public int[] getNum() {
		return num;
	}
    
    
    public static List<String> returnList(String[] Para, int curNum, CommandSender sender) {
        for(TabOpen tab : TabOpen.values() ){
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
