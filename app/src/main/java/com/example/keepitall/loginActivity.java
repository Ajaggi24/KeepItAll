package com.example.keepitall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * The class that holds the main functionality of the login page
 * We either login or register from this page
 * Registering will open a dialog box
 # login will send us to the home page
 */
public class loginActivity extends AppCompatActivity {

    private String username;
    private String password;
    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginButton;
    private final KeepItAll keepItAll = KeepItAll.getInstance();
    private TextView signUpText;

    private FirebaseFirestore Database = FirebaseFirestore.getInstance();
    private CollectionReference userCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // initialize variables
        usernameInput = findViewById(R.id.userName_Input);
        passwordInput = findViewById(R.id.password_Input);
        loginButton = findViewById(R.id.login_Button);
        signUpText = findViewById(R.id.signUpText);
        // Login button listener that calls the login method
        loginButton.setOnClickListener(v -> Login());
        signUpText.setOnClickListener(v -> openRegisterAccount());



        ///TODO: Make this part of the database
        //createMocKeepItAll();
        keepItAll.retrieveUsers();


        userCollection = Database.collection("users");
        userCollection.addSnapshotListener(new EventListener<QuerySnapshot>(){
            @Override
            public void onEvent(QuerySnapshot value, FirebaseFirestoreException error){
                if(error != null){
                    return;
                }
                if (value != null) {
                    // Clear the list of users
                    for (QueryDocumentSnapshot doc: value) {
                        // The collection stores the user as a HashMap
                        // [String, User]
                        // get the map
                        Map<String, Object> data = doc.getData();
                        // get the user
                        //User user = (User) data.get("User");
                        //if(user == null){
                            //Toast.makeText(loginActivity.this, "User is null", Toast.LENGTH_SHORT).show();
                        //}
                        //keepItAll.addUser(user);
                    }
                }



            }
        });
    }
    /**
     * Various messages that will be displayed if the user
     * badly inputs their username or password.
     * Cases:
     * 1. Both username and password are empty
     * 2. Only the username is empty
     * 3. Only the password is empty
     * 4. The username or password is incorrect
     */
    private void LoginMessages(){
        // Display a toast message if the username or password is empty
        if(usernameInput.getText().toString().isEmpty() && passwordInput.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter a username and password", Toast.LENGTH_SHORT).show();
        }
        // Display a toast message if only the username is empty
        else if(usernameInput.getText().toString().isEmpty() && !passwordInput.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
        }
        // Display a toast message if only the password is empty
        else if(!usernameInput.getText().toString().isEmpty() && passwordInput.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * This method will check if the username and password are correct
     * and if they are, it will go to the next activity.
     */
    private void Login(){
        username = usernameInput.getText().toString();
        password = passwordInput.getText().toString();
        LoginMessages();
        User userToLogin = keepItAll.getUserByName(username);
        // Check if the User is null
        if(userToLogin == null){
            Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!userToLogin.getPassword().equals(password)){
            Toast.makeText(this, "Username or password is incorrect", Toast.LENGTH_SHORT).show();
            return;
        }
        if(userToLogin.getPassword().equals(password)){
            Toast.makeText(this, "Login Successful, Welcome " + username, Toast.LENGTH_LONG).show();
            waitForSeconds(1.5f);
            launchHomePage();
        }
        else{
            Toast.makeText(this, "Username or password is incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method will open the register account dialog box
     */
    private void openRegisterAccount(){
        RegisterAccount registerAccount = new RegisterAccount();
        registerAccount.show(getSupportFragmentManager(), "Register Account");
    }

    /**
     * This method will launch the home page activity
     */
    private void launchHomePage(){
        Intent i = new Intent(this, HomePageActivity.class);
        i.putExtra("username", username);
        startActivity(i);
    }

    /**
     * This method will wait for a certain amount of seconds
     * (pause the program)
     * @param seconds the amount of seconds to wait
     */
    private void waitForSeconds(float seconds){
        try {
            Thread.sleep((long)seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will create a mock KeepItAll object
     * with a few users and items. Mostly for testing purposes
     */
    private void createMocKeepItAll(){
        User dev = new User("dev", "pass", "email");
        keepItAll.addUser(dev);
        // Create a few items
        Item item1 = new Item(new Date(2002-20-02), "Test Description 1", "Test Location 1", "Test Category 1", 1231, 10.f, "Test Serial Number 1");
        Item item2 = new Item(new Date(2002-20-02), "Test Description 2", "Test Location 2", "Test Category 2", 1232, 20.f, "Test Serial Number 2");
        // Add the items to the user
        dev.getItemManager().addItem(item1);
        dev.getItemManager().addItem(item2);
        User Cohen = new User("Cohen", "word", "email");
        keepItAll.addUser(Cohen);
        Item item3 = new Item(new Date(2002-20-02), "Test Description 2", "Test Location 2", "Test Category 2", 1232, 20.f, "Test Serial Number 2");
        Cohen.getItemManager().addItem(item3);
    }
}