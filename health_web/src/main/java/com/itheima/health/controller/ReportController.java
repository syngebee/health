package com.itheima.health.controller;

import com.itheima.health.entity.Result;
import com.itheima.health.service.MemberService;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.resources.MessageConstant;
import com.itheima.health.vo.RPTMemberVO;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;

    @Reference
    private SetmealService setmealService;

    @GetMapping("/getMemberCount")
    public Result getMemberCount(){
        try {
            RPTMemberVO map = memberService.getMemberCount();
            return new Result(true,MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    @GetMapping("/getSetmealZhanBi")
    public Result getSetmealZhanBi(){
        try {
            Map map =  setmealService.getSetmealReport();
            return  new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }
}
