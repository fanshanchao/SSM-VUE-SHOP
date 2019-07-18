package fsc.site.pojo;

import java.util.List;

/**
 * @author fanshanchao
 * @date 2019/4/11 18:03
 * 这个用来接受前端发来json数据的映射bean
 */
public class AcceptOrder extends Order{
    public AcceptOrder(){
        super();
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    private List<OrderItem> orderItems;
}
