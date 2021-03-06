# 用户管理系统介绍
通过实现一个用户管理系统，从解决问题的视角，以技术演进的方式逐步集成各种技术，由浅入深，循序渐进。

### 需求背景
> 1、用户注册的功能（用户有管理员类型及普通类型，仅支持普通类型用户的注册，管理员用户在创建数据库时初始化进去）；  
> 2、用户登录的功能（支持管理员及普通用户的登录，通过类型切换，登录后显示个人信息及功能菜单）；  
> 3、用户修改个人信息（暂时仅支持密码）的功能；  
> 4、管理员查看用户列表的功能，列表模块支持逻辑删除用户的功能；  
> 5、管理员添加用户的功能（要求用户名不能重复）；  
> 6、退出登录的功能（清除登录的 Session ，跳转到登录页）；  
> 7、完全遵守阿里的 P3C 编码规范；  
> 8、SpotBugs（原Findbugs） 静态代码检查清零；  
> 9、SonarLint 静态代码检查清零；  
> 10、测试用例覆盖到 Service 层的每一个接口；  
> 11、代码统一格式化（采用了谷歌的 Java 代码模版 [去下载](https://github.com/google/styleguide/blob/gh-pages/eclipse-java-google-style.xml)，模版略有修改，下载后需导入 Eclipse 中进行部分修改后使用）。  

## 技术演进记录  

**1.19.2-RELEASE**  
代码优化：1、按照阿里巴巴的编码规范，给部分类加上了 @Override 及 @author；2、增加了 MyBatis 插件（拦截器）相关的参考链接。

**1.19.1-RELEASE**  
代码优化：在SpringBoot集成MyBatis拦截器插件实现SQL日志打印的基础上，增加了拦截时仅针对包含注解 @SqlPrint 的对象才进行日志的打印。  
增加这个有场景的实例是为了方便大家加深对注解的理解。需要注意的是，因为注解是添加在实体类上，这意味着只有对包含注解的实体类进行操作时才能正常打印日志。简单来说，就是注解 @SqlPrint 是添加在 UserManageDemoMyBatis 类上，只有 UserServiceMyBatisImpl 的 modify 方法中对数据库的操作是基于对对象 UserManageDemoMyBatis 的操作，所以从功能上来说只有在修改用户密码时才会打印日志，而上一个版本中是打印所有的 SQL 日志，理解注解需留意其中的差异。

**1.19.0-RELEASE**  
SpringBoot集成MyBatis拦截器插件实现SQL日志打印    
MyBatis 组件允许你在已映射语句执行过程中的某一点进行拦截调用。默认情况下，MyBatis 允许使用插件来拦截的方法调用包括：  

>* Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)  
>* ParameterHandler (getParameterObject, setParameters)  
>* ResultSetHandler (handleResultSets, handleOutputParameters)  
>* StatementHandler (prepare, parameterize, batch, update, query)  

方法的作用分别如下：  
>* Executor - MyBatis 执行器，是MyBatis 调度的核心，负责SQL语句的生成和查询缓存的维护；  
>* ParameterHandler - 负责对用户传递的参数转换成 JDBC Statement 所需要的参数； 
>* ResultSetHandler - 负责将 JDBC 返回的 ResultSet 结果集对象转换成 List 类型的集合；  
>* StatementHandler - 封装了 JDBC Statement 操作，负责对 JDBC statement 的操作，如设置参数、将 Statement 结果集转换成 List 集合。  

