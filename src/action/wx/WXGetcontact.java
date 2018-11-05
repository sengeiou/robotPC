package action.wx;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class WXGetcontact{
	public static String WXGetcontactList(WXPerson wxPerson){
		JSONObject memberListJson = new JSONObject();
		WXConfig.https = HttpClients.custom().setDefaultCookieStore(WXConfig.cookieStore).build();
		HashMap<String,String> fm = new HashMap<String,String>();
        String url3="";
        if(!"https://wx2.qq.com/".equals(wxPerson.WXHost)){
            //url3 = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxgetcontact?r="+System.currentTimeMillis()+"&seq=0&skey="+WXConfig.skey;
            url3 = wxPerson.WXHost + "cgi-bin/mmwebwx-bin/webwxgetcontact?lang=zh_CN&pass_ticket=" + wxPerson.pass_ticket + "&r=" + System.currentTimeMillis() + "&seq=0&skey=" + wxPerson.skey;
        }else {
            url3 = wxPerson.WXHost + "cgi-bin/mmwebwx-bin/webwxgetcontact?lang=zh_CN&pass_ticket=" + wxPerson.pass_ticket + "&r=" + System.currentTimeMillis() + "&seq=0&skey=" + wxPerson.skey;
        }
        System.out.println("get WXMemberList"+url3);
		HttpGet method3 = WXConfig.creatHttpGet(url3,wxPerson);
		try {
			HttpResponse response = WXConfig.https.execute(method3);
			HttpEntity entitySort = response.getEntity();
			String html = EntityUtils.toString(entitySort, "utf-8");
			//System.out.println("拉取好友列表result:"+html);
			
			 memberListJson = JSONObject.parseObject(html);
			//System.out.println("JSON拉取全部好友："+memberListJson);
			String memberCount = memberListJson.getString("MemberCount");
			System.out.println("拉取全部好友："+memberCount+"个");
			//JSONArray friList = memberListJson.getJSONArray("MemberList");
			/*for(int i =0;i<friList.size();i++) {
				JSONObject fr = friList.getJSONObject(i);
				String NickName = fr.getString("NickName");
				String RemarkName = fr.getString("RemarkName");
				
				if(NickName.equals("Constantine") || NickName.equals("what") || NickName.equals("what") || NickName.equals("彬哥") ||RemarkName.equals("春花") || RemarkName.equals("彬哥") || RemarkName.equals("每天惠-惠语") || NickName.equals("疾风") || RemarkName.equals("疾风") || NickName.equals("小萁") || RemarkName.equals("小萁")  || NickName.equals("妙心（唐满红）") || RemarkName.equals("妙心（唐满红）") || NickName.startsWith("我的好好老婆")|| RemarkName.startsWith("我的好好老婆")) {
					fm.put(fr.getString("UserName"), NickName);
					System.out.println("NickName:"+NickName);
					System.out.println("RemarkName:"+RemarkName);
				}
				
			}*/
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*String webwxDataTicket = "";
		List<Cookie> cookies = WXConfig.cookieStore.getCookies();
		for (int i = 0; i < cookies.size(); i++) {
            if (cookies.get(i).getName().equals("webwx_data_ticket")) {
            	webwxDataTicket = cookies.get(i).getValue();
            }
        }*/
		
		/*System.out.println("--------------------------------------------------------");
		System.out.println(webwxDataTicket);
		System.out.println("--------------------------------------------------------");*/
		return memberListJson.toString();
	}
}