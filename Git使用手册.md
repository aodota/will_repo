Git使用手册
===========

### 下载Git Windows
[Git Windows](https://git-scm.com/download/)

### Git设置

- 在本地新建Git Workspace
- 进入该目录，右键`Git Bash Here`
- 输入命令

```Bash
# 初始化git
git init


# 创建一个全球用户名
git config --global user.name "aodota"

# 创建一个全球邮箱
git config --global user.email "MY_NAME@example.com"

# 存储git用户名和密码
git config --global credential.helper store
```
- 修改Git配置

![Git配置](image/Git配置.png)

### Git使用简介
- [git简易指南](http://www.bootcss.com/p/git-guide/)
- [git基本操作](http://www.runoob.com/git/git-basic-operations.html)
- [git教程](http://www.liaoxuefeng.com/wiki/0013739516305929606dd18361248578c67b8067c8c017b000)

### Git多用户使用

#### 1. 为每个用户创建`SSH Key`

``` Bash
ssh-keygen -t rsa -C "mail@email.com"
```

*记住不同用户输入不同的rsa文件名称*

#### 2. Github上添加生成的`SSH keys`

*对应的内容是`rsa.pub`中内容*

#### 3. 设置不同账户使用不同的`SSH Key`

```Bash
Host github1
 HostName github.com
 User user1
 IdentityFile ~/.ssh/id_rsa_user1

Host github2
 HostName github.com
 User user2
 IdentityFile ~/.ssh/id_rsa_user2
```

#### 4. 设置git项目下对应别名

位置在clone下来的git下面的`gitproject/.git/config`
``` Bash
# 原来的内容
url = git@github.com:user1/project.git
# 修改为的内容
url = git@github1:user1/project.git
```

#### 5. 为每个项目单独设置`username`和`email`

``` Bash
git config user.name "your_name"  
git config user.email "your_email"
```

