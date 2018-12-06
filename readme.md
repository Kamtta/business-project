# --------2018-12-03---------
## Git笔记
### git安装与配置 
#### 1、配置用户名
##### git config --global user.name "你的用户名"
#### 2、配置邮箱
##### git config --global user.email "你的邮箱"
#### 3、编码配置
##### 避免git gui中的中文乱码
##### git config --global gui.encoding utf-8
##### 避免git status显示的中文文件的乱码
##### git config --global core.quotepath off
#### 4、其他的配置
##### git config  --global core.ignorecase false
### git ssh key pair配置
#### 1、在git bash命令行窗口中输入
##### ssh-keygen -t rsa -C "你的邮箱"
#### 2、然后一路回车，不要输入任何密码之类，生成ssh key pair
#### 3、在用户目录下生成.ssh文件夹，找到公钥和私钥
##### id_rsa id_rsa.pub(公钥)
#### 4、将公钥的内容复制
#### 5、进人GitHub网站，将公钥添加进去，实现免钥登录
#### 6、执行git --version,出现版本信息，安装成功
### git工作原理
#### 编辑的时候处于工作区，将需要提交的内容添加到暂存区，然后提交到本地仓库，然后再提交到远程仓库GitHub上
### git常用命令
#### git init 创建本地仓库
#### git add . 添加所有文件到暂存区
#### git add 添加到暂存区
#### git commit -m "描述" 提交到本地仓库，并描述相关的信息
#### git status检查工作区文件状态
#### git log 查看提交commited的日记信息
#### git reset --hard 日记的唯一标识   提交版本的回退
#### git branch 查看分支（前面带着*，表示当前指向的分支）
#### git branch  分支名  创建分支（但不进行切换）
#### git checkout -b dev 创建并切换到dev分支
#### git clone 仓库地址 cloning to 本地仓库地址  克隆仓库 
#### 切换分支：git checkout 分支名
#### 拉取：git pull origin master
#### 提交：git push -u origin master(向master分支提交，第一次提交使用-u,接下来都不使用)
#### 分支合并：git merge 分支名(切换到master分支后对其他分支进行合并)
#### 关联远程仓库：git remote add origin "远程仓库的地址"
#### 第一次向远程仓库推送：git push -u origin master
#### 以后提交：git push origin master
#### 出现上传不了的时候，是因为远程仓库的东西没有拉下来，可在提交命令的后面加上-f，进行强制提交
### 企业项目开发模式
#### 分支开发，主干发布
#### 将分支推送到远程，切换到相应的分支：git push origin HEAD -u
###  .gitignore文件
#### 作用：告诉Git哪些文件不需要添加到版本管理中
##### 略的规则：#此为注释-将被Git忽略
##### *.a  忽略所有.a结尾的文件
##### ！lib.a  但lib.a除外
##### /TODO 仅仅忽略项目根目录下的TODO文件，不包括subdir/TODO
##### build/  忽略build/目录下的所有文件
##### doc/*.txt  忽略doc/notes.txt但不包括doc/server/arch.txt
### 删除远程仓库的文件
#### 1、git pull origin master
#### 2、git rm -r --cached "文件名"
#### 3、git commit -m "信息"
#### 4、git push -u origin master   
### 合并远程仓库的分支
#### 1、git checkout 分支名
#### 2、git pull origin 分支名
#### 3、git checkout master  
#### 4、git merge 分支名  
#### 5、git push origin master   <br/><br/>
# 电商项目-需求分析  
## 核心-购买
### 一、用户模块
#### 登录
#### 注册
#### 忘记密码
#### 获取用户信息
#### 修改密码
#### 登出
### 二、商品模块
#### 后台
#### 添加商品
#### 修改商品
#### 删除商品
#### 商品上下架
#### 查看商品
#### 前台（门户）
#### 搜索商品
#### 查看商品详情
### 三、类别模块
#### 添加类别
#### 修改类别
#### 删除类别
#### 查看类别
#### 看子类
#### 查看后代类别
### 四、购物车模块
#### 添加到购物车
#### 改购物车中某个商品的数量
#### 删除购物车商品
#### 全选/取消全选
#### 单选/取消单选
#### 看购物车中商品数量
### 五、地址模块
#### 添加地址
#### 修改地址
#### 删除地址
#### 查看地址
### 六、订单模块
#### 前台
#### 下订单
#### 订单列表
#### 取消订单
#### 订单详情
#### 后台
#### 订单列表
#### 订单详情
#### 发货
### 七、支付模块
#### 支付宝支付
#### 支付
#### 支付回调
#### 查看支付状态
### 八、线上部署
#### 阿里云部署  <br/><br/>
# --------2018-12-04----------
## 数据库设计
### 创建数据库
```
create database business;
use business;
```
### 用户表
```
create table user(
`id`  int(11)  not null  auto_increment comment '用户id',
`username`  varchar(50)  not null  comment '用户名',
`password`  varchar(50)  not null  comment '密码',
`email`     varchar(50)  not null  comment '邮箱',
`phone`     varchar(11)  not null  comment '联系方式',
`question`  varchar(100) not null  comment '密保问题',
`answer`    varchar(100) not null  comment '答案',
`role`      int(4)       not null  default 0 comment '用户角色，0：普通用户  1：管理员',
`create_time`  datetime  comment '创建时间',
`update_time`  datetime  comment '修改时间',
PRIMARY KEY(`id`),
UNIQUE KEY`user_name_index`(`username`)USING BTREE
 )ENGINE=InnoDB DEFAULT CHARSET=UTF8;
```
### 类别表
```
create table category(
`id`         int(11)  not null   auto_increment comment '类别id',
`parent_id`  int(11)  not null   default 0  comment '父类id',
`name`       varchar(50)  not null  comment '类别名称',
`status`     int(4)    default 1   comment '类别状态 1：正常  0：废弃',
`create_time` datetime  comment '创建时间',
`update_time` datetime  comment '修改时间',
PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

例子显示：
                id      parent_id
电子产品    1    1           0
家电        2    2           1  
手机        2    3           1
电脑        2    4           1
相机        2    5           1
华为手机    3    6           3     
小米手机    3    7           3
p系列       4    8           6
mate系列    4    9           6
```
### 商品表
```
create table product(
`id`            int(11)  not null auto_increment comment '商品id',
`category_id`   int(11)  not null comment '商品所属的类别id，值引用类别表的id',
`name`          varchar(100) not null comment '商品名称',
`detail`        text     comment '商品详情',
`subtitle`      varchar(200)  comment '商品副标题',
`main_image`    varchar(100)  comment '商品主图',
`sub_images`    varchar(200)  comment '商品子图',
`price`         decimal(20,2) not null comment '商品价格，总共20位，小数2位，整数18位',
`stock`         int(11)       comment '商品库存',
`status`        int(6)   default 1 comment '商品状态  1：在售 2：下架 3：删除',
`create_time`   datetime  comment '创建时间',
`update_time`   datetime  comment '修改时间',
PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;
```
### 购物车表
```
create table cart(
`id`      int(11)   not null auto_increment comment '购物车id',
`user_id` int(11)   not null comment '用户id',
`product_id`  int(11)  not null comment '商品id',
`quantity`  int(11)  not null comment '购买数量',
`checked`  int(4)   default 1 comment '1:选中  0：未选中',
`create_time`  datetime  comment '创建时间',
`update_time`  datetime  comment '修改时间',
PRIMARY KEY(`id`),
KEY `user_id_index`(`user_id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;
```
### 订单表
```
create table e_order(
`id`       int(11)      not null auto_increment comment '订单id，主键',
`order_no` bigint(20)   not null comment '订单编号',
`user_id`  int(11)      not null comment '用户id',
`payment`  decimal(20,2) not null comment '付款总金额，单位元，保留两位小数',
`payment_type`  int(4)  not null default 1 comment '支付方式 1：线上支付',
`status`   int(10)      not null comment '订单状态 0-已取消  10-未付款  20-已付款  30-已发货  40-已完成  50-已关闭',
`shipping_id`  int(11)  not null comment '收货地址id',
`postage`  int(10)      not null default 0 comment '运费',
`payment_time`  datetime  default null comment '已付款时间',
`send_time`  datetime  default null comment '已发货时间',
`close_time`  datetime  default null comment '已关闭时间',
`end_time`  datetime  default null comment '已结束时间',
`create_time`  datetime  default null comment '创建时间',
`update_time`  datetime  default null comment '修改时间',
PRIMARY KEY(`id`),
UNIQUE KEY `order_no_index`(`order_no`)USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;
```
### 订单明细表
```
create table order_item(
`id`        int(11)    not null  auto_increment  comment '订单明细id,主键',
`order_no`  bigint(20) not null  comment '订单编号',
`user_id`   int(11)    not null  comment '用户id',
`product_id` int(11)   not null  comment '商品id',
`product_name` varchar(100) not null comment '商品名称',
`product_image` varchar(100) comment '商品主图',
`current_unit_price` decimal(20,2) not null comment '下单时商品的价格，单位为元，保留两位小数',
`quantity`    int(10)  not null comment '商品购买的数量',
`total_price`  decimal(20,2) not null comment '商品的总价格，元为单位，保留两位小数',
`create_time`  datetime  default null comment '创建时间',
`update_time`  datetime  default null comment '修改时间',
PRIMARY KEY(`id`),
KEY `order_no_index`(`order_no`)USING BTREE,
KEY `order_no_user_id_index`(`order_no`,`user_id`)USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;
```
### 支付表
```
create table payinfo(
 `id`           int(11)    not null  auto_increment comment '主键',
 `order_no`     bigint(20) not null  comment '订单编号',
 `user_id`      int(11)  not null  comment '用户id',
 `pay_platform` int(4)  not null default 1  comment '1:支付宝 2:微信', 
 `platform_status`  varchar(50) comment '支付状态', 
 `platform_number`  varchar(100) comment '流水号',
 `create_time`    datetime  default null  comment '已创建时间',
 `update_time`    datetime  default null  comment '更新时间',
  PRIMARY KEY(`id`)
 )ENGINE=InnoDB DEFAULT CHARSET=UTF8;
 ```
 ### 地址表
 ```
 create table shipping(
 `id`       int(11)      not null  auto_increment,
 `user_id`       int(11)      not  null  ,
 `receiver_name`       varchar(20)      default   null  COMMENT '收货姓名' ,
 `receiver_phone`       varchar(20)      default   null  COMMENT '收货固定电话' ,
 `receiver_mobile`       varchar(20)      default   null  COMMENT '收货移动电话' ,
 `receiver_province`       varchar(20)      default   null  COMMENT '省份' ,
 `receiver_city`       varchar(20)      default   null  COMMENT '城市' ,
 `receiver_district`       varchar(20)      default   null  COMMENT '区/县' ,
 `receiver_address`       varchar(200)      default   null  COMMENT '详细地址' ,
  `receiver_zip`       varchar(6)      default   null  COMMENT '邮编' ,
 `create_time`       datetime      not null   comment '创建时间',
 `update_time`       datetime      not null   comment '最后一次更新时间',
  PRIMARY KEY(`id`)
 )ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
 ```
 <br/><br/>
 ## 笔记
 ### 项目架构--四层架构
```
1、视图层
2、控制层Controller
3、业务逻辑层Service
    接口和实现类
4、DAO层
```
<br/><br/>
### mybatis-generator安装配置及使用
```
pom配置：
<!-- mysql驱动包 -->
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.47</version>
    </dependency>

  
    <!--mybatis-generator依赖-->
    <dependency>
      <groupId>org.mybatis.generator</groupId>
      <artifactId>mybatis-generator-core</artifactId>
      <version>1.3.5</version>
    </dependency>
  
  build配置：
  在pluginManagement之前配置
  <plugins>
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.6</version>
                <configuration>
                    <verbose>true</verbose>
                    <overwrite>true</overwrite>
                </configuration>
            </plugin>
      
  </plugins>
  
  配置db.porperties文件：
  jdbc.username=root
  jdbc.password=******
  jdbc.driver=com.jdbc.mysql.Driver
  jdbc.url=jdbc:mysql://localhost:3306/business?CharacterEncoding=utf8
  插件配置：
  <generatorConfiguration>
        <property resource="db.properties"/>
      <!--配置mysql的驱动包jar，本地仓库的jar包位置，全限名-->
      <classPathEntry location=""/>
      <context id="context" targetRuntime="MyBatis3Simple">
          <commentGenerator>
              <property name="suppressAllComments" value="false"/>
              <property name="suppressDate" value="true"/>
          </commentGenerator>
          <!--配置相关的连接属性，使用el表达式进行获取导进来的db.properties属性值-->
          <jdbcConnection userId="" password="" driverClass="" connectionURL=""/>
  
          <javaTypeResolver>
              <property name="forceBigDecimals" value="false"/>
          </javaTypeResolver>
          <!-- 实体类，前面的包名为实体限定包名，后面为包的位置，如./src/main/java-->
          <javaModelGenerator targetPackage="" targetProject="">
              <property name="enableSubPackages" value="false"/>
              <property name="trimStrings" value="true"/>
          </javaModelGenerator>
          <!--配置sql文件,dao层的映射文件，也就是Mapper的包名，后面为包的位置，如./src/main/resources-->
          <sqlMapGenerator targetPackage="" targetProject="">
              <property name="enableSubPackages" value="false"/>
          </sqlMapGenerator>
          <!--生成Dao接口，dao层的包名，后面为包的位置-->
          <javaClientGenerator targetPackage="" type="XMLMAPPER" targetProject="">
              <property name="enableSubPackages" value="false"/>
          </javaClientGenerator>
  
          <!--配置数据表，有几张表就有相应的几个如下单项的配置，tablename对应的数据库表的名字，而domainObjectName则是本地实体类的名字-->
          <table  tableName="" domainObjectName=""  enableCountByExample="false" enableDeleteByExample="false"
                 enableSelectByExample="false" enableUpdateByExample="false"/>
      </context>
  </generatorConfiguration>
  
  在idea的右边显示中点击MavenProject中的plugin中的mybatis-generator进行执行。
```
### 项目框架搭建
```
1、建立相应的层级关系，相应的包有：service、dao、pojo、utils、commons和controller
2、将相应的web.xml、spring.xml、springmvc.xml、mybatis.xml等文件进行导入
3、搭建Tomcat服务器
```
### @RestController和@Controller的区别
```
@RestController中定义的相关内容返回的是json对象
@Controller中定义的相关内容返回的是视图对象
```
### 在web.xml文件中使用的/是指缺省名，如：在servlet中没有找到相应的方法映射的时候就会自动调用这个方法
### 在数据库设计中，订单的编号使用bigint类型，而不是varchar，是因为作为索引，更高效
### 在数据库设计中，冗余字段是指可以通过其他字段可以获取的字段，而在实际的开发中，需要用到冗余字段，是为了提升查询效率
<br/><br/>
# ------------------2018-12-05-----------------------
##笔记
###在@RestController中，返回的json数据的原理是：涉及的相关实体类，jvm自动去扫描相关的实体类的get方法，如果类中有定义的isXXX()方法，会被认为为get方法，然后返回
###在写dao层的数据库映射的时候，paramType应该为map类型，在自动集成的xml文件中，返回值的类型应该是ResultMap，名称为上面自动生成的Mapper的名称
###@RequestParam(value=" required=" defaultValue=")
```
required:默认是true，要求输入value的值，
        如果是false的话，可添可不添
defaultValue:如果required是false的话，采用这个值，如果是true的话，采用value的值
```