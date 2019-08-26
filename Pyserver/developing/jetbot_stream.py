import socket
from jetbot import Camera, bgr8_to_jpeg
import base64
import threading
import time
##全域變數設定
c2=""
video_s=""
ip=""
image=""
##
##取得當前ip
def setIP(newIP):
    global ip
    ip = newIP
##
def setImage(newImage):
    global image
    image = newImage
##建立圖片監聽伺服器
def createServer():
    global video_s
    video_s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
    video_s.setsockopt(socket.SOL_SOCKET,socket.SO_REUSEADDR,1)
    video_port = 7777
    video_s.bind((ip,video_port))
    video_s.listen()
##
def start():
    while True:
        global c2
        global video_s
        print("video socket wait for connecting")
        c2,addr2 = video_s.accept() ##等待連線
        Video_Stream()
def Video_Stream():
    ##傳送當前影像
    global c2
    global image
    print("encode image")
    strr = base64.b64encode(image.value)
    try:
        print("send")
        c2.sendall(strr)
        c2.close()
        print("send complete")
    except Exception as e :
        print("Error!")
        print(e)
