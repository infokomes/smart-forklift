package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

import sample.astar.*;
import sample.MachineLearning.*;

import static sample.Controller.prepareActionHandlers;
import static sample.astar.oilArray.*;
import static sample.gui.graphic.*;
import static sample.gui.map.*;
import static sample.gui.cases.*;
import static sample.gui.forklift.*;


public class Main extends Application {

    static int[] tempArray = new int[20];
    // Mouse events
    static double mouseX = 0.0;
    static double mouseY = 0.0;

    //Map
    static Map<Integer, AstarPoints> multiplePoints = new HashMap<Integer, AstarPoints>();

    //KnowledgeBase
    static KnowledgeBase knowledgeBase;

    //Strategies
    static Astar astar;
    static LearningStrategy learningStrategy;
    static int fieldNumber[] = new int[100];

    // Random for algorithm cases
    static Random randPoints = new Random();

    // Boolean for pathfinding (true if came back, false if still walking)
    static boolean didComeBack = false;

    public int iterator = 0;

    // Get cases that algorithm returns [x,y] and change them to map cases for example [ 15,15 ] -> 255
    private static void getFieldNumber() {
        int it = 0;
        for (int i = astar.pathXY.size() - 1; i >= 0; i--) {
            for (int j = 0; j < algorithmAvailablePoints.size(); j++) {
                if ((algorithmAvailablePoints.get(j).getX()) == (astar.pathXY.get(i).getX()) &&
                        (algorithmAvailablePoints.get(j).getY()) == (astar.pathXY.get(i).getY())) {
                    fieldNumber[it] = j;
                    it++;
                }
            }
        }
    }

// Oil Cordinate Converts
    private static void convertOilNumberToCoordinates() {
        for(int i = 0; i < oilsToDraw.length; i++) {
//            System.out.print("X: " + multiplePoints.get(oilsToDraw[i]).getX() + " Y: " + multiplePoints.get(oilsToDraw[i]).getY() + "\n");
            oilsCoordinates[i][0] = multiplePoints.get(oilsToDraw[i]).getX();
            oilsCoordinates[i][1] = multiplePoints.get(oilsToDraw[i]).getY();
        }
    }




    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) throws Exception {
        learningStrategy = new MultipleRegression();

       /* ********************************* START ALGORITHM!!!!!!!!!!! *************************************************
        a - array size (our map has 16x16)
        sy - start point y
        sx - start point x
        dy - destination point y
        dx - destination point x
        z - array with blocked cases

                      a   a  sy  sx dy dx    z                       */
        // astar.test(16, 16, 0, 0, 10, 8, astarBlockedPoints);
//        int randX = randPoints.nextInt(15);
//        int randY = randPoints.nextInt(15);
//
//        while (astar.foundPath == false) {
//            randX = randPoints.nextInt(15);
//            randY = randPoints.nextInt(15);
//            astar.test(16, 16, 0, 0, randY, randX, astarBlockedPoints);
//
//        }
//        for(int i = 0; i < 10; i++) {
//
//            int randOil = oilRandom.nextInt(pointsForOil.length);
////            while(!contains(oilArray, randOil)) {
////
////                randOil = oilRandom.nextInt(pointsForOil.length) - 1;
////            }
//            oilArray[i] = randOil;
//            astarBlockedPoints[80 + i] = pointsForOil[randOil];
//
//        }
        // scenario for oil
        oilArray[0] = 0;
        astarBlockedPoints[81] = pointsForOil[0];
        oilArray[1] = 15;
        astarBlockedPoints[82] = pointsForOil[15];
        oilArray[2] = 30;
        astarBlockedPoints[83] = pointsForOil[30];
        oilArray[3] = 32;
        astarBlockedPoints[84] = pointsForOil[32];
        oilArray[4] = 34;
        astarBlockedPoints[85] = pointsForOil[34];
        oilArray[5] = 36;
        astarBlockedPoints[86] = pointsForOil[36];
        oilArray[6] = 37;
        astarBlockedPoints[87] = pointsForOil[37];
        oilArray[7] = 17;
        astarBlockedPoints[88] = pointsForOil[17];
        astarBlockedPoints[89] = pointsForOil[21];
        oilArray[9] = 7;
        astarBlockedPoints[80] = pointsForOil[7];

//        for(int i=0;i<10;i++) {
//            System.out.print(oilArray[i] + "\n");
//        }
//        System.out.print(astarBlockedPoints[71][0] + " " + astarBlockedPoints[71][1]);


        int randCasePoint = randPoints.nextInt(80);
        //random
//        astar.test(16, 16, 0, 0, casesCoordinates[randCasePoint][0], casesCoordinates[randCasePoint][1], astarBlockedPoints);

        //scenario 1
        //  astar.test(16,16,0,0,15,15,astarBlockedPoints);

        //scenrio 2
//        astar.test(16,16,0,0,12,8,astarBlockedPoints);

        //scenario 3
//        astar.test(16,16,0,0,4,15,astarBlockedPoints);

        //scenario 4 not possible
        //  astar.test(16,16,0,0,3,2,astarBlockedPoints);


        prepareKnowledgeBase();

        int x = 0;
        int y = 0;
        for (int i = 0; i <= 255; i++) {
            algorithmAvailablePoints.put(i, new AstarPoints(x, y));
            x++;
            if (x > 15) {
                y++;
                x = 0;
            }
        }

        prepareMultiplePoints();

        getOilSlickNumber();
        convertOilNumberToCoordinates();

        Map<String, List<String>> knowledgeBase = Main.knowledgeBase.getKnowledgeBase();
        //System.out.println(knowledgeBase.toString());

        // Declare random case spawn-cases

//        for (int n = 0; n < 73; n += 8) casePoints[n][0] = 156.0;
//        for (int n = 1; n < 74; n += 8) casePoints[n][0] = 210.0;
//        for (int n = 2; n < 75; n += 8) casePoints[n][0] = 313.5;
//        for (int n = 3; n < 76; n += 8) casePoints[n][0] = 367.5;
//        for (int n = 4; n < 77; n += 8) casePoints[n][0] = 472.0;
//        for (int n = 5; n < 78; n += 8) casePoints[n][0] = 525.5;
//        for (int n = 6; n < 79; n += 8) casePoints[n][0] = 629.0;
//        for (int n = 7; n < 80; n += 8) casePoints[n][0] = 682.0;
        // Y

        IntStream.range(0, 80).forEach(
                n -> {
                    if (n < 8) {
                        casePoints[n][1] = 156.0;
                    } else if (n > 7 && n < 16) {
                        casePoints[n][1] = 195.0;
                    } else if (n > 15 && n < 24) {
                        casePoints[n][1] = 247.0;
                    } else if (n > 23 && n < 32) {
                        casePoints[n][1] = 282.0;
                    } else if (n > 31 && n < 40) {
                        casePoints[n][1] = 340.0;
                    } else if (n > 39 && n < 48) {
                        casePoints[n][1] = 372.0;
                    } else if (n > 47 && n < 56) {
                        casePoints[n][1] = 429.0;
                    } else if (n > 55 && n < 64) {
                        casePoints[n][1] = 466.0;
                    } else if (n > 63 && n < 72) {
                        casePoints[n][1] = 519.0;
                    } else if (n > 71 && n < 80) {
                        casePoints[n][1] = 554.0;
                    }
                }
        );


        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("sample.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            mainStage.setTitle("Inteligentny wozek widlowy");
            //   Group root = new Group();
            mainScene = new Scene(page);
            mainStage.setScene(mainScene);

            Canvas canvas = new Canvas(WIDTH, HEIGHT);

            page.getChildren().add(canvas);

            prepareActionHandlers();



            graphicsContext = canvas.getGraphicsContext2D();
            loadGraphics();

            getFieldNumber();
//            astar.test(16,16,0,0,15,15,astarBlockedPoints);

            /**
             * Main "game" loop
             */

            setCase();

            new AnimationTimer() {
                public void handle(long currentNanoTime) {

                    tickAndRender();
                    setStatement();
                    conveyorAnimated();

                }
            }.start();

            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Get random spawn cases and random cases.
        IntStream.range(0, 20).forEach(
                n -> {
                    int i = 0;
                    int k = caseSpawn.nextInt(79) + 1;
                    boolean temp = true;

                    while (temp == true) {
                        if (!contains(tempArray, k)) {
                            tempArray[n] = k;
                            i = k;
                            temp = false;
                        } else {
                            k = caseSpawn.nextInt(79) + 1;

                        }
                    }
                    int j = caseNumber.nextInt(8) + 1;

                    switch (j) {
                        case 1:
                            casesToSpawn[n] = caseOne;
                            break;
                        case 2:
                            casesToSpawn[n] = caseTwo;
                            break;
                        case 3:
                            casesToSpawn[n] = caseThree;
                            break;
                        case 4:
                            casesToSpawn[n] = caseFour;
                            break;
                        case 5:
                            casesToSpawn[n] = caseFive;
                            break;
                        case 6:
                            casesToSpawn[n] = caseSix;
                            break;
                        case 7:
                            casesToSpawn[n] = caseSeven;
                            break;
                        case 8:
                            casesToSpawn[n] = caseEight;
                            break;
                        default:
                            casesToSpawn[n] = caseOne;
                            break;
                    }
                    locOfCases[n] = i;

                }
        );

        for (int i = 0; i < algorithmAvailablePoints.size(); i++) {
            System.out.print("X:" + algorithmAvailablePoints.get(i).getX() + " Y:" + algorithmAvailablePoints.get(i).getY() + "\n");
        }


    }

    private void prepareMultiplePoints() {
        AstarPoints.prepareMultiplePoints(multiplePoints);
    }

    private void prepareKnowledgeBase() {
        knowledgeBase = new KnowledgeBase();
        knowledgeBase.addData("car parts", "gray");
        knowledgeBase.addData("car parts", "metal");
        knowledgeBase.addData("car parts", "heavy");
        knowledgeBase.addData("car parts", "middleweight");
        knowledgeBase.addData("car parts", "solid");
        knowledgeBase.addData("wood", "brown");
        knowledgeBase.addData("wood", "wooden");
        knowledgeBase.addData("wood", "heavy");
        knowledgeBase.addData("wood", "solid");
        knowledgeBase.addData("paper", "white");
        knowledgeBase.addData("paper", "paper");
        knowledgeBase.addData("paper", "light");
        knowledgeBase.addData("paper", "solid");
        knowledgeBase.addData("explosives", "red");
        knowledgeBase.addData("explosives", "labelled");
        knowledgeBase.addData("explosives", "middleweight");
        knowledgeBase.addData("explosives", "solid");
        knowledgeBase.addData("chemicals", "black");
        knowledgeBase.addData("chemicals", "labelled");
        knowledgeBase.addData("chemicals", "middleweight");
        knowledgeBase.addData("chemicals", "liquid");
        knowledgeBase.addData("water", "blue");
        knowledgeBase.addData("water", "metal");
        knowledgeBase.addData("water", "heavy");
        knowledgeBase.addData("water", "liquid");
        knowledgeBase.addData("oil", "yellow");
        knowledgeBase.addData("oil", "metal");
        knowledgeBase.addData("oil", "middleweight");
        knowledgeBase.addData("oil", "liquid");
        knowledgeBase.addData("glass", "blue");
        knowledgeBase.addData("glass", "transparent");
        knowledgeBase.addData("glass", "light");
        knowledgeBase.addData("glass", "solid");
    }

    private void setCase() {
        mainScene.addEventHandler(MouseEvent.MOUSE_RELEASED,
                mouseEvent -> mouseClicked());

    }

    private void mouseClicked() {
        mainPool.execute(() -> {
//            getRandomCase();
            int[] destinationXY = findPlace();
            astar.test(16,16,0,0,destinationXY[0],destinationXY[1],astarBlockedPoints);
            getFieldNumber();

            while (iterator < astar.pathXY.size() - 1) {
                handleGoingForPackage();
            }
            returnMode = true;
            unlockPack = true;

            while (iterator > 0 && returnMode) {
                handleReturning();
            }
            if (iterator == 0) {
                System.out.print("END\n");
                returnMode = false;
            }
        });
    }

    private void getRandomCase() {
        int random = new Random().nextInt(7);
//        if (random == 0)
//
    }
    private  int  findPlaces() {
        int places = 1;
        return  places;
    }

// Machine Learning
    private int[] findPlace() {
//        int[] result = new int[2];
//        result[0] = 15;
//        result[1] = 15;
//        return result;
//        String caseName = "glass";
        String caseName = "explosives";
//        String caseName = "oil";
//        String caseName = "wood";
        return learningStrategy.findDestinationPlace(knowledgeBase, caseName);
    }

    private void handleGoingForPackage() {
        iterator++;
        move();
    }

    private void move() {
        double xIterator = calculateXIterator();
        double yIterator = calculateYIterator();
        Runnable runnable = prepareRunableForMovingSlowly(xIterator, yIterator);
        pool.execute(runnable);

        waitUntilRunThreadFinishes(100 * movingTicks);
    }

    private void waitUntilRunThreadFinishes(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private double calculateXIterator() {
        return (multiplePoints.get(fieldNumber[iterator]).getX() - actualPositionW) / movingTicks;
    }

    private double calculateYIterator() {
        return (multiplePoints.get(fieldNumber[iterator]).getY() - actualPositionH) / movingTicks;
    }

    private Runnable prepareRunableForMovingSlowly(double xIterator, double yIterator) {
        return () -> {
            for (int i = 0; i < movingTicks; i++) {
                actualPositionW += xIterator;
                actualPositionH += yIterator;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void handleReturning() {
        if (iterator >= 0 && returnMode) {
            iterator--;
            move();
        }
    }

    public static boolean contains(int[] arr, int targetValue) {
        for (int s : arr) {
            if (s == targetValue) return true;
        }
        return false;
    }
}