package action;

import action.service.PddGoodsService;
import servlet.BaseServlet;

import javax.servlet.annotation.WebServlet;


@WebServlet(name = "pddGoods", urlPatterns = "/pddGoods")
public class PddGoodsAction extends BaseServlet {

    /**
     * 后台获取商品列表
     */
    public String getPddGoodsList(String goods_source,String spuCode,String spuName,String state,String price_min,String price_max,String page, String limit){
        int pageI = Integer.valueOf(page);
        int limitI = Integer.valueOf(limit);
        String res = PddGoodsService.getPddGoodsList(goods_source,spuCode,spuName, state, price_min, price_max, (pageI - 1) * limitI, limitI);
        return res;
    }

    /**
     * 更新商品上下架状态
     * @param id
     * @param state
     * @return
     */
    public String updateGoodsState(String id,String state){
        String res = PddGoodsService.updateGoods(id, state);
        return res;
    }
}
