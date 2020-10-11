package com.itheima.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.health.pojo.Member;
import com.itheima.health.vo.RPTMemberVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface MemberMapper extends BaseMapper<Member> {
    @Select("SELECT * FROM t_member WHERE PHONENUMBER=#{telephone}")
    Member findMemeberBytelephone(String telephone);

    @Select("SELECT  count(tm.ID) as memberCounts, \n" +
            "        concat(year(rp.fdate),\"-\",rp.fmonth) as yearAndMonth\n" +
            "from rpt_date  rp  ,t_member  tm\n" +
            "where rp.fyear = year(now())-1\n" +
            "group by rp.fmonth")
    List<Map> getMemberCount();
}
