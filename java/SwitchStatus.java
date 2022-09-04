/*
 * Requirement: Java
 * If want to run without compile, need java version >= 11
 * Usage(time in min):
 * java SwitchStatus.java 2
*/

// import java.awt.Robot;
// import java.awt.AWTException;
import java.awt.event.InputEvent;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

//import net.sourceforge.argparse4j.ArgumentParsers;

class SwitchStatus{
    //Please check your monitor scaling by checking the windows monitor scale. 125% == 1.25
    static final double screenScaleFactor = 1.25; 
    static enum GACDStatus{
        Offline,
        DeepDive
    };

    public static void main(String[] args) {
        
        GACDStatus status = GACDStatus.Offline;
        int delayMin = 1;

        System.out.println("*** Please open case console Dashboard in the HP Elitbook monitor with chrome***");

        if (args.length > 0){
            delayMin = Integer.valueOf(args[0]);
        } else {
            Scanner input = new Scanner(System.in);
            System.out.println("Input the number of Status wanted *[1. Offline] [2. DeepDive]:");
            int statusNumber = input.nextInt();
            switch (statusNumber){
                case 1:
                    status = GACDStatus.Offline;
                    break;
                case 2:
                    status = GACDStatus.DeepDive;
                    break;
            }
            
            System.out.println("Input the time delay until action(Minutes):");
            delayMin = input.nextInt();

            input.close();
        }

        System.out.println("#### Switching command center status to [" + status + "] in [" + String.valueOf(delayMin) + "] min ####");

        try {
            switchStatus(status, delayMin * 60);
        } catch (Exception e){
            System.out.println("Got AWTException");
        }

        System.out.println("Task finished");
    }

    private static void switchStatus(GACDStatus status, int delaySec) throws Exception{
        //bot.mouseMove(1000, 100);

        /*
        Based on 1080p screen:
        1500, 660 Offline
         1500, 700 DeepDive 
        */ 
        //System.out.println(String.valueOf(Toolkit.getDefaultToolkit().getScreenResolution()));
        
        click(1670, 155);
        click(1600, 255);
        switch (status){
            case Offline:
                move(1500, 660);
                delayWithCountDown(delaySec);
                click(1500, 660);

                //System.out.println(String.valueOf(MouseInfo.getPointerInfo().getLocation().getX()));
                break;
            case DeepDive:
                move(1500, 700);
                delayWithCountDown(delaySec);
                click(1500, 700);
                break;
            default:
        }
    }

    private static void click(int x, int y) throws Exception{
        Robot bot = new Robot();

        bot.mouseMove((int)(x / screenScaleFactor), (int)(y/screenScaleFactor));
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        bot.delay(1000);
        
    }

    private static void move(int x, int y) throws Exception{
        Robot bot = new Robot();

        bot.mouseMove((int)(x / screenScaleFactor), (int)(y/screenScaleFactor));
    }

    private static void delayWithCountDown(int delaySec) throws Exception{
        Robot bot = new Robot();

        Writer writer = new PrintWriter(System.out);
        
        System.out.println("Time remaining(sec): ");

        for (int i = delaySec; i >=0; i--) {
            writer.write("                             \r"); // return to the line start without new line, so override.
            writer.write(String.valueOf(i));
            writer.flush(); // this is used to push things out from the writer to console.
            bot.delay(1000);
            
        }
        writer.write("\n\r");
        //writer.close();
    }
}