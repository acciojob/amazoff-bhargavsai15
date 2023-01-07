package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {

    HashMap<String,Order> orderDb;
    HashMap<String,DeliveryPartner> deliveryDb;

    HashMap<String, List<String>> orderDeliveryPair;

    List<String> allOrders;

//    HashMap<String,>

    public OrderRepository() {
        orderDb=new HashMap<>();
        deliveryDb=new HashMap<>();
        orderDeliveryPair=new HashMap<>();
        allOrders=new ArrayList<>();
    }


    public void addOrder(Order order){
        String key=order.getId();
        orderDb.put(key,order);
        allOrders.add(key);
    }

    public void addPartner(String partnedId){
        deliveryDb.put(partnedId,new DeliveryPartner(partnedId));

    }

    public void addOrderPartnerPair(String orderId,String partnerId){
        if(!orderDeliveryPair.containsKey(partnerId)){
            orderDeliveryPair.put(partnerId,new ArrayList<>());
        }

        //If already order is present
        for(String s:orderDeliveryPair.get(partnerId)){
            if(s.equals(orderId)){
               return;
            }
        }
        orderDeliveryPair.get(partnerId).add(orderId);

    }

    public Order getOrderById(String orderId){
        return orderDb.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId){
        return deliveryDb.get(partnerId);
    }

    public int getOrderCountByPartnerId(String partnerId){
        for(String id:orderDeliveryPair.keySet()){
            if(id.equals(partnerId)){
                return orderDeliveryPair.get(id).size();
            }
        }
        return 0;
    }

    public List<String> getOrdersByPartnerId(String partnerId){
        List<String> orders=new ArrayList<>();
        for(String id:orderDeliveryPair.keySet()){
            if(id.equals(partnerId)){
                for(String order:orderDeliveryPair.get(id)){
                    orders.add(order);
                }
            }
        }
        return orders;
    }

    public List<String> getAllOrders(){
        List<String> res=new ArrayList<>();
        for(String id:orderDb.keySet()){
            res.add(id);
        }
        return res;
    }

    public int getCountOfUnassignedOrders(){
        for(String order:orderDb.keySet()){
            if(!allOrders.contains(order)){
                allOrders.add(order);
            }
        }
        for(List<String> values:orderDeliveryPair.values()){
            for(String ele:values){
                if(allOrders.contains(ele)){
                    allOrders.remove(ele);
                }
            }
        }

        return allOrders.size();
    }

    public void deleteOrderById(String orderId){
        outer:
        for(String partnerId:orderDeliveryPair.keySet()){
            for(String orders:orderDeliveryPair.get(partnerId)){
                if(orders.equals(orderId)) {
                    orderDeliveryPair.get(partnerId).remove(orderId);
                    deliveryDb.remove(partnerId);
                    break outer;
                }
            }
        }
        orderDb.remove(orderId);
    }

    public void deletePartnerById(String partnerId){

    }

}
