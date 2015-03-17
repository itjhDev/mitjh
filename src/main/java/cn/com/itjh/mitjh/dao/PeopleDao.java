package cn.com.itjh.mitjh.dao;

import java.util.Map;

import cn.com.itjh.mitjh.domain.People;

/**
 * 
* 使用人操作.
* <br>使用人操
* @Copyright itjh
* @Project
* @author 宋立君
* @date 2014年12月17日 下午3:12:10
* @Version 
* @JDK version used 8.0
* @Modification history none
* @Modified by none
 */
public interface PeopleDao {

    /**
     * 
    * 保存人.
    * <br>保存人
    * @Copyright itjh
    * @Project
    * @param people
    * @return
    * @return int 
    * @throws
    * @author 宋立君
    * @date 2014年12月17日 下午3:12:44
    * @Version 
    * @JDK version used 8.0
    * @Modification history none
    * @Modified by none
     */
    int save(People people);

    /**
     * 
    * 判断用户是否注册.
    * <br>//通过user_client_id判断用户是否注册过
    * @Copyright itjh
    * @Project
    * @param user_client_id
     * @param platform_id 
    * @return
    * @return People 
    * @throws
    * @author 宋立君
    * @date 2015年3月17日 下午2:12:08
    * @Version 
    * @JDK version used 8.0
    * @Modification history none
    * @Modified by none
     */
    People selectUserByUserClientId(Map<String, Object> peopleumap);

    /**
     * 
    * 修改用户信息.
    * <br>修改用户信息
    * @Copyright vcinema
    * @Project
    * @param peopleumap
    * @return
    * @return int 
    * @throws
    * @author 宋立君
    * @date 2015年3月17日 下午2:30:33
    * @Version 
    * @JDK version used 8.0
    * @Modification history none
    * @Modified by none
     */
    int updateUser(Map<String, Object> peopleumap);
}
