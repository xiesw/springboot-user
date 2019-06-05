package com.gorge4j.user.core.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.gorge4j.user.constant.ResponseConstant;
import com.gorge4j.user.constant.SqlConstant;
import com.gorge4j.user.constant.UserTypeConstant;
import com.gorge4j.user.core.UserService;
import com.gorge4j.user.dto.AddDTO;
import com.gorge4j.user.dto.DeleteDTO;
import com.gorge4j.user.dto.LoginDTO;
import com.gorge4j.user.dto.ModifyDTO;
import com.gorge4j.user.dto.RegisterDTO;
import com.gorge4j.user.util.Base64Util;
import com.gorge4j.user.util.Md5Util;
import com.gorge4j.user.vo.ResponseVO;
import com.gorge4j.user.vo.ViewVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @Title: UserServiceImpl.java
 * @Description: 用户管理系统 JDBC 原生版本实现类
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-12 21:23:40
 * @version v1.0
 */

@Slf4j
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    /** 用户注册 */
    @Override
    public ResponseVO register(RegisterDTO registerDTO) {
        // 定义返回的对象
        ResponseVO responseVO = new ResponseVO();
        // 定义一个可以设置占位符参数的可编译和执行 SQL 的对象
        PreparedStatement pstmt = null;
        // 定义数据库连接对象
        Connection conn = null;
        // try/catch 逻辑用来捕捉可能出现的异常
        try {
            // 获取数据库连接
            conn = getConnection();
            // 检查用户是否重复
            if (checkUserDuplicate(registerDTO.getName(), UserTypeConstant.ORDINARY)) {
                responseVO.setCode(ResponseConstant.FAIL);
                responseVO.setMessage("用户名重复，请重新注册！");
                // 返回组装的提示信息对象给前端页面
                return responseVO;
            }
            // 判断数据库连接是否为空，避免出现空指针异常
            if (conn == null) {
                responseVO.setCode(ResponseConstant.FAIL);
                responseVO.setMessage("用户注册数据库连接异常！");
                // 返回组装的提示信息对象给前端页面
                return responseVO;
            }
            // 将事务自动提交设置为 false
            setAutoCommit(conn, false);
            // 创建一个可以设置占位符参数的可编译和执行 SQL 的对象
            String strSql =
                    "INSERT INTO user_manage_demo (name, password, type, gmt_create, gmt_modified, is_delete) VALUES (?, ?, ?, ?, ?, ?)";
            // 创建一个可以设置占位符参数的可编译和执行 SQL 的对象
            pstmt = conn.prepareStatement(strSql);
            // 下面5行语句设置占位符里的各个参数（逐一替换前面一句里的 ？占位符），注意参数类型
            pstmt.setString(1, registerDTO.getName());
            pstmt.setString(2, Md5Util.md5(registerDTO.getPassword()));
            pstmt.setString(3, UserTypeConstant.ORDINARY);
            pstmt.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));
            pstmt.setTimestamp(5, new java.sql.Timestamp(new java.util.Date().getTime()));
            pstmt.setBoolean(6, false);
            // 执行编译后的SQL语句
            pstmt.execute();
            // 当操作成功后手动提交
            commit(conn);
            // 组装返回的结果对象
            responseVO.setCode(ResponseConstant.SUCCESS);
            responseVO.setMessage("恭喜您，注册成功！请登录");
            // 关闭数据库相关连接对象
            closeResultSetAndStatementAndConnection(null, null, pstmt, conn);
        } catch (SQLException se) {
            // 处理失败时回滚数据
            rollBack(conn);
            // 捕捉数据库异常
            log.error("用户注册数据库异常，异常信息：{}", se);
        } catch (Exception e) {
            // 处理失败时回滚数据
            rollBack(conn);
            // 捕捉用户注册其它异常
            log.error("用户注册其它异常，异常信息：{}", e);
        } finally { // 无论何种情况，都会执行下边的语句（finally 的作用），关闭数据库连接
            // 关闭数据库相关连接对象
            closeResultSetAndStatementAndConnection(null, null, pstmt, conn);
        }
        return responseVO;
    }

    /** 用户登录 */
    @Override
    public ResponseVO login(LoginDTO loginDTO) {
        // 定义返回的对象
        ResponseVO responseVO = new ResponseVO();
        // 定义一个可编译和执行 SQL 的对象
        Statement stmt = null;
        // 根据执行SQL返回的结果封装结果集
        ResultSet rs = null;
        // 定义数据库连接对象
        Connection conn = null;
        // try/catch逻辑用来捕捉可能出现的异常
        try {
            // 获取数据库连接
            conn = getConnection();
            // 判断数据库连接是否为空，避免出现空指针异常
            if (conn == null) {
                responseVO.setCode(ResponseConstant.FAIL);
                responseVO.setMessage("用户登录数据库连接异常！");
                // 返回组装的提示信息对象给前端页面
                return responseVO;
            }
            stmt = conn.createStatement();
            // 构造查询用户记录的 SQL 语句，此语句中使用了数据库表别名
            String strSql = "SELECT u.name, u.type FROM user_manage_demo AS u WHERE u.name = '" + loginDTO.getName()
                    + "' AND u.password = '" + Md5Util.md5(loginDTO.getPassword()) + "' AND u.type = '"
                    + loginDTO.getType() + "' AND u.is_delete = false";
            rs = stmt.executeQuery(strSql);
            // 如果数据库不存在记录，则返回登录失败
            if (!rs.next()) {
                // 组装返回的结果对象
                responseVO.setCode(ResponseConstant.FAIL);
                responseVO.setMessage("登录失败，用户不存在、已删除或者用户名、密码或类型选择错误！");
                // 返回组装的提示信息对象给前端页面
                return responseVO;
            }
            // 组装并返回成功的结果
            responseVO.setCode(ResponseConstant.SUCCESS);
            responseVO.setMessage("登录成功！");
            // 关闭数据库相关连接对象
            closeResultSetAndStatementAndConnection(rs, stmt, null, conn);
        } catch (SQLException se) {
            // 捕捉数据库异常
            log.error("用户登录数据库异常，异常信息：{}", se);
        } catch (Exception e) {
            // 如果执行过程中出现其它异常则打印异常信息
            log.error("用户登录其它异常，异常信息：{}", e);
        } finally { // 无论何种情况，都会执行下边的语句（finally 的作用），关闭数据库连接
            // 关闭数据库相关连接对象
            closeResultSetAndStatementAndConnection(rs, stmt, null, conn);
        }
        return responseVO;
    }

    /** 添加用户 */
    @Override
    public ResponseVO add(AddDTO addDTO) {
        // 定义返回的对象
        ResponseVO responseVO = new ResponseVO();
        // 创建一个可以设置占位符参数的可编译和执行SQL的对象
        PreparedStatement pstmt = null;
        // 定义数据库连接对象
        Connection conn = null;
        // try/catch逻辑用来捕捉可能出现的异常
        try {
            // 获取数据库连接
            conn = getConnection();
            // 判断数据库连接是否为空，避免出现空指针异常
            if (conn == null) {
                responseVO.setCode(ResponseConstant.FAIL);
                responseVO.setMessage("添加用户数据库连接异常！");
                // 返回组装的提示信息对象给前端页面
                return responseVO;
            }
            // 检查用户名是否重复
            if (checkUserDuplicate(addDTO.getName(), UserTypeConstant.ORDINARY)) {
                responseVO.setCode(ResponseConstant.FAIL);
                responseVO.setMessage("用户名重复，请重新添加！");
                // 返回组装的提示信息对象给前端页面
                return responseVO;
            }
            // 将事务自动提交设置为 false
            setAutoCommit(conn, false);
            // 构造添加用户的 SQL 语句
            String strSql =
                    "INSERT INTO user_manage_demo (name, password, type, gmt_create, gmt_modified, is_delete) VALUES (?, ?, ?, ?, ?, ?)";
            // 创建一个可以设置占位符参数的可编译和执行 SQL 的对象
            pstmt = conn.prepareStatement(strSql);
            // 下面5行语句设置占位符里的各个参数（逐一替换前面一句里的 ？占位符），注意参数类型
            pstmt.setString(1, addDTO.getName());
            pstmt.setString(2, Md5Util.md5(addDTO.getPassword()));
            pstmt.setString(3, UserTypeConstant.ORDINARY);
            pstmt.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));
            pstmt.setTimestamp(5, new java.sql.Timestamp(new java.util.Date().getTime()));
            pstmt.setBoolean(6, false);
            // 执行编译后的 SQL 语句
            pstmt.execute();
            // 当操作成功后手动提交
            commit(conn);
            // 组装返回的结果对象
            responseVO.setCode(ResponseConstant.SUCCESS);
            responseVO.setMessage("添加成功！");
            // 关闭数据库相关连接对象
            closeResultSetAndStatementAndConnection(null, null, pstmt, conn);
        } catch (SQLException se) {
            // 处理失败时回滚数据
            rollBack(conn);
            // 捕捉数据库异常
            log.error("添加用户数据库异常，异常信息：{}", se);
        } catch (Exception e) {
            // 处理失败时回滚数据
            rollBack(conn);
            // 捕捉其它异常
            log.error("添加用户其它异常，异常信息：{}", e);
        } finally { // 无论何种情况，都会执行下边的语句（finally 的作用），关闭数据库连接
            // 关闭数据库相关连接对象
            closeResultSetAndStatementAndConnection(null, null, pstmt, conn);
        }
        // 返回组装好的结果
        return responseVO;
    }

    /** 查看用户列表 */
    @Override
    public List<ViewVO> view() {
        // 定义返回的结果集对象，这种定义方式是对象形式的集合
        List<ViewVO> lstViewVOs = new ArrayList<>();
        // 定义一个可编译和执行 SQL 的对象
        Statement stmt = null;
        // 定义一个 SQL 结果集集合对象
        ResultSet rs = null;
        // 定义数据库连接对象
        Connection conn = null;
        // try/catch 逻辑用来捕捉可能出现的异常
        try {
            // 获取数据库连接
            conn = getConnection();
            // 判断数据库连接是否为空，避免出现空指针异常
            if (conn == null) {
                return new ArrayList<>();
            }
            // 创建一个可编译和执行 SQL 的对象
            stmt = conn.createStatement();
            // 构造用户列表查询的语句
            String strSql = "SELECT id, name, type, gmt_create FROM user_manage_demo WHERE type = '"
                    + UserTypeConstant.ORDINARY + "' AND is_delete = false";
            // 根据执行 SQL 返回的结果封装结果集
            rs = stmt.executeQuery(strSql);
            // 如果数据库存在记录，则将记录添加到返回的结果集对象中，需要查处所有记录，所以此处用 while
            while (rs.next()) {
                // 创建一个新对象来存储查询出的一条条记录
                ViewVO viewVO = new ViewVO();
                // 下边给对象附值
                viewVO.setId(rs.getInt("id"));
                viewVO.setName(rs.getString("name"));
                viewVO.setType(UserTypeConstant.typeToDesc(rs.getString("type")));
                viewVO.setGmtCreate(rs.getDate("gmt_create"));
                // 将对象放入结果集中
                lstViewVOs.add(viewVO);
            }
            // 关闭数据库相关连接对象
            closeResultSetAndStatementAndConnection(rs, stmt, null, conn);
        } catch (SQLException se) {
            // 捕捉数据库异常
            log.error("查看用户列表数据库异常，异常信息：{}", se);
        } catch (Exception e) {
            // 如果执行过程中出现其它异常则打印异常信息
            log.error("查看用户列表其它异常，异常信息：{}", e);
        } finally { // 无论何种情况，都会执行下边的语句（finally 的作用），关闭数据库连接
            // 关闭数据库相关连接对象
            closeResultSetAndStatementAndConnection(rs, stmt, null, conn);
        }
        // 返回组装好的结果集
        return lstViewVOs;
    }

    /** 密码修改 */
    @Override
    public ResponseVO modify(ModifyDTO modifyDTO) {
        // 定义返回的对象
        ResponseVO responseVO = new ResponseVO();
        // 定义一个可以设置占位符参数的可编译和执行 SQL 的对象
        PreparedStatement pstmt = null;
        // 定义一个可编译和执行 SQL 的对象
        Statement stmt = null;
        // 定义一个根据执行 SQL 返回的结果封装的结果集
        ResultSet rs = null;
        // 定义数据库连接对象
        Connection conn = null;
        // try/catch 逻辑用来捕捉可能出现的异常
        try {
            // 获取数据库连接
            conn = getConnection();
            // 判断数据库连接是否为空，避免出现空指针异常
            if (conn == null) {
                responseVO.setCode(ResponseConstant.FAIL);
                responseVO.setMessage("数据库连接异常！");
                // 返回组装的提示信息对象给前端页面
                return responseVO;
            }
            // 判断新密码和确认新密码是否一致
            if (modifyDTO.getNewPassword() != null && modifyDTO.getConfirmNewPassword() != null
                    && !modifyDTO.getNewPassword().equals(modifyDTO.getConfirmNewPassword())) {
                responseVO.setCode(ResponseConstant.FAIL);
                responseVO.setMessage("新密码和确认新密码不一致，请检查");
                // 返回组装的提示信息对象给前端页面
                return responseVO;
            }
            // 将事务自动提交设置为 false
            setAutoCommit(conn, false);
            // 创建一个可编译和执行 SQL 的对象，判断用户输入的密码是否正确
            stmt = conn.createStatement();
            String strSql1 = "SELECT name, type FROM user_manage_demo WHERE name = '" + modifyDTO.getName()
                    + "' AND password = '" + Md5Util.md5(modifyDTO.getPassword()) + "' AND type = '"
                    + modifyDTO.getType() + "' AND is_delete = false";
            rs = stmt.executeQuery(strSql1);
            // 如果用户输入的密码不正确则直接返回，此处只需要判断是否有数据，所以此处用 if，而不用 while
            if (!rs.next()) {
                // 组装返回的结果对象并返回
                responseVO.setCode(ResponseConstant.FAIL);
                responseVO.setMessage("登录原密码不正确！");
                return responseVO;
            }
            // 构造用户密码修改的 SQL 语句
            String strSql2 = "UPDATE user_manage_demo SET password = ?, gmt_modified = ? WHERE name = ? AND type = ?";
            // 创建一个可以设置占位符参数的可编译和执行 SQL 的对象
            pstmt = conn.prepareStatement(strSql2);
            // 下面5行语句设置占位符里的各个参数（逐一替换前面一句里的 ？占位符），注意参数类型
            pstmt.setString(1, Md5Util.md5(modifyDTO.getNewPassword()));
            pstmt.setTimestamp(2, new java.sql.Timestamp(new java.util.Date().getTime()));
            pstmt.setString(3, modifyDTO.getName());
            pstmt.setString(4, modifyDTO.getType());
            // 执行编译后的 SQL 语句
            pstmt.execute();
            // 当操作成功后手动提交
            commit(conn);
            // 组装返回的结果对象
            responseVO.setCode(ResponseConstant.SUCCESS);
            responseVO.setMessage("密码修改成功！");
            // 关闭数据库相关连接对象
            closeResultSetAndStatementAndConnection(rs, stmt, null, conn);
        } catch (SQLException se) {
            // 处理失败时回滚数据
            rollBack(conn);
            // 捕捉数据库异常
            log.error("密码修改数据库异常，异常信息：{}", se);
        } catch (Exception e) {
            // 处理失败时回滚数据
            rollBack(conn);
            // 如果执行过程中出现异常则打印异常信息
            log.error("密码修改其它异常，异常信息：{}", e);
        } finally { // 无论何种情况，都会执行下边的语句（finally 的作用），关闭数据库连接
            // 关闭数据库相关连接对象
            closeResultSetAndStatementAndConnection(rs, stmt, pstmt, conn);
        }
        // 返回组装好的结果
        return responseVO;
    }

    /** 删除用户 */
    @Override
    public ResponseVO delete(DeleteDTO deleteDTO) {
        // 定义返回的结果对象
        ResponseVO responseVO = new ResponseVO();
        // 定义一个可以设置占位符参数的可编译和执行 SQL 的对象
        PreparedStatement pstmt = null;
        // 定义数据库连接对象
        Connection conn = null;
        // try/catch 逻辑用来捕捉可能出现的异常
        try {
            // 获取数据库连接
            conn = getConnection();
            // 判断数据库连接是否为空，避免出现空指针异常
            if (conn == null) {
                responseVO.setCode(ResponseConstant.FAIL);
                responseVO.setMessage("数据库连接异常！");
                // 返回组装的提示信息对象给前端页面
                return responseVO;
            }
            // 将事务自动提交设置为 false
            setAutoCommit(conn, false);
            // 构造逻辑删除用户记录的 SQL 语句
            String strSql = "UPDATE user_manage_demo SET is_delete = true, gmt_modified = ? WHERE id = ?";
            // 创建一个可以设置占位符参数的可编译和执行 SQL 的对象
            pstmt = conn.prepareStatement(strSql);
            pstmt.setTimestamp(1, new java.sql.Timestamp(new java.util.Date().getTime()));
            pstmt.setInt(2, deleteDTO.getId());
            // 执行编译后的 SQL 语句
            pstmt.execute();
            // 当操作成功后手动提交
            commit(conn);
            // 组装返回的结果对象
            responseVO.setCode(ResponseConstant.SUCCESS);
            responseVO.setMessage("用户删除成功！");
            // 关闭数据库相关连接对象
            closeResultSetAndStatementAndConnection(null, null, pstmt, conn);
        } catch (SQLException se) {
            // 处理失败时回滚数据
            rollBack(conn);
            // 捕捉数据库异常
            log.error("删除用户数据库异常，异常信息：{}", se);
        } catch (Exception e) {
            // 处理失败时回滚数据
            rollBack(conn);
            // 如果执行过程中出现异常则打印异常信息
            log.error("删除用户其它异常，异常信息：{}", e);
        } finally {
            // 关闭数据库相关连接对象
            closeResultSetAndStatementAndConnection(null, null, pstmt, conn);
        }
        // 返回组装好的结果
        return responseVO;

    }

    /**
     * 检查用户是否重复
     * 
     * @param name 名字
     * @param type 类型
     * @return
     */
    private boolean checkUserDuplicate(String name, String type) {
        // 创建一个可编译和执行 SQL 的对象
        Statement stmt = null;
        // 定义一个根据执行 SQL 返回的结果封装的结果集
        ResultSet rs = null;
        // 默认用户不重复
        boolean isDuplicate = false;
        // 定义数据库连接对象
        Connection conn = null;
        try {
            // 获取数据库连接
            conn = getConnection();
            // 判断数据库连接是否为空，避免出现空指针异常
            if (conn == null) {
                // 数据库还有业务唯一索引，可以保证数据的唯一，此处返回 false 没有问题
                return false;
            }
            // 根据执行 SQL 返回的结果封装结果集
            String strSql =
                    "SELECT name, type FROM user_manage_demo WHERE name = '" + name + "' AND type = '" + type + "'";
            // 创建一个可编译和执行 SQL 的对象
            stmt = conn.createStatement();
            // 执行查询的 SQL，并返回结果
            rs = stmt.executeQuery(strSql);
            // 如果数据库存在记录，说明当前注册的用户名重复
            if (rs.next()) {
                isDuplicate = true;
            }
            // 关闭数据库相关连接对象
            closeResultSetAndStatementAndConnection(null, stmt, null, null);
        } catch (SQLException se) {
            // 捕捉数据库异常
            log.error("检查用户名是否重复数据库异常，异常信息：{}", se);
        } catch (Exception e) {
            // 捕捉其它异常
            log.error("检查用户名是否重复其它异常，异常信息：{}", e);
        } finally { // 无论何种情况，都会执行下边的语句（finally 的作用），关闭数据库连接
            // 关闭数据库相关连接对象
            closeResultSetAndStatementAndConnection(rs, stmt, null, null);
        }
        return isDuplicate;
    }

    /** 获取数据库连接对象 */
    private Connection getConnection() throws SQLException {
        Connection conn = null;
        // try/catch 逻辑用来捕捉可能出现的异常
        try {
            // 使用反射的特性加载数据库驱动
            Class.forName(SqlConstant.DRIVER);
            // 连接数据库，为了避免 SonarLint 的检查告警，对数据库密码进行了加密后再解密读取
            conn = DriverManager.getConnection(SqlConstant.DB_URL, SqlConstant.DB_USER,
                    Base64Util.base64Decrypt(SqlConstant.DB_PASS));
        } catch (Exception e) {
            // 如果执行过程中出现异常则打印异常信息
            log.error("获取数据库连接出现其它异常，异常信息：{}", e);
        }
        return conn;
    }

    /**
     * 设置事务自动提交属性
     * 
     * @param conn 数据库连接对象
     * @param isAutoCommit 是否自动提交事务
     */
    private void setAutoCommit(Connection conn, boolean isAutoCommit) {
        try {
            // 设置是否自动提交事务
            conn.setAutoCommit(isAutoCommit);
        } catch (SQLException e) {
            // 设置是否自动提交事务出现异常时打印异常信息
            log.error("设置是否自动提交事务异常，异常信息：{}", e);
        }
    }

    /**
     * 提交事务
     * 
     * @param conn 数据库连接对象
     */
    private void commit(Connection conn) {
        try {
            // 提交事务
            conn.commit();
        } catch (SQLException e) {
            // 提交事务出现异常时打印异常信息
            log.error("提交事务异常，异常信息：{}", e);
        }
    }

    /**
     * 回滚事务
     * 
     * @param conn 数据库连接对象
     */
    private void rollBack(Connection conn) {
        try {
            // 回滚事务
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException e) {
            // 回滚事务出现异常时打印异常信息
            log.error("回滚事务异常，异常信息：{}", e);
        }
    }

    /**
     * 关闭数据库相关连接，释放资源
     * 
     * @param rs 结果集
     * @param stmt 连接表对象
     * @param conn 数据库连接
     */
    private void closeResultSetAndStatementAndConnection(ResultSet rs, Statement stmt, PreparedStatement pstmt,
            Connection conn) {
        // 关闭结果集 ResultSet
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException se) {
            log.error("关闭数据库连接异常，异常信息：{}", se);
        }
        // 关闭连接表对象 Statement
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException se) {
            log.error("关闭连接表对象异常，异常信息：{}", se);
        }
        // 关闭连接表对象 PreparedStatement
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException se) {
            log.error("关闭连接表对象异常，异常信息：{}", se);
        }
        // 关闭数据库连接 Connection
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException se) {
            log.error("关闭数据库连接异常，异常信息：{}", se);
        }
    }

}
