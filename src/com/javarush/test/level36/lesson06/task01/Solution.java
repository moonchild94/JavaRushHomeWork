package com.javarush.test.level36.lesson06.task01;

import java.util.Collections;

/* Найти класс по описанию
1. Реализует интерфейс List
2. Является приватным статическим классом внутри популярного утилитного класса
3. Доступ по индексу запрещен - кидается исключение IndexOutOfBoundsException
4. Используйте рефлекшн, чтобы добраться до искомого класса
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(getExpectedClass());
    }

    public static Class getExpectedClass() {
        Class clazz = Collections.class;
        Class[] innerClazzes = clazz.getDeclaredClasses();
        for (Class innerClazz : innerClazzes)
        {
            if ("EmptyList".equals(innerClazz.getSimpleName()))
                return innerClazz;
        }

        return null;
    }
}
