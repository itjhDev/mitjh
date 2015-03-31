package cn.com.itjh.mitjh.service;

import java.util.List;
import java.util.Map;

import cn.com.itjh.mitjh.domain.Article;
import cn.com.itjh.mitjh.domain.ArticleCategory;

/**
 * 
* 处理文章的Service.
* <br>详细说明
* @Copyright vcinema
* @Project
* @author 宋立君
* @date 2014年10月30日 下午4:05:31
* @Version 
* @JDK version used 8.0
* @Modification history none
* @Modified by none
 */
public interface ArticleService {
    /**
     * 
    * 获取文章列表.
    * <br>根据分类ID获取文章列表
    * @Copyright itjh
    * @Project
    * @param params
    * @return
    * @return List<Article> 
    * @throws
    * @author 宋立君
    * @date 2014年10月30日 下午3:43:22
    * @Version 
    * @JDK version used 8.0
    * @Modification history none
    * @Modified by none
     */
    List<Article> queryArticleListByCat(Map<String, Object> params);
    /**
     * 
     * 获取文章列表.
     * <br>获取各分类最新的文章列表
     * @Copyright itjh
     * @Project
     * @param params
     * @return
     * @return List<Article> 
     * @throws
     * @author 宋立君
     * @date 2014年10月30日 下午3:43:22
     * @Version 
     * @JDK version used 8.0
     * @Modification history none
     * @Modified by none
     */
    List<Article> queryArticleListByNew(Map<String, Object> params);
    /**
     * 
    * 获取文章详情.
    * <br>根据文章ID获取文章详情
    * @Copyright vcinema
    * @Project
    * @param aid
    * @return
    * @return Article 
    * @throws
    * @author 宋立君
    * @date 2014年11月3日 上午11:54:47
    * @Version 
    * @JDK version used 8.0
    * @Modification history none
    * @Modified by none
     */
    Article queryArticleById(Map<String, Object> params);
    /**
     * 
    * 根据分类获取文章列表.
    * <br>根据不同的分类ID获取文章列表
    * @Copyright vcinema
    * @Project
    * @param params
    * @return
    * @return List<Article> 
    * @throws
    * @author 宋立君
    * @date 2014年12月2日 下午6:49:48
    * @Version 
    * @JDK version used 8.0
    * @Modification history none
    * @Modified by none
     */
    List<Article> queryArticleListByCategory(Map<String, Object> params);
    
    /**
     * 
    * 获取分类.
    * <br> 获取文章所有分类
    * @Copyright vcinema
    * @Project
    * @return
    * @return ArticleCategory 
    * @throws
    * @author 宋立君
    * @date 2014年12月2日 下午7:10:26
    * @Version 
    * @JDK version used 8.0
    * @Modification history none
    * @Modified by none
     */
    List<ArticleCategory> queryArticleCategory();
    
    
    
    /**
     * 
    * 查询文章总数.
    * <br>查询文章总数
    * @Copyright vcinema
    * @Project
    * @return
    * @return Long 
    * @throws
    * @author 宋立君
    * @date 2015年3月31日 下午5:29:59
    * @Version 
    * @JDK version used 8.0
    * @Modification history none
    * @Modified by none
     */
    Long selectCountByNew();
    
    
    /**
     * 
     * 根据分类查询文章总数. <br>
     * 根据分类查询文章总数
     * 
     * @Copyright vcinema
     * @Project
     * @return
     * @return Long
     * @throws
     * @author 宋立君
     * @date 2015年3月31日 下午5:29:59
     * @Version
     * @JDK version used 8.0
     * @Modification history none
     * @Modified by none
     */
    Long selectCountByByCategory(Map<String, Object> params);
}
