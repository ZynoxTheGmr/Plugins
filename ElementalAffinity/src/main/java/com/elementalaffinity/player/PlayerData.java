package com.elementalaffinity.player;

import com.elementalaffinity.element.Element;

public class PlayerData {
    private Element element;
    private int level;
    private int xp;

    public PlayerData(Element element, int level, int xp) {
        this.element = element;
        this.level = level;
        this.xp = xp;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}
