package ch.ost.rj.mge.budgeit.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM Item")
    List<Item> getItems();

    @Query("SELECT * FROM Item WHERE is_deleted=0")
    List<Item> getNotDeletedItems();

    @Query("SELECT * FROM Item WHERE category = :selectedCategory")
    List<Item> getItemsByCategory(String selectedCategory);

    @Insert
    void insert(Item item);

    @Update
    void updateItem(Item item);

}
