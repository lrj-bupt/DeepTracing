package org.tracing.interceptor.jdbccommons;

public class ConnectionInfo {
    private final String dbType;
    private String databaseName;
    private String databasePeer;

    public String getDbType() {
        return dbType;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabasePeer() {
        return databasePeer;
    }

    public void setDatabasePeer(String databasePeer) {
        this.databasePeer = databasePeer;
    }

    public ConnectionInfo(String dbType, String hosts, String databaseName) {

        this.dbType = dbType;
        this.databaseName = databaseName;
        this.databasePeer = hosts;
    }
}
