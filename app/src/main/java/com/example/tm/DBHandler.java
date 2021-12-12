package com.example.tm;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHandler {
    private DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    public DBHandler(Context context){
        dbHelper = new DBHelper(context);
    }

    public Integer usernameFinder(String username){
        sqLiteDatabase = dbHelper.getReadableDatabase();

        String selection = "username = ?";
        String[] selectionArgs = {username};

        Cursor cursor = sqLiteDatabase.query(DBHelper.table_user, null, selection, selectionArgs, null, null, null, null);

        if(cursor.moveToFirst()){
            return cursor.getInt(cursor.getColumnIndex(DBHelper.field_user_id));
        }
        return null;
    }

    public boolean passwordChecker(int id, String password){
        sqLiteDatabase = dbHelper.getReadableDatabase();

        String selection = "id = ?";
        String[] selectionArgs = {"" + id};

        Cursor cursor = sqLiteDatabase.query(DBHelper.table_user, null, selection, selectionArgs, null, null, null, null);

//        return password.equals(cursor.getString(cursor.getColumnIndex(DBHelper.field_user_password)));
        cursor.moveToFirst();

        if(password.equals(cursor.getString(cursor.getColumnIndex(DBHelper.field_user_password))))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void insertUser(User user)
    {
        sqLiteDatabase = dbHelper.getReadableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.field_user_name,user.getUsername());
        contentValues.put(DBHelper.field_user_dob,user.getDob());
        contentValues.put(DBHelper.field_user_gender,user.getGender());
        contentValues.put(DBHelper.field_user_password,user.getPassword());
        contentValues.put(DBHelper.field_user_phone,user.getPhone_number());
        contentValues.put(DBHelper.field_user_wallet,user.getWallet());

        sqLiteDatabase.insert("Users",null,contentValues);
    }

    public Cursor getuserdata(int id)
    {
        Cursor cursor;

        sqLiteDatabase = dbHelper.getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery("select username,wallet from Users where id ==" + id+"",null);

        return cursor;
    }



    public User getUser(int id){
        sqLiteDatabase = dbHelper.getReadableDatabase();

        String selection = "id = ?";
        String[] selectionArgs = {"" + id};



        Cursor cursor = sqLiteDatabase.query(DBHelper.table_user, null, selection, selectionArgs, null, null, null);

        User user = new User();

        if(cursor.moveToFirst()){

            user.setUsername(cursor.getString(cursor.getColumnIndex(DBHelper.field_user_name)));
            user.setGender(cursor.getString(cursor.getColumnIndex(DBHelper.field_user_gender)));
            user.setWallet(cursor.getInt(cursor.getColumnIndex(DBHelper.field_user_wallet)));
            user.setPhone_number(cursor.getString(cursor.getColumnIndex(DBHelper.field_user_phone)));
            user.setDob(cursor.getString(cursor.getColumnIndex(DBHelper.field_user_dob)));
        }

        return user;
    }

    public void updateWallet(int id, int value){
        sqLiteDatabase = dbHelper.getWritableDatabase();

        String selection = "id = ?";
        String[] selectionArgs = {"" + id};

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.field_user_wallet, value);

        sqLiteDatabase.update(DBHelper.table_user, contentValues, selection, selectionArgs);
    }

    public void insertTransaction(Transaction transaction, int productId, int userId){
        sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.field_transaction_productId,productId);
        contentValues.put(DBHelper.field_transaction_userId, userId);
        contentValues.put(DBHelper.field_transaction_date, transaction.getTransaction_date());

        sqLiteDatabase.insert(DBHelper.table_transaction, null, contentValues);
    }

    public ArrayList<Transaction> getTransaction(int userId){
        sqLiteDatabase = dbHelper.getReadableDatabase();

        String selection = "userId = ?";
        String[] selectionArgs = {"" + userId};

        Cursor cursor = sqLiteDatabase.query(DBHelper.table_transaction, null, selection, selectionArgs, null, null, null);

        ArrayList<Transaction> transactions = new ArrayList<Transaction>();




        while (cursor.moveToNext()){
            Transaction transaction = new Transaction();
            Product product = new Product();
            product = getProduct(cursor.getInt(cursor.getColumnIndex(DBHelper.field_transaction_productId)));
            transaction.setProduct_name(product.getName());
            transaction.setProduct_price(product.getPrice());
            transaction.setTransaction_date(cursor.getString(cursor.getColumnIndex((DBHelper.field_transaction_date))));
            transaction.setId(cursor.getInt(cursor.getColumnIndex((DBHelper.field_transaction_id))));
            transactions.add(transaction);
        }

        return transactions;
    }

    public void deleteTransaction(int id){
        sqLiteDatabase = dbHelper.getWritableDatabase();

        String selection = "id = ?";
        String[] selectionArgs = {"" + id};

        Cursor cursor = sqLiteDatabase.query(DBHelper.table_transaction, null, selection, selectionArgs, null, null, null);

        sqLiteDatabase.delete(DBHelper.table_transaction, selection, selectionArgs);
    }

    public void insertProduct(Product product){
        sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.field_product_id, product.getId());
        contentValues.put(DBHelper.field_product_name, product.getName());
        contentValues.put(DBHelper.field_product_price, product.getPrice());
        contentValues.put(DBHelper.field_product_minPlayer, product.getMin_player());
        contentValues.put(DBHelper.field_product_maxPlayer, product.getMax_player());
        contentValues.put(DBHelper.field_product_longitude, product.getLongitude());
        contentValues.put(DBHelper.field_product_latitude, product.getLatitude());

        sqLiteDatabase.insert(DBHelper.table_product, null, contentValues);
    }

    public Product getProduct(int id){
        sqLiteDatabase = dbHelper.getReadableDatabase();

        String selection = "id = ?";
        String[] selectionArgs = {"" + id};

        Cursor cursor = sqLiteDatabase.query(DBHelper.table_product, null, selection, selectionArgs, null, null, null);

        Product product = new Product();

        if(cursor.moveToFirst()){
            product.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.field_product_id)));
            product.setName(cursor.getString(cursor.getColumnIndex(DBHelper.field_product_name)));
            product.setMax_player(cursor.getInt(cursor.getColumnIndex(DBHelper.field_product_maxPlayer)));
            product.setMin_player(cursor.getInt(cursor.getColumnIndex(DBHelper.field_product_minPlayer)));
            product.setPrice(cursor.getInt(cursor.getColumnIndex(DBHelper.field_product_price)));
        }

        return product;
    }

    public ArrayList<Product> getAllProducts(){
        sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(DBHelper.table_product, null, null, null, null, null, null);

        ArrayList<Product> products = new ArrayList<Product>();


        while (cursor.moveToNext()){
            Product product = new Product();
            product.setId(cursor.getInt(cursor.getColumnIndex((DBHelper.field_product_id))));
            product.setName(cursor.getString(cursor.getColumnIndex((DBHelper.field_product_name))));
            product.setPrice(cursor.getInt(cursor.getColumnIndex((DBHelper.field_product_price))));
            product.setMin_player(cursor.getInt(cursor.getColumnIndex((DBHelper.field_product_minPlayer))));
            product.setMax_player(cursor.getInt(cursor.getColumnIndex((DBHelper.field_product_maxPlayer))));
            product.setLongitude(cursor.getInt(cursor.getColumnIndex((DBHelper.field_product_longitude))));
            product.setLatitude(cursor.getInt(cursor.getColumnIndex((DBHelper.field_product_latitude))));
            products.add(product);
        }

        if(products.isEmpty()){
            return null;
        }
        return products;

    }

}