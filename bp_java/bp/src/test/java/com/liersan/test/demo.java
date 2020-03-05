package com.liersan.test;

import com.liersan.bp.entity.User;
import com.liersan.bp.mapper.UserMapper;
import com.liersan.bp.utils.utils.MD5Utils;
import com.liersan.bp.utils.utils.RegExpUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;

@SpringBootTest
@RunWith(SpringRunner.class)
public class demo {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void demo01(){
        List<User> users = userMapper.selectAll();
        System.out.println(users);
    }

    @Test
    public void demo02(){
        System.out.println(UUID.randomUUID().toString());
        System.out.println(DigestUtils.md5DigestAsHex("123".getBytes()));
        System.out.println(MD5Utils.MD5Salt("123","123"));
    }

    @Test
    public void demo03(){
        //先用map 这个map是<String,list>形式的
        //map里面String是0 1 2 3 4 的形式
        //比如说当前时间是 12:00 那么0 就是12:00-11:55 那么1就是11:55-11:50 这样一个简单的约定
        //然后list就是一个对象的数组 这个数组里面就是在这个时间段的数据
        Map<String,List> map = new HashMap<>();

        //假设这是从数据库中拿来的数据
        ArrayList dbData =new ArrayList();
        //放点假数据
        for (int i=0;i<960;i++){
            dbData.add(i);
        }
        //冒泡算法 两次for循环 外层for循环是控制循坏次数
        //8小时96组 也就是循环96次
        for (int i=0;i<96;i++){
            //首先计算出来时间,我这里不好假设 我只能把960份数据分成96份 这样一份map中有10份数据
            int start = i*10;
            int end = start+10;
            List list = new ArrayList();
            //里层循环是循环的从数据库里面拿来的数据
            for (int j=0;j<dbData.size();j++){
                //开始控制 只要在这个段里的都拿出来
                if (j<end && j>start) {
                    //符合条件的塞入list
                    list.add(j);
                }
            }
            //内层循环结束 把list放如map中
            map.put(i+"",list);
        }
        System.out.println(map);
    }

    @Test
    public void demo04(){
        Boolean phone = RegExpUtils.phone("123");
        System.out.println(phone);
        System.out.println(RegExpUtils.password("a123456789123456789"));
    }

    @Test
    public void demo05(){
        User user = new User();
        user.setAccount("15838630117");
        User selectOne = userMapper.selectOne(user);
        selectOne.setPassword(MD5Utils.MD5Salt("a123456",selectOne.getAccount()));
        userMapper.updateByPrimaryKey(selectOne);
    }

    @Test
    public void demo06() throws FileNotFoundException {
        File file = ResourceUtils.getFile("/application.yml");
        System.out.println(file.toString());
    }

}
