package ch.ost.rj.mge.budgeit.db;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.time.LocalDate;

public class Converters {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static LocalDate dateFromTimestamp(String value) {
        return value == null ? null : LocalDate.parse(value);
    }

    @TypeConverter
    public static String dateToTimestamp(LocalDate date) {
        return date == null ? null : date.toString();
    }
}
