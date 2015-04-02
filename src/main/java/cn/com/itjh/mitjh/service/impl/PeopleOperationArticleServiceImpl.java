package cn.com.itjh.mitjh.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.itjh.mitjh.dao.PeopleDao;
import cn.com.itjh.mitjh.dao.PeopleOperationArticleDao;
import cn.com.itjh.mitjh.domain.Article;
import cn.com.itjh.mitjh.service.PeopleOperationArticleService;

/**
 * 
 * 用户操作文章Server. <br>
 * 用户收藏,喜欢,顶等操作
 * 
 * @Copyright itjh
 * @Project
 * @author 宋立君
 * @date 2015年3月17日 下午4:34:33
 * @Version
 * @JDK version used 8.0
 * @Modification history none
 * @Modified by none
 */
@Service
public class PeopleOperationArticleServiceImpl implements PeopleOperationArticleService {

    @Resource
    private PeopleDao peopleDao;
    @Resource
    private PeopleOperationArticleDao peopleOperationArticleDao;

    @Transactional
    @Override
    public int checkUserCollectionOrSaveUserCollection(Map<String, Object> params) {

        int result = 0;

        try {
            // 检测用户是否收藏过
            int count = peopleOperationArticleDao.checkUserCollection(params);
            if (count > 0) {//不进行收藏操作
                result = 2;
            }else{
                //进行收藏操作
                params.put("time", new Date());
                result = peopleOperationArticleDao.saveUserCollection(params);
            }
        } catch (Exception e) {
            result = -1;
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<Article> queryArticleListByUserCollection(Map<String, Object> params) {
        return peopleOperationArticleDao.queryArticleListByUserCollection(params);
    }

    @Override
    public int checkUserCollectionOrDeleteUserCollection(Map<String, Object> params) {
        
        int result = 0;
        try {
            // 检测用户是否收藏过
            int count = peopleOperationArticleDao.checkUserCollection(params);
            if (count > 0) {//不进行取消收藏操作
                //进行取消收藏操作
                params.put("time", new Date());
                result = peopleOperationArticleDao.deleteUserCollection(params);
            }else{
                result = 2;
            }
        } catch (Exception e) {
            result = -1;
            e.printStackTrace();
        }

        return result;
    }

}
