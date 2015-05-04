package cn.com.itjh.mitjh.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import cn.com.itjh.mitjh.domain.Article;
import cn.com.itjh.mitjh.domain.ArticleCategory;
import cn.com.itjh.mitjh.service.ArticleService;
import cn.com.itjh.util.HttpUtil;

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

            int start = showNum * (pageNum);// 因为redis中list元素位置基数是0
            if (start > 0) {
                start += pageNum;
            }
            int end = start + showNum;

            System.out.println("start: " + start + "  end:  " + end);

            // params.put("pageNum", pageNum);
            // params.put("showNum", showNum);
            logger.info("查询条件：" + params);

            // redisTemplate.delete("artices_new");
            List<Article> articles = new ArrayList<Article>();

            // 查询文章总数
            Long acount = articleService.selectCountByNew();

            Long anewl = redisTemplate.opsForList().size("artices_new");

            if (acount > anewl) {// 数据库文章总数比缓存多
                Long ad = (acount - anewl);
                params.put("limit", "limit " + ad);
                // 查询新增的数据,存放到缓存中
                articles = articleService.queryArticleListByNew(params);
                if (anewl == 0) {
                    anewl = redisTemplate.opsForList().rightPushAll("artices_new", articles);
                } else {
                    anewl = redisTemplate.opsForList().leftPushAll("artices_new", articles);
                }
            }

            if (anewl > 0) {// redis中有数据
                articles = redisTemplate.opsForList().range("artices_new", start, end);
            } else {
                articles = articleService.queryArticleListByNew(params);
                anewl = redisTemplate.opsForList().rightPushAll("artices_new", articles);
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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("redisTest")
    public String redisTest(@Context HttpServletRequest servletRequest, @Context HttpServletResponse servletResponse)
            throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();
        String resultJson = "";

        System.out.println(servletRequest);
        System.out.println("===========================");
        System.out.println(servletResponse);
        System.out.println(servletRequest.getPathTranslated());
        System.out.println(servletRequest.getContentLength());
        System.out.println(servletRequest.getContentType());


        System.out.println(HttpUtil.getBody(servletRequest));
        //
        // // int pageNo = 6;
        // // int pageSize = 6;
        // // redisTemplate.delete("artices_new");
        // // redisTemplate.delete("artices_2");
        // // redisTemplate.delete("artices_3");
        // // redisTemplate.delete("artices_4");
        // // 返回参数的map
        // Map<String, Object> result = new HashMap<String, Object>();
        // String resultJson = "";
        // //获取到的内容是结果base64编码后的字符串，所以这样的认证方式安全性不高
        // try {
        // String authValue = servletRequest.getHeader("Authorization"), username = null,
        // pwd = null; //获取到的请求头格式类似于 Basic MTIzOjEyMw==
        //
        // if(authValue != null){
        // byte [] values = decode(authValue);
        // String valuestr = new String(values);
        // String [] aa = valuestr.split(":");
        //
        // if(aa.length == 2){
        // username = aa[0];
        // pwd = aa[1];
        // }
        // }
        //
        // //用户名和密码都不为空时验证成功
        // if(username != null && username.length() >0
        // && username.equals(pwd)){
        // // servletResponse.setContentType("text/html; charset=UTF-8");
        // // servletResponse.getWriter().print("你已经通过验证!");
        // result.put("res", "已经通过验证");
        //
        // }else{ //未通过验证
        // servletResponse.setStatus(401); //设置好相应的状态
        // // servletResponse.setHeader("WWW-Authenticate", "Basic realm=\"My Application\"");
        // //
        // // //设置用户取消验证后的消息提示
        // // servletResponse.setContentType("text/html; charset=UTF-8");
        // // servletResponse.getWriter().print("HTTP STATUS -- 401!");
        // result.put("res", "没有通过验证");
        //
        // }
        // resultJson = gson.toJson(result);
        // System.out.println(resultJson);
        //
        // } catch (Exception e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        //
        return resultJson;
    }

    /**
     * @param bytes
     * @return
     */
    public static byte[] decode(String base64String) {
        return Base64.decodeBase64(base64String);
    }

    /**
     * 二进制数据编码为BASE64字符串
     * 
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(final byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
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
            Long anewl = redisTemplate.opsForList().size("artices_" + categoryId);
            if (acount > anewl) {// 数据库文章总数比缓存多
                Long ad = (acount - anewl);
                params.put("limit", "limit " + ad);
                // 查询新增的数据,存放到缓存中
                articles = articleService.queryArticleListByCategory(params);
                if (anewl == 0) {
                    anewl = redisTemplate.opsForList().rightPushAll("artices_" + categoryId, articles);
                } else {
                    anewl = redisTemplate.opsForList().leftPushAll("artices_" + categoryId, articles);
                }
            }
            if (anewl > 0) {// redis中有数据
                articles = redisTemplate.opsForList().range("artices_" + categoryId, start, end);
                logger.info("缓存中的分类：" + categoryId + " 总数：" + articles);
            } else {
                articles = articleService.queryArticleListByCategory(params);
                anewl = redisTemplate.opsForList().rightPushAll("artices_" + categoryId, articles);
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
    public String queryArticleById(@PathParam(value = "aid") int aid, @Context HttpServletRequest servletRequest,
            @Context HttpServletResponse servletResponse) {
        servletResponse.setContentType("application/json;charset=UTF-8");
        // 返回参数的map
        Map<String, Object> result = new HashMap<String, Object>();
        String resultJson = "";
        try {
            String userId = servletRequest.getParameter("userId");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", aid);
            params.put("userId", null != userId ? userId : "0");
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
                result.put("description", "没有此文章信息");// 描述信息
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
