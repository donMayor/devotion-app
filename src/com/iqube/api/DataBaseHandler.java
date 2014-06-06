package com.iqube.api;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.iqube.devotionapp.DailyDevotionalFragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by don_mayor on 5/15/14.
 */
public class DataBaseHandler extends SQLiteOpenHelper{

    public static int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "Catholic_Devotional_DB";
    public static String Devotion_Table_NAME = "dailyDevotion";
    public static String Bible_Reading_Table_NAME = "bible_reading";

    public static String KEY_VERSE = "verse";
    public static String KEY_CONTENT = "content";
    public static String KEY_BOOKMARK = "bookmark";
    public static String KEY_TITLE = "title";
    public static String KEY_MEMORIZE = "memorize";
    public static String KEY_MESSAGE = "message";

    //Database vaule for Catholic
    public static String KEY_ID = "id";
    public static String KEY_DATE = "date";
    public static String KEY_HEADER = "header";
    public static String KEY_MORNING_PRAYER = "morning_prayer";
    public static String KEY_INTERCESSION = "intercession";
    public static String KEY_CONCLUDING_PRAYER = "concluding_prayer";
    public static String KEY_ENTRANCE_ANTIPHON = "entrance_antiphon";
    public static String KEY_OPENING_PRAYER = "opening_prayer";
    public static String KEY_SUGGESTED_PRAYER_FOR_THE_FAITHFUL = "suggested_prayer_for_the_faithful";
    public static String KEY_PRAYER_OVER_OFFERING = "prayer_over_offering";
    public static String KEY_COMMUNION_ANTIPHON = "communion_antiphon";
    public static String KEY_PRAYER_AFTER_COMMUNION = "prayer_after_communion";
    public static String KEY_MEDITATION_OF_THE_DAY = "meditation_of_the_day";
    public static String KEY_MIDDAY_PRAYER = "midday_prayer";
    public static String KEY_EVENING_PRAYER_II = "evening_prayer_ii";
    public static String KEY_INTERCESSION_II = "intercession_ii";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void Upgrade(){
        onUpgrade(this.getWritableDatabase(), 1,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //String Query_CreateBibleReading = CreateBibleDB(Bible_Reading_Table_NAME, KEY_ID, KEY_DATE, KEY_VERSE, KEY_CONTENT, KEY_BOOKMARK);
        String Query_CreateDevotion = CreateDevotionDB(Devotion_Table_NAME,KEY_ID, KEY_DATE, KEY_HEADER, KEY_MORNING_PRAYER,
                                        KEY_INTERCESSION, KEY_CONCLUDING_PRAYER, KEY_ENTRANCE_ANTIPHON, KEY_OPENING_PRAYER,
                                   KEY_SUGGESTED_PRAYER_FOR_THE_FAITHFUL, KEY_PRAYER_OVER_OFFERING, KEY_COMMUNION_ANTIPHON,
                                    KEY_PRAYER_AFTER_COMMUNION, KEY_MEDITATION_OF_THE_DAY, KEY_MIDDAY_PRAYER, KEY_EVENING_PRAYER_II,
                                        KEY_INTERCESSION_II, KEY_BOOKMARK);

        db.execSQL(Query_CreateDevotion);
        //db.execSQL(Query_CreateBibleReading);
        Log.d("OnCreate", "See if it works");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Devotion_Table_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Bible_Reading_Table_NAME);
        Log.d("checking", "Anything here");
        onCreate(db);
    }

