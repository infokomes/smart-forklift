package sample.gui;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import static sample.astar.OilArray.*;
import static sample.gui.Gui.*;
import static sample.gui.Cases.*;
import static sample.gui.Forklift.*;

/**
 * Created by infokomes on 03.06.16.
 */
public class Graphic {

    // Etc
    public static Image background;
    public static Image conveyor;
    public static Image cover;


    public static void loadGraphics() {
        forklift = new Image("img/forklift.png");
        forklift2 = new Image("img/forklift2.png");
        background = new Image("img/background_test.png");
        conveyor = new Image("img/conveyor.png");
        cover = new Image("img/cover.png");
        caseOne = new Image("img/case1.png");
        caseTwo = new Image("img/case2.png");
        caseThree = new Image("img/case3.png");
        caseFour = new Image("img/case4.png");
        caseFive = new Image("img/case5.png");
        caseSix = new Image("img/case6.png");
        caseSeven = new Image("img/case7.png");
        caseEight = new Image("img/case8.png");
        oilSlick = new Image("img/oil.png");
    }

    //Gui
    public static void setStatement() {
        Font theFont = Font.font("Helvetica", FontWeight.BOLD, 24);
        graphicsContext.setFont(theFont);
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setLineWidth(1);

        graphicsContext.fillText("X: " + (int)actualPositionW, 1200, 50);
        graphicsContext.fillText("Y: " + (int)actualPositionH, 1350, 50);

        graphicsContext.fillText("Output:", 1200, 150);
    }

    public static void conveyorAnimated() {
        graphicsContext.drawImage(conveyor, 5, conveyorPos - 600);
        graphicsContext.drawImage(conveyor, 5, conveyorPos);
        conveyorPos = conveyorPos + 0.5;
        graphicsContext.drawImage(cover, 5, 618);
        if (conveyorPos >= 600) {
            conveyorPos = 0;
        }
    }
}