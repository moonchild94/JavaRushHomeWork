package com.javarush.test.level31.lesson06.home01;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* Добавление файла в архив
В метод main приходит список аргументов.
Первый аргумент - полный путь к файлу fileName.
Второй аргумент - путь к zip-архиву.
Добавить файл (fileName) внутрь архива в директорию 'new'.
Если в архиве есть файл с таким именем, то заменить его.

Пример входных данных:
C:/result.mp3
C:/pathToTest/test.zip

Файлы внутри test.zip:
a.txt
b.txt

После запуска Solution.main архив test.zip должен иметь такое содержимое:
new/result.mp3
a.txt
b.txt

Подсказка: нужно сначала куда-то сохранить содержимое всех энтри,
а потом записать в архив все энтри вместе с добавленным файлом.
Пользоваться файловой системой нельзя.
*/
public class Solution
{
    public static void main(String[] args) throws IOException
    {
        Map<ZipEntry, byte[]> zipEntries = new HashMap<ZipEntry, byte[]>();
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(args[1]));
        ZipEntry tmpZip;
        String fileName = args[0].substring(args[0].lastIndexOf(File.separator) + 1);
        while ((tmpZip = zipInputStream.getNextEntry()) != null)
        {
            int zipLength = zipInputStream.available();
            byte[] arr = new byte[zipLength];
            zipInputStream.read(arr, 0, zipLength);
            zipEntries.put(tmpZip, arr);
        }
        zipInputStream.close();
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(args[1]));
        for (Map.Entry<ZipEntry, byte[]> pair : zipEntries.entrySet())
        {
            if (!(pair.getKey().getName().equals(fileName)))
            {
                zipOutputStream.putNextEntry(new ZipEntry(pair.getKey().getName()));
                zipOutputStream.write(pair.getValue());
            }
        }
        zipOutputStream.putNextEntry(new ZipEntry("new" + File.separator + Paths.get(args[0]).getFileName()));
        Files.copy(Paths.get(args[0]), zipOutputStream);
        zipOutputStream.close();
    }
}
