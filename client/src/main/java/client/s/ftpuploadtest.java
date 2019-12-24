package com.eastmoney.startup;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.jboss.netty.handler.codec.base64.Base64;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPFile;
import com.enterprisedt.net.ftp.FTPTransferType;

import oracle.jdbc.Const;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ftpuploadtest {
	// public static void main(String[] args) throws ParseException, IOException
	// {
	// InputStream is;
	// try {
	// is =
	// getInutStream("http://static.sse.com.cn/disclosure/listedinfo/announcement/c/2019-03-30/600635_20190330_19.pdf");
	// } catch (KeyManagementException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (NoSuchAlgorithmException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (KeyStoreException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// byte[] buffer = new byte[2046];
	// int r = 0;
	//// while ((r = is.read(buffer)) > 0) {
	////
	//// }
	// }
	static FTPClient ftpClient = new FTPClient();

	public static void main(String[] args)
			throws ParseException, IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		try {
			ftpClient.setRemoteHost("");
			// ftpClient.setControlEncoding("UTF-8"); // 加上这一句，可以传中文文件名
			ftpClient.setConnectMode(FTPConnectMode.ACTIVE);// 被动模式:FTPConnectMode.PASV
			ftpClient.setRemotePort(21);

			ftpClient.setTimeout(30000);// 设置超时时间

			ftpClient.setDataSendBufferSize(1024 * 1024 * 50);
			ftpClient.setTransferBufferSize(1024 * 1024 * 50);
			ftpClient.setDataReceiveBufferSize(1024 * 1024 * 50);

			ftpClient.connect();
			ftpClient.login("", "");
			// ftpClient.mkdir("/test/");
			ftpClient.chdir("/");
			ftpClient.setType(FTPTransferType.BINARY);
			String[] ss = ftpClient.dir("/2019/2019-12-04/");
			FTPFile[] files = ftpClient.dirDetails("/2019/2019-12-04");
			//System.out.println(files[0].getPath());
			getList(files);

		} catch (IOException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void getList(FTPFile[] files) throws IOException, FTPException, ParseException {
		List<String> list=new ArrayList<String>();
		
		for (int i = 0; i < files.length; i++) {

			if (files[i].isFile()) {
				String path = files[i].getPath() + "/" + files[i].getName();
				list.add(path.replace("////", ""));
			} else {
				//System.out.println(files[i].getPath() + files[i].getName());
				FTPFile[] filess = ftpClient.dirDetails(files[i].getPath() + "/" + files[i].getName());
				getList(filess);

			}

		}
	}

	public String getMD5(File file) throws Exception {

		FileInputStream fis = new FileInputStream(file);
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] buffer = new byte[1024];
		int length = -1;
		while ((length = fis.read(buffer, 0, 1024)) != -1) {
			md.update(buffer, 0, length);
		}
		BigInteger bigInt = new BigInteger(1, md.digest());
		return bigInt.toString(16);

	}

	@SuppressWarnings("deprecation")
	public static InputStream getInutStream(String url) {
		CloseableHttpClient httpclient = null;
		int result = 0;
		SSLConnectionSocketFactory scsf;
		try {
			scsf = new SSLConnectionSocketFactory(
					SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy() {
						public boolean isTrusted(X509Certificate[] x509Certificates, String s)
								throws CertificateException {
							return true;
						}
					}).build(), NoopHostnameVerifier.INSTANCE);
			httpclient = HttpClients.custom().setSSLSocketFactory(scsf).build();
		} catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		HttpGet httppost = new HttpGet(url);
		System.out.println(httppost.getMethod());
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();// 设置请求和传输超时时间
		httppost.setConfig(requestConfig);
		httppost.addHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
		httppost.addHeader(new BasicHeader("Accept-Encoding", "identity"));

		httppost.addHeader(new BasicHeader("User-Agent",
				" Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36"));

		InputStream is = null;
		try {
			HttpResponse response = httpclient.execute(httppost);
			result = response.getStatusLine().getStatusCode();
			String results = response.getEntity().getContentType().getValue();
			response.setHeader("connection", "keep-alive");
			String desc = response.getFirstHeader("Content-Disposition").getValue();
			if (desc == null) {
				System.out.println("1");
			}
			System.out.println(results);

			if (result != 200) {
				// 重试操作
				int count = 0;
				while (result != 200 && count < 3) {

					httpclient.close();
					httpclient = null;
					httpclient = new DefaultHttpClient();
					response = httpclient.execute(httppost);
					result = response.getStatusLine().getStatusCode();
					count++;
				}
			}

			// is = response.getEntity().getContent();
			BufferedHttpEntity bufferedEntity = new BufferedHttpEntity(response.getEntity());
			System.out.println("ContentLength:" + bufferedEntity.getContentLength());
			is = (ByteArrayInputStream) bufferedEntity.getContent();
			byte[] b = new byte[28];

			is.read(b, 0, 28);
			System.out.println(bytesToHexString(b));
		} catch (Exception e) {
			System.out.println("111");
			System.out.println(e.getMessage());
		}

		return is;
	}

	private static String bytesToHexString(byte[] src) {

		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
}
