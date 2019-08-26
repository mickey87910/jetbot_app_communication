#!/usr/bin/python3.6
import socket
import jetbot_control
import threading
from jetbot import Robot
from uuid import uuid1
import os, traitlets
import jetbot_guide
##全域變數設定
c=""
s=""
ip=""
image=""
camera=""
robot = Robot()
thread_nums = 0
##
##取得當前ip
def setIP(newIP):
    global ip
    ip = newIP
##
##建立主要Socket協定
def createServer():
    global s
    s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
    s.setsockopt(socket.SOL_SOCKET,socket.SO_REUSEADDR,1)
    port = 9999
    s.bind((ip,port))
    s.listen()
##
def setCamera(newCamera):
    global camera
    camera = newCamera
def setImage(newImage):
    global image
    image = newImage
def save_snapshot(directory):
    ##儲存圖片
    global image
    print("saving image")
    image_path = os.path.join(directory, str(uuid1()) + '.jpg')
    with open(image_path, 'wb') as f:
        f.write(image.value)
    print("saving complete")
        
def chooseMode():
    global thread_nums,robot,camera
    thread_nums = thread_nums+1
    while True:
        global c
        print("thread_nums is "+str(thread_nums))
        #將接收到的資料進行字串分割,藉此去除前面奇怪的不可見字符
        data = c.recv(1024).decode("utf-8").split(":")
        print(data)
        #根據接收到的指令進行相關動作
        try:
            #停止jetbot
            if data[1]=="stop":
                print("mode:stop")
                jetbot_control.setRunning(False)
                jetbot_guide.setRunning(False)
                jetbot_guide.stop()
                jetbot_control.setLeftValue(0)
                jetbot_control.setRightValue(0)
            ##

            ##儲存圖片
            elif data[1]=="saveImage":
                thread_save = threading.Thread(target=save_snapshot('img'))
                thread_save.start()
            ##
            
            ##設定jetbot的左右馬達個別速度
            elif data[1]=="setSpeed":
                try:
                    jetbot_control.setLeftValue(float(data[2]))
                    jetbot_control.setRightValue(float(data[3]))
                    print("set motor speed :"+data[2]+","+data[3])
                except Exception as e:
                    print("Speed error!"+str(e))
            ##
            
            ##設定導航模式
            elif data[1]=="guide":
                print("mode:guide")
                jetbot_guide.setRobot(robot)
                jetbot_guide.setCamera(camera)
                jetbot_guide.setImage(image)
                jetbot_guide.setRunning(True)                
                jetbot_guide.start()
            elif data[1]=="guide_0":
                jetbot_guide.setTarget("0")
            elif data[1]=="guide_1":
                jetbot_guide.setTarget("1")
            elif data[1]=="guide_2":
                jetbot_guide.setTarget("2")
            elif data[1]=="guide_3":
                jetbot_guide.setTarget("3")
            elif data[1]=="guide_4":
                jetbot_guide.setTarget("4")
            elif data[1]=="guide_5":
                jetbot_guide.setTarget("5")
            elif data[1]=="guide_6":
                jetbot_guide.setTarget("6")
            ##
            
            ##設定控制模式
            elif data[1]=="control":
                print("mode:control")
                jetbot_control.setRobot(robot)
                jetbot_control.setRunning(True)
                jetbot_control.start()
            ##
            
            ##離開
            elif data[1]=="close":
                jetbot_control.setRunning(False)
                jetbot_guide.setRunning(False)
                jetbot_guide.stop()
                print("exit chooseMode")
                break
            ##
            
            else:
                print("recive weird string?")
        except Exception as e:
            print("GET exception:")
            print(e)
            break
    print("離開Main連線")
    thread_nums = thread_nums - 1
def start():
    while True:
        global c
        global s
        print("Main Socket wait for connecting")
        c,addr = s.accept() ##等待連線
        print(addr) ##顯示來源ip
        thread_mode = threading.Thread(target=chooseMode)
        thread_mode.start()
    
