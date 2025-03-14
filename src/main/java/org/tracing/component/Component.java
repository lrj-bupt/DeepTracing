package org.tracing.component;

public class Component {
    private int id;
    private String name;

    public Component(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
