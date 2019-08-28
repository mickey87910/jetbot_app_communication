# jetbot_app_communication

## Introduction
This Project is for the Contest.<br>
What we have done is using APP to control,take picture,guide.<br>
You can control jetbot by using APP <br>
You can use APP to take and save picture and then return result <br>
Developing <del> 3.You can use APP to load Guide AI model ,and then detect where it have to go <br> </del> 
![image](https://github.com/omega87910/jetbot_app_communication/blob/master/README_IMG/car.jpg)

## Required
Android version at least: 6.0
### Step 1. Start Python Socket
Copy Pyserver directory to jetbot.<br>
Initialize Server <br>
<code> cd Pyserver </code> <br>
<code> python3.6 jetbot_center.py </code> <br>
### Step 2. Install APP
Copy apk to your smartphone.<br>
Install APP and open it. <br>
#### Input your server ip into the textfield.<br>
![image](https://github.com/omega87910/jetbot_app_communication/blob/master/README_IMG/connect_interface.png)<br>
#### you can see the second page<br>
![image](https://github.com/omega87910/jetbot_app_communication/blob/master/README_IMG/chooseMode_interface.png)<br>
### Step 3. Control and Take pictures.
Go to "Control Mode" ,you can touch any button to control and take pictures.<br>
When you touch take pictures, jetbot will send a picture which is his camera seeing,and save image to img directory.<br>
![image](https://github.com/omega87910/jetbot_app_communication/blob/master/README_IMG/control_interface.png)<br>


### I think this is very convinience for you guys collecting pictures and training AI. Have a nice day.
