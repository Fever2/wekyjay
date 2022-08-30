package cn.wekyjay.www.wkkit.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;

import org.bukkit.configuration.file.YamlConfiguration;

import cn.wekyjay.www.wkkit.WkKit;


public class ChackFiles {
	public void chack(File file) {
		ergodicFile(file);
	}
	/**
	 * �����ļ�ִ�м��ڵ�
	 * @param file
	 */
	private void ergodicFile(File file) {
        for(File cfile : file.listFiles()) {
        	// ��������ļ������±���
        	if(cfile.isDirectory()){
        		ergodicFile(cfile);
        	}else {
        		chackFile(cfile);
        	}
        }
	}
	/**
	 * ����ļ��ڵ��Ƿ����ڲ���һ�£������һ������½ڵ㡣
	 * @param file
	 */
	private void chackFile(File file) {
		if(file.isFile()) {
			String tmpfilepath = file.getPath();
			int idex = tmpfilepath.lastIndexOf("WkKit");
			String filepath = tmpfilepath.substring(idex + 5);
			YamlConfiguration fileYaml = null;
			YamlConfiguration jarYaml = getYamlConfig(filepath.replaceAll("\\\\", "/"));
			if(file.exists() && jarYaml != null) {
				if(filepath.contains("config.yml") || filepath.contains("Language")) {
					fileYaml = YamlConfiguration.loadConfiguration(file);
					// �жϾ��ļ��Ƿ�����½ڵ�
					for(String key : jarYaml.getKeys(true)) {
						// ��������ھ����
						if(!fileYaml.contains(key)) {
							fileYaml.set(key, jarYaml.get(key));
						}
					}
					// �ж����ļ��Ƿ�ȱ�پɽڵ�
					for(String key : fileYaml.getKeys(true)) {
						// ��������ھ�ɾ��
						if(!jarYaml.contains(key)) {
							fileYaml.set(key, null);
						}
					}
					try {
						fileYaml.save(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	
	/**
	 * ��ȡJar�ڲ���Դ������
	 * @param path
	 * @return
	 */
	private YamlConfiguration getYamlConfig(String path){
        InputStream inputStream = null;
        YamlConfiguration yaml = null;
        try {
            inputStream = this.getClass().getResourceAsStream(path);
            File file = null;
			try {
				file = asFile(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
            if(null != file) {
                yaml = YamlConfiguration.loadConfiguration(file);
            }
            // �ر���
        } finally {
            if(inputStream != null){
                try{
                    inputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return yaml;
    }
	/**
	 * ������ת�ļ�
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static File asFile(InputStream inputStream) throws IOException{
		File tmp = new File("wkkit.tmp");
		OutputStream os = new FileOutputStream(tmp);
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
		  os.write(buffer, 0, bytesRead);
		}
		inputStream.close();
		os.close();
		return tmp;
	}

}
