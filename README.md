# jetbot_app_communication

## Introduction
What we have done is using APP to control,take pictures.<br>
You can control jetbot by using APP. <br>
You can take and save picture and then see what image was jetbot saved. <br>
<img src="https://github.com/omega87910/MY_GITHUB_IMAGES/blob/master/jetbot_app_communication/car.png" width="50%" height="50%"></img><br>

## Required
Android version at least: 6.0 <br>
jetbot*1 <br>
### Step 1. Start Python Socket
Copy Pyserver directory to jetbot.<br>
Initialize Server <br>
<code> cd Pyserver </code> <br>
<code> python3.6 jetbot_center.py </code> <br>
### Step 2. Install APP
Copy apk to your smartphone.<br>
Install APP and open it. <br>
#### Input your server ip into the textfield.<br>
<img src="https://github.com/omega87910/MY_GITHUB_IMAGES/blob/master/jetbot_app_communication/connect_interface.png" width="50%" height="50%"></img><br>
#### you can see the second page(GUIDE,RETURN BOOK,FOLLOW ,GO HOME, has been removed)<br>
<img src="https://github.com/omega87910/MY_GITHUB_IMAGES/blob/master/jetbot_app_communication/chooseMode_interface.png" width="50%" height="50%"></img><br>
### Step 3. Control and Take pictures.
Select "Control" ,you can touch any button to control and take pictures.<br>
When you touch take pictures, jetbot will send a picture which is his camera seeing,and save image to img directory.<br>
<img src="https://github.com/omega87910/MY_GITHUB_IMAGES/blob/master/jetbot_app_communication/control_interface.png" width="50%" height="50%"></img><br>

## Trouble Shooting
#### 1.Give APP Permission
<img src="https://github.com/omega87910/MY_GITHUB_IMAGES/blob/master/jetbot_app_communication/trouble.png" width="30%" height="30%"></img><br>

### I think this is very convenience for you guys collecting pictures and training AI. <br>
### Have a nice day.<br>
