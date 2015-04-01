package cn.com.itjh.mitjh.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.JedisPoolConfig;
import cn.com.itjh.mitjh.domain.Article;
import cn.com.itjh.mitjh.domain.ArticleCategory;
import cn.com.itjh.mitjh.service.ArticleService;

import com.google.gson.Gson;

/**
 * 
 * 文章server. <br>
 * 文章server
 * 
 * @Copyright vcinema
 * @Project
 * @author 宋立君
 * @date 2014年10月30日 下午4:08:39
 * @Version
 * @JDK version used 8.0
 * @Modification history none
 * @Modified by none
 */
@Repository
@Path("ArticleServer")
public class ArticleServer {

    @Resource
    private ArticleService articleService;

    private static final Logger logger = Logger.getLogger(ArticleServer.class.getName());

    @Resource
    private MemcachedClient memcachedClient;
    @Resource
    private RedisTemplate<String, Article> redisTemplate;

    Gson gson = new Gson();

    /**
     * 
     * 获取最新发布的文章. <br>
     * 获取最新发布的文章
     * 
     * @Copyright vcinema
     * @Project
     * @param pageNum
     * @param showNum
     * @param servletResponse
     * @return
     * @return String
     * @throws
     * @author 宋立君
     * @date 2014年11月3日 上午11:42:13
     * @Version
     * @JDK version used 8.0
     * @Modification history none
     * @Modified by none
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("queryArticleListByNew/{pageNum}/{showNum}")
    public String queryArticleListByNew(@PathParam(value = "pageNum") int pageNum,
            @PathParam(value = "showNum") int showNum, @Context HttpServletRequest servletRequest,
            @Context HttpServletResponse servletResponse) {
        servletResponse.setContentType("application/json;charset=UTF-8");
        // 返回参数的map
        Map<String, Object> result = new HashMap<String, Object>();
        String resultJson = "";
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            // pageNum = pageNum * showNum;

            int start = showNum * (pageNum) ;// 因为redis中list元素位置基数是0
            if (start > 0) {
                start += pageNum;
            }
            int end = start + showNum;

            System.out.println("start: " + start + "  end:  " + end);

//            params.put("pageNum", pageNum);
//            params.put("showNum", showNum);
            logger.info("查询条件：" + params);

//             redisTemplate.delete("artices_new");
            List<Article> articles = new ArrayList<Article>();

            // 查询文章总数
            Long acount = articleService.selectCountByNew();

            Long anewl = redisTemplate.opsForList().size("artices_new");
            
            if (acount > anewl) {//数据库文章总数比缓存多
                Long ad = (acount - anewl);
                params.put("limit", "limit " +  ad);
                //查询新增的数据,存放到缓存中
                articles = articleService.queryArticleListByNew(params);
                if (anewl == 0) {
                    anewl =  redisTemplate.opsForList().rightPushAll("artices_new", articles);
                }else{
                    anewl =  redisTemplate.opsForList().leftPushAll("artices_new", articles);
                }
            }

            if (anewl > 0) {// redis中有数据
                articles = redisTemplate.opsForList().range("artices_new", start, end);
            } else {
                articles = articleService.queryArticleListByNew(params);
                anewl =  redisTemplate.opsForList().rightPushAll("artices_new", articles);
            }

            if (null != articles) {
                System.out.println("文章列表长度：" + anewl);
                // logger.info("获取最新文章成功");
                result.put("result", 1);
                result.put("content", articles);
                result.put("description", "文章列表获取成功");// 描述信息

                //

            } else {
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("redisTest")
    public String redisTest() {
        int pageNo = 6;
        int pageSize = 6;
        redisTemplate.delete("artices_new");
        redisTemplate.delete("artices_2");
        redisTemplate.delete("artices_3");

        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("queryArticleListByCategory/{categoryId}/{pageNum}/{showNum}")
    public String queryArticleListByCategory(@PathParam(value = "categoryId") int categoryId,
            @PathParam(value = "pageNum") int pageNum, @PathParam(value = "showNum") int showNum,
            @Context HttpServletRequest servletRequest, @Context HttpServletResponse servletResponse) {
        servletResponse.setContentType("application/json;charset=UTF-8");
        // 返回参数的map
        Map<String, Object> result = new HashMap<String, Object>();
        String resultJson = "";
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            int start = showNum * (pageNum);// 因为redis中list元素位置基数是0
            if (start > 0) {
                start += pageNum;
            }
            int end = start + showNum;
            System.out.println("start: " + start + "  end:  " + end);
            params.put("categoryId", categoryId);
            logger.info("查询条件：" + params);
            List<Article> articles = new ArrayList<Article>();
            // 查询分类下文章总数
            Long acount = articleService.selectCountByByCategory(params);
            Long anewl = redisTemplate.opsForList().size("artices_"+categoryId);
            if (acount > anewl) {//数据库文章总数比缓存多
                Long ad = (acount - anewl);
                params.put("limit", "limit " +  ad);
                //查询新增的数据,存放到缓存中
                articles = articleService.queryArticleListByCategory(params);
                anewl =  redisTemplate.opsForList().leftPushAll("artices_"+categoryId, articles);
                if (anewl == 0) {
                    anewl =  redisTemplate.opsForList().rightPushAll("artices_"+categoryId, articles);
                }else{
                    anewl =  redisTemplate.opsForList().leftPushAll("artices_"+categoryId, articles);
                }
            }
            if (anewl > 0) {// redis中有数据
                articles = redisTemplate.opsForList().range("artices_"+categoryId, start, end);
                logger.info("缓存中的分类："+categoryId + " 总数：" + articles);
            } else {
                articles = articleService.queryArticleListByCategory(params);
                anewl =  redisTemplate.opsForList().rightPushAll("artices_"+categoryId, articles);
            }
      
            if (null != articles) {
                logger.info("获取分类：" + categoryId + "文章成功");
                result.put("result", 1);
                result.put("content", articles);
                result.put("description", "获取分类：" + categoryId + "文章成功");// 描述信息
            } else {
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

    /**
     * 
     * 获取单个文章. <br>
     * 根据文章id获取文章详情
     * 
     * @Copyright vcinema
     * @Project
     * @param aid
     *            文章id
     * @param servletResponse
     * @return
     * @return String
     * @throws
     * @author 宋立君
     * @date 2014年11月3日 上午11:42:31
     * @Version
     * @JDK version used 8.0
     * @Modification history none
     * @Modified by none
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("queryArticleById/{aid}")
    public String queryArticleById(@PathParam(value = "aid") int aid, @Context HttpServletResponse servletResponse) {
        servletResponse.setContentType("application/json;charset=UTF-8");
        // 返回参数的map
        Map<String, Object> result = new HashMap<String, Object>();
        String resultJson = "";
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", aid);
            Article article = articleService.queryArticleById(params);
            if (null != article) {
                logger.info("获取文章成功");
                result.put("aid", aid);
                result.put("result", 1);
                result.put("content", article);
                result.put("description", "文章获取成功");// 描述信息
            } else {
                result.put("aid", aid);
                result.put("result", -1);
                result.put("content", article);
                result.put("description", "文章列表获取失败");// 描述信息
            }
        } catch (Exception e) {
            result.put("result", -1);
            result.put("description", "系统异常");// 描述信息
            e.printStackTrace();
        }
        resultJson = gson.toJson(result);
        return resultJson;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("queryArticleCategory")
    public String queryArticleCategory(@Context HttpServletResponse servletResponse) {
        servletResponse.setContentType("application/json;charset=UTF-8");
        // 返回参数的map
        Map<String, Object> result = new HashMap<String, Object>();
        String resultJson = "";
        try {
            List<ArticleCategory> articleCategorys = articleService.queryArticleCategory();
            if (null != articleCategorys) {
                logger.info("获取全部分类成功");
                result.put("result", 1);
                result.put("content", articleCategorys);
                result.put("description", "获取全部分类");// 描述信息
            } else {
                result.put("result", -1);
                result.put("content", articleCategorys);
                result.put("description", "文章列表获取失败");// 描述信息
            }
        } catch (Exception e) {
            result.put("result", -1);
            result.put("description", "系统异常");// 描述信息
            e.printStackTrace();
        }
        resultJson = gson.toJson(result);
        return resultJson;
    }

}
