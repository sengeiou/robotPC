package action.wx;


import action.common.HttpClient;
import action.service.BaseService;
import action.service.MessageService;
import com.alibaba.fastjson.JSONObject;
import common.BaseCache;
import common.PropertiesConf;

import java.util.HashMap;
import java.util.Map;

public class WXFilter extends BaseService implements Runnable{
	static Map<String,WXUser> map = new HashMap<>();
	public static void filter(String Content, String FromUserName, String ToUserName,WXPerson wxPerson) {
		System.out.println("content: "+Content);
		WXUser wxUser = map.get(ToUserName);
		if(null == wxUser){
			WXUser user = new WXUser();
			user.setUserName(ToUserName);
			user.setFlag("0");
			map.put(ToUserName,user);
		}
		wxUser = map.get(ToUserName);
		String flag = wxUser.getFlag();
		String userName = wxUser.getUserName();
		if(Content.indexOf("<br/>") !=-1) {
			Content= Content.substring( Content.indexOf("<br/>")+5,Content.length());
			System.out.println("sssssssssssss" + Content);
		}
		if (Content.equals("会员") && !wxUser.getFlag().equals("1")) {
			WXSendmsg.sendMsg(1, "您好，您需要什么会员,请回复001，爱奇艺。。。", FromUserName, ToUserName,wxPerson);
		} else if (Content.equals("001") && !wxUser.getFlag().equals("1")) {
			String phone = HttpClient.sendHttp("http://172.16.40.187/wx/gp");
			if (phone.equals("0")) {
				WXSendmsg.sendMsg(1, "暂时没有空闲，请稍后重试", FromUserName, ToUserName,wxPerson);
			} else {
				new Thread() {
					public void run() {
						WXSendmsg.sendMsg(1, "账号:" + phone + ";请尽快登录。", FromUserName, ToUserName,wxPerson);
						for (int i = 0; 1 < 100; i++) {
							try {
								Thread.sleep(10000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							String sms = HttpClient.sendHttp("http://172.16.40.187/wx/gsms?phone=" + phone);
							if (sms.equals("")) {
								continue;
							} else {
								WXSendmsg.sendMsg(1, sms, FromUserName, ToUserName,wxPerson);
								i = 100;
								break;
							}
						}
					};
				}.start();
			}
		}else if(Content.equals("002") && !wxUser.getFlag().equals("1")){
			//String filePath = PropertiesConf.WECHAT_IMAGE_LOACH_PATH+"002.jpg";
			String filePath = "D:\\picture/cc.jpg";
			String uIn = wxPerson.wxuin;
			String sId = wxPerson.wxsid;
			String sKey = wxPerson.skey;
			//(String filePath,String cookie,String uIn,String sId,String sKey,String fileName,String fromUserName,String toUserName)
			WXSendmsg.sendFiled(filePath,uIn,sId,sKey,FromUserName,ToUserName,wxPerson);
		}else if(Content.equals("人工服务")){
			for(WXUser users : map.values()){
				if(users.getFlag().equals("1")){
					WXSendmsg.sendMsg(1, "人工客服正忙，请稍后重试", FromUserName, ToUserName,wxPerson);
					return;
				}
			}
			WXUser wxUser1 = map.get(ToUserName);
			wxUser1.setFlag("1");
			WXSendmsg.sendMsg(1, Content, FromUserName, PropertiesConf.TO_USER_NAME,wxPerson);
			MessageService.saveRobotMessage(Content,FromUserName,ToUserName,"1", BaseCache.getDateTime(),BaseCache.getDateTime());
		}else if(wxUser.getFlag().equals("1")){
			WXSendmsg.sendMsg(1, Content, FromUserName, PropertiesConf.TO_USER_NAME,wxPerson);
			MessageService.saveRobotMessage(Content,FromUserName,ToUserName,"1",BaseCache.getDateTime(),BaseCache.getDateTime());
		}else if(ToUserName.equals(PropertiesConf.TO_USER_NAME)){
			for (WXUser value : map.values()) {
				if(value.getFlag().equals("1")){
					WXSendmsg.sendMsg(1, Content, FromUserName, value.getUserName(),wxPerson);
					MessageService.saveRobotMessage(Content,FromUserName,ToUserName,"1",BaseCache.getDateTime(),BaseCache.getDateTime());
				}
			}
		}
	}

	@Override
	public void run() {
		System.out.println("3分钟未收到信息终止客服服务");
		String robotMessage = MessageService.findRobotMessage();
		long endTime = Long.valueOf(JSONObject.parseObject(robotMessage).getJSONArray("rs").getJSONObject(0).getString("end_time"));
		long currentTime = Long.valueOf(BaseCache.getDateTime());
		if(currentTime-endTime>180){
			for(WXUser users : map.values()){
				if(users.getFlag().equals("1")){
					WXUser wxUser1 = map.get(users.getUserName());
					wxUser1.setFlag("0");
					return;
				}
			}
		}
	}
}