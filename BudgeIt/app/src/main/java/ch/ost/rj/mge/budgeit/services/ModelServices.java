package ch.ost.rj.mge.budgeit.services;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ch.ost.rj.mge.budgeit.db.BudgeItDatabase;
import ch.ost.rj.mge.budgeit.model.Category;
import ch.ost.rj.mge.budgeit.model.CategoryDao;
import ch.ost.rj.mge.budgeit.model.Item;
import ch.ost.rj.mge.budgeit.model.ItemDao;

public class ModelServices {

    public static Item getItemById(Context context, int id) {
        BudgeItDatabase db = BudgeItDatabase.getInstance(context);
        ItemDao itemDao = db.itemDao();
        return itemDao.getItemById(id);
    }

    public static List<Item> getAllItems(Context context) {
        BudgeItDatabase db = BudgeItDatabase.getInstance(context);
        ItemDao itemDao = db.itemDao();
        return itemDao.getItems();
    }

    public static List<Item> getItemsByCategory(Context context, String categoryName) {
        BudgeItDatabase db = BudgeItDatabase.getInstance(context);
        ItemDao itemDao = db.itemDao();
        return itemDao.getItemsByCategory(categoryName);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static List<Item> getNotDeleteItems(Context context, String category) {

        LocalDate startdate = getDate(context);

        BudgeItDatabase db = BudgeItDatabase.getInstance(context);
        ItemDao itemDao = db.itemDao();
        if(category.equals("All Categories")) {
            return itemDao.getNotDeletedItems(startdate);
        } else {
            return itemDao.getNotDeletedItemsByCategory(category, startdate);
        }
    }

    public static List<String> getCategoryNamesForSpinner(Context context) {
        BudgeItDatabase db = BudgeItDatabase.getInstance(context);
        CategoryDao categoryDao = db.categoryDao();
        ArrayList<String> categories = new ArrayList<>();
        for(Category cat: categoryDao.getCategories()) {
            categories.add(cat.getName());
        }
        return categories;
    }

    public static void updateItem(Context context, Item item) {
        BudgeItDatabase db = BudgeItDatabase.getInstance(context);
        ItemDao itemDao = db.itemDao();
        itemDao.updateItem(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static LocalDate getDate(Context context) {
        PreferencesService service = new PreferencesService();
        String interval = service.getIntervalSettingAsString(context);

        switch(interval) {
            case "weekly":
                LocalDate date = LocalDate.now();
                return date.with(DayOfWeek.MONDAY);
            case "monthly":
                return LocalDate.now().withDayOfMonth(1);
            default:
                return LocalDate.now();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static float getRestBudget(Context context, String category) {

        LocalDate startdate = getDate(context);

        BudgeItDatabase db = BudgeItDatabase.getInstance(context);
        ItemDao itemDao = db.itemDao();
        if(category.equals("All Categories")) {
            return itemDao.getRestBudgetAll(startdate);
        } else {
            return itemDao.getRestBudgetByCategory(category, startdate);
        }

    }
}
