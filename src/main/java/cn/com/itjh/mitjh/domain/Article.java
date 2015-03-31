package cn.com.itjh.mitjh.domain;

import java.io.Serializable;

/**
 *  
 * 文章实体类. <br>
 * 文章实体类
 * @Copyright itjh
 * @Project
 * @author 宋立君
 * @date 2014年10月30日 下午3:37:28
 * @Version
 * @JDK version used 8.0
 * @Modification history none
 * @Modified by none
 */
public class Article implements Serializable {

    /**
    
    * 
    
    */
    
    private static final long serialVersionUID = -786696046736928251L;
    private Integer aid; //文章ID
    private String date; //文章发布时间
    private String title;//文章标题
    private String summary;//文章摘要
    private String content;//文章摘要
    private String img; //文章缩略图
    private Integer author_id; //作者ID
    private String author; //作者名称

    private Integer isUserCollect; // 用户是否收藏此文章
    
    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    
    
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Article() {
        super();
        // TODO Auto-generated constructor stub
    }

  

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    

    public Integer getIsUserCollect() {
        return isUserCollect;
    }

    public void setIsUserCollect(Integer isUserCollect) {
        this.isUserCollect = isUserCollect;
    }

    public Article(Integer aid, String date, String title, String summary, String content, String img,
            Integer author_id, String author, Integer isUserCollect) {
        super();
        this.aid = aid;
        this.date = date;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.img = img;
        this.author_id = author_id;
        this.author = author;
        this.isUserCollect = isUserCollect;
    }

    @Override
    public String toString() {
        return "Article [aid=" + aid + ", date=" + date + ", title=" + title + ", summary=" + summary + ", content="
                + content + ", img=" + img + ", author_id=" + author_id + ", author=" + author + ", isUserCollect="
                + isUserCollect + "]";
    }

}
