package ch.ost.rj.mge.budgeit.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import ch.ost.rj.mge.budgeit.model.Category;
import ch.ost.rj.mge.budgeit.model.CategoryDao;
import ch.ost.rj.mge.budgeit.model.Item;
import ch.ost.rj.mge.budgeit.model.ItemDao;

@Database(entities = {Item.class, Category.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class BudgeItDatabase extends RoomDatabase {

    private static final String dbName = "budgetItDatabase.db";
    private static volatile BudgeItDatabase instance;

    public static synchronized BudgeItDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    public BudgeItDatabase() {
    }

    private static BudgeItDatabase create(final Context context) {
        return Room.databaseBuilder(context, BudgeItDatabase.class, dbName).allowMainThreadQueries().build();
    }

    public abstract ItemDao itemDao();

    public abstract CategoryDao categoryDao();
}
