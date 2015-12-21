package com.javarush.test.level33.lesson05.home05;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonAutoDetect
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, property ="className")
@JsonSubTypes.Type(value=Car.class, name="car")
public class Car extends Auto {
}