package cn.com.itjh.mitjh.domain;

/**
 * 
 * 文章分类. <br>
 * 文章分类
 * 
 * @Copyright vcinema
 * @Project
 * @author 宋立君
 * @date 2014年12月2日 下午7:08:25
 * @Version
 * @JDK version used 8.0
 * @Modification history none
 * @Modified by none
 */
public class ArticleCategory {

    private String id;
    private String name;
    private String slug;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public ArticleCategory(String id, String name, String slug) {
        super();
        this.id = id;
        this.name = name;
        this.slug = slug;
    }

    public ArticleCategory() {
        super();
        // TODO Auto-generated constructor stub
    }
}
