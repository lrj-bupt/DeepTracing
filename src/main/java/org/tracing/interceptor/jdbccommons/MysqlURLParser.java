package org.tracing.interceptor.jdbccommons;

public class MysqlURLParser extends AbstractURLParser{
    public MysqlURLParser(String url) {
        super(url);
    }

    @Override
    public ConnectionInfo parse() {
        return null;
    }
}
