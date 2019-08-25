package com.materialuiux.roomdatabaseexample;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.materialuiux.roomdatabaseexample.interfaces.IContactDAO;
import com.materialuiux.roomdatabaseexample.models.Contacts;

@Database(entities = {Contacts.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract IContactDAO getContactDAO();
}
