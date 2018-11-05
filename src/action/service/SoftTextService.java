package action.service;

import cache.ResultPoor;
import common.StringHandler;
import common.Utils;

public class SoftTextService extends BaseService {

    public static String saveSoftText(String softTextTitle,String softTextAuthor,String content,String createTime,int editedUser){
        int sid = BaseService.sendObjectCreate(660,softTextTitle,softTextAuthor,content,createTime,editedUser);
        String result = ResultPoor.getResult(sid);
        return result;
    }

    public static String getSoftTextlist(String softTextTitle,String state,String begin,String end,int page,int limit){
        StringBuffer sql = new StringBuffer();
        if(softTextTitle!=null&&!"".equals(softTextTitle)){
            sql.append(" and soft_text_title ="+softTextTitle);
        }
        if(state !=null&&!"".equals(state)){
            sql.append(" and state = "+state);
        }
        if(begin!=null&&!"".equals(begin)){
            String created_date1 = Utils.transformToYYMMddHHmmss(begin);
            sql.append(" and create_time between '").append(created_date1).append("'");
        }
        if(end!=null&&!"".equals(end)){
            String end_date = Utils.transformToYYMMddHHmmss(end);
            sql.append(" and '").append(end_date).append("'");
        }
        sql.append(" order by create_time desc");
        int sid = BaseService.sendObjectBase(661, sql.toString(), page, limit);
        String result = ResultPoor.getResult(sid);
        return StringHandler.getRetString(result);

    }

}
