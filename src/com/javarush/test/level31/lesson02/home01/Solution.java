package com.javarush.test.level31.lesson02.home01;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/* Проход по дереву файлов
1. На вход метода main подаются два параметра.
Первый - path - путь к директории, второй - resultFileAbsolutePath - имя файла, который будет содержать результат.
2. Для каждого файла в директории path и в ее всех вложенных поддиректориях выполнить следующее:
2.1. Если у файла длина в байтах больше 50, то удалить его.
2.2. Если у файла длина в байтах НЕ больше 50, то для всех таких файлов:
2.2.1. отсортировать их по имени файла в возрастающем порядке, путь не учитывать при сортировке
2.2.2. переименовать resultFileAbsolutePath в 'allFilesContent.txt'
2.2.3. в allFilesContent.txt последовательно записать содержимое всех файлов из п. 2.2.1. Тела файлов разделять "\n"
2.3. Удалить директории без файлов (пустые).
Все файлы имеют расширение txt.
*/
public class Solution
{
    private static TreeSet<Path> sortedPaths = new TreeSet<>(new Comparator<Path>()
    {
        @Override
        public int compare(Path path1, Path path2)
        {
            String name1 = path1.toFile().getName();
            String name2 = path2.toFile().getName();
            return name1.compareTo(name2);
        }
    });

    public static void main(String[] args) throws IOException
    {
        Path pathRoot = Paths.get(args[0]);
        Path resultFile = Paths.get(args[1]);
        String newName = resultFile.getParent() + File.separator + "allFilesContent.txt";

        Files.walkFileTree(pathRoot, new SolutionFileVisitor<Path>());

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile.toFile())))
        {
            for (Path path : sortedPaths)
            {
                try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile())))
                {
                    if (path.toFile().length() != 0)
                    {
                        while (reader.ready())
                        {
                            String data = reader.readLine();
                            writer.write(data);
                            if (!path.equals(sortedPaths.last()) || reader.ready())
                            {
                                writer.write("\n");
                            }
                        }
                    }
                }
            }
        }

        Files.move(resultFile, Paths.get(newName));
    }

    private static class SolutionFileVisitor<T> extends SimpleFileVisitor<T>
    {
        @Override
        public FileVisitResult visitFile(T file, BasicFileAttributes attrs) throws IOException
        {
            Path path = (Path) file;
            long size = (long) Files.getAttribute(path, "size");
            if (size > 50)
            {
                Files.delete(path);
            }
            else
            {
                Solution.sortedPaths.add(path);
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(T dir, BasicFileAttributes attrs) throws IOException
        {
            Path path = (Path) dir;
            if (Files.isDirectory(path) && path.toFile().listFiles().length == 0)
            {
                Files.delete(path);
            }
            return FileVisitResult.CONTINUE;
        }
    }
}
