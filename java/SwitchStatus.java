/*
 * Requirement: Java
 * If want to run without compile, need java version >= 11
 * Usage(time in min):
 * java SwitchStatus.java 2
*/

import java.util.*;
import java.awt.*;                //main package used: import java.awt.Robot; import java.awt.AWTException;
import java.awt.event.InputEvent;
import java.io.*;
import java.time.LocalDateTime;    

class SwitchStatus{
    //Please check your monitor scaling by checking the Windows monitor scale. Now using 100%=1.0
    static final double screenScaleFactor = 1.0; 
    static enum GACDStatus{
        Offline,
        DeepDive,
        CustomerMeeting,
        AtLunch
    };

    static class Task{
        GACDStatus status;
        double delayMin;
        
        Task(GACDStatus status, double delayMin) {
            this.status = status;
            this.delayMin = delayMin;
        }
    }

    public static void main(String[] args) {

        GACDStatus status = GACDStatus.Offline;
        double delayMin = 1;

        Queue<Task> taskQueue = new ArrayDeque<>();

        System.out.println("\n\n*** Please open case console Dashboard in the HP Elitbook main monitor 900p with chrome and Windows scale at 100% ***");

        if (args.length > 0){
            System.out.println("Command line imput not suppoted yet");
            delayMin = Double.valueOf(args[0]);
        } else {
            
            Scanner input = new Scanner(System.in);
            long accumulateWaitTime = 0;

            //Getting the status to switch to and put in a task queue.
            Boolean addingMoreTask = true;

            while (addingMoreTask){
                Task curTask = new Task(GACDStatus.Offline, 1);

                System.out.println("Input the number of Status wanted [1. Offline] [2. DeepDive] [3. CustomerMeeting] [4. AtLunch]:");
                int statusNumber = input.nextInt();
                switch (statusNumber){
                    case 1:
                        curTask.status = GACDStatus.Offline;
                        break;
                    case 2:
                        curTask.status = GACDStatus.DeepDive;
                        break;
                    case 3:
                        curTask.status = GACDStatus.CustomerMeeting;
                        break;
                    case 4:
                        curTask.status = GACDStatus.AtLunch;
                        break;
                    default:
                        System.out.println("No available status, set to default status [Offline]");
                        curTask.status = GACDStatus.Offline;
                }
                
                System.out.println("Input the time delay until action(Minutes):");
                curTask.delayMin = input.nextDouble();

                taskQueue.offer(curTask);

                accumulateWaitTime += (long)curTask.delayMin;
                LocalDateTime expectedTaskActionTime = LocalDateTime.now();
                System.out.println("Task added, switch to [" + curTask.status + "] in [" + String.valueOf(curTask.delayMin) + "] min expecting at [" + expectedTaskActionTime.plusMinutes(accumulateWaitTime) + "]");

                // Get choice to continue add task or not
                char addMoreTaskChoice = '?';

                while (addMoreTaskChoice != 'y' && addMoreTaskChoice != 'n'){
                    System.out.println("Do you want to add more tasks? (y/n): ");
                    addMoreTaskChoice = input.next().charAt(0);
                }

                addingMoreTask = addMoreTaskChoice == 'y' ? true : false;
                System.out.println("======================================================================");
            }

            input.close();
        }

        //executing the tasks
        while (!taskQueue.isEmpty()){

            Task curTask = taskQueue.poll();

            System.out.println("======================================================================");
            System.out.println("\n\n*** Task Start ***\n");
            LocalDateTime taskActionTime = LocalDateTime.now();
            System.out.println("#### Switching GACD status to [" + curTask.status + "] in [" + String.valueOf(curTask.delayMin) + "] min expecting at [" + taskActionTime.plusMinutes((long)curTask.delayMin) + "] ####");

            try {
                switchStatus(curTask.status, (int)(curTask.delayMin * 60));
            } catch (Exception e){
                System.out.println("Got Exception");
            }

            System.out.println("*** Task finished ***");
        }

        System.out.println("*** Queue empty all tasks finished ***");
    }

    private static void switchStatus(GACDStatus status, int delaySec) throws Exception{
        /*
            Based on 1080p screen:
            1500, 660 Offline
            1500, 700 DeepDive 
        */ 
        
        click(1403, 140); // click arrow
        click(1300, 222); // Expand status list
        switch (status){
            case Offline:
                int[] OfflineAxis = {1300, 602};
                move(OfflineAxis[0], OfflineAxis[1]); // old 670
                delayWithCountDown(delaySec);
                click(OfflineAxis[0], OfflineAxis[1]);
                break;
            case DeepDive:
                int[] DeepDiveAxis = {1300, 632};
                move(DeepDiveAxis[0], DeepDiveAxis[1]); // old 690
                delayWithCountDown(delaySec);
                click(DeepDiveAxis[0], DeepDiveAxis[1]);
                break;
            case CustomerMeeting:
                int[] CustomerMeetingAxis = {1300, 722};
                move(CustomerMeetingAxis[0], CustomerMeetingAxis[1]); 
                delayWithCountDown(delaySec);
                click(CustomerMeetingAxis[0], CustomerMeetingAxis[1]);
                break;
            case AtLunch:
                int[] AtLunchAxis = {1300, 432};
                move(AtLunchAxis[0], AtLunchAxis[1]); 
                delayWithCountDown(delaySec);
                click(AtLunchAxis[0], AtLunchAxis[1]);
                break;
            default:
                System.out.println("!!! Wrong status, can not proceed !!!");
        }
        click(1400, 140); // fold the pop window

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
