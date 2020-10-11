package com.itheima.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.health.pojo.Member;
import com.itheima.health.vo.RPTMemberVO;

public interface MemberService extends IService<Member> {
    RPTMemberVO getMemberCount();
}
