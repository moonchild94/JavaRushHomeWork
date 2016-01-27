package com.javarush.test.level37.lesson04.task01;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/* Круговой итератор
Класс Solution наследуется от ArrayList.
Напишите свой класс RoundIterator внутри Solution, который будет итератором для списка Solution.
Итератор должен ходить по кругу по всем элементам.
В остальном поведение должно быть идентичным текущему итератору.
*/
public class Solution<T> extends ArrayList<T> {
    public static void main(String[] args) {
        Solution<Integer> list = new Solution<>();
        list.add(1);
        list.add(2);
        list.add(3);

        int count = 0;
        for (Integer i : list) {
            //1 2 3 1 2 3 1 2 3 1
            System.out.print(i + " ");
            count++;
            if (count == 10) {
                break;
            }
        }
    }

    @Override
    public Iterator<T> iterator()
    {
        return new RoundIterator();
    }

    public class RoundIterator implements ListIterator
    {
        private ListIterator iterator = Solution.this.listIterator();

        @Override
        public boolean hasNext()
        {
            if (!iterator.hasNext())
            {
                iterator = Solution.this.listIterator();
            }
            return iterator.hasNext();
        }

        @Override
        public Object next()
        {
            if (!iterator.hasNext())
            {
                iterator = Solution.this.listIterator();
            }
           return iterator.next();
        }

        @Override
        public boolean hasPrevious()
        {
            return iterator.hasPrevious();
        }

        @Override
        public Object previous()
        {
            return iterator.previous();
        }

        @Override
        public int nextIndex()
        {
            if (!iterator.hasNext())
            {
                iterator = Solution.this.listIterator();
            }
            return iterator.nextIndex();
        }

        @Override
        public int previousIndex()
        {
            return iterator.previousIndex();
        }

        @Override
        public void remove()
        {
            iterator.remove();
        }

        @Override
        public void set(Object o)
        {
            iterator.set(o);
        }

        @Override
        public void add(Object o)
        {
            iterator.add(o);
        }
    }
}
