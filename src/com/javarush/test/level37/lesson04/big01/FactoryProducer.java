package com.javarush.test.level37.lesson04.big01;

import com.javarush.test.level37.lesson04.big01.female.FemaleFactory;
import com.javarush.test.level37.lesson04.big01.male.MaleFactory;

/**
 * Created by Дарьюшка on 21.01.2016.
 */
public class FactoryProducer
{
    public static AbstractFactory getFactory(HumanFactoryType humanFactoryType)
    {
        switch (humanFactoryType.ordinal())
        {
            case 0 : return new MaleFactory();
            case 1 : return new FemaleFactory();
        }

        return null;
    }

    public enum HumanFactoryType {MALE, FEMALE}
}
