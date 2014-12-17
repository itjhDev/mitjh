package cn.com.itjh.mitjh.domain;

/**
 * 
 * 人. <br>
 * 人
 * 
 * @Copyright itjh
 * @Project
 * @author 宋立君
 * @date 2014年12月17日 下午3:08:01
 * @Version
 * @JDK version used 8.0
 * @Modification history none
 * @Modified by none
 */
public class People {
    private Integer id;
    private String user_client_id;
    private String nickname;
    private String face;
    private Integer platform_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser_client_id() {
        return user_client_id;
    }

    public void setUser_client_id(String user_client_id) {
        this.user_client_id = user_client_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public Integer getPlatform_id() {
        return platform_id;
    }

    public void setPlatform_id(Integer platform_id) {
        this.platform_id = platform_id;
    }

    public People(Integer id, String user_client_id, String nickname, String face, Integer platform_id) {
        super();
        this.id = id;
        this.user_client_id = user_client_id;
        this.nickname = nickname;
        this.face = face;
        this.platform_id = platform_id;
    }
    public People(String user_client_id, String nickname, String face, Integer platform_id) {
        super();
        this.user_client_id = user_client_id;
        this.nickname = nickname;
        this.face = face;
        this.platform_id = platform_id;
    }

    public People() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    public String toString() {
        return "People [id=" + id + ", user_client_id=" + user_client_id + ", nickname=" + nickname + ", face=" + face
                + ", platform_id=" + platform_id + "]";
    }
}
