package ch.ost.rj.mge.budgeit.services;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ch.ost.rj.mge.budgeit.db.BudgeItDatabase;
import ch.ost.rj.mge.budgeit.model.Category;
import ch.ost.rj.mge.budgeit.model.CategoryDao;
import ch.ost.rj.mge.budgeit.model.Item;
import ch.ost.rj.mge.budgeit.model.ItemDao;

public class ModelServices {

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

    public static List<Item> getNotDeleteItems(Context context, String category) {
        BudgeItDatabase db = BudgeItDatabase.getInstance(context);
        ItemDao itemDao = db.itemDao();
        if(category.equals("All")) {
            return itemDao.getNotDeletedItems();
        } else {
            return itemDao.getNotDeletedItemsByCategory(category);
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

    public static float getRestBudget(Context context, String category) {
        BudgeItDatabase db = BudgeItDatabase.getInstance(context);
        ItemDao itemDao = db.itemDao();
        if(category.equals("All")) {
            return itemDao.getRestBudgetAll();
        } else {
            return itemDao.getRestBudgetByCategory(category);
        }

    }
}
