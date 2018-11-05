package action;

import action.service.PictureService;
import common.StringHandler;
import servlet.BaseServlet;

import javax.servlet.annotation.WebServlet;

/**
 * chrysaor使用
 * @author cuiw
 */
@WebServlet(name = "Picture", urlPatterns = "/picture")
public class PictureAction extends BaseServlet {

    /**
     * 查询所有的图片分类
     * @return
     */
    public String getPictureCategoryInfo(){
        String res = PictureService.getPictureCategoryInfo();
        return StringHandler.getRetString(res);
    }

    public String insertPictureCategory(){

        return null;
    }

}
