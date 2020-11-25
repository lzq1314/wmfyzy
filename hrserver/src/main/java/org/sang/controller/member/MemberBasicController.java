package org.sang.controller.member;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sang.bean.Member;
import org.sang.bean.RespBean;
import org.sang.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sang on 2018/1/12.
 */
@RestController
@RequestMapping("/member/basic")
public class MemberBasicController {
    @Autowired
    MemberService memberService;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(value = "/addMember", method = RequestMethod.POST)
    public RespBean addMember(Member member) {
        //现根据手机号查询用户是否存在，若存在则提示用户存在
    	Member m = memberService.getOneMemberByPhone(member.getPhoneNumber());
    	if(m != null) {
    		return RespBean.error("用户已存在!");
    	}
    	if (memberService.addMember(member) == 1) {
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @RequestMapping(value = "/getMemberList", method = RequestMethod.GET)
    public Map<String, Object> getMemberList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "") String keywords,
            String beginDateScope, String phoneNumber) {
        Map<String, Object> map = new HashMap<>();
        Date startBeginDate = null;
        Date endBeginDate = null;
        if (beginDateScope != null && beginDateScope.contains(",")) {
            try {
                String[] split = beginDateScope.split(",");
                startBeginDate = dateFormat.parse(split[0]);
                endBeginDate = dateFormat.parse(split[1]);
            } catch (ParseException e) {
                System.out.println(e);

            }
        }
        List<Member> membersByPage = memberService.getMemberList(page, size,keywords,startBeginDate,endBeginDate,phoneNumber);
        Long count = memberService.getCountByKeywords(keywords,startBeginDate,endBeginDate,phoneNumber);
        map.put("members", membersByPage);
        map.put("count", count);
        return map;
    }

    @RequestMapping(value = "/updateMember", method = RequestMethod.PUT)
    public RespBean updateEmp(Member member) {
    	//现根据手机号查询用户是否存在，若存在则提示用户存在
    	Member m = memberService.getOneMemberByPhone(member.getPhoneNumber());
    	if(m.getId() != member.getId()) {
    		return RespBean.error("更新的用户手机号码已存在!");
    	}
    	if (memberService.updateMember(member) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @RequestMapping(value = "/addMoney", method = RequestMethod.POST)
    public RespBean addMoney(String id, String amount) {
		BigDecimal bignum = new BigDecimal(amount);
		memberService.addTransactionRecords(id, bignum);
        return RespBean.ok("更新成功!");
    }
}
