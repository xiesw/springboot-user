package com.gorge4j.user.interceptor;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * @Title: SqlInterceptor.java
 * @Description: MyBatis 拦截器实现 SQL 打印。@Signature 完成插件签名：告诉 MyBatis 当前插件用来拦截哪个对象的哪个方法
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-07-16 23:29:45
 * @version v1.0
 */

@Slf4j
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class SqlInterceptor implements Interceptor {
    
    /**
     * Mybatis 要求插件必须编写 Annotation 注解，是必须而不是可选。@Intercepts 注解：装载一个 @Signature 列表，一个 @Signature
     * 其实就是一个需要拦截的方法封装。一个拦截器要拦截多个方法，所以是一个 @Signature 列表。 Mybatis 只能拦截
     * ParameterHandler、ResultSetHandler、StatementHandler、Executor 共 4 个接口对象内的方法。 重新审视
     * interceptorChain.pluginAll() 方法：该方法在创建上述 4 个接口对象时调用，其含义为给这些接口对象注册拦截器功能，注意是注册，而不是执行拦截。
     * 拦截器执行时机：plugin() 方法注册拦截器后，那么，在执行上述 4 个接口对象内的具体方法时，就会自动触发拦截器的执行，也就是插件的执行。
     */

    private Properties properties;

    /**
     * 拦截目标对象的目标方法的执行 可以通过 invocation 来获取拦截的目标方法及参数，以及执行目标方法，包含代理的几个重要元素 method、target、args
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // MappedStatement 维护了一条 mapper.xml 文件里面 select 、update、delete、insert 节点的封装
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        // 示例：com.gorge4j.user.mapper.UserManageDemoMyBatisMapper.findUserManageDemoByNameAndPasswordAndType
        String sqlId = mappedStatement.getId();
        // BoundSql 表示动态生成的 SQL 语句以及相应的参数信息对象
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        // MyBatis 所有的配置信息都维持在 Configuration 对象之中，包括 id、Statement 类型、SQL 类型、mapper 的 xml 文件的绝对地址、是否使用缓存等配置
        Configuration configuration = mappedStatement.getConfiguration();
        Object returnValue = null;
        long start = System.currentTimeMillis();
        // 继续执行原目标方法
        returnValue = invocation.proceed();
        long end = System.currentTimeMillis();
        long time = (end - start);
        /** 当 SQL 执行大于 1 毫秒时打印 SQL，所以也可以用于慢 SQL 的打印及监控 */
        if (time > 1) {
            String sql = getSql(configuration, boundSql, sqlId, time);
            log.info(sql);
        }
        // 返回执行后的结果
        return returnValue;
    }

    /**
     * 作用：包装目标对象，包装的意思就是为目标对象创建一个代理对象
     */
    @Override
    public Object plugin(Object target) {
        /** 判断下需要拦截的目标对象类型，需要拦截时才拦截，否则直接返回本身，减少被代理的次数 */
        if (target instanceof Executor) {
            // 借助 Plugin 的 wrap 方法来使用当前的拦截器包装我们的目标对象，返回的就是为当前 target 创建的动态代理
            // 使用 JDK 的动态代理，给 target 对象创建一个 delegate 代理对象，以此来实现方法拦截和增强功能，它会回调 intercept() 方法。
            return Plugin.wrap(target, this);
        } else {
            // 无需拦截则直接返回目标对象本身
            return target;
        }
    }

    /**
     * 将插件注册时的 property 属性设置进来
     */
    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     * 组装需要打印的信息，包括 SQL 语句、特殊标记、SQL 执行时间等
     * 
     * @param configuration MyBatis 所有的配置信息都维持在 Configuration 对象之中
     * @param boundSql 表示动态生成的 SQL 语句以及相应的参数信息对象
     * @param sqlId 拦截的执行 SQL 的id（可以理解为方法）
     * @param time SQL 执行时间
     * @return
     */
    private static String getSql(Configuration configuration, BoundSql boundSql, String sqlId, long time) {
        String sql = showSql(configuration, boundSql);
        StringBuilder str = new StringBuilder(100);
        // 拦截的执行 SQL 的id（可以理解为方法），例如：com.gorge4j.user.mapper.UserManageDemoMyBatisMapper.update
        str.append(sqlId);
        str.append(" : ");
        // 具体执行的 SQL 语句
        str.append(sql);
        str.append(" >>>>>> ");
        // SQL 执行的时间
        str.append("SQL执行耗时: ");
        str.append(time);
        str.append("ms");
        return str.toString();
    }

    /** 按类型格式化 SQL 里的参数 */
    private static String getFormatParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }

    /** 组装需要打印的 SQL */
    private static String showSql(Configuration configuration, BoundSql boundSql) {
        // 取得参数的对象，实际上就是一个多个参数的 map 结构
        // 示例：parameterObject:MapperMethod{password=123456,name=xxxxxx,type=O,param3=O,param1=xxxxxx,param2=123456}
        Object parameterObject = boundSql.getParameterObject();
        // 查询 SQL 中的参数
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // 将 SQL 中一个或多个回车换行符号替换成一个空格
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (!parameterMappings.isEmpty() && parameterObject != null) {
            // Mybatis 在启动时就会通过 TypeHandlerRegistry 进行注册，即建立 JdbcType, JavaType, TypeHandler 三者之间的关系。
            // 因此，这意味着在 Mybatis 启动时我们也需要通过 TypeHandlerRegistry 将我们的所有的枚举类型（JavaType）与自定义的枚举
            // TypeHandler（EnumTypeHandler）建立联系
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            // 自定义 TypeHandler 时会走这个逻辑，建立 JavaType 和 JdbcType 之间的联系
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                // 替换 SQL 中的占位符 “?” 为具体的参数，replaceFirst 作为是替换匹配到的第一个占位符 “?”
                sql = sql.replaceFirst("\\?", getFormatParameterValue(parameterObject));
            }
            // 没有自定义的 TypeHandler，走通用逻辑
            else {
                // 拿到 target（拦截对象）的元数据
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                // 参数是按顺序存储的，下面逻辑按顺序来替换
                for (ParameterMapping parameterMapping : parameterMappings) {
                    // 获取参数的名称
                    String propertyName = parameterMapping.getProperty();
                    // 如果元数据对象中存在该参数，则替换相应的占位符 “?”
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getFormatParameterValue(obj));
                    }
                    // 如果有额外的参数，走下面的逻辑
                    else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getFormatParameterValue(obj));
                    }
                }
            }
        }
        // 返回组装好的 SQL
        return sql;
    }

}
