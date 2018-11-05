package action.common;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName ResultJSONUtil
 * @Description TODO
 * @Author yanhuo
 * @Date 2018/10/15 15:20
 * @Version 1.0
 **/
public class ResultJSONUtil {
    public static String success(Object str){
        JSONObject resoult = new JSONObject();
        resoult.put("code","0");
        resoult.put("message","");
        resoult.put("result",str);
        return resoult.toString();
    }
}
