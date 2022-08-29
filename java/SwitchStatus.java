import java.awt.Robot;
import java.awt.AWTException;
import java.awt.event.InputEvent;
import java.awt.*;
import java.lang.Thread;

class SwitchStatus{
    public static void main(String[] args) {
        System.out.println("[Switching command center status to offline]");

        try {
            String status = "Offline";
            int delaySec = 1 * 60;

            switchStatus(status, delaySec);
        } catch (Exception e){
            System.out.println("Got AWTException");
        }
        System.out.println("Move finished");
    }

    private static void switchStatus(String status, int delaySec) throws AWTException{
        Robot bot = new Robot();
        //bot.mouseMove(1000, 100);

        /*
        1500, 660 Offline
         1500, 700 DeepDive 
        */ 
        System.out.println(String.valueOf(Toolkit.getDefaultToolkit().getScreenResolution()));
        
        click(1670, 155);
        click(1600, 255);
        switch (status){
            case "Offline":
                click(1500, 660);

                //System.out.println(String.valueOf(MouseInfo.getPointerInfo().getLocation().getX()));
                break;
            default:

        }
    }

    private static void click(int x, int y) throws AWTException{
        Robot bot = new Robot();

        double screenScaleFactor = 1.25;
        bot.mouseMove((int)(x / screenScaleFactor), (int)(y/screenScaleFactor));
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        bot.delay(1000);
    }
}