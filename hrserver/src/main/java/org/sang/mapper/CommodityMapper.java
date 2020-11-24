package org.sang.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.sang.bean.Commodity;

/**
 * Created by lizhiqiang on 2020/11/18.
 */
public interface CommodityMapper {
	List<Commodity> getCommodityById();
	
	List<Map<String, Object>> getOrdersCommodityByMemberId(@Param("memberId") Long memberId);

}
