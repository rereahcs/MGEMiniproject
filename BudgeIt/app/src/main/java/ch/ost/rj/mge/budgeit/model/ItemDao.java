package ch.ost.rj.mge.budgeit.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM Item WHERE id=:id")
    Item getItemById(int id);

    @Query("SELECT * FROM Item")
    List<Item> getItems();

    @Query("SELECT * FROM Item WHERE is_deleted=0 AND date >= :startDate")
    List<Item> getNotDeletedItems(LocalDate startDate);

    @Query("SELECT * FROM Item WHERE category = :selectedCategory AND date >= :startDate AND is_deleted=0")
    List<Item> getNotDeletedItemsByCategory(String selectedCategory, LocalDate startDate);

    @Query("SELECT * FROM Item WHERE category = :selectedCategory")
    List<Item> getItemsByCategory(String selectedCategory);

    @Query("SELECT SUM(amount) FROM Item WHERE is_deleted = 0 AND date >= :startDate")
    float getRestBudgetAll(LocalDate startDate);

    @Query("SELECT SUM(amount) FROM Item WHERE category = :selectedCategory AND date >= :startDate AND is_deleted=0")
    float getRestBudgetByCategory(String selectedCategory, LocalDate startDate);

    @Insert
    void insert(Item item);

    @Update
    void updateItem(Item item);

}
