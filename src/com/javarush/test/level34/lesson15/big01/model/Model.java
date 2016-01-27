package com.javarush.test.level34.lesson15.big01.model;

import com.javarush.test.level34.lesson15.big01.controller.EventListener;

import java.nio.file.Paths;
import java.util.Set;

/**
 * Created by Дарьюшка on 25.01.2016.
 */
public class Model
{
    public static final int FIELD_SELL_SIZE = 20;

    private EventListener eventListener;
    private int currentLevel = 1;
    private LevelLoader levelLoader = new LevelLoader(Paths.get(".\\res\\levels.txt"));
    private GameObjects gameObjects;

    public void setEventListener(EventListener eventListener)
    {
        this.eventListener = eventListener;
    }

    public GameObjects getGameObjects()
    {
        return gameObjects;
    }

    public void restartLevel(int level)
    {
        gameObjects = levelLoader.getLevel(level);
    }

    public void restart()
    {
        restartLevel(currentLevel);
    }

    public void startNextLevel()
    {
        restartLevel(++currentLevel);
    }

    public void move(Direction direction)
    {
        Player player = gameObjects.getPlayer();

        if (checkWallCollision(player, direction))
        {
            return;
        }

        if (checkBoxCollision(direction))
        {
            return;
        }

        switch (direction)
        {
            case LEFT:
                player.move(-FIELD_SELL_SIZE, 0);
                break;
            case RIGHT:
                player.move(FIELD_SELL_SIZE, 0);
                break;
            case UP:
                player.move(0, -FIELD_SELL_SIZE);
                break;
            case DOWN:
                player.move(0, FIELD_SELL_SIZE);
                break;
        }

        checkCompletion();
    }

    public boolean checkWallCollision(CollisionObject gameObject, Direction direction)
    {
        Set<Wall> walls = gameObjects.getWalls();
        for (Wall wall : walls)
        {
            if (gameObject.isCollision(wall, direction))
            {
                return true;
            }
        }
        return false;
    }

    public boolean checkBoxCollision(Direction direction)
    {
        Player player = gameObjects.getPlayer();
        Set<Box> boxes = gameObjects.getBoxes();
        boolean hasCollision = false;
        Box collisionBox = null;
        for (Box box : boxes)
        {
            if (player.isCollision(box, direction))
            {
                collisionBox = box;
                break;
            }
        }

        if (collisionBox != null)
        {
            for (Box box : boxes)
            {
                if (collisionBox.isCollision(box, direction))
                {
                    hasCollision = true;
                    break;
                }
            }

            hasCollision |= checkWallCollision(collisionBox, direction);

            if (!hasCollision)
            {
                switch (direction)
                {
                    case LEFT:
                        collisionBox.move(-FIELD_SELL_SIZE, 0);
                        break;
                    case RIGHT:
                        collisionBox.move(FIELD_SELL_SIZE, 0);
                        break;
                    case UP:
                        collisionBox.move(0, -FIELD_SELL_SIZE);
                        break;
                    case DOWN:
                        collisionBox.move(0, FIELD_SELL_SIZE);
                        break;
                }
            }
        }

        return hasCollision;
    }

    public void checkCompletion()
    {
        Set<Box> boxes = gameObjects.getBoxes();
        Set<Home> homes = gameObjects.getHomes();
        boolean wasCompleted = true;

        if (boxes.size() != homes.size())
        {
            return;
        }

        for (Box box : boxes)
        {
            boolean isCurrentBoxInHome = false;

            for (Home home : homes)
            {
                if (box.getX() == home.getX() && box.getY() == home.getY())
                {
                    isCurrentBoxInHome = true;
                    break;
                }
            }

            wasCompleted &= isCurrentBoxInHome;
        }

        if (wasCompleted)
        {
            eventListener.levelCompleted(currentLevel);
        }
    }
}
