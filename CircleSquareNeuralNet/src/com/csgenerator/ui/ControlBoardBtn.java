package com.csgenerator.ui;

import javax.swing.*;
import java.awt.*;

public class ControlBoardBtn extends JButton {
    int xLoc;
    int yLoc;
    boolean pixelEnabled;
    BinaryImgCanvas canv;
    ImgControlBoard icb;

    public ControlBoardBtn(int x, int y, BinaryImgCanvas cnv, ImgControlBoard icb) {
        super();
        xLoc = x;
        yLoc = y;
        pixelEnabled = false;
        this.canv = cnv;
        this.icb = icb;
        this.setVisible(true);
    }

    public void setPixelEnabled(boolean isPixEnabled) {
        this.pixelEnabled = isPixEnabled;

        if (isPixEnabled) {
            this.setBackground(Color.black);
        } else {
            this.setBackground(Color.white);
        }
    }
}
