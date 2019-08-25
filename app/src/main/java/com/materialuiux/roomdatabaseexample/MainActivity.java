package com.materialuiux.roomdatabaseexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.materialuiux.roomdatabaseexample.adapter.Ad_Contacts;
import com.materialuiux.roomdatabaseexample.interfaces.IContactDAO;
import com.materialuiux.roomdatabaseexample.models.Contacts;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * type to identity the intent if its for new contact or to edit contact
     */
    private static final String TYPE = "type";

    // the DAO to access database
    IContactDAO contactDAO;
    AppDatabase database;

    // UI references.
    FloatingActionButton addContact;
    RecyclerView recyclerView;
    Ad_Contacts ad_contacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup Database and get DAO
        database = Room.databaseBuilder(this, AppDatabase.class, "contactsdb")
                .allowMainThreadQueries()
                .build();
        contactDAO = database.getContactDAO();

        // initialize views
        addContact = findViewById(R.id.add_contact);
        addContact.setOnClickListener(this);
        recyclerView = findViewById(R.id.Recycler_View);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    public void onClick(View view) {
        if (view == addContact) {
            // add new contact
            Intent intent = new Intent(this, NewContactActivity.class);
            intent.putExtra(TYPE, 0);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // retrieve all data that was written into the database
        List<Contacts> contactsList = contactDAO.getAllContacts();
        Collections.reverse(contactsList);
        // set the data into the recycler View
        ad_contacts = new Ad_Contacts(MainActivity.this, contactsList);
        recyclerView.setAdapter(ad_contacts);
    }
}
