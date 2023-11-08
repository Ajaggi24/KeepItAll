package com.example.keepitall;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import android.nfc.Tag;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class ItemManager implements Serializable {
    private ArrayList<Item> itemList;
    private FirebaseFirestore Database = FirebaseFirestore.getInstance();
    private CollectionReference userCollection;

    /**
     * Constructor for the User Object
     * default constructor
     */
    public ItemManager() {
        this.itemList = new ArrayList<>();
        this.userCollection = Database.collection("users");
    }

    /**
     * Adds an item to the the item list and syncs it with the database (fireStore)
     * @param item
     * @param user
     */
    public void addItem_DataSync(Item item, User user) {
        if (item == null || user == null || user.getUserName() == null) {
            return;
        }
        // Create a reference to the Firestore collection
        CollectionReference itemsCollection = userCollection.document(user.getUserName()).collection("items");
        // Use the item's name as the document ID
        DocumentReference itemDocument = itemsCollection.document(item.getName());
        // set the items
        itemDocument.set(item);
        itemList.add(item);
    }

    public void editItem_DataSync(Item item, User user) {
        if (item == null || user == null || user.getUserName() == null) {
            return;
        }
        // Create a reference to the Firestore collection
        CollectionReference itemsCollection = userCollection.document(user.getUserName()).collection("items");

        // Query the Firestore collection to find the document with the specified name
        Query query = itemsCollection.whereEqualTo("name", item.getName());

        // Execute the query
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    // Get the first matching document (assuming there's only one)
                    DocumentSnapshot document = querySnapshot.getDocuments().get(0);

                    // Update the item data in the document
                    document.getReference().set(item);
                }
            } else {
                // Handle the query error, if needed
            }
        });
    }



    /**
     * adds an item to the the item list
     * @param item to be added
     */
    public void addItem(Item item) {
        itemList.add(item);
    }

    /**
     * deletes  item to the the item list
     * @param item to be deleted
     */
    public void deleteItem(Item item) {
        itemList.remove(item);
    }
    /**
     *  used to edit the given properties of the item
     *
     *
     * @param item           The item to be edited. Must not be null.
     * @param newPurchaseDate The new purchase date of the item.
     * @param newDescription  The new description of the item.
     * @param newMake         The new make of the item.
     * @param newModel        The new model of the item.
     * @param newSerialNumber The new serial number of the item.
     * @param newValue        The new value of the item.
     *
     */
    public void editItem(Item item, Date newPurchaseDate, String newDescription, String newMake, String newModel, Integer newSerialNumber, Float newValue) {
        item.setPurchaseDate(newPurchaseDate);
        item.setDescription(newDescription);
        item.setMake(newMake);
        item.setModel(newModel);
        item.setSerialNumber(newSerialNumber);
        item.setValue(newValue);
    }
    /**
     * the output is the  item you are searching for while comparing with name
     * @param position to be searched
     * @return item with the spec name or else null
     */
    public Item getItem(int position) {
        return itemList.get(position);
    }

    /**
     * the output is the list containg all the items
     *
     * @return a new array list containng all the items by itemManager
     */

    public ArrayList<Item> getAllItems() {

        return new ArrayList<>(itemList);
    }

    public boolean itemInList(int position) {
        return itemList.contains(position);
    }

    /**
     * sorts the list in asc/desc order based on different cases
     *
     */
    public void sortItems(List<Item> items, String sortBy, String sortOrder) {
        Comparator<Item> comparator = null;

        switch (sortBy.toLowerCase()) {
            case "purchasedate":
                comparator = Comparator.comparing(Item::getPurchaseDate);
                break;
            case "description":
                comparator = Comparator.comparing(Item::getDescription);
                break;
            case "make":
                comparator = Comparator.comparing(Item::getMake);
                break;
            case "model":
                comparator = Comparator.comparing(Item::getModel);
                break;
            case "serialnumber":
                comparator = Comparator.comparing(Item::getSerialNumber);
                break;
            case "value":
                comparator = Comparator.comparing(Item::getValue);
                break;
            default:
                throw new IllegalArgumentException("Invalid sortBy parameter");
        }

        if (sortOrder.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }

        Collections.sort(items, comparator);
    }
}





