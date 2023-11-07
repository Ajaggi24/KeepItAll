package com.example.keepitall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

public class ViewItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        displayText();

        // Go back to HomePage when back or homeButton is clicked
        Button backButton = findViewById(R.id.viewBackButton);
        backButton.setOnClickListener(v -> finish());
        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> finish());

        // If item logo is clicked, go to change logo page
        ImageView logoImage = findViewById(R.id.itemIcon);
        logoImage.setOnClickListener(v -> changeActivity(ChangeLogoActivity.class));

        // If gallery button is clicked, go to item gallery page
        Button galleryButton = findViewById(R.id.galleryButton);
        galleryButton.setOnClickListener(v -> changeActivity(ImageGalleryActivity.class));

        // TODO: delete functionality, tag functionality, edit properties functionality
    }

    /**
     * Displays the clicked item's properties
     */
    private void displayText() {
        // Get item properties
        Item item = (Item) getIntent().getSerializableExtra("item");
        String name = item.getName();
        Date date = item.getPurchaseDate();
        String make = item.getMake();
        String model = item.getModel();
        Float value = item.getValue();
        Integer serialNum = item.getSerialNumber();
        String description = item.getDescription();

        // Get text views
        TextView nameView = findViewById(R.id.itemNameText);
        TextView dateView = findViewById(R.id.purchaseDateText);
        TextView makeView = findViewById(R.id.makeText);
        TextView modelView = findViewById(R.id.modelText);
        TextView valueView = findViewById(R.id.valueText);
        TextView serialNumView = findViewById(R.id.serialNumberText);
        TextView descriptionView = findViewById(R.id.descriptionText);

        // Set text based on item properties
        nameView.setText(name);
        dateView.setText("Date of Purchase: " + date.toString());
        makeView.setText("Item Make: " + make);
        modelView.setText("Item Model: " + model);
        valueView.setText("Estimated Value: " + value.toString());
        serialNumView.setText("Serial Number: " + serialNum.toString());
        descriptionView.setText("Description: " + description);
    }

    /**
     * Changes the activity based on clicked button
     * @param activity
     */
    private void changeActivity(Class activity) {
        Intent intent = new Intent(ViewItemActivity.this, activity);
        startActivity(intent);
    }
}