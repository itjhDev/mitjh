package cn.com.itjh.mitjh.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.itjh.mitjh.dao.ArticleDao;
import cn.com.itjh.mitjh.domain.Article;
import cn.com.itjh.mitjh.domain.ArticleCategory;
import cn.com.itjh.mitjh.service.ArticleService;

/**
 * 
* 处理文章Service实现类.
* <br>处理文章Service实现类
* @Copyright itjh
* @Project
* @author 宋立君
* @date 2014年10月30日 下午4:06:26
* @Version 
* @JDK version used 8.0
* @Modification history none
* @Modified by none
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleDao articleDao;
    
    @Override
    public List<Article> queryArticleListByCat(Map<String, Object> params) {
        return null;
    }

    @Override
    public List<Article> queryArticleListByNew(Map<String, Object> params) {
        return articleDao.queryArticleListByNew(params);
    }

    @Override
    public Article queryArticleById(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return articleDao.queryArticleById(params);
    }

    @Override
    public List<Article> queryArticleListByCategory(Map<String, Object> params) {
        return articleDao.queryArticleListByCategory(params);
    }

    @Override
    public   List<ArticleCategory> queryArticleCategory() {
        return articleDao.queryArticleCategory();
    }

}