   public String CreateDevotionDB(String Table_NAME, String Key_ID, String Key_DATE, String KEY_HEADER
                                   ,String KEY_MORNING_PRAYER, String KEY_INTERCESSION, String KEY_CONCLUDING_PRAYER, String KEY_ENTRANCE_ANTIPHON
                                   , String KEY_OPENING_PRAYER, String KEY_SUGGESTED_PRAYER_FOR_THE_FAITHFUL,
                                  String KEY_PRAYER_OVER_OFFERING, String KEY_COMMUNION_ANTIPHON, String KEY_PRAYER_AFTER_COMMUNION,
                                  String KEY_MEDITATION_OF_THE_DAY, String KEY_MIDDAY_PRAYER, String KEY_EVENING_PRAYER_II, String KEY_INTERCESSION_II, String KEY_BOOKMARK){
        String Query_Create= "CREATE TABLE "+ Table_NAME + "("+Key_ID + " INTEGER PRIMARY KEY," + Key_DATE + " TEXT," +
                                KEY_HEADER + " TEXT," + KEY_MORNING_PRAYER+ " TEXT," + KEY_INTERCESSION + " TEXT, " + KEY_CONCLUDING_PRAYER+ " TEXT,"
                                + KEY_ENTRANCE_ANTIPHON+ " TEXT,"+ KEY_OPENING_PRAYER+ " TEXT," + KEY_SUGGESTED_PRAYER_FOR_THE_FAITHFUL+ " TEXT,"
                                    + KEY_PRAYER_OVER_OFFERING+ " TEXT,"+ KEY_COMMUNION_ANTIPHON+ " TEXT," + KEY_PRAYER_AFTER_COMMUNION+ " TEXT,"
                                + KEY_MEDITATION_OF_THE_DAY+ " TEXT,"+ KEY_MIDDAY_PRAYER+ " TEXT,"+ KEY_EVENING_PRAYER_II+ " TEXT,"
                                + KEY_INTERCESSION_II+ " TEXT,"+ KEY_BOOKMARK + " INTEGER" +")";
       return Query_Create;
   }
    public String CreateBibleDB(String Table_NAME, String Key_ID, String Key_DATE
            ,String Key_VERSE, String Key_CONTENT, String Key_BOOKMARK){
        String Query_Create= "CREATE TABLE "+ Table_NAME + "("+Key_ID + " INTEGER PRIMARY KEY," + Key_DATE + " TEXT,"
                                + Key_VERSE + " TEXT," + Key_CONTENT + " TEXT,"
                                    + Key_BOOKMARK + " INTEGER" +")";

        return Query_Create;

    }

    public void InsertIntoDB(ArrayList<HashMap<String, String>> key_value, boolean is_devotion){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Devotion_Table_NAME, null);

