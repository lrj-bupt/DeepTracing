package org.tracing.component;

public class ComponentDefine {
    public static final Component HTTP_CLIENT = new Component(1, "http client");

    public static final Component RABBITMQ_CONSUMER = new Component(2, "rabbit consumer");

    public static final Component DUBBO = new Component(3, "dubbo");

    public static final Component RABBITMQ_PRODUCER = new Component(4, "rabbit producer");

    public static final Component MYSQL_JDBC_DRIVER = new Component(5, "mysql jdbc driver");

    public static final Component SPRING_WEB = new Component(6, "spring web");

    public static final Component VERTX = new Component(59, "Vert.x");

    public static final Component TOMCAT = new Component(60, "Tomcat");

}
