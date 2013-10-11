package com.csgenerator.ui;


import java.awt.*;

public class BinaryImgCanvas extends Canvas {

    private boolean[] imgData;
    private int imgWidth;
    private int imgHeight;

    public BinaryImgCanvas(int imgWidth, int imgHeight) {
        super();
        this.imgHeight = imgHeight;
        this.imgWidth = imgWidth;
    }

    public void setImgData(boolean[] imgData) {
        this.imgData = imgData;
        this.repaint();
    }

    public boolean[] getImgData() {
        return imgData;
    }

    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());

        int pixelWidth = getWidth() / imgWidth;
        int pixelHeight = getHeight() / imgHeight;

        for (int curPix = 0; curPix < imgData.length; curPix++) {
            int curX = curPix % imgWidth;
            int curY = curPix / imgWidth;

            int curPixelX = curX * pixelWidth;
            int curPixelY = curY * pixelHeight;

            if (imgData[curPix]) {
                g.setColor(Color.black);
            } else {
                g.setColor(Color.white);
            }

            g.fillRect(curPixelX, curPixelY, pixelWidth, pixelHeight);
        }
    }
}
