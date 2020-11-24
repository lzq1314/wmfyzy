package org.sang.service;

import org.apache.ibatis.annotations.Param;
import org.sang.bean.Employee;
import org.sang.bean.Member;
import org.sang.bean.TransactionRecords;
import org.sang.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by sang on 2018/1/12.
 */
@Service
@Transactional
public class MemberService {
    @Autowired
    MemberMapper memberMapper;

    public int addMember(Member member) {
        return memberMapper.addMember(member);
    }

    public List<Member> getMemberList(Integer page, Integer size,String keywords,Date startBeginDate,Date endBeginDate,String phoneNumber) {
        int start = (page - 1) * size;
        return memberMapper.getMemberByPage(start, size,keywords,startBeginDate,endBeginDate,phoneNumber);
    }

    public Long getCountByKeywords(String keywords,Date startBeginDate,Date endBeginDate,String phoneNumber) {
        return memberMapper.getCountByKeywords(keywords,startBeginDate,endBeginDate,phoneNumber);
    }

    public int updateMember(Member member) {
        return memberMapper.updateMember(member);
    }

    public int addTransactionRecords(String id,BigDecimal amount) {
    	Member member = memberMapper.getOneMemberById(Long.valueOf(id));//根据id查询会员信息
    	
    	
        Member m = new Member();
        m.setId(member.getId());
        Double balance = (new BigDecimal(member.getBalance()).add(amount)).doubleValue();
        m.setBalance(balance);
        memberMapper.updateMember(m);//更新会员的余额
        
        TransactionRecords records = new TransactionRecords();
        records.setTransaction_name("充值");
        records.setAmount(balance);
        records.setPhoneNumber(member.getPhoneNumber());
        records.setMemberCardNumber(member.getMemberCardNumber());
        records.setMember_id(member.getId());
        return memberMapper.addTransactionRecords(records);//插入交易记录
    }

    public Member getOneMemberByPhone(String phoneNumber) {
        return memberMapper.getOneMemberByPhone(phoneNumber);
    }
}
