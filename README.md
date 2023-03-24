# diary
个人日记、文章、文档服务

因为最近个人生活变化比较大，加上最近两年对自己过去生活状况理解比较糟糕。
虽然有经常学习、阅读的习惯，但对于了解自己、明白人生局限性、寻求自己想要的幸福，仅仅依靠大脑记忆还是不够。
又，互联网时代的程序猿，我早已不记得上次提笔是什么时候了。
所以，想编写一个服务，方便自己随时随地可以储存自己的日记、文章、心得等自己认为需要记录的文字及文件的服务。

本服务支持邮件、CSV文本储存、Mysql储存，支持直接通过API进行访问，对于客户端使用方式，可能会先做一个简单能用的PC端页面，具体暂时不做计划。

一些想法：本想使用Sqlite(单个文件、灵活方便)作为数据库，但是参考资料不够，若有大佬能使用SpringBoot3.0+JPA架构出使用Sqlite作为数据库的方式还请不吝告知。

### 技术选型
* 数据储存：Mysql
* ORM框架：Jpa
* 对象管理容器：SpringBoot
* Web框架：SpringWeb
* 密匙身份认证：
* Apache系列工具类
* Lombok
* 
