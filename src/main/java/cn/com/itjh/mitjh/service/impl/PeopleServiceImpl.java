package cn.com.itjh.mitjh.service.impl;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.stereotype.Service;

import cn.com.itjh.mitjh.dao.PeopleDao;
import cn.com.itjh.mitjh.domain.People;
import cn.com.itjh.mitjh.service.PeopleService;

@Service
public class PeopleServiceImpl implements PeopleService {

    @Resource
    private PeopleDao peopleDao;

   
    @Override
    public int save(People people) {
        return peopleDao.save(people);
    }

}
