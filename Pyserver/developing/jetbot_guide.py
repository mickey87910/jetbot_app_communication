import torch
import torchvision
import cv2
import numpy as np
import torch.nn.functional as F
import time



##全域變數
robot=""
camera=""
image=""
target = "0" ##搜尋目標之類別
running = True
##
model = torchvision.models.alexnet(pretrained=False)
model.classifier[6] = torch.nn.Linear(model.classifier[6].in_features, 7)
print("loding model")
model.load_state_dict(torch.load('library_model.pth'))
print("load success")
device = torch.device('cuda')
model = model.to(device)
mean = 255.0 * np.array([0.485, 0.456, 0.406])
stdev = 255.0 * np.array([0.229, 0.224, 0.225])
normalize = torchvision.transforms.Normalize(mean, stdev)
recent_class=[]
recent_class_count=[0,0,0,0,0,0,0]

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
def setImage(newImage):
    global image
    image = newImage
def setRobot(newRobot):
    global robot
    robot = newRobot
def setTarget(newTarget):
    global target
    target = newTarget
    print("guide to "+str(target))
##取得list裡最大值的索引位置(藉此判斷類別)
def argmax(lst):
    return max(range(len(lst)), key=lst.__getitem__)
##
def update(change):
    global robot,recent_class,recent_class_count,target

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
    
     ##################
    
    acc = class_acc[detect_class]
    
    print(detect_class)##偵測的類別
    print("acc:"+str(acc))##命中率
    print("recent:"+str(offten))
    print("search:"+str(target))
    ##根據偵測類別進行之行為
    if str(detect_class) == str(target):
        robot.forward(0.6)
        time.sleep(1)
    ##若不是尋求的類別則
    else:
        robot.left(0.4)
    ##
    time.sleep(0.1)
def start():
    global camera
    print("intialize")
    update({'new': camera.value}) # we call the function once to intialize
    camera.observe(update, names='value')  # this attaches the 'update' function to the 'value' traitlet of our camera
    print("camera set") 
def stop():
    global camera,robot,running
    camera.unobserve(update, names='value')
    robot.stop()
def setRunning(newRunning):
    global running
    running = newRunning
def getRunning():
    global running
    return running
def setCamera(newCamera):
    global camera
    camera = newCamera
