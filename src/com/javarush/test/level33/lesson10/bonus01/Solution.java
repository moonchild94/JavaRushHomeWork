package com.javarush.test.level33.lesson10.bonus01;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;

/* Комментарий внутри xml
Реализовать метод toXmlWithComment, который должен возвращать строку - xml представление объекта obj.
В строке перед каждым тэгом tagName должен быть вставлен комментарий comment.
Сериализация obj в xml может содержать CDATA с искомым тегом. Перед ним вставлять комментарий не нужно.

Пример вызова:  toXmlWithComment(firstSecondObject, "second", "it's a comment")
Пример результата:
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<first>
    <!--it's a comment-->
    <second>some string</second>
    <!--it's a comment-->
    <second>some string</second>
    <!--it's a comment-->
    <second><![CDATA[need CDATA because of < and >]]></second>
    <!--it's a comment-->
    <second/>
</first>
*/
public class Solution
 {
     public static void main(String[] args)
     {
         Foo foo = new Foo();
         foo.i1 = 1;
         foo.i2 = 2;
         foo.i3 = 3;
         System.out.println(toXmlWithComment(foo, "i2", "oooooooooooooooo"));
     }

    public static String toXmlWithComment(Object obj, String tagName, String comment)
    {
        try
        {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);

            String searchString = "<" + tagName + ">";
            String commentXml = "<!--" + comment + "-->\n";
            String cdataStart = "<![CDATA[";
            String cdataEnd = "]]>";
            return writer.toString().replaceAll(searchString, commentXml + searchString);
        }
        catch (JAXBException e)
        {
            e.printStackTrace();
        }

        return null;
    }

     @XmlType
     @XmlRootElement
     public static class Foo
     {
         @XmlElement
         public int i1;
         @XmlElement
         public int i2;
         @XmlElement
         public int i3;
     }
}
