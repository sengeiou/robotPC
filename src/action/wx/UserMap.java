package action.wx;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UserMap
 * @Description TODO
 * @Author yanhuo
 * @Date 2018/10/18 8:56
 * @Version 1.0
 **/
public class UserMap {
    private UserMap(){}
    private static  Map<String,WXPerson> userMap = null;
    public static Map getUserMap(){
        if(userMap == null) {
             userMap = new HashMap<>();

        }
        return userMap;
    }
}
