package com.example.taskapp.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskapp.models.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query(value = "SELECT * FROM task")
    List<Task> getAll();


    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAllLive();
//    варианты читения

    @Insert
    void insert(Task task);

    @Delete
    void delete (Task task);

    @Update
    void update(Task task);
}
