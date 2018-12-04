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
#### 4、git push -u origin master   <br/><br/>
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
#### 阿里云部署
