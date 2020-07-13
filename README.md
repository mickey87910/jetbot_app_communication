# jetbot_app_communication

## Introduction
You can control jetbot by using APP. <br>
You can take and save picture and then see what image was jetbot saved. <br>
<img src="https://github.com/omega87910/MY_GITHUB_IMAGES/blob/master/jetbot_app_communication/car.png" width="50%" height="50%"></img><br>

## Requirement
Android version ≥ 6.0 <br>
jetbot*1 <br>
## Usage
### Step 1. Start Python Socket
Copy <code>Pyserver</code> directory to jetbot.<br>
Initialize Server <br>
<code> cd Pyserver </code> <br>
<code> python3.6 jetbot_center.py </code> <br>
### Step 2. Install APP
Copy ``.apk`` to your smartphone.<br>
Install APP and run it. <br>
#### Input your jetbot ipv4 address into the textfield.<br>
<img src="https://github.com/omega87910/MY_GITHUB_IMAGES/blob/master/jetbot_app_communication/connect_interface.png" width="50%" height="50%"></img><br>
#### you can see the second page(GUIDE,RETURN BOOK,FOLLOW ,GO HOME, have been removed)<br>
<img src="https://github.com/omega87910/MY_GITHUB_IMAGES/blob/master/jetbot_app_communication/chooseMode_interface.png" width="50%" height="50%"></img><br>
### Step 3. Control and Take pictures.
press "Control" button, then you can press button to control jetbot and take pictures.<br>
When you press "take pictures" button, jetbot will send a picture which is his camera seeing,and save the picture to ``img`` directory.<br>
<img src="https://github.com/omega87910/MY_GITHUB_IMAGES/blob/master/jetbot_app_communication/control_interface.png" width="50%" height="50%"></img><br>

## Knows Problem
#### 1.Permission Problem  
Give APP Permission  
<img src="https://github.com/omega87910/MY_GITHUB_IMAGES/blob/master/jetbot_app_communication/trouble.png" width="30%" height="30%"></img>  

### I think this is very convenience for you guys collecting pictures and training AI. <br>
### Have a nice day.<br>

## Contributors
[![](https://github.com/omega87910.png?size=50)](https://github.com/omega87910)
<a href="https://github.com/BlackBloodE"><img src="https://github.com/BlackBloodE.png" width=50></a>
<a href="https://github.com/shes094101"><img src="https://github.com/shes094101.png" width=50></a>
