package org.example.util;

import java.awt.*;

public class GBC extends GridBagConstraints {
    public GBC(int gridx, int gridy) {
        this.insets = new Insets(5, 5, 5, 5);
        this.gridx = gridx;
        this.gridy = gridy;
    }

    public GBC() {
        this(0, 0);
    }

    public void moveUp() {
        this.gridy--;
    }

    public void moveDown() {
        this.gridy++;
    }

    public void moveLeft() {
        this.gridx--;
    }

    public void moveRight() {
        this.gridx++;
    }

    public void moveTo(int gridx, int gridy) {
        this.gridx = gridx;
        this.gridy = gridy;
    }

    public void reset() {
        gridx = 0;
        gridy = 0;
        gridwidth = 1;
        gridheight = 1;

        weightx = 0;
        weighty = 0;
        anchor = CENTER;
        fill = NONE;

        insets = new Insets(0, 0, 0, 0);
        ipadx = 0;
        ipady = 0;
    }
}