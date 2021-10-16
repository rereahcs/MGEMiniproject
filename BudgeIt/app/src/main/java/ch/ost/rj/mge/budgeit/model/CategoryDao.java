package ch.ost.rj.mge.budgeit.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM Category")
    List<Category> getCategories();

    @Insert
    void insert(Category category);

    @Delete
    void delete(Category category);
}
