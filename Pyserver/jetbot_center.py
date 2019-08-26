import threading
import socket
import jetbot_stream, jetbot_socket
from jetbot import Camera, bgr8_to_jpeg
import ipywidgets.widgets as widgets
import traitlets
##取得ip
ss = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
ss.connect(("8.8.8.8", 80))
ip = ss.getsockname()[0]
##初始化相機
camera = Camera.instance(width=224, height=224)
image = widgets.Image(format='jpeg', width=224, height=224)
camera_link = traitlets.dlink((camera, 'value'), (image, 'value'), transform=bgr8_to_jpeg)
##jetbot_socket設定
jetbot_socket.setIP(ip)
jetbot_socket.createServer()
jetbot_socket.setImage(image)
jetbot_socket.setCamera(camera)
##

##jetbot_stream設定
jetbot_stream.setIP(ip)
jetbot_stream.createServer()
jetbot_stream.setImage(image)
##
##執行伺服器執行緒
thread_socket = threading.Thread(target=jetbot_socket.start)
thread_socket.start()
thread_stream = threading.Thread(target=jetbot_stream.start)
thread_stream.start()
print("ALL Server Ready")