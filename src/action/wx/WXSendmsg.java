package action.wx;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class WXSendmsg{
	//发送文本消息
	public static void sendMsg(Integer Type,String Content,String FromUserName,String ToUserName,WXPerson wxPerson) {
		JSONObject msg = new JSONObject();
		msg.put("Type", Type);
		msg.put("Content",Content);
		msg.put("FromUserName", FromUserName);
		msg.put("ToUserName", ToUserName);
		msg.put("LocalID", System.currentTimeMillis()+"0132");
		msg.put("ClientMsgId", System.currentTimeMillis()+"0132");
		
		JSONObject jsxsj = new JSONObject();
		JSONObject job = new JSONObject();
		job.put("Uin",wxPerson.wxuin );
		job.put("Sid",wxPerson.wxsid );
		job.put("Skey",wxPerson.skey );
		job.put("DeviceID",  "e" + WXConfig.randomNum(15));
		
		jsxsj.put("BaseRequest", job);
		jsxsj.put("Msg", msg);
		jsxsj.put("Scene", 0);
		
		String url2 = wxPerson.WXHost+"cgi-bin/mmwebwx-bin/webwxsendmsg?lang=zh_CN&pass_ticket="+wxPerson.pass_ticket;
		HttpPost method2 = new HttpPost(url2);
		method2.setHeader("Accept", "application/json, text/plain, */*");
		method2.setHeader("Content-Type", "application/json;charset=UTF-8");
		System.out.println("-----------------------------------");
		System.out.println(jsxsj.toString());
		
		StringEntity stringEntityx = new StringEntity(jsxsj.toString(),"utf-8");
		method2.setEntity(stringEntityx);

		try {
			HttpResponse responsex = WXConfig.https.execute(method2);
			HttpEntity entitySortx = responsex.getEntity();
			String senthtml = EntityUtils.toString(entitySortx, "utf-8");
			System.out.println(senthtml);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 发送文件 1、上传文件到微信服务器 2、获取消息id发送给好友
	 */
	public static void sendFiled(String filePath,String uIn,String sId,String sKey,String fromUserName,String toUserName,WXPerson wxPerson) throws JSONException {
		String mediaType = "3";
		String Wdeviceid = "e" + WXConfig.randomNum(15);
		String WpassTicket = wxPerson.pass_ticket;
		String webwxDataTicket = wxPerson.pass_ticket;
		//1、上传文件到微信服务器
		String result = upload(filePath,uIn,sId,sKey,Wdeviceid,WpassTicket,webwxDataTicket,fromUserName,toUserName,mediaType,wxPerson);//执行图片上传，返回流媒体id。PS：微信网页版中的发送文件/图片/等分为两步1.上传到服务器拿到返回的mediaId,2.发送通知消息
		JSONObject json = JSONObject.parseObject(result);
		System.out.println("上传返回的结果："+json);
		String mediaId = json.get("MediaId").toString();
		System.out.println("*******文件消息ID："+json.get("MediaId"));
		//发送图片
		Long currentTimeMillis = System.currentTimeMillis();
		String jsonParamsByFile = "{\"BaseRequest\":{\"Uin\":"+uIn+",\"Sid\":\""+sId+"\",\"Skey\":\""+sKey+"\",\"DeviceID\":\""+Wdeviceid+"\"},\"Msg\":{\"Type\":3,"+"\"MediaId\":\""+mediaId+"\",\"FromUserName\":\""+fromUserName+"\",\"ToUserName\":\""+toUserName+"\",\"LocalID\":\""+currentTimeMillis+"\",\"ClientMsgId\":\""+currentTimeMillis+"\"},\"Scene\":0}";
		String cookie  = "mm_lang=zh_CN; MM_WX_NOTIFY_STATE=1; MM_WX_SOUND_STATE=1; refreshTimes=5; wxuin=483476408; webwxuvid=90a337598d4ed2c2b9be1012139f45c13b1e8a25d6e7bca852428f2b625453a8f8f03020c6da504784251a8b7681318a; last_wxuin=483476408; wxsid="+sId+"; webwx_data_ticket="+wxPerson.pass_ticket+"; webwx_auth_ticket=CIsBEPuAqnwagAFkxKlP41uXBk/kTgiPj6+DclE/o1897zh8VbAHE9nLkwyURrsQ/9485MBZVZ0ldbS9lC/VikJhODZ2jvtRGmwAcXSYS08xyHiYBk982nuNNmScHbfmGZg+1AKwVFMx+oCDjVCU3UcHpKi5+E53u4hLKCooa1AodX7Yj6G45VK28g==; login_frequency=1; wxloadtime="+System.currentTimeMillis()+"_expired; wxpluginkey=1539678603";
		System.out.println("++++++++++cookie:"+cookie);
		String url = wxPerson.WXHost+"cgi-bin/mmwebwx-bin/webwxsendmsgimg?fun=async&f=json&lang=zh_CN&pass_ticket="+webwxDataTicket;
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		post.addHeader(new BasicHeader("Cookie", cookie));//发送文件必须设置,cookie

		try {
			StringEntity s = new StringEntity(jsonParamsByFile);
			post.setEntity(s);
			HttpResponse res = client.execute(post);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity entity = res.getEntity();
				System.out.println("发送图片》》》》》》》》》》》》》》》》》》"+EntityUtils.toString(entity, "utf-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//1、上传图片到微信服务器
	public static String upload(String filePath,String WuIn,String WsId,String WsKey,String Wdeviceid,String WpassTicket,String webwxDataTicket,String WfromUserName,String toUserName,String type,WXPerson wxPerson) {
		HttpClient https;
		CookieStore cookieStore = new BasicCookieStore();
		https = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		String domain="";
		if(!"https://wx2.qq.com/".equals(wxPerson.WXHost)){
			 domain = wxPerson.WXHost.substring(8,10);
		}else {
			 domain = wxPerson.WXHost.substring(8,11);
		}
		String uIn = WuIn;
		String sId = WsId;
		String sKey = WsKey;
		String deviceid = Wdeviceid;
		String passTicket =  WpassTicket;
		String fromUserName = WfromUserName;

		String response = null;
		try {
			File file = new File(filePath);
			if (!file.exists() || !file.isFile()) {
				throw new IOException("文件不存在");
			}

			String urlObj = "https://file."+domain+".qq.com/cgi-bin/mmwebwx-bin/webwxuploadmedia?f=json";

			MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
			mEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			mEntityBuilder.setCharset(Charset.forName("utf-8"));
			mEntityBuilder.addTextBody("id", "WU_FILE_1");
			mEntityBuilder.addTextBody("type", type);
			mEntityBuilder.addTextBody("lastModifiedDate", getGMT8Time().toString() );
			mEntityBuilder.addTextBody("mediatype", "pic");
			mEntityBuilder.addTextBody("uploadmediarequest", "{\"UploadType\":2,\"BaseRequest\":{\"Uin\":"+uIn+",\"Sid\":\""+sId+"\",\"Skey\":\""+sKey+"\",\"DeviceID\":\""+deviceid+"\"},\"ClientMediaId\":"+System.currentTimeMillis()+",\"TotalLen\":"+file.length()+",\"StartPos\":0,\"DataLen\":"+file.length()+",\"MediaType\":4,\"FromUserName\":\""+fromUserName+"\",\"ToUserName\":\""+toUserName+"\",\"FileMd5\":\""+getFileMD5String(file)+"\"}");
			mEntityBuilder.addTextBody("webwx_data_ticket", webwxDataTicket);
			mEntityBuilder.addTextBody("pass_ticket", passTicket);
			System.out.println("{\"UploadType\":2,\"BaseRequest\":{\"Uin\":"+uIn+",\"Sid\":\""+sId+"\",\"Skey\":\""+sKey+"\",\"DeviceID\":\""+deviceid+"\"},\"ClientMediaId\":"+System.currentTimeMillis()+",\"TotalLen\":"+file.length()+",\"StartPos\":0,\"DataLen\":"+file.length()+",\"MediaType\":4,\"FromUserName\":\""+fromUserName+"\",\"ToUserName\":\""+toUserName+"\",\"FileMd5\":\""+getFileMD5String(file)+"\"}");

			mEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			mEntityBuilder.addBinaryBody("filename", file, ContentType.DEFAULT_BINARY, file.getName());

			HttpPost method2 = new HttpPost(urlObj);
			method2.setEntity(mEntityBuilder.build());
			System.out.println("上传文件到服务器："+method2.toString());
			method2.setHeader("Content-Type", "multipart/form-data");
			method2.setHeader("Host", "file."+domain+".qq.com");
			System.out.println("上传图片TUR："+wxPerson.WXHost.substring(0,18));
			if(!"https://wx2.qq.com/".equals(wxPerson.WXHost)) {
				method2.setHeader("Origin", wxPerson.WXHost.substring(0, 17));
			}else {
				method2.setHeader("Origin", wxPerson.WXHost.substring(0, 18));
			}

			HttpResponse responsex = https.execute(method2);
			HttpEntity entitySortx = responsex.getEntity();
			response = EntityUtils.toString(entitySortx, "utf-8");

			System.out.println("上传文件到服务器响应："+response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public static Date getGMT8Time(){
		Date gmt8 = null;
		try {
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+0800"), Locale.CHINESE);
			Calendar day = Calendar.getInstance();
			day.set(Calendar.YEAR, cal.get(Calendar.YEAR));
			day.set(Calendar.MONTH, cal.get(Calendar.MONTH));
			day.set(Calendar.DATE, cal.get(Calendar.DATE));
			day.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
			day.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
			day.set(Calendar.SECOND, cal.get(Calendar.SECOND));
			gmt8 = day.getTime();
		} catch (Exception e) {
			System.out.println("获取GMT8时间 getGMT8Time() error !");
			e.printStackTrace();
			gmt8 = null;
		}
		return  gmt8;
	}

	public static String getFileMD5String(File file) throws IOException, NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		FileInputStream in = new FileInputStream(file);
		FileChannel ch = in.getChannel();
		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
		messageDigest.update(byteBuffer);
		return byteArrayToHex(messageDigest.digest());
	}
	private static String byteArrayToHex(byte[] byteArray) {
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		char[] resultCharArray = new char[byteArray.length * 2];
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		return new String(resultCharArray);
	}


}