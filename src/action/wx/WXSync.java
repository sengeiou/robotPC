package action.wx;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.formula.functions.T;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class WXSync {
	static Map<String, JSONObject> mapMsg = new HashMap<>();
	public static void WXSyncMessage(WXPerson wxPerson) {
		while (true) {
			String url4 = wxPerson.WXHost+"cgi-bin/mmwebwx-bin/webwxsync?sid=" + wxPerson.wxsid + "&skey=" + wxPerson.skey + "&lang=zh_CN&pass_ticket=" + wxPerson.pass_ticket;
			//System.out.println("循环查询最新消息："+url4);
			HttpPost method4 = new HttpPost(url4);
			method4.setHeader("Accept", "application/json, text/plain, */*");
			method4.setHeader("Content-Type", "application/json;charset=UTF-8");
			System.out.println("循环查看最新消息-----------------------------------");


			JSONObject job = new JSONObject();

			job.put("Uin", wxPerson.wxuin);
			job.put("Sid", wxPerson.wxsid);
			job.put("Skey", wxPerson.skey);
			job.put("DeviceID", "e" + WXConfig.randomNum(15));

			JSONObject webwxsyncJ = new JSONObject();
			webwxsyncJ.put("BaseRequest", job);
			webwxsyncJ.put("SyncKey", WXConfig.SyncKey);
			int now = (int) System.currentTimeMillis();
			webwxsyncJ.put("rr", (~now) + "");

			//System.out.println(webwxsyncJ.toString());
			try {
				StringEntity stringEntity = new StringEntity(webwxsyncJ.toString());
				method4.setEntity(stringEntity);

				HttpResponse response = WXConfig.https.execute(method4);
				HttpEntity entitySort = response.getEntity();
				String html = EntityUtils.toString(entitySort, "utf-8");
				//System.out.println(html);

				JSONObject jooo = JSONObject.parseObject(html);
				WXConfig.SyncKey = jooo.getJSONObject("SyncKey");

				JSONArray AddMsgList = jooo.getJSONArray("AddMsgList");
				for (int i = 0; i < AddMsgList.size(); i++) {
					JSONObject AddMsg = AddMsgList.getJSONObject(i);
					 String Content = AddMsg.getString("Content");
					String msgType = AddMsg.getString("MsgType");
					String msgId = AddMsg.getString("MsgId");
					String fromUserName = AddMsg.getString("FromUserName");
					String toUserName = AddMsg.getString("ToUserName");
					System.out.println("收到最新消息：msgId="+msgId+"messageType"+msgType+"Content"+Content);
					if("3".equals(msgType)){
						String url = "";
						if(!"https://wx2.qq.com/".equals(wxPerson.WXHost)) {
							url= wxPerson.WXHost.substring(0, 17);
						}else {
							url= wxPerson.WXHost.substring(0, 18);
						}
						reMessage(Content,fromUserName,toUserName,msgType,msgId,wxPerson.skey,url);
					 }else {
						reMessage(Content,fromUserName,toUserName,null,null,null,null);
					}
					WXFilter.filter(Content, AddMsg.getString("ToUserName"), AddMsg.getString("FromUserName"),wxPerson);

				}
				Thread.sleep(5000);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public static JSONObject reMessage(String message,String FromUserName,String ToUserName,String msgType,String MsgID,String skey,String url){
		JSONObject js = new JSONObject();
		if(null == message || "".equals(message)) {
			js = mapMsg.get(FromUserName);
			mapMsg.remove(FromUserName);
		}else {
			String newSkey="";
			String newUrl = "";
			if(null !=skey) {
				 newSkey = skey.replace("@", "%40");
			}
			js.put("message",message);
			js.put("FromUserName",FromUserName);
			js.put("msgId",MsgID);
			js.put("skey",newSkey);
			js.put("msgType",msgType);
			js.put("url",url);
			mapMsg.put(ToUserName, js);
		}
		return js;
	}

	public static void getImgMessage(String msgId,WXPerson wxPerson,String skey,HttpServletResponse res){
		String url = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxgetmsgimg?&MsgID=" + msgId
				+ "&skey="+skey+"&type=slave";
		System.out.println("getImgurl:"+url);
		HttpGet httpPost = new HttpGet(url);
		httpPost.setHeader("Host", "wx2.qq.com");
		httpPost.setHeader("Connection", "keep-alive");
		httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
		httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
		httpPost.setHeader("Referer", "https://wx2.qq.com/");
		httpPost.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
		httpPost.setHeader("Accept", "image/webp,image/apng,image/*,*/*;q=0.8");
		httpPost.setHeader("Cookie", "wxuin="+wxPerson.wxuin+"; webwxuvid=ee895109c290615aa8ecf51c8453ff11d9927e4557e79dc50eb6294186ba0040df0bdca731d2e322f7fa5e67ba2f9a1c; tvfe_boss_uuid=bb96d9fa59c2b064; pgv_pvid=5569382948; mm_lang=zh_CN; wxsid="+wxPerson.wxsid+"; webwx_data_ticket="+wxPerson.pass_ticket+"; webwx_auth_ticket=CIsBELKMvsUCGoAB43Lu1syqQeMqhTIB95OG+eQF5HbCXwUfQHfOOvH9v5/+lfSswQzVtgJHajW0HpYAkH2ig5wc1AIWVP1/haKFwIH7rLJrliigcwROgjJPUqXjK6eIWuljU3KYIFa1INTpzvSQM3sH0PLHmzS3t/QPDwsYeqj5y73CfMIjN7OoIDg=; MM_WX_NOTIFY_STATE=1; MM_WX_SOUND_STATE=1; wxloadtime="+System.currentTimeMillis()+"_expired; wxpluginkey=1541113442");
		int timeout = 200000;
		RequestConfig config = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
				.setConnectionRequestTimeout(timeout).build();
		httpPost.setConfig(config);
		String html = "";
		try {
			HttpResponse resoult = WXConfig.https.execute(httpPost);
			System.out.println("收到的图片："+resoult);
			InputStream inputStream = resoult.getEntity().getContent();
            /*String result = new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining(System.lineSeparator()));
            System.out.println("++++++++++++++++++++++++++++++"+result);*/
			byte data[] = readInputStream(inputStream);
			res.setHeader("Content-Type","image/jped");
			res.setContentType("image/jpg"); //设置返回的文件类型
			OutputStream os = res.getOutputStream();
			os.write(data);
			os.flush();
			os.close();
		}catch (Exception e) {
		e.printStackTrace();
		}
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while( (len=inStream.read(buffer)) != -1 ){
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		outStream.close();
		return outStream.toByteArray();
	}
}