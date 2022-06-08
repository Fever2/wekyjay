package cn.wekyjay.www.wkkit.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;

import cn.wekyjay.www.wkkit.WkKit;

public class CronManager {

  
    	//��ȡԤ�����ʵ��
		static CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ);
		//�����ṩ��Ԥ���崴��Cron������
		static CronParser parser = new CronParser(cronDefinition);
	
		/**
		 * ͨ�����ʽ��ȡ�´�ִ�е�ʱ��
		 * @param cron
		 * @return Date
		 */
	public static Date getNextExecution(String cron) {
		//��ȡ��ǰʱ����
		ZonedDateTime now = ZonedDateTime.now();
		//��ȡִ��ʱ����
		ExecutionTime executionTime = ExecutionTime.forCron(parser.parse(cron));
		//��ȡ�´�ִ�е�ʱ��
		Optional<ZonedDateTime> nextExecution = executionTime.nextExecution(now);
		//ת��Date��
		Date date = Date.from(nextExecution.get().toInstant());
		return date;
	}
	
	/**
	 * ��ȡָ��ʱ�����һ��ִ��ʱ��
	 * @param time
	 * @param cron
	 * @return
	 * @throws ParseException
	 */
	public static Date getNextExecution(String time,String cron){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date lastdate = null;
		try {
			lastdate = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ZonedDateTime ztime = ZonedDateTime.ofInstant(lastdate.toInstant(), ZoneId.systemDefault());
		//��ȡִ��ʱ����
		ExecutionTime executionTime = ExecutionTime.forCron(parser.parse(cron));
		//��ȡ�´�ִ�е�ʱ��
		Optional<ZonedDateTime> nextExecution = executionTime.nextExecution(ztime);
		//ת��Date��
		Date date = Date.from(nextExecution.get().toInstant());
		return date;
	}
	
	/**
	 * ��ȡ�����´���ȡ��ʱ������
	 * @param time
	 * @param cron
	 * @return
	 */
	public static String getDescribeToNext(String cron) {
		//��ȡִ��ʱ����
		ZonedDateTime now = ZonedDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
		ExecutionTime executionTime = ExecutionTime.forCron(parser.parse(cron));
		Optional<Duration> timeToNextExecution = executionTime.timeToNextExecution(now);
		// ��ó���ʱ��
		Optional<Long> duration = timeToNextExecution.map(Duration::toMillis);
		Calendar newcalc = Calendar.getInstance();//����һ��ʱ������
		newcalc.setTimeInMillis(duration.get());//��newlc�ĺ�����ת��Ϊʱ��
		
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
		
		return str;
	}
	
	/**
	 * �ж��Ƿ񳬹���ˢ��ʱ��
	 * @param shuttime
	 * @param cron
	 * @return
	 * @throws ParseException
	 */
	public static Boolean isExecuted(String shuttime,String cron) {
		Calendar cnow = Calendar.getInstance();
		Calendar cnext = Calendar.getInstance();
		cnext.setTime(getNextExecution(shuttime, cron));
		if(cnow.getTimeInMillis() >= cnext.getTimeInMillis()) {
			return true;
		}
		return false;
	}
	
}
