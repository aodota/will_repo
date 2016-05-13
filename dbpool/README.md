# DBPool
>超级简单的连接池

### 连接池配置
```XML
<?xml version="1.0" encoding="UTF-8"?>
<config>
	<db>
        <!-- 连接池名称 -->
		<name>will.game</name>
        <!-- 数据库Driver -->
		<driver>com.mysql.jdbc.Driver</driver>
        <!-- 数据库用户名 -->
		<user>root</user>
         <!-- 数据库密码 -->
		<password>1234</password>
		
        <!-- 连接池空闲检查周期 -->
		<poolCheckInterval>5000</poolCheckInterval>
		
        <!-- 最小连接数 -->
		<minConn>20</minConn>
        <!-- 最大连接数 -->
		<maxConn>50</maxConn>
        
		<!-- 连接最大空闲时间 -->
        <connAliveTime>60000</connAliveTime>
        <!-- 连接空闲多久之后检查连接存活 -->
		<checkInterval>60000</checkInterval>
        <!-- 使用之前是否需要检查连接存活 -->
		<testBeforeUse>true</testBeforeUse>
        <!-- 数据库存活检查SQL -->
		<testSql>select 1</testSql>
        <!-- 数据库连接URL -->
		<url>jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&amp;characterEncoding=utf8&amp;autoReconnect=true</url>
	</db>
</config>
```

### 创建一个连接池
```Java
ConnectionPoolManager.getInstance().configure(new FileInputStream(new File("dbpool.xml")));
```

### 与Spring整合

```XML
<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource" lazy-init="true">
    	<property name="driverClassName">
    		<value>com.will.dbpool.DBPoolDriver</value>
    	</property>
    	<property name="url">
    		<value>will.game</value>
    	</property>
</bean>
```



