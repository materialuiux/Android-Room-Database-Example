package com.materialuiux.roomdatabaseexample.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.materialuiux.roomdatabaseexample.models.Contacts;

import java.util.List;

@Dao
public interface IContactDAO {

    /**
     * Get all Contacts in database ordered by ASC
     *
     * @return a list with all Contacts
     */
    @Query("SELECT * FROM Contacts")
    List<Contacts> getAllContacts();

    /**
     * Get Contacts in database ordered by id
     *
     * @return a Contacts
     */
    @Query("SELECT * FROM Contacts WHERE uid = :uid")
    Contacts getItemById(int uid);

    /**
     * Function to insert a contacts in room database
     *
     * @param contacts to be inserted in database
     */
    @Insert
    void insertContacts(Contacts contacts);

    /**
     * Function to Update an contacts in room database
     *
     * @param contacts the object to be Update
     */
    @Update
    void updateContacts(Contacts contacts);

    /**
     * Function to delete an contacts in room database
     *
     * @param contacts the object to be deleted
     */
    @Delete
    void deleteContacts(Contacts contacts);

}
