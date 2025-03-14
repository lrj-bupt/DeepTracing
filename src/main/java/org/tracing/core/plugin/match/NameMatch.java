package org.tracing.core.plugin.match;

public class NameMatch implements ClassMatch {
    private String name;
    public NameMatch(String name)
    {
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public static NameMatch byName(String className) {
        return new NameMatch(className);
    }
}
