mitjh

IT江湖接口服务

// 获取文章列表
http://api.itjh.net/v1/ArticleServer/queryArticleListByNew/0/11 

// 根据文章ID获取文章详情
http://api.itjh.net/v1/ArticleServer/queryArticleById/33 

// 根据分类获取文章列表
http://api.itjh.net/v1/ArticleServer/queryArticleListByCategory/3/0/11


//第三方用户登录接口
http://api.itjh.net/v1/PeopleServer/saveUser 

POST方式
传入参数
nickname 用户名(昵称)
face 用户第三方头像
user_client_id 第三方平台用户id
platform_id 第三方平台 0:微博 1:微信
``` json
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
```

#用户收藏文章
http://api.itjh.net/v1/poas/userCollectionArticle

POST方式
传入参数
user_client_id 第三方平台用户id
article_id 文章id

``` json
返回参数：
--成功
{
    "result": 1,
    "description": "收藏成功"
}
--收藏过
{
    "result": 2,
    "description": "用户已经收藏"
}
```
//获取用户收藏文章列表
http://api.itjh.net/v1/poas/queryArticleListByUserCollection/186256489/0/11

GET方式

186256489 // 用户id

{
    "result": 1,
    "description": "文章列表获取成功",
    "content": [
        {
            "aid": 312,
            "date": "2015-01-23 15:33:08.0",
            "title": "重磅推荐丨移动互联网的本质",
            "img": "http://www.itjh.net/wp-content/uploads/2015/01/45548asdqwe22.png",
            "author_id": 1,
            "author": "IT江湖"
        },
        {
            "aid": 368,
            "date": "2015-03-15 09:29:19.0",
            "title": "程序猿的经典语录（一）",
            "img": "http://www.itjh.net/wp-content/uploads/2014/12/1401074960812.jpg",
            "author_id": 1,
            "author": "IT江湖"
        },
        {
            "aid": 369,
            "date": "2015-03-16 14:37:03.0",
            "title": "移动互联网结束后，我们会不会失业？",
            "img": "http://www.itjh.net/wp-content/uploads/2015/03/f21789406342c4b302ef43d8c4a83d18.jpg",
            "author_id": 1,
            "author": "IT江湖"
        }
    ]
}

#修改根据文章id获取文章详情接口
http://api.itjh.net/v1/ArticleServer/queryArticleById/403?userId=1862462231


content 字段中新增isUserCollect 判断用户是否收藏过