通过 MyBatis 提供的强大机制，使用插件是非常简单的，只需实现 Interceptor 接口，并指定想要拦截的方法签名即可。MyBatis 官网关于插件（拦截器）的介绍 [点击查看](http://www.mybatis.org/mybatis-3/zh/configuration.html#plugins)

**1.18.1-RELEASE** 
代码优化：1、修复注册用户名正则校验规则的 BUG（正确的规则：6-30位长度的字母及数字组合）；2、将 MyBatis 的 Mapper 类移入独立的 mapper 目录，并修改对应日志中的路径；3、MySQL 连接增加时区的配置参数，避免数据库的创建及更新时间错误；4、优化定时任务的执行频率，减少日志的打印；5、用户信息更新采用对象的形式而非直接参数的形式。

**1.18.0-RELEASE**  
SpringBoot添加自定义规则校验器注解  
Bean Validation 是一个数据验证规范，属于 Java EE 6 的子规范，规范的参考实现为 hibernate-validator，也是 Spring 框架推荐使用的实现。AbstractMessageInterpolator 消息解析有优先顺序，从高到底：ValidationMessages\*.properties > ContributorValidationMessages\*.properties > org.hibernate.validator.ValidationMessages\*.properties 顺序。  
  

**1.17.1-RELEASE**  
代码优化：修改拼写错误的文件命名。  

**1.17.0-RELEASE**  
SpringBoot集成Swagger组件  
Swagger 是用于 API 接口的描述可视化组件，可以将服务中的 API 接口通过网页服务的形式展现出来，解决工作中接口文档更新维护不及时的难题，并可用于接口的单元测试。Swagger 组件的集成，首先在 POM 中引入相关的依赖，然后增加 Swagger 的配置类，接着在需要生成描述的接口上加上必要的注解就集成完毕了。本例中有个特殊情况是之前集成了 Filter 相关的内容，因为 Swagger 需要开放 HTTP 服务，所以也需要加过滤器，否则会被拦截，这点需要注意。  

**1.16.1-RELEASE**  
代码优化：小部分代码不符合阿里 P3C 规范的修改，之前 P3C 插件有段时间故障，卸载插件重新安装了一下，再次扫描了代码。另外，添加了部分集成技术或组件的官网或者藏考网址。   

**1.16.0-RELEASE**  
SpringBoot集成异步处理  
SpringBoot 集成异步处理非常简单，启动类 SpringbootUserApplication 加上注解 @EnableAsync 以便支持异步的注解。然后在需要支持异步的方法上添加 @Async 注解来支持异步。注意在同一个类内部进行异步方法的调用是失效的，本次集成的例子是在 Controller 层调用 Service 层的异步方法。例子中删除用户的方法变成异步后，可以看到删除用户后在用户列表里依然存在，需要刷新之后才会消失，很明显的能看到异步的效果，注意查看效果时 Service 的实现是 MyBatis 版本，如果需要切换回同步调用，删除上述内容中提到的 @EnableAsync 和 @Async 即可。详细请参考官网的用法和解释 [点击查看](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/annotation/EnableAsync.html)  

**1.15.0-RELEASE**  
SpringBoot集成定时任务  
SpringBoot 集成定时任务非常简单，启动类 SpringbootUserApplication 加上注解 @EnableScheduling 以便支持定时任务的注解。然后在需要支持定时任务的方法上添加类似如下注解 @Scheduled(cron = "0/30 * * * * ?") 来支持定时任务，括弧里代表了时间的参数配置，详细请参考官网的用法及解释 [点击查看](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/annotation/EnableScheduling.html)  

**1.14.0-RELEASE**  
SpringBoot集成Controller层请求参数及返回结果的打印  
SpringBoot 集成请求参数及返回结果的切面类，将参数及结果以日志的形式打印出来。集成的方式很简单，在 com.gorge4j.user.aspect 下创建切面处理类 ReqAndRespLogAspect，留意类中方法上的扫描路径及 @Aspect 注解。AspectJ 的官网 [点击查看](https://www.eclipse.org/aspectj/)，学习的网站 [点击查看](https://www.baeldung.com/aspectj)。

**1.13.0-RELEASE**  
SpringBoot集成Lombok组件  
SpringBoot 集成 Lombok，以便精简部分代码。首先 IDE 需添加对 Lombok 的支持（官网里有具体的操作说明：[Eclipse集成](https://www.projectlombok.org/setup/eclipse)，[IDEA集成](https://www.projectlombok.org/setup/intellij)），另外 POM 也需添加对 Lombok 的依赖，然后将部分 POJO 类（例如：DTO、VO、Entity等）里的 Getter/Setter 方法及 Controller 层及 Service 层的日志（例如：@slf4j）换成 Lombok 对应的注解，以便精简部分代码。Lombok 的官方网址 [点击查看](https://www.projectlombok.org/)  

**1.12.1-RELEASE**  
代码优化：SpotBugs静态代码检查清零，新增部分配置解决控制台日志 WARN 警告提示的问题。

**1.12.0-RELEASE**  
SpringBoot集成Druid连接池组件  
SpringBoot 集成了 JdbcTemplate 或者 JPA 组件后（POM 中引入了相关依赖），SpringBoot 会自动加载一些配置，其中就包括连接池的配置，默认的连接池配置是 Hikari 连接池。此次集成在 application.properties 中添加了 spring.datasource.type=com.alibaba.druid.pool.DruidDataSource 配置后，连接池就用成了 Druid，阿里巴巴开源的连接池，本次也集成Druid配套的监控服务（服务启动后通过此链接来访问监控的控制台：http://127.0.0.1:8080/druid/sql.html，账号密码相关配置在包com.gorge4j.user.servlet下的DruidStatViewServlet类里）。官网 [点击查看](https://github.com/alibaba/druid/wiki/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98)  

**1.11.1-RELEASE**  
代码优化：Actuator 健康检查设置独立的端口，跟应用服务的端口隔离开，配置如下：management.server.port=8081 ，访问时这么访问：http://localhost:8081/actuator/health。另外优化了上一个版本中提到的 Actuator 的官网链接，也优化了 Thymeleaf 及 JPA 的部分配置。

**1.11.0-RELEASE**  
SpringBoot集成Actuator健康检查  
实战中有一个需求，就是要保证服务的稳定性，我们希望能实时知道服务的进程是不是活着，服务调用是否正常。从这两个维度可以监控到服务的健康状况，进程可以从运维层面解决，而健康检查需要从服务中入手，最好服务中能提供一个接口，这个接口不做任何具体的业务，只是用来检测服务的健康状态，然后我们可以通过一些定时任务或监控的检查机制，定期调这个接口，如果接口正常相应，说明服务正常。SpringBoot 可以很方便的集成健康检查的组件。首先 POM 中引入依赖 spring-boot-starter-actuator，application.properties 中添加配置 management.endpoints.web.exposure.include=*，这个是添加所有的检测端点，然后通过访问链接（例如：http://localhost:8080/actuator/health）就可以查看接口的相应结果。实战中建议按需要去开，并做好鉴权和访问规则限制等。相关内容及详细使用可查看 SpringBoot 官网的介绍 [点击查看](https://docs.spring.io/spring-boot/docs/current/reference/html/howto-actuator.html)。

**1.10.1-RELEASE**  
代码优化：优化了部分注释，规范了部分文件的命名，修复了部分不影响核心功能的小 BUG，优化了部分代码结构。

**1.10.0-RELEASE**  
SpringBoot集成MyBatis持久化组件  
这个版本集成 MyBatis 持久化组件，MyBatis 目前在实践过程中使用的频率非常高，也是一个非常有灵活性和生产力的组件。集成稍微复杂一些，请重点关注这个版本的提交记录涉及的文件及修改的内容。MyBatis 的官方网站及相关简介 [点击查看](http://www.mybatis.org/mybatis-3/zh/index.html)。

**1.9.0-RELEASE**  
SpringBoot集成JPA持久化组件  
这个版本集成了 Spring 的 JPA 持久化组件，JPA 默认实现了一些常用的 CURD 的一些方法，可以进一步精简代码。集成也非常的简单，POM 依赖加上 spring-boot-starter-data-jpa 的依赖，包 com.gorge4j.user.entity 下创建 UserManageDemoJpa 实体类，包 com.gorge4j.user.dao 下创建 UserRepository 持久化的操作类，包 com.gorge4j.user.core.impl 下创建 UserServiceJpaImpl 业务逻辑处理类，集成完毕。Spring Data JPA 官方网站及相关介绍 [点击查看](https://spring.io/projects/spring-data-jpa#overview)。

**1.8.3-RELEASE**  
代码优化：将 JdbcTemplate 事务统一切回注解式的事务（更简单），同时修复部分不影响主体功能的小 BUG，优化部分注释。

**1.8.2-RELEASE**  
此版本集成了 JdbcTemplate 声明式事务
在上一个版本 JdbcTemplate 支持注解式的事务基础上，添加对声明式事务的支持。以包 com.gorge4j.user 下的 UserServiceJdbcTemplateImpl 类中的 add 及 delete 方法为例来添加对声明式事务的支持。需要在文件目录 src/main/resources 下添加 transaction.xml 配置文件，添加事务管理器及事务通知和切面的一些配置，并在启动类 SpringBootUserApplication 下导入该 XML 资源即可。

**1.8.1-RELEASE**  
此版本集成了 JdbcTemplate 注解式事务
上一个版本集成了 JdbcTemplate 持久化组件，这里有一个问题，就是没有添加对事务的支持。之前也提到，事务在工作实践中非常重要，那么这个版本来集成一下事务，我们先实现注解式的事务，效果是在 UserServiceJdbcTemplateImpl 类需要做事务控制的方法上添加 @Transactional(rollbackFor = Exception.class) 注解即可支持事务。在启动类 SpringBootUserApplication 里加上注解 @EnableTransactionManagement 等同于在XML配置文件里加上 <tx:annotation-driven /> 配置。POM 里添加依赖 spring-boot-starter-jdbc 之后，框架会默认注入 DataSourceTransactionManager 实例。
总结一下：POM 里添加依赖 spring-boot-starter-jdbc 之后，只需要在方法上添加 @Transactional(rollbackFor = Exception.class) 注解即可支持事务，启动类 SpringBootUserApplication 不需要添加注解 @EnableTransactionManagement，也不需要通过配置文件添加 <tx:annotation-driven />配置。参考网址 [点击查看](https://www.ibm.com/developerworks/cn/java/j-master-spring-transactional-use/)

**1.8.0-RELEASE**  
此版本集成了 JdbcTemplate 持久化组件  
前面的版本我们能看到，对数据库的操作都是基于原生的 JDBC，每次都要手动获取数据库连接、处理异常、关闭连接，代码写法及逻辑处理都非常的低效，我们需要通过持久化组件来提高效率，JdbcTemplate 就是 Spring 官方提供的其中一种数据持久化的组件，来提升对数据库操作的效率。JdbcTemplate 默认使用的 Hikari 连接池 [GIT开源地址](https://github.com/HikariObfuscator/Hikari)。另外，项目也支持 JdbcTemplate 和原生 JDBC版本的切换，在 Controller 层 Service 注入的地方，可以通过切换实现类的别名来选择不同的版本，通过两个版本的对比也能看出 JdbcTemplate 版本代码更精简。Spring Data JDBC 官方网站及相关介绍 [点击查看](https://spring.io/projects/spring-data-jdbc#overview)。  

**1.7.0-RELEASE**  
SpringBoot集成JDBC事务的控制  
数据库数据的一致性在工作实战中是非常重要的一件事，要特别重视，一方面建议在数据库表设计表结构的时候加上业务唯一索引（可能是单个字段的索引，也可能是多字段的联合索引），另一方面要在代码层面处理好事务的事情，在逻辑处理出现异常的时候及时回滚事务，此版本实现了 JDBC 的事务控制。实际工作中一定要非常注意这个问题，确保加上了事务控制的逻辑，同时一定要去验证确认事务逻辑确实有效。因为脏数据的处理非常的麻烦，并且容易引发其它问题。关于数据库事务的百度百科 [点击查看](https://baike.baidu.com/item/%E6%95%B0%E6%8D%AE%E5%BA%93%E4%BA%8B%E5%8A%A1/9744607)

**1.6.1-RELEASE**  
代码优化：替换了部分常量，按照标准格式化了前端代码，增加了部分注释，优化了部分小bug，提升了部分代码的可读性

**1.6.0-RELEASE**  
SpringBoot集成图形验证码  
随着技术的发展，人工智能逐渐出现在生活中，在一些应用系统中，如何识别是人在操作还是机器在操作，显得尤为重要，因为系统的资源是有限的，如果机器操作占用了大量的服务器资源，会导致资源不足从而影响正常用户的操作，有时候甚至能引起金钱上的损失（比如通过机器恶意刷短信验证码），所以如何防范机器人显得尤为重要。这次的版本集成的图形验证码就是为了一定程度上解决这个问题，通过增加图形验证码，增加了机器人操作的难度，也相当于增加了攻击的成本，虽然现在有一些图片识别的技术，但是性价比不高的事情，如果得不偿失攻击者也不会去做，所以相当于一定程度上增加了系统的安全性。这次集成的内容稍多，详细内容请看提交记录。

**1.5.0-RELEASE**  
SpringBoot集成Filter过滤器  
在系统中我们可以看到，针对管理员和普通用户，目前对权限的校验只是放到了页面上，也就是只在前端模版引擎 JSP 及 Thymeleaf 层做了校验，实际上在 Controller 这一层没有对权限进行有效的控制，没有登录的情况下，通过浏览器直接输入网址也能访问到没有权限访问的页面（虽然没有数据），但这肯定是有问题的。这次的版本就是为了解决这个问题，我们集成了过滤器 Filter 对管理员及普通用户的权限进行了控制。集成的方式，在包 com.gorge4j.user.filter 下建了两个 Filter ，一个用于所有用户的鉴权，一个用于管理员用户的鉴权。另外在启动类上加了一个注解：@ServletComponentScan(value="com.gorge4j.user.filter")，将过滤器注入到容器里，简单来说就是让服务知道这两个类是过滤器，而不是普通的类。关于 Filter 的介绍可查看 Apache 官方文档介绍 [点击查看](http://tomcat.apache.org/tomcat-9.0-doc/servletapi/index.html)。

**1.4.0-RELEASE**  
SpringBoot集成Controller层参数校验及全局异常的处理  
实战中我们经常遇到的一个问题就是参数校验的问题，如果把参数的校验都放到 Controller 层或者 Service 层来校验，一方面会增加业务代码的复杂性，也让业务代码显得非常的臃肿。还有一个问题就是，校验不通过的结果如何告诉用户。我们期望的结果是，参数的校验最好能通过简单的配置来实现，检验不通过的自动抛出异常，然后系统自动处理异常，并且把异常处理的结果封装好告诉用户，业务层面无须干预。JSR303 规范 [官网查看](https://www.jcp.org/en/jsr/detail?id=303) <解决参数校验的问题>及全局异常处理<解决参数校验的异常封装的问题>就是为了一起配合解决这个问题。此版本集成了参数的校验及全局异常的处理。参数校验集成的方式，包 com.gorge4j.user.dto 下面的数据传输对象需要加上校验的规则及提示信息，Controller 层的参数前面需要加上 @Valid 的注解。全局异常处理集成的方式，包 com.gorge4j.user.enumeration 添加了一个常用请求失败的返回码和返回信息定义，全局异常处理类的核心逻辑在包 com.gorge4j.user.handler 下的 GlobalExceptionHandler 类里边。上一个版本也能看到，项目实际上前后端都是没有参数校验的，这个是有问题的，这个版本就是为了解决这个问题。

**1.3.0-RELEASE**  
SpringBoot集成Logback日志组件  
日志在开发工作中的重要性不言而喻，程序的调试、问题的定位、数据的分析甚至程序的监控等都离不开日志数据，JDK 也有相关的日志工具 java.util.logging ，但是功能不够强大。Logback 是日志组件中的比较优秀的，使用方面目前在市场上属于主流，在易用性、灵活配置（包括日志级别的控制、日志传递的控制、日志应用范围的控制等）方面有比较大的优势。此版本集成了 Logback 的日志打印组件，关于如何配置 logback.xml 里有详细的注释。集成的方式，除了需添加 logback.xml 配置文件，pom 的依赖里也需要加上 spring-boot-starter-logging 的依赖，然后程序里的需定义日志的实例（例如：private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);），还需要注意 import 导入的具体包（import org.slf4j.Logger; 和 import org.slf4j.LoggerFactory;），详情见包 com.gorge4j.user.core.impl 下的 UserServiceImpl 类中的实例。[查看官网](https://logback.qos.ch/)

**1.2.0-RELEASE**  
SpringBoot集成Thymeleaf前端模版引擎  
Spring Boot 官方推荐的前端模版引擎是 Thymeleaf（[官网查看](https://www.thymeleaf.org/)），此版本集成了 Thymeleaf ，实现功能跟 JSP 版本基本没有差异，有部分细节展示上有细微的差异。JSP/Thymeleaf 可以并存，那如何切换呢？可以通过配置文件 application.properties 下的 spring.thymeleaf.enabled 配置来切换，当 spring.thymeleaf.enabled=true 时 Thymeleaf 模版引擎生效，当 spring.thymeleaf.enabled=false 时 JSP 模版引擎生效。注意一个细节，如果要打包然后运行，pom 的 packaging 配置需要改成 jar，当以 jsp 版本打包运行时需改回 war，这个细节需注意。

**1.1.0-RELEASE**  
SpringBoot集成登录密码的MD5加密  
实际工作中，为了防止用户密码明文的泄漏（内部能接触到数据库数据的员工，或者黑客攻击拖库等），密码通过 MD5 加密之后不可逆，即便坏人拿到了数据，也无法拿到真实的密码，达到保密的作用，实操中为了进一步加强密码的强度，可以采取 MD5 加盐的方式（举例说明：把用户的明文密码+用户id之后的字符串进行md5加密之后存到数据库）进行加密。此次版本用户登录的密码改成了 MD5 加密的方式，注意原来数据库里的管理员用户 admin 及其它普通用户的明文密码需要改成 MD5 加密后的字符串，然后回填到数据库里，常用密码：123456对应的 MD5 密文是：e10adc3949ba59abbe56e057f20f883e  
说明：所有的加密手段没有100%绝对的安全，比如虽然 MD5 加密不可逆，但是只是计算的难度太大，随着计算机运算的能力越来越强，有朝一日也是可以短时间直接解密的，并且随着 MD5 加密应用的普及，积累的样本越来越多，越来越多的数据可以通过撞库手段反解出明文。实操能做的就是根据各种场景的需要，通过各种加密手段，增加破解的难度和成本，降低被破解的概率，保证相对的安全。另外，安全不是一个孤立的概念，需要从软/硬件、系统、存储、网络、防火墙、程序、加密甚至内部管理流程/规范等多个方面综合来保障安全。另外，对加密算法有兴趣的可以去了解 [加密算法](https://baike.baidu.com/item/加密算法)  
将所有用户的密码改成 MD5 加密之后的值：e10adc3949ba59abbe56e057f20f883e，原文：123456，以下是 SQL 语句  

```sql
UPDATE `springboot-user-demo`.`user_manage_demo` SET password = 'e10adc3949ba59abbe56e057f20f883e' WHERE is_delete = false;
```

**1.0.0-RELEASE**  
通过SpringBoot+JSP+MySQL实现简单的用户管理系统  
采用了Spring Boot + JSP + MySQL，并使用了 JSP 的 JSTL 标签，实现了简单的用户管理系统，完全遵守阿里 P3C规范（安装地址：https://p3c.alibaba.com/plugin/eclipse/update，在 Eclipse 里通过 “帮助”——>“安装新软件”，然后输入安装地址来安装）、Findbugs / SpotBugs（[官网查看](https://spotbugs.github.io/)，Eclipse可以在应用市场里直接安装） 告警清零、SonarLint （[官网查看](https://www.sonarlint.org/)）告警清零，代码格式化使用了谷歌的 Java 代码格式模版 [去下载](https://github.com/google/styleguide/blob/gh-pages/eclipse-java-google-style.xml)（调整了缩进<2个改成了4个>和换行<100改到了阿里 P3C 要求的120>），模版文件下载后需导入 Eclipse 中进行部分修改后使用；

### 采用的技术栈
基础技术栈是 Spring Boot + MySQL + JSP/Thymeleaf，目标是以技术演进的方式，以解决问题的视角，逐步集成各种技术，最终过渡到一个可以投入到实战开发的脚手架项目。  

### 使用说明
1、笔者的本地数据库账号及密码为：root/12345678，如果你本地数据库的账号密码不一样，请在项目的包 com.gorge4j.user.constat 下的 SqlConstant.java 类里修改成自己的账号密码，注意密码需 base64 加密 [去加密](http://tool.oschina.net/encrypt?type=3)；  
2、在 MySQL 中创建数据库，数据库名：springboot-user-demo，字符集：utf8mb4，排序规则：utf8mb4\_general\_ci；  
3、在 MySQL 中创建表，SQL 如下：
 
```sql 
CREATE TABLE `user_manage_demo` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id，用户id',
  `name` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '用户密码',
  `type` varchar(5) NOT NULL COMMENT '用户类型（A：管理员；O：普通用户）',
  `gmt_create` datetime NOT NULL COMMENT '记录创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '记录修改时间',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '记录是否逻辑删除（0：未删除；1：已删除）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name_type` (`name`,`type`) USING BTREE COMMENT '用户名称、类型唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='用户管理表';
```

4、数据库表中初始化一条管理员数据  

```sql
INSERT INTO `springboot-user-demo`.`user_manage_demo`(`id`, `name`, `password`, `type`, `gmt_create`, `gmt_modified`, `is_delete`) VALUES (1, 'admin', '123456', 'A', '2019-05-19 20:26:22', '2019-05-19 20:26:27', 0);
```
用户登录密码切换到 MD5 加密的版本时注意执行下面的 SQL 语句，执行完后所有有效的用户登录的密码初始化成了：123456  

```sql
UPDATE `springboot-user-demo`.`user_manage_demo` SET password = 'e10adc3949ba59abbe56e057f20f883e' WHERE is_delete = false;
```

5、将项目下载到本地，然后通过 IDE（Eclipse/IDEA） 导入项目后启动项目，启动正常后，在浏览器中输入：http://localhost:8080/login 来访问用户管理系统（管理员账号及密码：admin/123456），常用的访问地址:  
注册：http://localhost:8080/regisger   
登录：http://localhost:8080/login  
登录后的首页：http://localhost:8080/index  
