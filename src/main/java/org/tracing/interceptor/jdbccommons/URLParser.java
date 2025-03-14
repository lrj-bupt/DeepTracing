package org.tracing.interceptor.jdbccommons;

import com.mysql.cj.jdbc.JdbcConnection;

public class URLParser {
    private static final String MYSQL_JDBC_URL_PREFIX = "jdbc:mysql";

    public static ConnectionInfo parser(String url) {
        ConnectionURLParser parser = null;
        String lowerCaseUrl = url.toLowerCase();
        if(lowerCaseUrl.startsWith(MYSQL_JDBC_URL_PREFIX)) {
            parser = new MysqlURLParser(url);
        }

        return parser.parse();
    }
}
