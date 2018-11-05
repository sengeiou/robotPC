package action.common;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClient {
	public static String sendHttp(String url) {
		try {
			HttpPost method5 = new HttpPost(url);
			CloseableHttpClient httpsssss = HttpClients.createDefault();
			HttpResponse responsexxxxxx = httpsssss.execute(method5);
			HttpEntity entitySortxxxxx = responsexxxxxx.getEntity();
			return EntityUtils.toString(entitySortxxxxx, "utf-8");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}