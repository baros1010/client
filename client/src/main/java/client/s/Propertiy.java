package client.s;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
/**
 * 1 定義一個Properties類
 * 2讀取test.properties
 * 3pro.load（）
 * 4獲取屬性的值
 * @author firas
 *
 */


public class Propertiy {

	public String getproperties() throws IOException{
		Properties pro=new Properties();
		 InputStream in = Propertiy.class.getClassLoader().getResourceAsStream(
                 "test.properties");
		pro.load(in);
		String admin=pro.getProperty("admin");
		String password =pro.getProperty("password");
		String pwdAndAdmin=admin+":"+password;
		ConcurrentMap<String, String> concurrentMap = new ConcurrentHashMap<String, String>();
		concurrentMap.put("pwdAndAdmin", pwdAndAdmin.trim());
		
		return (String) concurrentMap.get(pwdAndAdmin);
		
		
		
		
		
		
	}
}
