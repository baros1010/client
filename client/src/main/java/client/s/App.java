package client.s;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.Path;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import Account.AccountList;
import apl.demo.entity.account.Account;

/**
 * Hello world!
 *
 */
@Path("/simulator")
public class App {
	/**
	 * url 操作 1new一个url类 2 用HttpURLConnection将其强制转换 3这是req
	 * 
	 * @param args
	 * @throws IOException
	 */

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		String URL = "https://chenlei.mybluemix.net/cic/jpbanking/apic/demo/stub/simulator/accounts/";
		Scanner scanner = new Scanner(System.in);
//		System.out.println("请输入账号");
//		String user = scanner.nextLine();
//		System.out.println("请输入密码");
//		String password = scanner.nextLine();
		
		System.out.println("请输入userId");
		String read = scanner.nextLine();// 控制台輸入id
		String targetURL = URL + read;// 拼接url
		URL restServiceURL = new URL(targetURL);
		//String auth = user + ":" + password;
		Propertiy pro=new Propertiy();
		String auth=pro.getproperties();
		byte[] b = auth.getBytes();
		String auths = Base64.getEncoder().encodeToString(b);
		HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();// 鏈接url
		httpConnection.setRequestMethod("GET");// 請求方式
		httpConnection.setRequestProperty("Accept", "application/json");// head
		httpConnection.setRequestProperty("Authorization", auths);// basic
																	// 驗證
		try {

			if (httpConnection.getResponseCode() != 200) {
				if (httpConnection.getResponseCode() == 400) {
					BufferedReader responseBuffer = new BufferedReader(
							new InputStreamReader((httpConnection.getErrorStream())));

					String output;
					System.out.println("Output from Server:  \n");

					while ((output = responseBuffer.readLine()) != null) {
						System.out.println(output);
					}

				}

				httpConnection.disconnect();
				throw new RuntimeException(
						"HTTP GET Request Failed with Error code : " + httpConnection.getResponseCode());
			}
			/**
			 * 1 新建一个bufferreader； 2 新建一个url的输入流 3
			 * 用responseBuffer的readLine()方法逐一取值 4输出
			 * 
			 * 
			 */

			BufferedReader responseBuffer = new BufferedReader(
					new InputStreamReader((httpConnection.getInputStream())));
			String out = null;
			String output;
			System.out.println("Output from Server:  \n");
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			while ((output = responseBuffer.readLine()) != null) {

				Account[] accountList = objectMapper.readValue(output, Account[].class);
				// list = accountList[0].getAccount();
				// for (Account s : list) {
				for (int i = 0; i < accountList.length; i++) {

					System.out.println(accountList[i].getAccountNo());
					System.out.println(accountList[i].getBankCode());
					System.out.println(accountList[i].getBankKanaName());

					// String s = accountList[i].toString();
					//
					// String ss = s.replace("[", " ");
					// String sss = ss.replace("]", " ");
					// System.out.println(sss);
				}

				// }
			}

			httpConnection.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {// getInputStream()不能做异常的流处理

			e.printStackTrace();
		}

	}
}
