package com.weightmonitorid.android.weightmonitor.helper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.weightmonitorid.android.weightmonitor.model.Weight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 09/10/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG = "DatabaseHelper";

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "BudidayaLeleDB";

    //table names
    public static final String TABLE_STATISTIC_1 = "statistic1";
    public static final String TABLE_STATISTIC_2 = "statistic2";
    public static final String TABLE_STATISTIC_3 = "statistic3";
    public static final String TABLE_STATISTIC_4 = "statistic4";
    public static final String TABLE_STATISTIC_5 = "statistic5";

    //common column names
    public static final String KEY_ID = "id";

    //Statistic Table - column names
    public static final String KEY_DATE = "date";
    public static final String KEY_WEIGHT = "data";


    //Table Create Statements
    private static final String CREATE_TABLE_STATISTIC_1 = "CREATE TABLE "
            + TABLE_STATISTIC_1 + "(" + KEY_ID + " INTEGER PRIMARY KEY NOT NULL, "
            + KEY_DATE + " TEXT, " + KEY_WEIGHT + " TEXT" + ")";

    private static final String CREATE_TABLE_STATISTIC_2 = "CREATE TABLE "
            + TABLE_STATISTIC_2 + "(" + KEY_ID + " INTEGER PRIMARY KEY NOT NULL, "
            + KEY_DATE + " TEXT, " + KEY_WEIGHT + " TEXT" + ")";

    private static final String CREATE_TABLE_STATISTIC_3 = "CREATE TABLE "
            + TABLE_STATISTIC_3 + "(" + KEY_ID + " INTEGER PRIMARY KEY NOT NULL, "
            + KEY_DATE + " TEXT, " + KEY_WEIGHT + " TEXT" + ")";

    private static final String CREATE_TABLE_STATISTIC_4 = "CREATE TABLE "
            + TABLE_STATISTIC_4 + "(" + KEY_ID + " INTEGER PRIMARY KEY NOT NULL, "
            + KEY_DATE + " TEXT, " + KEY_WEIGHT + " TEXT" + ")";

    private static final String CREATE_TABLE_STATISTIC_5 = "CREATE TABLE "
            + TABLE_STATISTIC_5 + "(" + KEY_ID + " INTEGER PRIMARY KEY NOT NULL, "
            + KEY_DATE + " TEXT, " + KEY_WEIGHT + " TEXT" + ")";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //creating required tables
        db.execSQL(CREATE_TABLE_STATISTIC_1);
        db.execSQL(CREATE_TABLE_STATISTIC_2);
        db.execSQL(CREATE_TABLE_STATISTIC_3);
        db.execSQL(CREATE_TABLE_STATISTIC_4);
        db.execSQL(CREATE_TABLE_STATISTIC_5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATISTIC_1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATISTIC_2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATISTIC_3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATISTIC_4);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATISTIC_5);

        //create new tables
        onCreate(db);
    }




    public long insertWeight1(List<Weight> weights){

        SQLiteDatabase db = this.getWritableDatabase();
        long weight_id = 1;

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            for (Weight weight : weights) {
//                ContentValues values = new ContentValues();
                values.put(KEY_DATE, weight.getDate());
                values.put(KEY_WEIGHT, weight.getWeight());
                weight_id = db.insert(TABLE_STATISTIC_1, null, values);
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
            return weight_id;
        }
    }
    public long insertWeight2(List<Weight> weights){

        SQLiteDatabase db = this.getWritableDatabase();
        long weight_id = 1;

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            for (Weight weight : weights) {
//                ContentValues values = new ContentValues();
                values.put(KEY_DATE, weight.getDate());
                values.put(KEY_WEIGHT, weight.getWeight());
                weight_id = db.insert(TABLE_STATISTIC_2, null, values);
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
            return weight_id;
        }
    }
    public long insertWeight3(List<Weight> weights){

        SQLiteDatabase db = this.getWritableDatabase();
        long weight_id = 1;

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            for (Weight weight : weights) {
//                ContentValues values = new ContentValues();
                values.put(KEY_DATE, weight.getDate());
                values.put(KEY_WEIGHT, weight.getWeight());
                weight_id = db.insert(TABLE_STATISTIC_3, null, values);
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
            return weight_id;
        }
    }
    public long insertWeight4(List<Weight> weights){

        SQLiteDatabase db = this.getWritableDatabase();
        long weight_id = 1;

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            for (Weight weight : weights) {
//                ContentValues values = new ContentValues();
                values.put(KEY_DATE, weight.getDate());
                values.put(KEY_WEIGHT, weight.getWeight());
                weight_id = db.insert(TABLE_STATISTIC_4, null, values);
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
            return weight_id;
        }
    }
    public long insertWeight5(List<Weight> weights){

        SQLiteDatabase db = this.getWritableDatabase();
        long weight_id = 1;

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            for (Weight weight : weights) {
//                ContentValues values = new ContentValues();
                values.put(KEY_DATE, weight.getDate());
                values.put(KEY_WEIGHT, weight.getWeight());
                weight_id = db.insert(TABLE_STATISTIC_5, null, values);
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
            return weight_id;
        }
    }

