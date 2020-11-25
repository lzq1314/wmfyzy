package org.sang.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.sang.bean.Commodity;
import org.sang.bean.Member;
import org.sang.bean.Order;
import org.sang.bean.TransactionRecords;
import org.sang.mapper.CommodityMapper;
import org.sang.mapper.MemberMapper;
import org.sang.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by sang on 2018/1/12.
 */
@Service
@Transactional
public class OrderService {
    @Autowired
    OrderMapper orderMapper;
    
    @Autowired
    MemberMapper memberMapper;
    
    @Autowired
    CommodityMapper commodityMapper;

    public int addOrder(Order order) {
        return orderMapper.addOrder(order);
    }

    public List<Order> getOrderByPhone(String phoneNumber,String memberCardNumber) {
        return orderMapper.getOrderByPhone(phoneNumber,memberCardNumber);
    }

    public Long getCountByPhone(String phoneNumber,String memberCardNumber) {
        return orderMapper.getCountByPhone(phoneNumber,memberCardNumber);
    }
    public Member getOneMemberByPhone(String phoneNumber) {
        return memberMapper.getOneMemberByPhone(phoneNumber);
    }
    
    public List<Commodity> getCommodityById() {
        return commodityMapper.getCommodityById();
    }
    
    public List<Map<String, Object>> getOrdersCommodityByMemberId(Long memberId) {
        return commodityMapper.getOrdersCommodityByMemberId(memberId);
    }
    
    public int deleteOrderBymemberId(Long memberId) {
        return orderMapper.deleteOrderBymemberId(memberId);
    }
    
    public int doSettlement(Member member) {
    	//1、根据phoneNumber将所有的订单数据转移到订单历史表
    	//2、根据phoneNumber删除订单表的数据
    	//3、减除余额
    	orderMapper.isrOrderToHistory(member.getId());
    	orderMapper.deleteOrderBymemberId(member.getId());
    	return memberMapper.updateMember(member);
    }
    
    public List<Order> getOrderHistoryByPhone(String phoneNumber,String memberCardNumber,Integer page, Integer size) {
    	int start = (page - 1) * size;
    	return orderMapper.getOrderHistoryByPhone(phoneNumber,memberCardNumber,start,size);
    }

    public Long getOrderHistoryCountByPhone(String phoneNumber,String memberCardNumber) {
        return orderMapper.getOrderHistoryCountByPhone(phoneNumber,memberCardNumber);
    }
    
    public List<TransactionRecords> getTransHistoryByPhone(String phoneNumber,Integer page, Integer size) {
    	int start = (page - 1) * size;
    	return orderMapper.getTransHistoryByPhone(phoneNumber,start,size);
    }

    public Long getTransHistoryCountByPhone(String phoneNumber) {
        return orderMapper.getTransHistoryCountByPhone(phoneNumber);
    }
}
