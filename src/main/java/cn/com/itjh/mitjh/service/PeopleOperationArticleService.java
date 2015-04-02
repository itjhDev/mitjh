package cn.com.itjh.mitjh.service;

import java.util.List;
import java.util.Map;

import cn.com.itjh.mitjh.domain.Article;

/**
 * 
* 用户操作文章Server.
* <br>用户收藏,喜欢,顶等操作
* @Copyright itjh
* @Project
* @author 宋立君
* @date 2015年3月17日 下午4:33:48
* @Version 
* @JDK version used 8.0
* @Modification history none
* @Modified by none
 */
public interface PeopleOperationArticleService {

    /**
     * 
    * 检测用户是否收藏过此文章.
    * <br>检测用户是否收藏过此文章
    * @Copyright vcinema
    * @Project
    * @param params
    * @return
    * @return int 
    * @throws
    * @author 宋立君
    * @date 2015年3月17日 下午4:59:18
    * @Version 
    * @JDK version used 8.0
    * @Modification history none
    * @Modified by none
     */
    int checkUserCollectionOrSaveUserCollection(Map<String, Object> params);
    /**
     * 
    * 获取用户收藏文章列表.
    * <br>根据用户id获取收藏列表
    * @Copyright itjh
    * @Project
    * @param user_client_id
    * @param pageNum
    * @param showNum
    * @param servletResponse
    * @return
    * @return String 
    * @throws
    * @author 宋立君
    * @date 2015年3月17日 下午5:56:46
    * @Version 
    * @JDK version used 8.0
    * @Modification history none
    * @Modified by none
     */
    List<Article> queryArticleListByUserCollection(Map<String, Object> params);
    /**
     * 
    * 用户取消收藏文章.
    * <br>用户取消收藏文章
    * @Copyright vcinema
    * @Project
    * @param params
    * @return
    * @return int 
    * @throws
    * @author 宋立君
    * @date 2015年4月2日 下午3:41:22
    * @Version 
    * @JDK version used 8.0
    * @Modification history none
    * @Modified by none
     */
    int checkUserCollectionOrDeleteUserCollection(Map<String, Object> params);

}
