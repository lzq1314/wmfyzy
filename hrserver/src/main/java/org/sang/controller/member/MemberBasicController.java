package org.sang.controller.member;

import org.sang.HrserverApplication;
import org.sang.bean.Employee;
import org.sang.bean.Member;
import org.sang.bean.Position;
import org.sang.bean.RespBean;
import org.sang.common.EmailRunnable;
import org.sang.common.poi.PoiUtils;
import org.sang.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

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

    @RequestMapping(value = "/updateEmp", method = RequestMethod.PUT)
    public RespBean updateEmp(Member member) {
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
