package com.csgenerator.ui;

import com.csgenerator.gen.ImageFactory;
import com.googlecode.fannj.Fann;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class GeneratorRunner extends JFrame {

    private ImageFactory sf;
    private BinaryImgCanvas cnv;
    private ImgControlBoard icb;
    private int imgWidth = 50;
    private int imgHeight = 30;
    private JLabel outputLabel;

    public static void main(String[] args) {
        new GeneratorRunner();
    }

    public GeneratorRunner() {
        sf = new ImageFactory(imgWidth, imgHeight);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initializeGraphics();
            }
        });
        System.setProperty("jna.library.path", "D:/Temp/");

        System.out.println( System.getProperty("jna.library.path") ); //maybe the path is malformed
        File file = new File(System.getProperty("jna.library.path") + "fannfloat.dll");
        System.out.println("Is the dll file there:" + file.exists());
        System.load(file.getAbsolutePath());
    }

    private void initializeGraphics() {
        initializeFrameSettings();
        initializeCanvas();
        initializeControlBoard();
        initializeButtons();
        initializeLabels();
    }

    private void initializeFrameSettings() {
        this.setSize(675, 675);
        this.setResizable(false);
        this.setLayout(null);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setVisible(true);
    }

    private void initializeCanvas() {
        cnv = new BinaryImgCanvas(imgWidth, imgHeight);
        cnv.setImgData(sf.generateCircle());
        cnv.setLocation(15, 15);
        cnv.setSize(500, 300);
        this.add(cnv);
    }

    private void initializeControlBoard() {
        icb = new ImgControlBoard(imgWidth, imgHeight, cnv);
        icb.setImgData(cnv.getImgData());
        icb.setLocation(15, 330);
        icb.setSize(500, 300);
        this.add(icb);
    }

    private void initializeButtons() {
        initializeSampleManipulationButtons();
        initializeNeuralNetworkButtons();
    }

    private void initializeSampleManipulationButtons() {
        JButton genCircle = new JButton("Create Circle");
        genCircle.setSize(125, 25);
        genCircle.setLocation(530, 15);
        genCircle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean[] img = sf.generateCircle();
                cnv.setImgData(img);
                icb.setImgData(img);
            }
        });
        this.add(genCircle);

        JButton genSquare = new JButton("Create Square");
        genSquare.setSize(125, 25);
        genSquare.setLocation(530, 55);
        genSquare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean[] img = sf.generateSquare();
                cnv.setImgData(img);
                icb.setImgData(img);
            }
        });
        this.add(genSquare);

        JButton staticizeImgBtn = new JButton("Apply Static");
        staticizeImgBtn.setSize(125, 25);
        staticizeImgBtn.setLocation(530, 95);
        staticizeImgBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean[] img = sf.applyStatic(cnv.getImgData());
                cnv.setImgData(img);
                icb.setImgData(img);
            }
        });
        this.add(staticizeImgBtn);
    }

    private void initializeNeuralNetworkButtons() {
        JButton genTestDataBtn = new JButton("Gen Test Data");
        genTestDataBtn.setSize(125, 25);
        genTestDataBtn.setLocation(530, 605);
        genTestDataBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File outFile = new File("training.data");
                PrintWriter pw = null;

                try {
                    pw = new PrintWriter(outFile);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }

                Random r = new Random();
                for (int trial = 0; trial < 5000; trial++) {
                    boolean isCircle;
                    boolean[] pixData;
                    if (r.nextBoolean()) {
                        isCircle = true;
                        pixData = sf.generateCircle();
                    } else {
                        isCircle = false;
                        pixData = sf.generateSquare();
                    }

                    for (int i = 0; i < pixData.length; i++) {
                        if (pixData[i]) {
                            pw.print("1\t");
                        } else {
                            pw.print("0\t");
                        }
                    }

                    if (isCircle) {
                        pw.println("1");
                    } else {
                        pw.println("0");
                    }
                }

                pw.close();
            }
        });
        this.add(genTestDataBtn);

        JButton testImgBtn = new JButton("Test Image");
        testImgBtn.setSize(125, 25);
        testImgBtn.setLocation(530, 330);
        testImgBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean[] imgData = cnv.getImgData();
                float[] inputs = new float[imgData.length];

                for (int i = 0; i < imgData.length; i++) {
                    inputs[i] = imgData[i] ? 1.0f : 0.0f;
                }

                Fann fann = new Fann("oneHundredthRMSE");
                float[] outputs = fann.run(inputs);
                fann.close();

                outputLabel.setText("Result: " + outputs[0]);
            }
        });
        this.add(testImgBtn);
    }

    private void initializeLabels() {
        outputLabel = new JLabel("Result: ");
        outputLabel.setLocation(530, 370);
        outputLabel.setSize(125, 25);
        this.add(outputLabel);

        JLabel circleLabel = new JLabel("Circle  = 1.0");
        circleLabel.setLocation(530, 410);
        circleLabel.setSize(125, 25);
        this.add(circleLabel);

        JLabel squareLabel = new JLabel("Square = 0.0");
        squareLabel.setLocation(530, 430);
        squareLabel.setSize(125, 25);
        this.add(squareLabel);
    }
}
