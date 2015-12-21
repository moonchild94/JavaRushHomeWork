package com.javarush.test.level33.lesson15.big01.tests;

import com.javarush.test.level33.lesson15.big01.Shortener;
import com.javarush.test.level33.lesson15.big01.strategies.*;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Дарьюшка on 13.12.2015.
 */
public class FunctionalTest extends TestCase
{
    public void testStorage(Shortener shortener)
    {
        String s1 = new String("first");
        String s2 = new String("second");
        String s3 = new String("first");

        long id1 = shortener.getId(s1);
        long id2 = shortener.getId(s2);
        long id3 = shortener.getId(s3);

        Assert.assertNotEquals(id2, id1);
        Assert.assertNotEquals(id2, id3);
        Assert.assertEquals(id1, id3);

        String value1 = shortener.getString(id1);
        String value2 = shortener.getString(id2);
        String value3 = shortener.getString(id3);

        Assert.assertEquals(value1, s1);
        Assert.assertEquals(value2, s2);
        Assert.assertEquals(value3, s3);
    }

    @Test
    public void testHashMapStorageStrategy()
    {
        testStorage(new Shortener(new HashMapStorageStrategy()));
    }

    @Test
    public void testOurHashMapStorageStrategy()
    {
        testStorage(new Shortener(new OurHashMapStorageStrategy()));
    }

    @Test
    public void testFileStorageStrategy()
    {
        testStorage(new Shortener(new FileStorageStrategy()));
    }

    @Test
    public void testHashBiMapStorageStrategy()
    {
        testStorage(new Shortener(new HashBiMapStorageStrategy()));
    }

    @Test
    public void testDualHashBidiMapStorageStrategy()
    {
        testStorage(new Shortener(new DualHashBidiMapStorageStrategy()));
    }

    @Test
    public void testOurHashBiMapStorageStrategy()
    {
        testStorage(new Shortener(new OurHashBiMapStorageStrategy()));
    }
}