        for(int i=0; i<cursor.getColumnCount(); i++){
            System.out.println(cursor.getColumnName(i));
        }
        ContentValues values = new ContentValues();
//        if(is_devotion){
            for(int k = 0; k < key_value.size(); k++){
                HashMap<String, String> hashMap_key_values = key_value.get(k);
                Log.d("hashMapGetting", hashMap_key_values.get(KEY_DATE));

                values.put(KEY_DATE, hashMap_key_values.get(KEY_DATE));
                values.put(KEY_HEADER, hashMap_key_values.get(KEY_HEADER));
                values.put(KEY_MORNING_PRAYER, hashMap_key_values.get(KEY_MORNING_PRAYER));
                values.put(KEY_INTERCESSION, hashMap_key_values.get(KEY_INTERCESSION));
                values.put(KEY_CONCLUDING_PRAYER, hashMap_key_values.get(KEY_CONCLUDING_PRAYER));
                values.put(KEY_OPENING_PRAYER, hashMap_key_values.get(KEY_OPENING_PRAYER));
                values.put(KEY_SUGGESTED_PRAYER_FOR_THE_FAITHFUL, hashMap_key_values.get(KEY_SUGGESTED_PRAYER_FOR_THE_FAITHFUL));
                values.put(KEY_PRAYER_OVER_OFFERING, hashMap_key_values.get(KEY_PRAYER_OVER_OFFERING));
                values.put(KEY_COMMUNION_ANTIPHON, hashMap_key_values.get(KEY_COMMUNION_ANTIPHON));
                values.put(KEY_PRAYER_AFTER_COMMUNION, hashMap_key_values.get(KEY_PRAYER_AFTER_COMMUNION));
                values.put(KEY_MEDITATION_OF_THE_DAY, hashMap_key_values.get(KEY_MEDITATION_OF_THE_DAY));
                values.put(KEY_MIDDAY_PRAYER, hashMap_key_values.get(KEY_MIDDAY_PRAYER));
                values.put(KEY_EVENING_PRAYER_II, hashMap_key_values.get(KEY_EVENING_PRAYER_II));
                values.put(KEY_INTERCESSION_II, hashMap_key_values.get(KEY_INTERCESSION_II));
                values.put(KEY_ENTRANCE_ANTIPHON, hashMap_key_values.get(KEY_ENTRANCE_ANTIPHON));
                values.put(KEY_BOOKMARK, hashMap_key_values.get(KEY_BOOKMARK));


                db.insert(Devotion_Table_NAME, null, values);
                Log.d("inserted", "done");
            }
//        }
//        else{
//            for(int k = 0; k < key_value.size(); k++){
//                HashMap<String, String> hashMap_key_values = key_value.get(k);
//
//                values.put(KEY_DATE, hashMap_key_values.get(KEY_DATE));
//                values.put(KEY_VERSE, hashMap_key_values.get(KEY_VERSE));
//                values.put(KEY_CONTENT, hashMap_key_values.get(KEY_CONTENT));
//                values.put(KEY_BOOKMARK, hashMap_key_values.get(KEY_BOOKMARK));
//
//                db.insert(Bible_Reading_Table_NAME, null, values);
//            }
//        }

    }

    public Cursor getSelectedRecord(String Table_Name, int Table_COLUMN_Index, String[] Table_COLUMN, String[] Selection_Arg){
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor curs = db.rawQuery("SELECT * FROM "+Table_Name+" WHERE ")
        Cursor cursor = db.query(Table_Name, Table_COLUMN, Table_COLUMN[Table_COLUMN_Index]+ "=?", Selection_Arg, null, null, Table_COLUMN[0] );
        return cursor;
    }
    public ArrayList<HashMap<String, String>> getFilteredRecord(String Table_Name, int Table_COLUMN_Index, String[] Table_COLUMN, String[] Selection_Arg) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Table_Name, Table_COLUMN, Table_COLUMN[Table_COLUMN_Index]+ "=?", Selection_Arg, null, null, Table_COLUMN[0] );
        ArrayList<HashMap<String, String>> All_Records= new ArrayList<HashMap<String, String>>();
        HashMap<String, String> Each_Record = new HashMap<String, String>();
        if(cursor!=null){
            cursor.moveToFirst();

            while(!cursor.isAfterLast()){
                Each_Record = new HashMap<String, String>();
                for(int k=0; k<cursor.getColumnCount(); k++){
                    Each_Record.put(cursor.getColumnName(k), cursor.getString(k));
                }
                All_Records.add(Each_Record);
                cursor.moveToNext();

            }
        }
        else{
            Log.d("nothing", "nothing");
        }

        return All_Records;
    }

    public ArrayList<HashMap<String, String>> getAllRecords(String Table_Name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Table_Name, null);
        ArrayList<HashMap<String, String>> All_Records= new ArrayList<HashMap<String, String>>();
        HashMap<String, String> Each_Record = new HashMap<String, String>();
        if(cursor!=null){
            cursor.moveToFirst();

            while(!cursor.isAfterLast()){
                Each_Record = new HashMap<String, String>();
                for(int k=0; k<cursor.getColumnCount(); k++){
                    Log.d("row count", "row affected"+cursor.getCount());
                    Each_Record.put(cursor.getColumnName(k), cursor.getString(k));
                }
                All_Records.add(Each_Record);
                cursor.moveToNext();

            }
        }

        return All_Records;
    }

    public int updateRecord(String Table_Name, int Value, int Key_ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BOOKMARK, Value);

        return db.update(Table_Name, values, KEY_ID + " = ?",
                new String[] {String.valueOf(Key_ID)});
    }


}
