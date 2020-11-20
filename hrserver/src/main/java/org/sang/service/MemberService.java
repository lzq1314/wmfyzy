package org.sang.service;

import org.apache.ibatis.annotations.Param;
import org.sang.bean.Employee;
import org.sang.bean.Member;
import org.sang.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    /*SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    SimpleDateFormat birthdayFormat = new SimpleDateFormat("yyyy-MM-dd");
    DecimalFormat decimalFormat = new DecimalFormat("##.00");*/

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

    public int addTransactionRecords() {
        //
        Member m = new Member();
        m.setId();
        m.setBalance()
        int updateMember(@Param("member") Member member);
        int addTransactionRecords(TransactionRecords member);


        return memberMapper.updateMember(member);
    }

}
