package com.example.room_database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.appcompat.view.menu.ListMenuItemView;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MainDao {

    //insert query
    @Insert(onConflict = REPLACE)
    void insert(MainData mainData);

    //delete query

    @Delete
    void delete(MainData mainData);

    //delete all query
    @Delete
    void reset(List<MainData> mainData);

    //update query
    @Query("UPDATE table_name SET text =:sTEXT WHERE ID =:sID")
    void update(int sID, String sTEXT);

    //get ll data query
    @Query("SELECT * FROM table_name")
    List<MainData> getALL();
}
