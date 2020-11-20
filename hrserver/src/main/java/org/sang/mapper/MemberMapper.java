package org.sang.mapper;

import org.apache.ibatis.annotations.Param;
import org.sang.bean.Employee;
import org.sang.bean.Member;
import org.sang.bean.Nation;
import org.sang.bean.PoliticsStatus;
import org.sang.bean.TransactionRecords;

import java.util.Date;
import java.util.List;

/**
 * Created by lizhiqiang on 2020/11/18.
 */
public interface MemberMapper {
    int addMember(Member member);

    List<Member> getMemberByPage(@Param("start") Integer start, @Param("size") Integer size,
                                 @Param("keywords") String keyword, @Param("startBeginDate") Date startBeginDate,
                                 @Param("endBeginDate") Date endBeginDate,
                                 @Param("phoneNumber") String phoneNumber);

    Long getCountByKeywords(@Param("keywords") String keywords,@Param("startBeginDate") Date startBeginDate,@Param("endBeginDate") Date endBeginDate,@Param("phoneNumber") String phoneNumber);

    int updateMember(@Param("member") Member member);

    int addTransactionRecords(TransactionRecords member);
    
    Member getOneMemberById(@Param("id") Long id);

}
