package com.javarush.test.level34.lesson15.big01.view;

import com.javarush.test.level34.lesson15.big01.controller.Controller;
import com.javarush.test.level34.lesson15.big01.controller.EventListener;
import com.javarush.test.level34.lesson15.big01.model.GameObjects;

import javax.swing.*;

public class View extends JFrame {
    private Controller controller;
    private Field field;

    public View(Controller controller) {
        this.controller = controller;
    }

    public void init() {
        field = new Field(this);
        add(field);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setTitle("Сокобан");
        setVisible(true);
    }

    public void update()
    {
        field.repaint();
    }

    public GameObjects getGameObjects()
    {
        return controller.getGameObjects();
    }

    public void setEventListener(EventListener eventListener)
    {
        this.field.setEventListener(eventListener);
    }

    public void completed(int level)
    {
        update();
        String title = "Level was completed";
        String message = "Success! Level was completed. Click the OK button to go to the next level";
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
        controller.startNextLevel();
    }

}
