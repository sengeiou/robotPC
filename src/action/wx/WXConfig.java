package action.wx;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;

import java.util.Random;

public class WXConfig{
	public static CloseableHttpClient https;
	
	public static CookieStore cookieStore = new BasicCookieStore();
	
	/*public static String skey = null;
	public static String wxsid = null;
	public static String wxuin = null;
	public static String pass_ticket = null;
	public static String isgrayscale;
	public static String appid = null;
	public static String UserName;
	public static String WXHost = null;
	public static String nickName = null;
	public static String headImgUrl = null;*/
	
	public static HttpGet creatHttpGet(String url,WXPerson wxPerson) {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Connection", "keep-alive");
		if(!"https://wx2.qq.com/".equals(wxPerson.WXHost)){
			httpGet.setHeader("Host","wx.qq.com");
			httpGet.setHeader("Accept","application/json, text/plain, */*");
			httpGet.setHeader("Referer","https://wx.qq.com/?&lang=zh_CN");
			httpGet.setHeader("Accept-Encoding","gzip, deflate, br");
			httpGet.setHeader("Accept-Language","zh-CN,zh;q=0.9");
			httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
			httpGet.setHeader("Cookie","mm_lang=zh_CN; MM_WX_NOTIFY_STATE=1; MM_WX_SOUND_STATE=1; webwxuvid=74da1cb6fe8fb25f7d7d099a8436cff9578d59752c63d535e7b0c94b5010684e4f40c089bcd7cbdd71f6c737513de367; wxpluginkey=1539766562; wxuin="+wxPerson.wxuin+"; last_wxuin=3477143430; refreshTimes=5; wxsid="+wxPerson.wxsid+"; wxloadtime="+System.currentTimeMillis()+"; webwx_data_ticket="+wxPerson.pass_ticket+"; webwx_auth_ticket=CIsBEIWKkaUGGoAB3Ql6cvpGwxxHfMfHjo7qbJau7Gq1CJGqOdI34JSKiZef7/NvY7rHgfoF3LBH2krIK7ZVt6cN3SYiysZMywxUyxWcN2xQ9farq+TC7BiEfJ1Z6IulocNkEksH4zjmZ7+zIqBK1uas0H4vxGxiXTOf95DSGnaVeAkTG6Fo5+6Wrns=; login_frequency=2");
			//System.out.println("cookie:"+"mm_lang=zh_CN; MM_WX_NOTIFY_STATE=1; MM_WX_SOUND_STATE=1; webwxuvid=74da1cb6fe8fb25f7d7d099a8436cff9578d59752c63d535e7b0c94b5010684e4f40c089bcd7cbdd71f6c737513de367; wxpluginkey=1539766562; wxuin="+WXConfig.wxuin+"; last_wxuin=3477143430; refreshTimes=5; wxsid="+WXConfig.wxsid+"; wxloadtime="+System.currentTimeMillis()+"; webwx_data_ticket="+WXConfig.pass_ticket+"; webwx_auth_ticket=CIsBEIWKkaUGGoAB3Ql6cvpGwxxHfMfHjo7qbJau7Gq1CJGqOdI34JSKiZef7/NvY7rHgfoF3LBH2krIK7ZVt6cN3SYiysZMywxUyxWcN2xQ9farq+TC7BiEfJ1Z6IulocNkEksH4zjmZ7+zIqBK1uas0H4vxGxiXTOf95DSGnaVeAkTG6Fo5+6Wrns=; login_frequency=2");
		}else {
			httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
			httpGet.setHeader("Cookie","tvfe_boss_uuid=445be9551251cb95; pgv_pvid=5025078092; wxuin="+wxPerson.wxuin+"; mm_lang=zh_CN; webwxuvid=90a337598d4ed2c2b9be1012139f45c16e0103baa57ac785176f0d08ed900e5500b58ab585cb88f3230faf3f019a5dea; MM_WX_NOTIFY_STATE=1; MM_WX_SOUND_STATE=1; refreshTimes=5; last_wxuin=483476408; wxpluginkey=1539419762; wxsid="+wxPerson.wxsid+"; wxloadtime="+System.currentTimeMillis()+"; webwx_data_ticket="+wxPerson.pass_ticket +";webwx_auth_ticket=CIsBEN+H+vgOGoABNBX0up/LXqWgcaIGceI2uHJRP6NfPe84fFWwBxPZy5NmHu27w0PvVhH6iFOWaz4ep/7eUzWmbGtLHLNztABJIR82fwprpPvG9RcMuyH+8ar0jZSd6sYFiSLZivXKuNnf4j/jBgMXSuN6oAjbhA9RoigqKGtQKHV+2I+huOVStvI=; login_frequency=2");
		}

		return httpGet;
	}
	
	public static String characters = "0123456789";
    public static String randomNum(int factor){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < factor; i++) {
            // nextInt(10) = [0, 10)
            sb.append(characters.charAt(random.nextInt(10)));
        }
        return sb.toString();
    }

    //public static HttpPost createHttpPost(String url){
		//HttpPost httpPost = new HttpPost(url);
		//httpPost.setHeader("Accept", "application/json, text/plain, */*");
		//httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
		//httpPost.setHeader("Host", "wx2.qq.com");
		//httpPost.setHeader("Pragma", "no-cache");
		//httpPost.setHeader("Accept-Language","zh-CN,zh;q=0.9");
		//httpPost.setHeader("Accept-Encoding","gzip, deflate, br");
		//httpPost.setHeader("Cookie","tvfe_boss_uuid=445be9551251cb95; pgv_pvid=5025078092; wxuin=483476408; mm_lang=zh_CN; webwxuvid=90a337598d4ed2c2b9be1012139f45c16e0103baa57ac785176f0d08ed900e5500b58ab585cb88f3230faf3f019a5dea; MM_WX_NOTIFY_STATE=1; MM_WX_SOUND_STATE=1; refreshTimes=5; last_wxuin=483476408; wxpluginkey=1539419762; wxsid="+wxPerson.wxsid+"; wxloadtime="+System.currentTimeMillis()+"; webwx_data_ticket="+WXConfig.pass_ticket +";webwx_auth_ticket=CIsBEN+H+vgOGoABNBX0up/LXqWgcaIGceI2uHJRP6NfPe84fFWwBxPZy5NmHu27w0PvVhH6iFOWaz4ep/7eUzWmbGtLHLNztABJIR82fwprpPvG9RcMuyH+8ar0jZSd6sYFiSLZivXKuNnf4j/jBgMXSuN6oAjbhA9RoigqKGtQKHV+2I+huOVStvI=; login_frequency=2");
		//httpPost.setHeader("Referer", "https://wx2.qq.com/?&lang=zh_CN");
		//httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
		//httpPost.setHeader("Connection", "keep-alive");
		//httpPost.setHeader("Accept", "application/json, text/plain, */*");
		//httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
		//httpPost.setHeader("Accept", "application/json, text/plain, */*");
		//httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
		//httpPost.setHeader("Host", "wx.qq.com");
		//httpPost.setHeader("Pragma", "no-cache");
		//httpPost.setHeader("Referer", "https://wx2.qq.com/?&lang=zh_CN");
		//httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
		//httpPost.setHeader("Connection", "keep-alive");
		//return httpPost;
	//}
	
    public static JSONObject SyncKey;
}