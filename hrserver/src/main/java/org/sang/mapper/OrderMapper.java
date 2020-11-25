package org.sang.mapper;

import org.apache.ibatis.annotations.Param;
import org.sang.bean.Employee;
import org.sang.bean.Member;
import org.sang.bean.Nation;
import org.sang.bean.Order;
import org.sang.bean.PoliticsStatus;
import org.sang.bean.TransactionRecords;

import java.util.Date;
import java.util.List;

/**
 * Created by lizhiqiang on 2020/11/18.
 */
public interface OrderMapper {
    int addOrder(Order order);

    List<Order> getOrderByPhone(@Param("phoneNumber") String phoneNumber, @Param("memberCardNumber") String memberCardNumber);

    Long getCountByPhone(@Param("phoneNumber") String phoneNumber, @Param("memberCardNumber") String memberCardNumber);
    

	int deleteOrderBymemberId(@Param("memberId") Long memberId);
	
	int isrOrderToHistory(@Param("memberId") Long memberId);
	
	List<Order> getOrderHistoryByPhone(@Param("phoneNumber") String phoneNumber, @Param("memberCardNumber") String memberCardNumber,@Param("start") int start, @Param("size") Integer size);

    Long getOrderHistoryCountByPhone(@Param("phoneNumber") String phoneNumber, @Param("memberCardNumber") String memberCardNumber);
    
    List<TransactionRecords> getTransHistoryByPhone(@Param("phoneNumber") String phoneNumber,@Param("start") int start, @Param("size") Integer size);

    Long getTransHistoryCountByPhone(@Param("phoneNumber") String phoneNumber);
    
}
