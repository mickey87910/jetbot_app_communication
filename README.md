# jetbot_app_communication
This Project is for the Contest.<br>
What we have done is using APP to control,take picture,guide.
# What you can do for jetbot is
1.You can control jetbot by using APP <br>
2.You can use APP to take picture and then return result <br>
Developing <del> 3.You can use APP to load Guide AI model ,and then detect where it have to go <br> </del> 

# Required
Android version at least: 6.0
# Step 1. Start Python Socket
Initialize Server <br>
<code> cd Pyserver </code> <br>
<code> python3.6 jetbot_center.py </code> <br>
DO NOT use developing code in "developing" directory. <br>
# Step 2. Install APP
Install APP and open it. <br>
Input your server ip into the textfield.<br>
if you can see the second page then you are success.<br>  
# Step 3. Control and Take pictures.
Go to "Control Mode" ,you can touch any button to control and take pictures.<br>
When you touch take pictures, jetbot will send a picture which is his camera seeing,and save image to img directory.
