package ch.ost.rj.mge.budgeit.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM Item WHERE id=:id")
    Item getItemById(int id);

    @Query("SELECT * FROM Item")
    List<Item> getItems();

    @Query("SELECT * FROM Item WHERE is_deleted=0")
    List<Item> getNotDeletedItems();

    @Query("SELECT * FROM Item WHERE category = :selectedCategory AND is_deleted=0")
    List<Item> getNotDeletedItemsByCategory(String selectedCategory);

    @Query("SELECT * FROM Item WHERE category = :selectedCategory")
    List<Item> getItemsByCategory(String selectedCategory);

    @Query("SELECT SUM(amount) FROM Item WHERE is_deleted=0")
    float getRestBudgetAll();

    @Query("SELECT SUM(amount) FROM Item WHERE category = :selectedCategory AND is_deleted=0")
    float getRestBudgetByCategory(String selectedCategory);

    @Insert
    void insert(Item item);

    @Update
    void updateItem(Item item);

}
