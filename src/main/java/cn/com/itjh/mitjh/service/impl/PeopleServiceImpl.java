package cn.com.itjh.mitjh.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.itjh.mitjh.dao.PeopleDao;
import cn.com.itjh.mitjh.domain.People;
import cn.com.itjh.mitjh.service.PeopleService;

@Service
public class PeopleServiceImpl implements PeopleService {

    @Resource
    private PeopleDao peopleDao;

    @Transactional
    @Override
    public int save(People people) {

        int count = 0;

        // 通过user_client_id,get,Platform_id判断用户是否注册过
        Map<String, Object> peopleumap = new HashMap<String, Object>();
        peopleumap.put("userClientId", people.getUser_client_id());
        peopleumap.put("platformId", people.getPlatform_id());
        People peopleu = peopleDao.selectUserByUserClientId(peopleumap);

        if (null != peopleu) {
            // 用户通过第三方注册过，检测信息是否修改，从而修改客户端用户信息
            // 修改SQL
            StringBuffer usql = new StringBuffer();

            if (!peopleu.getNickname().equals(people.getNickname())) {// 判断昵称
                usql.append("nickname='" + people.getNickname()+"'");
            }
            if (!peopleu.getFace().equals(people.getFace())) {// 判断头像
                usql.append("face='" + people.getFace()+"'");
            }

            if (usql.length() > 0) {
                usql.append("where user_client_id='" + people.getUser_client_id() +"' And platform_id=" + people.getPlatform_id());
                //修改用户信息
                peopleumap = new HashMap<String, Object>();
                String resql = usql.toString();
                peopleumap.put("resql", resql);
                count = peopleDao.updateUser(peopleumap);
            }else{
                count = 2;
            }
        } else {// 没有注册进行保存
            count = peopleDao.save(people);
        }
        return count;
    }

}
