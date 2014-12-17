package cn.com.itjh.mitjh.dao;

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
}
