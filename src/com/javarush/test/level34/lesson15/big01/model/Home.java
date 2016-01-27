package com.javarush.test.level34.lesson15.big01.model;

import java.awt.*;

/**
 * Created by Дарьюшка on 25.01.2016.
 */
public class Home extends GameObject
{
    public Home(int x, int y)
    {
        super(x, y, 2 * Model.FIELD_SELL_SIZE, 2 * Model.FIELD_SELL_SIZE);
    }

    @Override
    public void draw(Graphics graphics)
    {
        graphics.setColor(new Color(0x66FF0000, true));

        int leftUpperCornerX = getX() - getWidth() / 2;
        int leftUpperCornerY = getY() - getHeight() / 2;

        graphics.fillOval(leftUpperCornerX, leftUpperCornerY, getWidth(), getHeight());
    }
}
