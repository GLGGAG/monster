package com.orange.interceptor;

import com.alibaba.druid.sql.SQLUtils;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetInternalMethods;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.StatementInterceptor;
import com.orange.support.util.LogUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Properties;

/**
 * 打印 MariaDB 的执行语句(基于 mysql 驱动的拦截器)
 *
 * @author : GLGGAG
 * @since : 2017/10/19 16:24
 */
@Component
public class ShowSqlInterceptor implements StatementInterceptor {

    @Override
    public void init(Connection connection, Properties properties) throws SQLException {}

    @Override
    public ResultSetInternalMethods preProcess(String sql, Statement statement,
                                               Connection connection) throws SQLException {
        if ((sql == null || sql.trim().length() == 0) && statement != null) {
            sql = statement.toString();
            if (sql != null && sql.trim().length() > 0 && sql.indexOf(':') > 0) {
                sql = SQLUtils.formatMySql(sql.substring(sql.indexOf(':') + 1).trim());
            }
        }
        if (sql != null && sql.trim().length() > 0) {
            if (LogUtil.SQL_LOG.isDebugEnabled()) {
                LogUtil.SQL_LOG.debug("{}", sql);
            }
        }
        return null;
    }

    @Override
    public ResultSetInternalMethods postProcess(String s, Statement statement,
                                                ResultSetInternalMethods resultSetInternalMethods, Connection connection) throws SQLException {
        return null;
    }

    @Override
    public boolean executeTopLevelOnly() { return false; }

    @Override
    public void destroy() {}
}
