package com.javarush.test.level34.lesson15.big01.model;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;

/**
 * Created by Дарьюшка on 26.01.2016.
 */
public class LevelLoader
{
    private Path levels;

    public LevelLoader(Path levels)
    {
        this.levels = levels;
    }

    public GameObjects getLevel(int level)
    {
        try(RandomAccessFile stream = new RandomAccessFile(levels.toFile().getPath(), "rw"))
        {
            stream.readUTF();
            stream.readUTF();
            String offset = stream.readUTF().substring(13, 17);
            int offsetInt = Integer.parseInt(offset, 16);

        }

        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
