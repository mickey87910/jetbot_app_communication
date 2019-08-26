import torch
import torchvision

model = torchvision.models.alexnet(pretrained=False)
model.classifier[6] = torch.nn.Linear(model.classifier[6].in_features, 7)
print("success")
model.load_state_dict(torch.load('library_model.pth'))
print("success")
device = torch.device('cuda')
model = model.to(device)
print("success")
import cv2
import numpy as np

mean = 255.0 * np.array([0.485, 0.456, 0.406])
stdev = 255.0 * np.array([0.229, 0.224, 0.225])

normalize = torchvision.transforms.Normalize(mean, stdev)
print("success")

def preprocess(camera_value):
    global device, normalize
    x = camera_value
    x = cv2.cvtColor(x, cv2.COLOR_BGR2RGB)
    x = x.transpose((2, 0, 1))
    x = torch.from_numpy(x).float()
    x = normalize(x)
    x = x.to(device)
    x = x[None, ...]
    return x
import traitlets
from IPython.display import display
import ipywidgets.widgets as widgets
from jetbot import Camera, bgr8_to_jpeg

camera = Camera.instance(width=224, height=224)
image = widgets.Image(format='jpeg', width=224, height=224)

camera_link = traitlets.dlink((camera, 'value'), (image, 'value'), transform=bgr8_to_jpeg)

print("success")
from jetbot import Robot

robot = Robot()
print("success")
import torch.nn.functional as F
import time
target_class = 4 ##找的類別
##取得list裡最大值的索引位置(藉此判斷類別)
def argmax(lst):
    return max(range(len(lst)), key=lst.__getitem__)
##
recent_class=[]
recent_class_count=[0,0,0,0,0,0,0]
def update(change):
    global robot
    x = change['new']
    x = preprocess(x)
    y = model(x)
    # we apply the `softmax` function to normalize the output vector so it sums to 1 (which makes it a probability distribution)
    y = F.softmax(y, dim=1)
    yf =y.flatten()
    ##偵測可能之類別
    class_acc = []
    for i in range(len(yf)):
        class_acc.append(float(yf[i]))
    detect_class = argmax(class_acc)

    ############Beta

    #添加到最近偵測之類別的陣列
    if len(recent_class)>100:
        recent_class.pop(0)
    recent_class.append(detect_class)
    ##

    ##找出近100次的類別大概都是哪一個
    for i in range(len(recent_class_count)):
        recent_class_count[i] = recent_class.count(i)
    offten = argmax(recent_class_count)
    ##

#     ##################

    acc = class_acc[detect_class]

    print(detect_class)##偵測的類別
    print("acc:"+str(acc))##命中率
    print("recent"+str(offten))
    ##根據偵測類別進行之行為
    if detect_class == target_class:
        robot.set_motors(0.6,0.65)
        time.sleep(1)
    else:##不是尋求的類別
        robot.left(0.5)
    ##
    time.sleep(0.001)

update({'new': camera.value})  # we call the function once to intialize
print("success")
camera.observe(update, names='value')  # this attaches the 'update' function to the 'value' traitlet of our camera
camera.unobserve(update, names='value')
robot.stop()

