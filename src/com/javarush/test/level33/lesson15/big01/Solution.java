package com.javarush.test.level33.lesson15.big01;

import com.javarush.test.level33.lesson15.big01.strategies.*;

import java.util.*;

/**
 * Created by Дарьюшка on 11.12.2015.
 */
public class Solution
{
    public static void main(String[] args)
    {
        testStrategy(new HashMapStorageStrategy(), 100);
        testStrategy(new OurHashMapStorageStrategy(), 100);
        testStrategy(new FileStorageStrategy(), 100);
        testStrategy(new OurHashBiMapStorageStrategy(), 100);
        testStrategy(new HashBiMapStorageStrategy(), 100);
        testStrategy(new DualHashBidiMapStorageStrategy(), 100);
    }

    public static Set<Long> getIds(Shortener shortener, Set<String> strings)
    {
        Set<Long> ids = new HashSet<>();
        for (String value : strings)
            ids.add(shortener.getId(value));

        return ids;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys)
    {
        Set<String> strings = new HashSet<>();
        for (Long key : keys)
            strings.add(shortener.getString(key));

        return strings;
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber)
    {
        Helper.printMessage(strategy.getClass().getSimpleName());

        Set<String> strings = new HashSet<>();
        for (int i = 0; i < elementsNumber; i++)
            strings.add(Helper.generateRandomString());

        Shortener shortener = new Shortener(strategy);

        Date start = new Date();
        Set<Long> ids = getIds(shortener, strings);
        Date end = new Date();
        Helper.printMessage(Long.toString(end.getTime() - start.getTime()));

        start = new Date();
        Set<String> values = getStrings(shortener, ids);
        end = new Date();
        Helper.printMessage(Long.toString(end.getTime() - start.getTime()));

        if (strings.equals(values))
            Helper.printMessage("Тест пройден.");
        else
            Helper.printMessage("Тест не пройден.");
    }
}
