package org.sang.controller.member;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sang.bean.Commodity;
import org.sang.bean.Member;
import org.sang.bean.Order;
import org.sang.bean.OrdersParams;
import org.sang.bean.RespBean;
import org.sang.bean.TransactionRecords;
import org.sang.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sang on 2018/1/12.
 */
@RestController
@RequestMapping("/shopping/basic/")
public class OrderBasicController {
	@Autowired
	OrderService orderService;

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@RequestMapping(value = "/addOrder", method = RequestMethod.POST)
	public RespBean addOrder(@RequestBody Map<String, Object> map) {
		List<Map<String, Object>> commodity = (List<Map<String, Object>>) map.get("commodity");
		String phoneNumber = map.get("phoneNumber").toString();
		Member m = orderService.getOneMemberByPhone(phoneNumber);
		// 删除之前的订单
		int deleteFlag = orderService.deleteOrderBymemberId(m.getId());
		if (deleteFlag == -1) {
			return RespBean.ok("添加失败!");
		}
		for (Map<String, Object> com : commodity) {
			if ((int) com.get("cnt") > 0) {
				Order order = new Order();
				order.setCommodity(Long.valueOf(com.get("id").toString()));
				order.setCommodity_amount(Double.valueOf(com.get("commodity_amount").toString()));
				order.setCommodity_count((int) com.get("cnt"));
				order.setCommodity_name(com.get("commodity_name").toString());
				order.setMember_id(m.getId());
				order.setMember_name(m.getName());
				order.setMemberCardNumber(m.getMemberCardNumber());
				order.setPhoneNumber(m.getPhoneNumber());
				orderService.addOrder(order);
			}
		}

		return RespBean.ok("添加成功!");
	}

	@RequestMapping(value = "/getOrderByPhone", method = RequestMethod.GET)
	public Map<String, Object> getOrderByPhone(String phoneNumber, String memberCardNumber) {
		Map<String, Object> map = new HashMap<>();
		List<Order> orders = orderService.getOrderByPhone(phoneNumber, memberCardNumber);
		Long count = orderService.getCountByPhone(phoneNumber, memberCardNumber);
		map.put("orders", orders);
		map.put("count", count);
		return map;
	}

	@RequestMapping(value = "/getOneMemberByPhone", method = RequestMethod.GET)
	public Map<String, Object> getOneMemberByPhone(String phoneNumber) {
		Map<String, Object> map = new HashMap<>();
		Member m = orderService.getOneMemberByPhone(phoneNumber);
		if(m == null) {
			map.put("member", null);
			return map;
		}
		map.put("member", m);
		// 查询当前用户订单数量
		List<Map<String, Object>> commodityList = orderService.getOrdersCommodityByMemberId(m.getId());
		map.put("commodityList", commodityList);
		return map;
	}

	@RequestMapping(value = "/getCommodity", method = RequestMethod.GET)
	public Map<String, Object> getCommodity() {
		Map<String, Object> map = new HashMap<>();
		List<Commodity> c = orderService.getCommodityById();
		map.put("commodity", c);
		return map;
	}
	
	@RequestMapping(value = "/doSettlement", method = RequestMethod.GET)
	public RespBean doSettlement(String phoneNumber,String amount) {
		Member m = orderService.getOneMemberByPhone(phoneNumber);
		BigDecimal bignum = new BigDecimal(amount);//消费金额
		BigDecimal bignum1 =new BigDecimal(m.getBalance());//原来的余额
		m.setBalance(bignum1.subtract(bignum).doubleValue());
		//根据用户的手机号将所有
		if(orderService.doSettlement(m) == 1) {
			return RespBean.ok("消费成功!");
		}
		return RespBean.error("消费失败");
	}
	
	@RequestMapping(value = "/getOrderHistoryByPhone", method = RequestMethod.GET)
	public Map<String, Object> getOrderHistoryByPhone(String phoneNumber, String memberCardNumber,@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
		Map<String, Object> map = new HashMap<>();
		List<Order> orders = orderService.getOrderHistoryByPhone(phoneNumber, memberCardNumber,page,size);
		Long count = orderService.getOrderHistoryCountByPhone(phoneNumber, memberCardNumber);
		map.put("orders", orders);
		map.put("count", count);
		return map;
	}
	
	@RequestMapping(value = "/getTransHistoryByPhone", method = RequestMethod.GET)
	public Map<String, Object> getTransHistoryByPhone(String phoneNumber,@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
		Map<String, Object> map = new HashMap<>();
		List<TransactionRecords> orders = orderService.getTransHistoryByPhone(phoneNumber,page,size);
		Long count = orderService.getTransHistoryCountByPhone(phoneNumber);
		map.put("orders", orders);
		map.put("count", count);
		return map;
	}

}
