package sample.gui;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import sample.KnowledgeBase;
import sample.astar.AstarPoints;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static sample.astar.OilArray.oilArray;
import static sample.astar.OilArray.oilSlick;
import static sample.astar.OilArray.oilsCoordinates;
import static sample.gui.Forklift.*;
import static sample.gui.Forklift.forklift;
import static sample.gui.Graphic.background;

import static sample.gui.Cases.*;
import static sample.Main.*;
import static sample.gui.Graphic.*;


/**
 * Created by infokomes on 03.06.16.
 */
public class Gui {

    public  static ExecutorService mainPool = Executors.newFixedThreadPool(1);
    public  static ExecutorService pool = Executors.newFixedThreadPool(1);
    public  static int WIDTH = 1800;
    public  static int HEIGHT = 800;
    public  static Scene mainScene;

    public static GraphicsContext graphicsContext;
    public static HashSet<String> currentlyActiveKeys = new HashSet<String>();
    public static Map<Integer, AstarPoints> algorithmAvailablePoints = new HashMap<Integer, AstarPoints>();


    // True if right, false if left
    public static boolean leftOrRight = true;
    public static boolean unlockPack = false;
    public static boolean returnMode = false;

    public static double actualPositionH = 0;
    public static double actualPositionW = 110;
    public static double conveyorPos = 0.0;
    static int randj;
    static Random generator = new Random();

    public static void tickAndRender() {
        // clear canvas
        graphicsContext.clearRect(0, 0, WIDTH, HEIGHT);
        graphicsContext.drawImage(background, 0, 0);


        // ZAMIANA NA WSPÓŁRZĘDNE
        for(int i = 0; i < oilArray.length; i++) {
            graphicsContext.drawImage(oilSlick, oilsCoordinates[i][0] + 5, oilsCoordinates[i][1] + 25);
        }


        // Spawn Cases.
        IntStream.range(0, 20).forEach(
                n -> {
                    double distance = Math.sqrt(
                            (Math.pow((actualPositionH - casePoints[locOfCases[n]][1]), 2)) +
                                    (Math.pow((actualPositionW - casePoints[locOfCases[n]][0]),
                                            2)));

                    // if distance of Forklift and case is greater than 30 draw all random Cases
                    if (distance != -10000) {
                        graphicsContext.drawImage(casesToSpawn[n], casePoints[locOfCases[n]][0],
                                casePoints[locOfCases[n]][1]);

                        // if distance of Forklift and case < 30 change state that Forklift is busy
                    } else if (distance < 55 && unlockPack) {
                        caseNotToSpawn = true;
                        numberOfCase = n;

                    }
//                    if (casesToSpawn[numberOfCase] != null && unlockPack) {
//                        actualCase = null;
//                    }
                    // if Forklift is busy draw case on the Forklift
                    if (caseNotToSpawn == true && unlockPack) {
                        graphicsContext.drawImage(actualCase, actualPositionW + 10, actualPositionH);
                    }
                }
        );

//        // delete case from bookstand
//        casesToSpawn[numberOfCase] = null;

        // if the Forklift approaches the tape, we remove the pack
        if (actualPositionW <= 60) {
            caseNotToSpawn = false;
            actualCase = null;
        }

        //leftOrRight = false;

        graphicsContext.drawImage(forklift, actualPositionW, actualPositionH);

        if (currentlyActiveKeys.contains("R"))
        {
            randj = generator.nextInt(100) + 1;
            RandTypeCase = randj % knowledgeBase.getKnowledgeBase().size();
            for (int i = 0; i < 4; i++){
                ActualPropertiesName[i] = knowledgeBase.getKnowledgeBase().get(RandCaseName[RandTypeCase]).get(i);
            }
//            setStatement();

        }

   //To Tests
            // Arrow keys moving
            if (currentlyActiveKeys.contains("LEFT"))
            {
                actualPositionW = actualPositionW - 1.5;
                graphicsContext.drawImage(actualCase, actualPositionW, actualPositionH);
                leftOrRight = false;
            }
            else if (currentlyActiveKeys.contains("RIGHT"))
            {
                actualPositionW = actualPositionW + 1.5;
                graphicsContext.drawImage(actualCase, actualPositionW , actualPositionH);
                leftOrRight = true;
            }
            else if (currentlyActiveKeys.contains("DOWN")) {
                actualPositionH = actualPositionH + 1.5;
                if (leftOrRight == true) {
                    graphicsContext.drawImage(actualCase, actualPositionW, actualPositionH);
                } else {
                    graphicsContext.drawImage(actualCase, actualPositionW, actualPositionH);
                }
            }
            else if (currentlyActiveKeys.contains("UP")) {
                actualPositionH = actualPositionH - 1.5;
                if (leftOrRight == true) {
                    graphicsContext.drawImage(actualCase, actualPositionW, actualPositionH);
                } else {
                    graphicsContext.drawImage(actualCase, actualPositionW, actualPositionH);
                }
           }
           else {
                if (leftOrRight == true) {
                    graphicsContext.drawImage(actualCase, actualPositionW, actualPositionH);
                } else {
                    graphicsContext.drawImage(actualCase, actualPositionW, actualPositionH);
                }
            }
            //Set case
            if (currentlyActiveKeys.contains("F1"))
            {
                //skrzynka = ....
//                rodzaj polki = regresion();
                // actual case = skrzynka
                actualCase = caseOne ;
                graphicsContext.drawImage(actualCase, actualPositionW, actualPositionH);
            }
            if (currentlyActiveKeys.contains("F2"))
            {
                actualCase = caseTwo ;
                graphicsContext.drawImage(actualCase, actualPositionW, actualPositionH);
            }
            if (currentlyActiveKeys.contains("F3"))
            {
                actualCase = caseThree ;
                graphicsContext.drawImage(actualCase, actualPositionW, actualPositionH);
            }
            if (currentlyActiveKeys.contains("F4"))
            {
                actualCase = caseFour ;
                graphicsContext.drawImage(actualCase, actualPositionW, actualPositionH);
            }

            if (currentlyActiveKeys.contains("A"))
            {
                TypeRegal = 1;
            }
            if (currentlyActiveKeys.contains("S"))
            {
                TypeRegal = 2;
            }
            if (currentlyActiveKeys.contains("D"))
            {
                TypeRegal = 3;
            }
            if (currentlyActiveKeys.contains("F"))
            {
                TypeRegal = 4;
            }



    }
}
