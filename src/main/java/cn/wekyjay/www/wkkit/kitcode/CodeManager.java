package cn.wekyjay.www.wkkit.kitcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.wekyjay.www.wkkit.WkKit;
import cn.wekyjay.www.wkkit.config.LangConfigLoader;
import cn.wekyjay.www.wkkit.tool.WKTool;

public class CodeManager {
	private final static String Base32Alphabet = "ABCDEFGHIJKMNPQRSTUVWXYZ23456789";
	private static String password;
	
	//��byteתΪ�ַ�����������Ҫ��λ��
	final static int convertByteCount = 5;
	
	public static String getPassword() {
		return password;
	}
	
	public static void checkPassWord() {
		String value = WkKit.getWkKit().getConfig().getString("KitCode.Key");
		String pattern = "^(?=.*[a-zA-Z])(?=.*\\d).+$";
		if(!(value != null && WKTool.ismatche(value, pattern) && value.length() > 6 && value.length() < 18)) {
			   char charr[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
			   StringBuilder sb = new StringBuilder();
			   Random r = new Random();
			   for (int x = 0; x < 12; ++x) {
				   sb.append(charr[r.nextInt(charr.length)]);
			   }
			   password = sb.toString();
			   WkKit.getWkKit().getConfig().set("KitCode.Key", sb.toString());
			   WkKit.getWkKit().saveConfig();
			   WkKit.getWkKit().getLogger().info(LangConfigLoader.getString("AUTO_GENERATE_KEY"));
		}else {
			password = value;
		}
	}
 
	/**
	 * ���ɶһ���
	 * ����ÿһ�����ɶһ�����������Ϊint�����ֵ��2147483647
	 * @param time
	 * @param id
	 * @param count
	 * @return
	 */
	public static List<String> create(byte groupid,int codecount,int codelength,String password) {
		List<String> cdklist = new ArrayList<>();

		//8λ�������ܳ���
		int fullcodelength = codelength * convertByteCount / 8; 
		//������ʱ���idͬʱ�������
		//����1��id4�������n,У����1 
		int randcount = fullcodelength - 6;//������ж��ٸ�
		
		//��������С��0 ������
		if(randcount <= 0 ) {
			return null;
		}
		for(int i = 0 ; i < codecount ; i ++) {
			//����ʹ��i��Ϊcode��id
			//����nλ�����
			byte[] randbytes = new byte[randcount];
			for(int j = 0 ; j  < randcount ; j ++) {
				randbytes[j] = (byte)(Math.random() * Byte.MAX_VALUE);
			}
 
			//�洢��������
			ByteHelper byteHapper = ByteHelper.CreateBytes(fullcodelength);
			byteHapper.AppendNumber(groupid).AppendNumber(i).AppendBytes(randbytes);
 
			//����У���� ����ʹ������������ӵ��ܺ���byte.max ȡ��
			byte verify = (byte) (byteHapper.GetSum() % Byte.MAX_VALUE);
			byteHapper.AppendNumber(verify);
 
			//ʹ���������ʱ���ID�������
			for(int j = 0 ; j < 5 ; j ++) {
				byteHapper.bytes[j] = (byte) (byteHapper.bytes[j] ^ (byteHapper.bytes[5 + j % randcount]));
			}
 
			//ʹ���������������ݽ����������������
			byte[] passwordbytes = password.getBytes();
			for(int j = 0 ; j < byteHapper.bytes.length ; j++){
				byteHapper.bytes[j] = (byte) (byteHapper.bytes[j] ^ passwordbytes[j % passwordbytes.length]);
			}
			
			//����洢���յ�����
			byte[] bytes = new byte[codelength];
			
			//��6λһ�鸴�Ƹ���������
			for(int j = 0 ; j < byteHapper.bytes.length ; j ++) {
				for(int k = 0 ; k < 8 ; k ++) {
					int sourceindex = j*8+k;
					int targetindex_x = sourceindex / convertByteCount;
					int targetindex_y = sourceindex % convertByteCount;
					byte placeval = (byte)Math.pow(2, k);
					byte val = (byte)((byteHapper.bytes[j] & placeval) == placeval ? 1:0);
					//����ÿһ��bit
					bytes[targetindex_x] = (byte)(bytes[targetindex_x] | (val << targetindex_y));
				}
			}
			
			StringBuilder result = new StringBuilder();
			//�༭�������������ַ���
			for(int j = 0 ; j < bytes.length ; j ++) {
				result.append(Base32Alphabet.charAt(bytes[j]));
				if(j == 3 || j == 7) result.append('-');
			}
			// ������ھ���������
			if(WkKit.CDKConfig.contains(result.toString()))i--;
			else  cdklist.add(result.toString());
		}
		return cdklist;
	}
	
	/**
	 * ��֤�һ���
	 * @param code
	 */
	public static Boolean VerifyCode(String code){
		if(code.length() != 14 || !code.contains("-")) return false;
		// ȥ���ֽ�
		StringBuilder result = new StringBuilder();
		for(String str : code.split("-")) {
			result.append(str);
		}
		code = result.toString();
		
		byte[] bytes = new byte[code.length()];
		
		//���ȱ����ַ������ַ����л�ȡ��Ӧ�Ķ���������
		for(int i=0;i<code.length();i++){
		    byte index = (byte) Base32Alphabet.indexOf(code.charAt(i));
		    bytes[i] = index;
		}
		
		//��ԭ����
		int fullcodelength = code.length() * convertByteCount / 8;
		int randcount = fullcodelength - 6;//������ж��ٸ�
		
		byte[] fullbytes = new byte[fullcodelength];
		for(int j = 0 ; j < fullbytes.length ; j ++) {
			for(int k = 0 ; k < 8 ; k ++) {
				int sourceindex = j*8+k;
				int targetindex_x = sourceindex / convertByteCount;
				int targetindex_y = sourceindex % convertByteCount;
				
				byte placeval = (byte)Math.pow(2, targetindex_y);
				byte val = (byte)((bytes[targetindex_x] & placeval) == placeval ? 1:0);
				
				fullbytes[j] = (byte) (fullbytes[j] | (val << k));
			}
		}
 
		//���ܣ�ʹ���������������ݽ����������������
		byte[] passwordbytes = password.getBytes();
		for(int j = 0 ; j < fullbytes.length ; j++){
			fullbytes[j] = (byte) (fullbytes[j] ^ passwordbytes[j % passwordbytes.length]);
		}
 
		//ʹ���������ʱ���ID�������
		for(int j = 0 ; j < 5 ; j ++) {
			fullbytes[j] = (byte) (fullbytes[j] ^ (fullbytes[5 + j % randcount]));
		}
		
		//��ȡУ���� �����У����λ��������λ���ܺ�
		int sum = 0;
		for(int i = 0 ;i < fullbytes.length - 1; i ++){
			sum += fullbytes[i];
		}
		byte verify = (byte) (sum % Byte.MAX_VALUE);
		
		//У��
		if(verify == fullbytes[fullbytes.length - 1]){
			return true;
		}else {
			return false;
		}
	}
}
