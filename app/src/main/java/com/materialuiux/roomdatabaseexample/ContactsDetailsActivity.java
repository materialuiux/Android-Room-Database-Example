package com.materialuiux.roomdatabaseexample;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.materialuiux.roomdatabaseexample.interfaces.IContactDAO;
import com.materialuiux.roomdatabaseexample.models.Contacts;

public class ContactsDetailsActivity extends AppCompatActivity {

    private static final String ID = "id";
    private static final String TYPE = "type";
    private static int id;


    // the DAO to access database
    IContactDAO contactDAO;
    AppDatabase database;
    Contacts contactsDetails;

    // UI references.
    TextView contactNumber, contactAddress;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_details);

        // setup Database and get DAO
        database = Room.databaseBuilder(this, AppDatabase.class, "contactsdb")
                .allowMainThreadQueries()
                .build();
        contactDAO = database.getContactDAO();

        // get contact id
        Intent mIntent = getIntent();
        if (mIntent != null) {
            id = mIntent.getIntExtra(ID, 0);
        }

        // initialize views
        toolbar = findViewById(R.id.toolbar);
        contactNumber = findViewById(R.id.Contact_Number);
        contactAddress = findViewById(R.id.Contact_Address);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        contactsDetails = contactDAO.getItemById(id);
        getSupportActionBar().setTitle(contactsDetails.getFirstName() + " " + contactsDetails.getLastName());
        contactNumber.setText(contactsDetails.getPhoneNumber());
        contactAddress.setText(contactsDetails.getAddress());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_edit:
                // Edit the Contact by passing 1 to the NewContactActivity that represent the editing in the NewContactActivity.
                Intent intent = new Intent(this, NewContactActivity.class);
                intent.putExtra(TYPE, 1);
                intent.putExtra(ID, contactsDetails.getUid());
                startActivity(intent);
                return true;
            case R.id.menu_delete:
                // Delete Contact.
                contactDAO.deleteContacts(contactsDetails);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
