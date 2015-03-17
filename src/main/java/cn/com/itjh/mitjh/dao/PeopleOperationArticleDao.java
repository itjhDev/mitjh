package cn.com.itjh.mitjh.dao;

import java.util.List;
import java.util.Map;

import cn.com.itjh.mitjh.domain.Article;

/**
 * 
* 用户操作文章的行为.
* <br>用户操作文章
* @Copyright itjh
* @Project
* @author 宋立君
* @date 2015年3月17日 下午5:04:22
* @Version 
* @JDK version used 8.0
* @Modification history none
* @Modified by none
 */
public interface PeopleOperationArticleDao {

    /**
     * 
    * 检测用户是否收藏过.
    * <br>检测用户是否收藏过
    * @Copyright vcinema
    * @Project
    * @param params
    * @return
    * @return int 
    * @throws
    * @author 宋立君
    * @date 2015年3月17日 下午5:05:00
    * @Version 
    * @JDK version used 8.0
    * @Modification history none
    * @Modified by none
     */
    int checkUserCollection(Map<String, Object> params);

    /**
     * 
    * 保存用户收藏文章.
    * <br>保存用户收藏文章
    * @Copyright itjh
    * @Project
    * @param params
    * @return
    * @return int 
    * @throws
    * @author 宋立君
    * @date 2015年3月17日 下午5:20:42
    * @Version 
    * @JDK version used 8.0
    * @Modification history none
    * @Modified by none
     */
    int saveUserCollection(Map<String, Object> params);
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
}
