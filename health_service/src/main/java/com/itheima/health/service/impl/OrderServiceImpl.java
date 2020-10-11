package com.itheima.health.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.health.mapper.MemberMapper;
import com.itheima.health.mapper.OrderMapper;
import com.itheima.health.mapper.OrderSettingMapper;
import com.itheima.health.mapper.SetmealMapper;
import com.itheima.health.pojo.Member;
import com.itheima.health.pojo.Order;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.OrderService;
import com.itheima.health.utils.date.DateUtils;
import com.itheima.health.utils.resources.MessageConstant;
import com.itheima.health.vo.OrderVO;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service
@Transactional
public class OrderServiceImpl extends ServiceImpl<OrderMapper,Order> implements OrderService {

    @Autowired
    private OrderSettingMapper orderSettingMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private SetmealMapper setmealMapper;



    @Override
    public Order add(Map map) {
        try {
            //参数转换
            String orderDate = (String) map.get("orderDate");
            int setmealId =Integer.parseInt((String) map.get("setmealId"));
            String telephone = (String) map.get("telephone");
            String name = (String) map.get("name");
            String sex = (String) map.get("sex");
            String idCard = (String) map.get("idCard");
            //查询t_ordersetting  根据日期判断当前是否已经预约满
            int count = orderSettingMapper.isOrderOk(orderDate);
            if (count>0){
                //可以预约
                Order order = new Order();
                Date date = DateUtils.parseString2Date(orderDate);
                //预约订单的日期
                order.setOrderDate(date);
                //预约订单的初始状态
                order.setOrderStatus(Order.ORDERSTATUS_NO);
                //预约的类型
                order.setOrderType(Order.ORDERTYPE_WEIXIN);
                //设置订单所选的套餐
                order.setSetmealId(setmealId);
                //更新预约表,已预约数+1
                orderSettingMapper.updateReservationsByOrderDate(orderDate);
                //查询当前手机号是否注册过
                Member member = memberMapper.findMemeberBytelephone(telephone);
                if(member==null){
                    //新用户
                    member = new Member();
                    member.setName(name);
                    member.setPhoneNumber(telephone);
                    member.setSex(sex);
                    member.setIdCard(idCard);
                    member.setRegTime(new Date());
                    memberMapper.insert(member);
                }else {
                    //如果是老用户,可能已经预约过了,不能重复预约
                    int orderCount =baseMapper.findOrderIsExistByOrderDateAndSetmealIdAndMemberId(member.getId(),setmealId,orderDate);
                    if(orderCount>0){
                        //  老会员 已经预约过了
                        throw  new  RuntimeException(MessageConstant.HAS_ORDERED);
                    }
                }
                order.setMemberId(member.getId());
                System.out.println("member = " + member);
                baseMapper.insert(order);
                return order;
            }else {
                throw  new  RuntimeException(MessageConstant.ORDER_FULL);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new  RuntimeException(e.getMessage());
        }


    }

    @Override
    public OrderVO findOrderVOById(String id) {
        Order order = getById(id);
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order,orderVO);

        Member member = memberMapper.selectById(order.getMemberId());
        Setmeal setmeal = setmealMapper.selectById(order.getSetmealId());

        orderVO.setMember(member.getName());
        orderVO.setSetmeal(setmeal.getName());
        return orderVO;
    }
}
