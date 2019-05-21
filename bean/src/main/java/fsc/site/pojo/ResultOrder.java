package fsc.site.pojo;

import java.util.List;

/**
 * @author fanshanchao
 * @date 2019/4/12 12:54
 * 返回给前端的order的json对象格式
 */
public class ResultOrder extends Order{
    List<ResultOrderGoods> resultOrderGoods;

    public List<ResultOrderGoods> getResultOrderGoods() {
        return resultOrderGoods;
    }

    public void setResultOrderGoods(List<ResultOrderGoods> resultOrderGoods) {
        this.resultOrderGoods = resultOrderGoods;
    }
    @Override
    public String toString() {
        return "ResultOrder{" +
                "resultOrderGoods=" + resultOrderGoods +
                '}';
    }
}
