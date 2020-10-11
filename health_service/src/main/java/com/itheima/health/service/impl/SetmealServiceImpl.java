package com.itheima.health.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.entity.PageResult;
import com.itheima.health.dto.SetmealDTO;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.mapper.CheckGroupMapper;
import com.itheima.health.mapper.CheckItemMapper;
import com.itheima.health.mapper.SetmealMapper;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.resources.RedisConstant;
import com.itheima.health.vo.CheckGroupVO;
import com.itheima.health.vo.SetmealVO;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper,Setmeal> implements SetmealService {

    @Autowired
    private CheckGroupMapper checkGroupMapper;

    @Autowired
    private CheckItemMapper checkItemMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void add(SetmealDTO setmealDTO) {
        //获取返回的主键id, mp已做好了
        Integer setmealId = setmealDTO.getId();
        //不等于null代表走更新,删除中间表关系
        if (setmealId!=null){
            baseMapper.relieveBySetmealId(setmealId);
        }
        //检查组基本信息保存,更新或新增
        saveOrUpdate(setmealDTO);
        setmealId=setmealDTO.getId();
        Long[] groupIds = setmealDTO.getGroupIds();
        if (groupIds!=null && groupIds.length!=0){
            for (Long groupId : groupIds) {
                baseMapper.addSetMealAndCheckGroupInfos(setmealId, groupId);
            }
        }

        //图片不等于null才存
        if(setmealDTO.getImg()!=null){
            //保存到redis中第二个集合,确实保存到数据库的图片
            redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmealDTO.getImg());
        }


    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //条件构造器,下面使用
        LambdaQueryWrapper<Setmeal> lambda = new QueryWrapper<Setmeal>().lambda();
        String queryString = queryPageBean.getQueryString();
        Boolean flag = (queryString!=null);
        //模糊查询条件,等于null就不用加这条件了
        if (flag){
            lambda.and(f->f
                    .like(Setmeal::getName,queryString)
                    .or()
                    .eq(Setmeal::getCode,queryString)
                    .or()
                    .eq(Setmeal::getHelpCode,queryString));
        }
        //查询有效数据
        lambda.eq(Setmeal::getIs_delete,0);
        //分页查询,传入页码和每页显示条数
        Page<Setmeal> page = page(new Page<>(queryPageBean.getCurrentPage(),queryPageBean.getPageSize()), lambda);
        return new PageResult(page.getTotal(),page.getRecords());
    }

    @Override
    public SetmealVO findSetMealDetail(Integer id) {
        //查询套餐信息
        SetmealVO setmealVO = new SetmealVO();
        Setmeal setmeal = getById(id);
        //拷贝过去
        BeanUtils.copyProperties(setmeal,setmealVO);
        //根据ID查询所有对应的检查组ID
        List<Integer> checkGroupIds = baseMapper.findCheckGroupsIdBySetmealId(id);
        //声明一个checkGroupVOList需要塞入SetmealVO
        List<CheckGroupVO> checkGroupVOList = new ArrayList<>();

        //遍历检查组ID
        for (Integer checkGroupId : checkGroupIds) {
            //得到CheckGroup实体
            CheckGroup checkGroup = checkGroupMapper.selectById(checkGroupId);
            //还得装CheckItem实体,先拷贝
            CheckGroupVO checkGroupVO = new CheckGroupVO();
            BeanUtils.copyProperties(checkGroup,checkGroupVO);
            //先放到集合中,再处理CheckItem,引用类型没事
            checkGroupVOList.add(checkGroupVO);

            //根据ID查询所有对应的检查项ID
            List<Integer> checkItemIds = checkGroupMapper.findCheckItemIdsByCheckGroupId(checkGroupId);

            //声明一个checkItemList需要塞入CheckGroupVO
            List<CheckItem> checkItemList = new ArrayList<>();

            //遍历检查项ID,放入集合
            for (Integer checkItemId : checkItemIds) {
                CheckItem checkItem = checkItemMapper.selectById(checkItemId);
                checkItemList.add(checkItem);
            }
            //塞入CheckGroupVO
            checkGroupVO.setCheckItems(checkItemList);
        }
        setmealVO.setCheckGroups(checkGroupVOList);
        return setmealVO;
    }

    @Override
    public Map getSetmealReport() {
        HashMap<String, Object> map = new HashMap<>();
        List<Map> setmealCount =  baseMapper.getSetmealReport();
        List<Object> setmealNames = new ArrayList<>();
        for (Map map1 : setmealCount) {
            Object name = map1.get("name");
            setmealNames.add(name);
        }

        map.put("setmealNames",setmealNames);
        map.put("setmealCount",setmealCount);
        return map;
    }
}
