## QYCMS

最初打算为熟悉公司所用技术而写的练手项目，第一版准备先出一个文章发布系统。
包含简单的登录、文章管理。

项目为前后端分离项目，整体为三部分，两个前端一个后端。

此为Java后端。

~~浏览用的前端暂时未开发。~~

[qy-console](https://github.com/iwinder/qy-console.git)为管理端前端

[qy-web](https://github.com/iwinder/qy-web.git) 浏览端前端

### 简单历程
- 2018.11.04  完成初版的浏览端首页主要功能的静态布局。
- 2018.11.03 完成第一版博客的管理功能（包含分类、标签、图片上传）；用户管理功能（用户增删改查）。


```js
gradle build -Denv=test -x test
gradle build   -x test
```