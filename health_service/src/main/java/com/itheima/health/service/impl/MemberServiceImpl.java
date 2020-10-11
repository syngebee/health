package com.itheima.health.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.health.mapper.MemberMapper;
import com.itheima.health.pojo.Member;
import com.itheima.health.service.MemberService;
import com.itheima.health.vo.RPTMemberVO;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MemberServiceImpl extends ServiceImpl<MemberMapper,Member> implements MemberService {
    @Override
    public RPTMemberVO getMemberCount() {
        List<Map> memberCount = baseMapper.getMemberCount();
        RPTMemberVO rptMemberVO = new RPTMemberVO();

        List<Object> memberCountsList = new ArrayList<>();
        List<Object> yearAndMonthList = new ArrayList<>();
        for (Map map : memberCount) {
            memberCountsList.add(map.get("memberCounts"));
            yearAndMonthList.add(map.get("yearAndMonth"));
        }
        rptMemberVO.setMemberCounts(memberCountsList);
        rptMemberVO.setYearAndMonth(yearAndMonthList);
        return rptMemberVO;
    }
}
