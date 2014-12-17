package cn.com.itjh.mitjh.service;

import cn.com.itjh.mitjh.domain.People;

/**
 * 
* 人Service.
* <br>操作人Service
* @Copyright itjh
* @Project
* @author 宋立君
* @date 2014年12月17日 下午3:14:32
* @Version 
* @JDK version used 8.0
* @Modification history none
* @Modified by none
 */
public interface PeopleService {

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
