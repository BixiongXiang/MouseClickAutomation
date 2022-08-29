# Usage:
# python switch2Offline.py 35
# time unit: minutes

# requirement:
# python 3
# pip install pyautogui


# Auto change command center status, axis for screen of main HP Elitbook 1920x1080
# please open dashboard then run this script
# https://command-center.support.aws.a2z.com/case-console#/dashboard

# useful command
# check position pyautogui.position()
import pyautogui
import time
import sys

# Define status string
deepDive = 'DeepDive'
offline = 'Offline'

def main():
    # define sleep time
    delayMinutes = 1

    # get parameter from command line 
    if len(sys.argv) > 1: 
        print('DelayTime: ' + sys.argv[1] + 'minutes')
        delayMinutes = int(sys.argv[1])
    else:
        print('No argument, use default values')

    # Switch status
    switchStatusWithDelay(delayMinutes, offline)

def switchStatusWithDelay(delayMinutes, status):
    print('Do not move mouse! Status switch start ......')
    time.sleep(1)
    print('Switching to [' + status + ']')
    
    # Open Drop list
    pyautogui.moveTo(1670, 155)
    time.sleep(1)
    pyautogui.click(1670, 155) 

    # Open Status drop list
    pyautogui.moveTo(1600, 255)
    time.sleep(1)
    pyautogui.click(1600, 255) 

    print('sleep for ' + str(delayMinutes) + ' minutes')
    
    #### Status:
    # 1500, 660 Offline
    # 1500, 700 DeepDive
    if status == offline:
        pyautogui.moveTo(1500, 660)
        sleepWithCountDown(delayMinutes * 60)
        pyautogui.click(1500, 660)

    elif status == deepDive:
        pyautogui.moveTo(1500, 700)
        sleepWithCountDown(delayMinutes * 60)
        pyautogui.click(1500, 700)

    print('Mouse clicked, switch finished')

def sleepWithCountDown(timeInSecond):
    for i in range(timeInSecond,0,-1):
        sys.stdout.write("\r")
        sys.stdout.write("{:2d} seconds remaining.".format(i))
        sys.stdout.flush()
        time.sleep(1)

# main script
main()