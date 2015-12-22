package com.javarush.test.level35.lesson04.home01;

public class GenericStatic {
    public static <Number> Number someStaticMethod(Number genericObject) {
        System.out.println(genericObject);
        return genericObject;
    }
}
