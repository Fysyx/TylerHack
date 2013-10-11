package com.csgenerator.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImgControlBoard extends JPanel{

    private int imgWidth;
    private int imgHeight;
    private ControlBoardBtn[] cntrlBtns;

    public ImgControlBoard(int imgWidth, int imgHeight, BinaryImgCanvas cnv) {
        super();
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.setLayout(null);

        cntrlBtns = new ControlBoardBtn[imgWidth * imgHeight];

        for (int i = 0; i < cntrlBtns.length; i++) {
            int curX = i % imgWidth;
            int curY = i / imgWidth;


            cntrlBtns[i] = new ControlBoardBtn(curX, curY, cnv, this);
            cntrlBtns[i].setPixelEnabled(false);
            cntrlBtns[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    ((ControlBoardBtn)e.getSource()).setPixelEnabled(!((ControlBoardBtn)e.getSource()).pixelEnabled);
                    ((ControlBoardBtn)e.getSource()).canv.setImgData(((ControlBoardBtn)e.getSource()).icb.getImgData());
                }
            });
            this.add(cntrlBtns[i]);
        }
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);

        int pixelWidth = getWidth() / imgWidth;
        int pixelHeight = getHeight() / imgHeight;

        for (int i = 0; i < cntrlBtns.length; i++) {
            int curX = i % imgWidth;
            int curY = i / imgWidth;

            cntrlBtns[i].setLocation(curX * pixelWidth, curY * pixelHeight);
            cntrlBtns[i].setSize(pixelWidth, pixelHeight);
        }
    }

    public boolean[] getImgData() {
        boolean[] result = new boolean[cntrlBtns.length];

        for (int i = 0; i < cntrlBtns.length; i++) {
            result[i] = cntrlBtns[i].pixelEnabled;
        }

        return result;
    }

    public void setImgData(boolean[] imgData) {
        for (int i = 0; i < cntrlBtns.length; i++) {
            cntrlBtns[i].setPixelEnabled(imgData[i]);
        }
    }

}
