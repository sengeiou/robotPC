package action.service;

import cache.ResultPoor;

/**
 * @author cuiw
 * @decription cuiw
 */

public class PictureService extends BaseService{

    public static String getPictureCategoryInfo(){
        int sid = sendObject(476);
        String res = ResultPoor.getResult(sid);
        return res;
    }

    public static String insertPictureCategory(){

        return null;
    }

}
