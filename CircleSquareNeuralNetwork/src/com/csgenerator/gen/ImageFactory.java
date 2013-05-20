package com.csgenerator.gen;

import java.awt.*;
import java.util.Random;

public class ImageFactory {
	
	private int width;
	private int height;
    private double staticChance = 0.01;
	
	public ImageFactory(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public boolean[] generateCircle() {
		boolean[][] canvas = new boolean[width][height];
		Random r = new Random();
		int radius = Math.max(r.nextInt(Math.min(width/2, height/2) - 1), 10);
		int xPos = r.nextInt(width - (radius * 2)) + radius;
		int yPos = r.nextInt(height - (radius * 2)) + radius;

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (Point.distance(x,y,xPos,yPos) < radius) {
                    canvas[x][y] = true;
                }
			}
		}
		
		return flattenArray(canvas);
	}

    public boolean[] applyStatic(boolean[] img) {
        Random r = new Random();

        for (int i = 0; i < img.length; i++) {
            if (r.nextDouble() < staticChance) {
                img[i] = !img[i];
            }
        }

        return img;
    }
	
	public boolean[] generateSquare() {
		boolean[][] canvas = new boolean[width][height];
        Random r = new Random();
        int sqWidth = Math.max(r.nextInt(Math.min(width/2, height/2) - 1), 10);
        int xPos = r.nextInt(width - (sqWidth * 2)) + sqWidth;
        int yPos = r.nextInt(height - (sqWidth * 2)) + sqWidth;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x >= xPos && x <= xPos + sqWidth &&
                        y >= yPos && y <= yPos + sqWidth) {
                    canvas[x][y] = true;
                }
            }
        }
		
		return flattenArray(canvas);
	}
	
	private boolean[] flattenArray(boolean[][] twoD) {
        //printTwoDArray(twoD);
		boolean[] flattenedArray = new boolean[width * height];
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y ++) {
				flattenedArray[y * width + x] = twoD[x][y];
			}
		}
		
		return flattenedArray;
	}

    private void printTwoDArray(boolean[][] twoD) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y ++) {
                if (twoD[x][y]) {
                    System.out.print("1");
                } else {
                    System.out.print("0");
                }
            }

            System.out.println();
        }
    }
}