//    public long createFavorite(int foodId){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_FOOD_FK, foodId);
//
//        //insert row
//        long favorite_id = db.insert(TABLE_FAVORITE, null, values);
//
//        return favorite_id;
//    }


//    public long createPond(Pond pond, long[] progress1_id, long[] progress2_id,
//                           long[] progress3_id){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, pond.getName());
//        values.put(KEY_SEED_AMOUNT, pond.getSeed_amount());
//        values.put(KEY_DATE, pond.getDate());
//
//        //insert row
//        long pond_id = db.insert(TABLE_POND, null, values);
//
//        //insert progress_ids
//        for(long progress_id : progress1_id){
//            createPondProgress(pond_id, progress_id);
//        }
//        for(long progress_id : progress2_id){
//            createPondProgress(pond_id, progress_id);
//        }
//        for(long progress_id : progress3_id){
//            createPondProgress(pond_id, progress_id);
//        }
//
//        return pond_id;
//    }


//    public int getCategoryCount(){
//        String countQuery = "SELECT * FROM " + TABLE_CATEGORY;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//
//        int count = cursor.getCount();
//        cursor.close();
//
//        return count;
//    }


    public int getWeight1Count(){
        String countQuery = "SELECT * FROM " + TABLE_STATISTIC_1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }
    public int getWeight2Count(){
        String countQuery = "SELECT * FROM " + TABLE_STATISTIC_2;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }
    public int getWeight3Count(){
        String countQuery = "SELECT * FROM " + TABLE_STATISTIC_3;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }
    public int getWeight4Count(){
        String countQuery = "SELECT * FROM " + TABLE_STATISTIC_4;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }
    public int getWeight5Count(){
        String countQuery = "SELECT * FROM " + TABLE_STATISTIC_5;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }



    public Weight getWeight1(long weight_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_STATISTIC_1 + " WHERE "
                + KEY_ID + " = " + weight_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Weight weight = new Weight();
        weight.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        weight.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
        weight.setWeight(c.getLong(c.getColumnIndex(KEY_WEIGHT)));

        return weight;
    }
    public Weight getWeight2(long weight_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_STATISTIC_2 + " WHERE "
                + KEY_ID + " = " + weight_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Weight weight = new Weight();
        weight.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        weight.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
        weight.setWeight(c.getLong(c.getColumnIndex(KEY_WEIGHT)));

        return weight;
    }
    public Weight getWeight3(long weight_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_STATISTIC_3 + " WHERE "
                + KEY_ID + " = " + weight_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Weight weight = new Weight();
        weight.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        weight.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
        weight.setWeight(c.getLong(c.getColumnIndex(KEY_WEIGHT)));

        return weight;
    }
    public Weight getWeight4(long weight_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_STATISTIC_4 + " WHERE "
                + KEY_ID + " = " + weight_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Weight weight = new Weight();
        weight.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        weight.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
        weight.setWeight(c.getLong(c.getColumnIndex(KEY_WEIGHT)));

        return weight;
    }
    public Weight getWeight5(long weight_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_STATISTIC_5 + " WHERE "
                + KEY_ID + " = " + weight_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Weight weight = new Weight();
        weight.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        weight.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
        weight.setWeight(c.getLong(c.getColumnIndex(KEY_WEIGHT)));

        return weight;
    }


    public Weight getHighestWeight1(){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT MAX("+KEY_WEIGHT+") FROM " + TABLE_STATISTIC_1;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

//        db.query(DATABASE_TABLE, null, "price=(SELECT MAX(price))", null, null, null, null)

//        String[] selectionArgs = new String[]{
//                '%' + String.valueOf(foodName) + '%'
//        };

        Log.e(LOG, selectQuery);

//        String[] args = { "first string", "second@string.com" };
        Cursor c2 = db.query(TABLE_STATISTIC_1, new String [] {"MAX(data)"}, null, null, null, null, null);


        Weight weight = null;
//        if (c2 != null) {
//            if (c2.moveToFirst()) {
//                do {
        if( c2 != null && c2.moveToFirst() ){
            c2.moveToNext();
            weight = new Weight();
//                    weight.setId(c.getInt(0));
//                    weight.setDate(c.getString(1));
                    weight.setWeight(c.getLong(2));

                    //adding to information list
//                } while (c2.moveToNext());
            c2.close();
//            }
        }
        return weight;
    }
    public Weight getHighestWeight2(){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT MAX("+KEY_WEIGHT+") FROM " + TABLE_STATISTIC_2;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Weight weight = new Weight();
//        weight.setId(c.getInt(c.getColumnIndex(KEY_ID)));
//        weight.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
        weight.setWeight(c.getLong(c.getColumnIndex(KEY_WEIGHT)));

        return weight;
    }
    public Weight getHighestWeight3(){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT MAX("+KEY_WEIGHT+") FROM " + TABLE_STATISTIC_3;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Weight weight = new Weight();
//        weight.setId(c.getInt(c.getColumnIndex(KEY_ID)));
//        weight.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
        weight.setWeight(c.getLong(c.getColumnIndex(KEY_WEIGHT)));

        return weight;
    }
    public Weight getHighestWeight4(){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT MAX("+KEY_WEIGHT+") FROM " + TABLE_STATISTIC_4;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        Weight weight = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    weight = new Weight();
                    weight.setId(c.getInt(0));
                    weight.setDate(c.getString(1));
                    weight.setWeight(c.getLong(2));

                    //adding to information list
                } while (c.moveToNext());
            }
        }
        return weight;
    }
    public Weight getHighestWeight5(){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT MAX("+KEY_WEIGHT+") FROM " + TABLE_STATISTIC_5;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

            Weight weight = new Weight();
//        weight.setId(c.getInt(c.getColumnIndex(KEY_ID)));
//        weight.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
            weight.setWeight(c.getLong(c.getColumnIndex(KEY_WEIGHT)));

            return weight;
    }




    public List<Weight> getAllWeight1(){
        List<Weight> weights = new ArrayList<Weight>();
        String selectQuery = "SELECT id, date, data FROM " + TABLE_STATISTIC_1;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    Weight weight = new Weight();
                    weight.setId(c.getInt(0));
                    weight.setDate(c.getString(1));
                    weight.setWeight(c.getLong(2));

                    //adding to information list
                    weights.add(weight);
                } while (c.moveToNext());
            }
        }
        return weights;
    }
    public List<Weight> getAllWeight2(){
        List<Weight> weights = new ArrayList<Weight>();
        String selectQuery = "SELECT id, date, data FROM " + TABLE_STATISTIC_2;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    Weight weight = new Weight();
                    weight.setId(c.getInt(0));
                    weight.setDate(c.getString(1));
                    weight.setWeight(c.getLong(2));

                    //adding to information list
                    weights.add(weight);
                } while (c.moveToNext());
            }
        }
        return weights;
    }
    public List<Weight> getAllWeight3(){
        List<Weight> weights = new ArrayList<Weight>();
        String selectQuery = "SELECT id, date, data FROM " + TABLE_STATISTIC_3;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    Weight weight = new Weight();
                    weight.setId(c.getInt(0));
                    weight.setDate(c.getString(1));
                    weight.setWeight(c.getLong(2));

                    //adding to information list
                    weights.add(weight);
                } while (c.moveToNext());
            }
        }
        return weights;
    }
    public List<Weight> getAllWeight4(){
        List<Weight> weights = new ArrayList<Weight>();
        String selectQuery = "SELECT id, date, data FROM " + TABLE_STATISTIC_4;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    Weight weight = new Weight();
                    weight.setId(c.getInt(0));
                    weight.setDate(c.getString(1));
                    weight.setWeight(c.getLong(2));

                    //adding to information list
                    weights.add(weight);
                } while (c.moveToNext());
            }
        }
        return weights;
    }
    public List<Weight> getAllWeight5(){
        List<Weight> weights = new ArrayList<Weight>();
        String selectQuery = "SELECT id, date, data FROM " + TABLE_STATISTIC_5;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    Weight weight = new Weight();
                    weight.setId(c.getInt(0));
                    weight.setDate(c.getString(1));
                    weight.setWeight(c.getLong(2));

                    //adding to information list
                    weights.add(weight);
                } while (c.moveToNext());
            }
        }
        return weights;
    }





    //update weight
    public int updateWeight1(Weight weight){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, weight.getDate());
        values.put(KEY_WEIGHT, weight.getWeight());

        //updateing row
        return db.update(TABLE_STATISTIC_1, values, KEY_ID + " = ?",
                new String[]{String.valueOf(weight.getId())});
    }
    public int updateWeight2(Weight weight){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, weight.getDate());
        values.put(KEY_WEIGHT, weight.getWeight());

        //updateing row
        return db.update(TABLE_STATISTIC_2, values, KEY_ID + " = ?",
                new String[]{String.valueOf(weight.getId())});
    }
    public int updateWeight3(Weight weight){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, weight.getDate());
        values.put(KEY_WEIGHT, weight.getWeight());

        //updateing row
        return db.update(TABLE_STATISTIC_3, values, KEY_ID + " = ?",
                new String[]{String.valueOf(weight.getId())});
    }
    public int updateWeight4(Weight weight){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, weight.getDate());
        values.put(KEY_WEIGHT, weight.getWeight());

        //updateing row
        return db.update(TABLE_STATISTIC_4, values, KEY_ID + " = ?",
                new String[]{String.valueOf(weight.getId())});
    }
    public int updateWeight5(Weight weight){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, weight.getDate());
        values.put(KEY_WEIGHT, weight.getWeight());

        //updateing row
        return db.update(TABLE_STATISTIC_5, values, KEY_ID + " = ?",
                new String[]{String.valueOf(weight.getId())});
    }


    //closing database
    public void closeDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
