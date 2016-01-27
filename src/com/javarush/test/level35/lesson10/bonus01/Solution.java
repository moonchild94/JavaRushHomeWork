package com.javarush.test.level35.lesson10.bonus01;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/* ClassLoader - что это такое?
Реализуйте логику метода getAllAnimals.
Аргумент метода pathToAnimals - это абсолютный путь к директории, в которой хранятся скомпилированные классы.
Путь не обязательно содержит / в конце.
НЕ все классы наследуются от интерфейса Animal.
НЕ все классы имеют публичный конструктор без параметров.
Только для классов, которые наследуются от Animal и имеют публичный конструктор без параметров, - создать по одному объекту.
Добавить созданные объекты в результирующий сет и вернуть.
Метод main не участвует в тестировании.
*/
public class Solution {
    public static void main(String[] args) {
        Set<? extends Animal> allAnimals = getAllAnimals("D://test/");
        System.out.println(allAnimals);
    }

    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) {
        Set<Animal> result = new HashSet<>();
        File[] paths = new File(pathToAnimals).listFiles();
        for (File path : paths)
        {
            try
            {
                if (path.getName().endsWith(".class"))
                {
                    URL[] urls = null;
                    try
                    {
                        urls = new URL[]{new File(pathToAnimals).toURI().toURL()};
                    }
                    catch (MalformedURLException e)
                    {
                        e.printStackTrace();
                    }
                    Class<?> clazz = new URLClassLoader(urls).loadClass(path.getName().
                            substring(0, path.getAbsolutePath().length() - 6));
                    List<Class<?>> interfaces = Arrays.asList(clazz.getInterfaces());
                    if (interfaces.contains(Animal.class))
                    {
                        try
                        {
                            result.add((Animal) clazz.newInstance());
                        }
                        catch (InstantiationException e)
                        {

                        }
                        catch (IllegalAccessException e)
                        {

                        }
                    }
                }
            }
            catch(ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        return result;
    }
}
