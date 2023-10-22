package com.example.keepitall;

import java.util.ArrayList;

/**
 * Used for storing all of a users information, including their name and item manager
 */
public class User  implements Comparable<User>{

    // Primary Variables used and help by the User
    private String userName;
    private ItemManager itemManager;
    private Item selectedItem;      

    /**
     * Constructor for the User Object
     * @param userName
     */
    public User(String userName) {
        this.userName = userName;
        itemManager = new ItemManager();
    }


    public Item viewItem(String itemName){
        // Get the desired item from the itemManager, based on the given name
        selectedItem = ItemManager.getItem(itemName);
        // we can now do funcionality with the item we have selected
    }

    public ArrayList<Item> viewAllItems(){
        // Returns the list of all of the items within the ItemManager
    }

    /**
     * Compares the Username of two different Users, returns 0 if they are equal
     * This is primarily used if we ever need to sort users
     * @param userToCompare
     * @return
     * 0 -> if they are equal
     * -1 -> if they are not
     */
    @Override public int compareTo(User userToCompare) {
        return this.userName.compareTo(userToCompare.getUserName());
    }


    // Getters and Setters
    public String getUserName() {return userName;}
    public void setUserName(String userName) {this.userName = userName;}
    public Item getSelectedItem() {return selectedItem;}
}
