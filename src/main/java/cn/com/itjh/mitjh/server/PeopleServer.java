package cn.com.itjh.mitjh.server;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;

import cn.com.itjh.mitjh.domain.People;
import cn.com.itjh.mitjh.service.PeopleService;

/**
 * 
 * 处理人(用户)接口. <br>
 * 处理人(用户)接口
 * 
 * @Copyright itjh
 * @Project
 * @author 宋立君
 * @date 2014年12月17日 下午3:16:38
 * @Version
 * @JDK version used 8.0
 * @Modification history none
 * @Modified by none
 */
@Repository
@Path("PeopleServer")
public class PeopleServer {

    @Resource
    private PeopleService peopleService;

    private static final Logger logger = Logger.getLogger(PeopleServer.class.getName());

    @Resource
    private MemcachedClient memcachedClient;

    Gson gson = new Gson();

    @Path("/saveUser")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String saveUser(@FormParam(value = "user_client_id") String user_client_id,
            @FormParam(value = "face") String face, @FormParam(value = "platform_id") int platform_id,
            @FormParam(value = "nickname") String nickname, @Context HttpServletRequest servletRequest,
            @Context HttpServletResponse servletResponse) {
        servletResponse.setContentType("application/json;charset=UTF-8");
        // 返回参数的map
        Map<String, Object> result = new HashMap<String, Object>();
        String resultJson = "";
        try {
            if (null != user_client_id && "" != user_client_id && null != face && "" != face && null != nickname
                    && "" != nickname) {
                                
                People people = new People(user_client_id,nickname,face,platform_id);

                int count = peopleService.save(people);
                if (count == 1) {
                    logger.info("保存用户成功");
                    result.put("result", 1);
                    result.put("people", people);
                    result.put("description", "用户信息注册成功");// 描述信息
                }
                if (count == 2) {
                    logger.info("用户登录成功");
                    result.put("result", 1);
                    result.put("people", people);
                    result.put("description", "用户信息获取成功");// 描述信息
                }
            } else {
                result.put("result", 0);
                result.put("description", "信息传入有误");// 描述信息
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        resultJson = gson.toJson(result);
        return resultJson;
    }
}
