package action.wx;


import action.common.HttpClient;
import common.PropertiesConf;

public class WXFilter {
	public static void filter(String Content, String FromUserName, String ToUserName,WXPerson wxPerson) {
		System.out.println("content: "+Content);
		if(Content.indexOf("<br/>") !=-1) {
			Content= Content.substring( Content.indexOf("<br/>")+5,Content.length());
			System.out.println("sssssssssssss" + Content);
		}
		if (Content.equals("会员")) {
			WXSendmsg.sendMsg(1, "您好，您需要什么会员,请回复001，爱奇艺。。。", FromUserName, ToUserName,wxPerson);
		} else if (Content.equals("001")) {
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
		}else if(Content.equals("002")){
			//String filePath = PropertiesConf.WECHAT_IMAGE_LOACH_PATH+"002.jpg";
			String filePath = "D:\\picture/cc.jpg";
			String uIn = wxPerson.wxuin;
			String sId = wxPerson.wxsid;
			String sKey = wxPerson.skey;
			//(String filePath,String cookie,String uIn,String sId,String sKey,String fileName,String fromUserName,String toUserName)
			WXSendmsg.sendFiled(filePath,uIn,sId,sKey,FromUserName,ToUserName,wxPerson);
		}
	}
}