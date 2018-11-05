package action;

import action.service.SoftTextService;
import common.Utils;
import servlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;

/**
 * yanhuo
 * 2018-9-29
 * 推广文案
 */
@WebServlet(name = "softText", urlPatterns = "/softText")
public class SoftTextAction extends BaseServlet {

    /**
     * 添加推文
     * @param softTextTitle
     * @param softTextAuthor
     * @param content
     * @param request
     * @return
     */
    public String addSoftText(String softTextTitle,String softTextAuthor,String content,HttpServletRequest request){
        if(softTextTitle ==null || "".equals(softTextTitle) || softTextAuthor == null || "".equals(softTextAuthor)
                || content == null || "".equals(content)){
            return "参数有误！";
        }
        HttpSession session=request.getSession();
        int userId=Integer.valueOf(session.getAttribute("userId").toString());
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(date);
        String createTime = Utils.transformToYYMMddHHmmss(str);
        String s = SoftTextService.saveSoftText(softTextTitle, softTextAuthor, content,createTime,userId);
        return s;
    }

    /**
     * 查询推文列表
     * @param softTextTitle
     * @param state
     * @param begin
     * @param end
     * @param page
     * @param limit
     * @return
     */
    public String getSoftText(String softTextTitle,String state,String begin,String end,String page,String limit){
        int pageI = Integer.valueOf(page);
        int limitI = Integer.valueOf(limit);
        String softTextlist = SoftTextService.getSoftTextlist(softTextTitle, state, begin, end, (pageI-1)*limitI, limitI);
        return softTextlist;
    }
}
