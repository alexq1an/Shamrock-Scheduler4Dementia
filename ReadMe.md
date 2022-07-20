## Shamrock 

**Steps to Run our application**

**Step 1:** Clone Repo<br> 
**Step 2:** Open in Android Studio, build and view in emulator<br>Ensure that your emulator has at least<br>
<ul>
    <li>Target API level 19 (Kitkat) or higher</li>
    <li>Uses Android 4.0 or higher</li>
</ul>

Personally our emulator is 
<ul>
    <li>Pixel 5</li>
    <li>API Level 22 Lollipop</li>
    <li>Android 5.1</li>
</ul>

However, default emulator works fine as well 

**What our application does so far...**
<ul>
    <li>Can read and write to firestore Cloud</li>
    <li>Can Switch pages</li>
    <li>Basic methods of Caregiver class needed for database have been implemented </li>
    <li>Basic methods of Parent class needed for database have been implemented </li>
    <li>Basic methods of Task class needed for database have been implemented </li>
    <li>Basic methods of Scehdule class needed for database have been implemented </li>
    <li>Allows the user to choose their role between caregiver and patient </li>
    <li>Allows the caregiver to create a new account </li>
    <li>Checks if the user enters a well formatted email address </li>
    <li>Checks that the user enters a 6 digit/symbol/alphabet/character password </li>
    <li>Checks if that particular account already exist or not </li>
    <li>Allows the caregiver to login in an already created account </li>
    <li>If the caregiver account is new then the app allows to add new patient information </li>
    <li>For patient information it takes in Name, Age, Unique Id, Sex, Description </li>
    <li>Patients can login by using the unique Id </li>
    <li>Allows the caregiver to scroll through the patient list </li>
    <li>Allows the caregiver to choose a particular patient </li>
    <li>Allows the caregiver to edit patient information </li>
    <li>Allows to select the date for the schedule </li>
    <li>Can Set Alarm </li>
    <li>Notifies the user when the alarm rings </li>
    <li>Allows the user to cancel the set alarm </li>
    <li>Allows the user to stop the alarm and takes it to the notification activiy page </li>
    
</ul>

If emulator has been setup correctly then it should take a couple moments for the app to startup<br>
Then you should see this screen<br>
<img src= "login.png">

<br>
The **Create Account** button should store the username and password in the database<br>
The **Load** button will print the values we have stored in our database <br>
The **Update Password** button should update the password of the given user in the database, if the user does not exist then it will create the user <br>
The **Log In** button will lead the user to the home page
