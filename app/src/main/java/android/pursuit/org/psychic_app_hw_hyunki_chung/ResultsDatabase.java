package android.pursuit.org.psychic_app_hw_hyunki_chung;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.pursuit.org.psychic_app_hw_hyunki_chung.model.Image;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ResultsDatabase extends SQLiteOpenHelper {
    // Objects up top, Strings, then primitives at the bottom
    private static ResultsDatabase resultsDatabaseInstance;
    private static final String TABLE_RESULTS = "Results";
    private static final String TABLE_IMAGES = "Images";
    private static final String DATATBASE_NAME = "results.db";
    private static final int SCHEMA_VERSION = 1;
    private double attempts;
    private double correct;

    //nice touch with the synchronized keyword.
    static synchronized ResultsDatabase getInstance(Context context) {
        if (resultsDatabaseInstance == null) {
            resultsDatabaseInstance = new ResultsDatabase(context.getApplicationContext());
        }
        return resultsDatabaseInstance;
    }

    ResultsDatabase(@Nullable Context context) {
        super(context, DATATBASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_RESULTS +
                        " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "num_attempts INTEGER, num_correct INTEGER);");
        db.execSQL(
                "CREATE TABLE " + TABLE_IMAGES +
                        " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "image_type TEXT, image_url TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    void updateResults(boolean isCorrect) {
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT num_attempts,num_correct" + " FROM " + TABLE_RESULTS + ";", null);
        if (cursor.moveToFirst() && cursor.getCount() > 0) {
                ContentValues values = new ContentValues();
                values.put("num_attempts", cursor.getDouble(cursor.getColumnIndex("num_attempts")) + 1);

                if (isCorrect) {
                    values.put("num_correct", cursor.getDouble(cursor.getColumnIndex("num_correct")) + 1);
                }
                getWritableDatabase().update(TABLE_RESULTS, values, null, null);

        } else {

            if (isCorrect) {
                getWritableDatabase().execSQL("INSERT INTO " + TABLE_RESULTS +
                        "(num_attempts, num_correct) VALUES(1,1);");
            } else {
                getWritableDatabase().execSQL("INSERT INTO " + TABLE_RESULTS +
                        "(num_attempts, num_correct) VALUES(1,0);");
            }
        }

        cursor.close();
    }

    int getResults() {
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT num_attempts,num_correct" + " FROM " + TABLE_RESULTS + ";", null);
        //no need for the null check. you always have a cursor object when you call getReadableDatabase, what you might have is an empty table, in that case you can still close the cursor.
            if (cursor.moveToFirst()) {
                do {
                    attempts = cursor.getDouble(cursor.getColumnIndex("num_attempts"));
                    correct = cursor.getDouble(cursor.getColumnIndex("num_correct"));
                } while (cursor.moveToNext());
            }
        cursor.close();
        return (int) Math.floor((correct / attempts) * 100);
    }

    void addImage(String type, String imageURL) {
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_IMAGES + " WHERE image_type = '" + type +
                        "' AND image_url = '" + imageURL + "';", null);

        if(cursor.getCount() == 0) {
        getWritableDatabase().execSQL("INSERT INTO " + TABLE_IMAGES +
                "(image_type, image_url) VALUES('" + type + "','" + imageURL + "');");
        }

        cursor.close();
    }

    List<Image> getImageList(String type) {
        Log.d("query getimagelist database", type);
        List<Image> imageList = new ArrayList<>();
        Image image;
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_IMAGES + ";", null);


            if (cursor.moveToFirst()) {

                do {
                    Log.d("query looping type in do loop",cursor.getString(cursor.getColumnIndex("image_type")));
                    if (cursor.getString(cursor.getColumnIndex("image_type")).equals(type)) {
                        Log.d("getimagelist",cursor.getString(cursor.getColumnIndex("image_type")));
                        image = new Image(

                                type,
                                cursor.getString(cursor.getColumnIndex("image_url")));

                        imageList.add(image);
                    }
                } while (cursor.moveToNext());
            }
        cursor.close();

        return imageList;
    }

    void clearResults(){
            SQLiteDatabase db = this.getWritableDatabase();
             db.delete(TABLE_RESULTS,null,null);
            db.execSQL("delete from "+ TABLE_RESULTS);
            db.close();
    }
}
