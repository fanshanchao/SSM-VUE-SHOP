package fsc.site.util;

import com.alibaba.fastjson.JSONObject;
import fsc.site.pojo.ResultGoods;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @author fanshanchao
 * @date 2019/5/6 22:58
 * 用于连接elasticsearch客户端的工具
 */
public class ElasticsearchUtil {
    //elasticsearch服务器的地址
    private static final String HOST = "120.76.56.254";
    //elasticsearch服务器的端口号
    private static final int PORT = 9300;
    //elasticsearch客户端
    private  TransportClient client = null;
    public ElasticsearchUtil(){
        //设置集群信息 注意别加这个 加就错put("client.transport.sniff", true)
        Settings settings = Settings.builder().put("cluster.name","my-application").build();
        //创建客户端
        try {
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddresses(new TransportAddress(InetAddress.getByName(HOST), PORT));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加/更新一个商品到elasticsearch中去
     * @param goods
     */
    public void putGoods(ResultGoods goods){
        client.prepareIndex("shop", "goods",goods.getGoodsId().toString()).setSource(JSONObject.toJSONString(goods), XContentType.JSON).get();
    }

    /**
     * 添加/更新一个列表商品到elasticsearch
     * @param list
     */
    public void putGoodsList(List<ResultGoods> list){
        for(ResultGoods goods:list){
            client.prepareIndex("shop", "goods",goods.getGoodsId().toString()).setSource(JSONObject.toJSONString(goods), XContentType.JSON).get();
        }
    }

    /**
     * 在elasticsearch中删除某个商品
     * @param goodsId
     */
    public void deleteGoods(Integer goodsId){
        client.prepareDelete("shop", "goods", goodsId.toString()).get();
    }
    /**
     * 关闭连接
     */
    public void closeConnection(){
        client.close();
    }
}
