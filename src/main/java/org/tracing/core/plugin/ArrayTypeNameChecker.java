package org.tracing.core.plugin;

public class ArrayTypeNameChecker {
    public static void check(String typeName){
        if(typeName.endsWith("[]")){
            throw new IllegalArgumentException("ArrayTypeNameChecker: typeName should not end with []");
        }
    }
}
