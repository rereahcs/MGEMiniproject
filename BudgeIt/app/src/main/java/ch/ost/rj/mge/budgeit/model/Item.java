package ch.ost.rj.mge.budgeit.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name ="amount")
    private float amount;

    @ColumnInfo(name ="category")
    private String category;

    @ColumnInfo(name ="description")
    private String description;

    @ColumnInfo(name ="date")
    private LocalDate date;

    @ColumnInfo(name ="is_deleted")
    private boolean isDeleted;

    private Item(){ }

    public Item(String category, String description, float amount, LocalDate date) {
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.isDeleted = false;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public float getAmount() { return amount; }

    public void setAmount(float amount) { this.amount = amount; }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isDeleted() { return isDeleted; }

    public void setDeleted(boolean deleted) { isDeleted = deleted; }
}
