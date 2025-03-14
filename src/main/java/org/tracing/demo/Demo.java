package org.tracing.demo;

public class Demo {
    public Demo(String name){
//        System.out.println("构造方法");
    }
    public void sayHello(){

    }
    @Test
    public void sayHello(String name){
        System.out.println("hello " + name);
    }
}
