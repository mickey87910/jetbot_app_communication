import time
import threading # 引入threading
leftvalue = 0
rightvalue = 0
running = True
robot = "" 
def control():
    print("start running control mode")
    while running:
        print("is controlling")
        global leftvalue
        global rightvalue
        #更改左右馬達速度
        robot.set_motors(leftvalue,rightvalue)
        print("當前速度: "+str(leftvalue)+","+str(rightvalue))
        time.sleep(0.2)
    robot.stop()
    print("離開control")
        
#開始執行control模式
def start():
    thread_1 = threading.Thread(target=control)
    thread_1.start()
#設定開始及停止的設定函式
def setRunning(newRunning):
    global running
    running = newRunning
def getRunning():
    global running
    return running
#設定Robot
def setRobot(newRobot):
    global robot
    robot = newRobot
def setLeftValue(newLeftValue):
    global leftvalue
    leftvalue = newLeftValue
def setRightValue(newRightValue):
    global rightvalue
    rightvalue = newRightValue