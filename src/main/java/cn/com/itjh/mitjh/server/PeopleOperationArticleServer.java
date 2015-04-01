package cn.com.itjh.mitjh.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cn.com.itjh.mitjh.domain.Article;
import cn.com.itjh.mitjh.service.ArticleService;
import cn.com.itjh.mitjh.service.PeopleOperationArticleService;

import com.google.gson.Gson;

/**
 * 
 * 用户操作文章Server. <br>
 * 用户收藏,喜欢,顶等操作
 * 
 * @Copyright itjh
 * @Project
 * @author 宋立君
 * @date 2015年3月17日 下午4:32:23
 * @Version
 * @JDK version used 8.0
 * @Modification history none
 * @Modified by none
 */
@Repository
@Path("poas")
public class PeopleOperationArticleServer {

    private static final Logger logger = Logger.getLogger(PeopleOperationArticleServer.class.getName());

    @Resource
    private MemcachedClient memcachedClient;

    @Resource
    private ArticleService articleService;

    @Resource
    private PeopleOperationArticleService peopleOperationArticleService;

    Gson gson = new Gson();

    /**
     * 
     * 用户收藏文章. <br>
     * 用户收藏文章
     * @Copyright itjh
     * @Project
     * @param user_client_id
     *            用户客户端id
     * @param article_id
     *            文章id
     * @return
     * @return String
     * @throws
     * @author 宋立君
     * @date 2015年3月17日 下午4:43:37
     * @Version
     * @JDK version used 8.0
     * @Modification history none
     * @Modified by none
     */
    @Path("/userCollectionArticle")
    @POST
    public String userCollectionArticle(@FormParam(value = "user_client_id") String user_client_id,
            @FormParam(value = "article_id") String article_id, @Context HttpServletResponse servletResponse) {
        servletResponse.setContentType("application/json;charset=UTF-8");
        logger.info("用户：" + user_client_id + "收藏文章：" + article_id);
        // 返回参数的map
        Map<String, Object> result = new HashMap<String, Object>();
        String resultJson = "";

        // 查询参数
        Map<String, Object> params = new HashMap<String, Object>();
        // 保存收藏信息
        if (!"".equals(user_client_id) && null != user_client_id && !"".equals(article_id) && null != article_id) {

            // 判断用户是否收藏过,保存
            params.put("user_client_id", user_client_id);
            params.put("article_id", article_id);

            int cuc = peopleOperationArticleService.checkUserCollectionOrSaveUserCollection(params);
            switch (cuc) {
            case 2:
                logger.info("用户已经收藏此文章");
                result.put("result", 2);
                result.put("description", "用户已经收藏");// 描述信息
                break;
            case -1:
                logger.info("系统异常");
                result.put("result", -1);
                result.put("description", "系统异常");// 描述信息
                break;
            case 0:
                logger.info("收藏失败");
                result.put("result", 0);
                result.put("description", "收藏失败");// 描述信息
                break;
            default:
                logger.info("收藏成功");
                result.put("result", 1);
                result.put("description", "收藏成功");// 描述信息
                break;
            }
        } else {
            result.put("result", 0);
            result.put("description", "信息传入有误");// 描述信息
        }

        // 返回收藏信息
        resultJson = gson.toJson(result);
        return resultJson;
    }
    

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
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("queryArticleListByUserCollection/{user_client_id}/{pageNum}/{showNum}")
    public String queryArticleListByUserCollection(@PathParam(value = "user_client_id") int user_client_id,@PathParam(value = "pageNum") int pageNum,
            @PathParam(value = "showNum") int showNum, @Context HttpServletResponse servletResponse) {
        servletResponse.setContentType("application/json;charset=UTF-8");
        // 返回参数的map
        Map<String, Object> result = new HashMap<String, Object>();
        String resultJson = "";
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            pageNum = pageNum*showNum;
            params.put("pageNum", pageNum);
            params.put("showNum", showNum); 
            params.put("user_client_id", user_client_id);
            List<Article> articles = peopleOperationArticleService.queryArticleListByUserCollection(params);
            if (null != articles) {
                logger.info("获取用户收藏文章列表");
                result.put("result", 1);
                result.put("content", articles);
                result.put("description", "文章列表获取成功");// 描述信息
            }else{
                result.put("result", -1);
                result.put("content", articles);
                result.put("description", "文章列表获取失败");// 描述信息
            }
        } catch (Exception e) {
            logger.info("系统异常\n");
            logger.info(e.getMessage());
            result.put("result", 0);
            result.put("description", "系统异常");// 描述信息
            e.printStackTrace();
        }
        resultJson = gson.toJson(result);
        return resultJson;
    }

}
