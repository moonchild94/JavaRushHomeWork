package com.javarush.test.level33.lesson15.big01;

import com.javarush.test.level33.lesson15.big01.strategies.StorageStrategy;

/**
 * Created by Дарьюшка on 11.12.2015.
 */
public class Shortener
{
    private Long lastId = 0L;
    private StorageStrategy storageStrategy;

    public Shortener(StorageStrategy storageStrategy)
    {
        this.storageStrategy = storageStrategy;
    }

    public synchronized Long getId(String value)
    {
        if (storageStrategy.containsValue(value))
            return storageStrategy.getKey(value);

        lastId++;
        storageStrategy.put(lastId, value);
        return lastId;
    }

    public synchronized String getString(Long id)
    {
        if (storageStrategy.containsKey(id))
            return storageStrategy.getValue(id);

        return null;
    }
}
