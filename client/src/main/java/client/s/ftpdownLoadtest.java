

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;

public class ftpdownLoadtest {
	
	public static void main(String[] args) throws IOException, FTPException {
		
		FTPClient ftpClient = new FTPClient();
		 ftpClient.setRemoteHost("");
		 ftpClient.setControlEncoding("UTF-8"); // 加上这一句，可以传中文文件名
		 ftpClient.setConnectMode(FTPConnectMode.PASV);// 被动模式:FTPConnectMode.PASV
		// ftpClient.setRemotePort(2121);
		
		 ftpClient.setTimeout(30000);// 设置超时时间
		 ftpClient.setDataSendBufferSize(1024*1024*50);
		 ftpClient.setTransferBufferSize(1024*1024*50);
		 ftpClient.setDataReceiveBufferSize(1024*1024*50);
		 ftpClient.connect();
		 ftpClient.login("","");
		 byte[] b=null;
		 b=ftpClient.get(null);
		 String infocode="";
		 String ATTACHTYPE="0";
		 String url="http://61.129.129.224:9090/"+"nrsweb/upload?"
					+ "uploadtype=H12&infocode="+infocode+"&name="
							+ URLEncoder.encode("", "UTF-8")
							+ "&seq=1&pageNum=0&fileType="+ATTACHTYPE;
		 HttpPost httppost = new HttpPost(url);
	        DefaultHttpClient httpclient = null;
	        int result = 0;
	        httppost.setEntity(new ByteArrayEntity(b));
	        httpclient = new DefaultHttpClient();
	        try {
	            HttpParams params = httpclient.getParams();
	            if (params == null) {
	                params = new BasicHttpParams();
	            }
	            HttpConnectionParams.setConnectionTimeout(params, 60 * 1000);
	            HttpConnectionParams.setSoTimeout(params, 3 * 60 * 1000);
	            httpclient.setParams(params);
	            HttpResponse response = httpclient.execute(httppost);
	            result = response.getStatusLine().getStatusCode();
	            System.out.println(result);
	            
	        }
	        
	        catch(Exception e){
	        	e.getMessage();
	        }finally{
	        	
	            httppost.releaseConnection();
	      
	        }
	        
	}
}
