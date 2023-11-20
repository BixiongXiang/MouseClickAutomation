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
import java.time.LocalDateTime;    

//import net.sourceforge.argparse4j.ArgumentParsers;

class SwitchStatus{
    //Please check your monitor scaling by checking the windows monitor scale. 125% == 1.25
    static final double screenScaleFactor = 1.25; 
    static enum GACDStatus{
        Offline,
        DeepDive,
        CustomerMeeting
    };

    public static void main(String[] args) {

        GACDStatus status = GACDStatus.Offline;
        double delayMin = 1;

        System.out.println("\n\n*** Please open case console Dashboard in the HP Elitbook main monitor with chrome and Window scale at 125% ***");

        if (args.length > 0){
            delayMin = Double.valueOf(args[0]);
        } else {
            Scanner input = new Scanner(System.in);
            System.out.println("Input the number of Status wanted [1. Offline] [2. DeepDive] [3. CustomerMeeting]:");
            int statusNumber = input.nextInt();
            switch (statusNumber){
                case 1:
                    status = GACDStatus.Offline;
                    break;
                case 2:
                    status = GACDStatus.DeepDive;
                    break;
                case 3:
                    status = GACDStatus.CustomerMeeting;
                    break;
            }
            
            System.out.println("Input the time delay until action(Minutes):");
            delayMin = input.nextDouble();

            input.close();
        }

        System.out.println("#### Switching GACD status to [" + status + "] in [" + String.valueOf(delayMin) + "] min ####");

        try {
            switchStatus(status, (int)(delayMin * 60));
        } catch (Exception e){
            System.out.println("Got Exception");
        }

        System.out.println("*** Task finished ***");
    }

    private static void switchStatus(GACDStatus status, int delaySec) throws Exception{
        /*
            Based on 1080p screen:
            1500, 660 Offline
            1500, 700 DeepDive 
        */ 
        
        click(1670, 155); // click arrow
        click(1600, 255); // Expand status list
        switch (status){
            case Offline:
                move(1500, 680); // old 670
                delayWithCountDown(delaySec);
                click(1500, 680);
                break;
            case DeepDive:
                move(1500, 710); // old 690
                delayWithCountDown(delaySec);
                click(1500, 710);
                break;
            case CustomerMeeting:
                move(1500, 810); // old 690
                delayWithCountDown(delaySec);
                click(1500, 810);
                break;
            default:
                System.out.println("!!! Wrong status, can not proceed !!!");
        }
        click(1670, 155); // fold the pop window

        // print the time
        LocalDateTime finishingTime = LocalDateTime.now();
        System.out.println("Click at time: " + finishingTime);
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
            writer.write("\r"); // return to the line start without new line, so override.
            writer.write(String.valueOf(i) + "                         ");
            writer.flush(); // this is used to push things out from the writer to console.
            bot.delay(1000);
        }
        writer.write("\n\n  ");
        writer.flush();
        //writer.close();
    }
}