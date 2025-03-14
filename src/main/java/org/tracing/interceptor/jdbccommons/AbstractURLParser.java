package org.tracing.interceptor.jdbccommons;

public abstract class AbstractURLParser implements ConnectionURLParser{
    protected String url;
    public AbstractURLParser(String url) {
        this.url = url;
    }
}
