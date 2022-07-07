****## Shamrock 

**Steps to Run our application**

**Step 1:** Clone Repo<br> 
**Step 2:** Open in Android Studio, build and view in emulator<br>Ensure that your emulator has at least<br>
<ul>
    <li>Target API level 19 (KitKat) or higher </li>
    <li>Uses Android 4.4 or higher</li>
</ul>

Personally our emulator is 
<ul>
    <li>Pixel 5</li>
    <li>Lollipop</li>
    <li>Android 10.0</li>
</ul>

However, default emulator works fine as well 

**What our application does so far...**
<ul>
    <li>Can read and write to firestore Cloud</li>
    <li>Can Switch pages upon 'Log in'</li>
    <li>Basic methods of Caregiver class needed for database have been implemented </li>
</ul>

If emulator has been setup correctly then it should take a couple moments for the app to startup<br>
Then you should see this screen<br>
<img src= "login.png">

<br>
The **create account** button should store the username and password in the database<br>
The **Load** button will print the values we have stored in our database <br>
The **Update Password** button should update the password of the given user in the database, if the user does not exist then it will create the user <br>
The **Log In** button will lead the user to the home page
