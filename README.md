mitjh

IT江湖接口服务

获取最新文章列表
http://203.195.152.45:8080/mitjh/ArticleServer/queryArticleListByNew/0/10 
获取单个文章详情
http://203.195.152.45:8080/mitjh/ArticleServer/queryArticleById/33  

根据文章分类获取文章列表
http://192.168.16.59:8080/mitjh/ArticleServer/queryArticleListByCategory/3/0/11  

获取文章分类
http://192.168.16.59:8080/mitjh/ArticleServer/queryArticleCategory  
  技术，经验quwen，趣文，资讯，程序员

第三方登录 注册用户接口
https://github.com/itjhDev/mitjh.git 

POST方式
传入参数
nickname 用户名(昵称)
face 用户第三方头像
user_client_id 第三方平台用户id
platform_id 第三方平台 1:新浪微博2:微博

返回参数：
{
    "result": 1,
    "description": "用户信息注册成功",
    "people": {
        "user_client_id": "186256489",
        "nickname": "IT江湖网",
        "face": "http://55465.pnh",
        "platform_id": 0
    }
}