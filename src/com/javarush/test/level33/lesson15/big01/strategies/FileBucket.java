package com.javarush.test.level33.lesson15.big01.strategies;

import com.javarush.test.level33.lesson15.big01.ExceptionHandler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Дарьюшка on 12.12.2015.
 */
public class FileBucket
{
    private Path path;

    public FileBucket()
    {
        try
        {
            this.path = Files.createTempFile("", "");
            path.toFile().deleteOnExit();
        }
        catch (IOException e)
        {
            ExceptionHandler.log(e);
        }
    }

    public long getFileSize()
    {
        try
        {
            return Files.size(path);
        }
        catch (IOException e)
        {
            ExceptionHandler.log(e);
        }

        return 0;
    }

    public void putEntry(Entry entry)
    {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path.toFile())))
        {
            outputStream.writeObject(entry);
        }
        catch (IOException e)
        {
            ExceptionHandler.log(e);
        }
    }

    public Entry getEntry()
    {
        if (getFileSize() == 0)
            return null;

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path.toFile())))
        {
            return (Entry) inputStream.readObject();
        }
        catch (Exception e)
        {
            ExceptionHandler.log(e);
        }

        return null;
    }

    public void remove()
    {
        try
        {
            Files.delete(path);
        }
        catch (IOException e)
        {
            ExceptionHandler.log(e);
        }
    }
}
