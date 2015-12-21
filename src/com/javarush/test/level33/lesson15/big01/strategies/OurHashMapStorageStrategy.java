package com.javarush.test.level33.lesson15.big01.strategies;

/**
 * Created by Дарьюшка on 12.12.2015.
 */
public class OurHashMapStorageStrategy implements StorageStrategy
{
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private Entry[] table = new Entry[DEFAULT_INITIAL_CAPACITY];
    private int size;
    private int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    private float loadFactor = DEFAULT_LOAD_FACTOR;

    private int hash(Long k)
    {
        k ^= (k >>> 20) ^ (k >>> 12);
        return (int) (k ^ (k >>> 7) ^ (k >>> 4));
    }

    private int indexFor(int hash, int length)
    {
        return hash & (length - 1);
    }

    private Entry getEntry(Long key)
    {
        int hash = hash((long)key.hashCode());
        for (Entry entry : table)
        {
            if (entry != null && hash == entry.hash)
                do
                {
                    if (key.equals(entry.getKey()))
                        return entry;
                } while ((entry = entry.next) != null);
        }

        return null;
    }

    private void resize(int newCapacity)
    {
        Entry[] newTable = new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int)(newCapacity * loadFactor);
    }

    private void transfer(Entry[] newTable)
    {
        for (int i = 0; i < table.length; i++)
            if (table[i] != null)
                newTable[indexFor(table[i].hash, newTable.length)] = table[i];
    }

    private void addEntry(int hash, Long key, String value, int bucketIndex)
    {
        Entry e = table[bucketIndex];
        table[bucketIndex] = new Entry(hash, key, value, e);
    }

    private void createEntry(int hash, Long key, String value, int bucketIndex)
    {
        table[bucketIndex] = new Entry(hash, key, value, null);
        size++;
        if (size >= threshold)
            resize(table.length * 2);
    }

    @Override
    public boolean containsKey(Long key)
    {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value)
    {
        for (Entry entry : table)
        if (entry != null)
            do
            {
                if (value.equals(entry.getValue()))
                    return true;
            } while ((entry = entry.next) != null);

        return false;
    }

    @Override
    public void put(Long key, String value)
    {
        if (key != null)
        {
            int hash = hash((long)key.hashCode());
            int index = indexFor(hash, table.length);
            Entry entry = table[index];

            if (entry != null)
            {
                do
                {
                    if (entry.hash == hash && (entry.key == key || key.equals(entry.key)))
                    {
                        entry.value = value;
                        return;
                    }
                }
                while ((entry = entry.next) != null);

                addEntry(hash, key, value, index);
            }
            else
                createEntry(hash, key, value, index);
        }
        else
        {
            Entry entry = table[0];

            if (entry != null)
            {
                do
                {
                    if (entry.hash == 0 && (entry.key == key || key.equals(entry.key)))
                    {
                        entry.value = value;
                        return;
                    }
                }
                while ((entry = entry.next) != null);
            }
            else
                createEntry(0, key, value, 0);
        }
    }

    @Override
    public Long getKey(String value)
    {
        for (Entry entry : table)
        if (entry != null)
            do
            {
                if (value.equals(entry.getValue()))
                    return entry.getKey();
            } while ((entry = entry.next) != null);

        return null;
    }

    @Override
    public String getValue(Long key)
    {
        return getEntry(key).getValue();
    }
}