package test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.security.Security;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;



import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.ImmediateRefreshHandler;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlListItem;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.lowagie.text.pdf.codec.Base64.InputStream;
public class JsoupHttpClient {
    public static void main(String[] args) throws Exception {
      for(int i=0;i<100;i++){
    	  pceggs(); 
    	  Thread.sleep(1000);
     }
    	 

    	
        
    }
    public static Page pceggs() throws Exception{
    	WebClient webClient = new WebClient(BrowserVersion.CHROME);
    	webClient.getOptions().setThrowExceptionOnScriptError(false);
    	webClient.getOptions().setUseInsecureSSL(true);
    	//是否启用js
        webClient.getOptions().setJavaScriptEnabled(false);
        //是否启用css
        webClient.getOptions().setCssEnabled(false);
      
        //设置Ajax控制器
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        Page page = webClient.getPage("http://www.chinamoney.com.cn/ags/ms/cm-u-notice-issue/bIssuAn?eDate=2020-03-24&isNewTab=1&limit=1&pageNo=2&pageSize=30&sDate=2020-03-22&timeln=1&typeCode=null");//登陆页面
//        HtmlInput username = (HtmlInput) page.getElementById("ctl00_web_center_EmailTxt");//账号输入框的id
//        HtmlInput password = (HtmlInput) page.getElementById("ctl00_web_center_PassTxt");//密码输入框的id
//        HtmlInput code=(HtmlInput) page.getElementById("ctl00_web_center_checkcodes");//验证码输入栏的id
//        HtmlInput login=(HtmlInput) page.getElementById("ctl00_web_center_SubMit");
//        HtmlImage vaCode=(HtmlImage) page.getElementById("ctl00_web_center_checkcode");
//        File file=new File("E://yzm.png");
//        //保存图片到项目根目录下
//        vaCode.saveAs(file);
//        Scanner scanner=new Scanner(System.in);
//        String codes=scanner.nextLine();
//        username.setAttribute("value","mt-jf@163.com");
//        password.setAttribute("value","TTYJBG");
//        code.setAttribute("value", codes);
//        page=login.click();
        String s=page.getWebResponse().getContentAsString();
     System.out.println(s);
     webClient.close();
       // Page pageS=webClient.getPage("http://vip.nxny.com/DownLoad/DownLoad.aspx?rnd=191213142409138");
      //  saveFile(pageS, "E://ab.pdf");
        return page;
  


    }
    public static void saveFile(Page page, String file) throws Exception {
    	ByteArrayInputStream is =  (ByteArrayInputStream) page.getWebResponse().getContentAsStream();
        FileOutputStream output = new FileOutputStream(file);
        //IOUtils.copy(is, output);
        output.close();
    }

}
