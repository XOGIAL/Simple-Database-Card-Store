package com.example.tm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String db_name_u = "UsersDB";
    private static final String db_name_p = "ProductsDB";
    private static final String db_name_t = "TransactionsDB";
    private static final int db_version = 1;

    // table user
    public static final String table_user = "Users";
    public static final String field_user_id = "id";
    public static final String field_user_name = "username";
    public static final String field_user_password = "password";
    public static final String field_user_phone = "phone";
    public static final String field_user_gender = "gender";
    public static final String field_user_wallet = "wallet";
    public static final String field_user_dob = "dob";


    private static final String create_user = "CREATE TABLE IF NOT EXISTS " + table_user + " (" +
            field_user_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            field_user_name + " TEXT," +
            field_user_password + " TEXT," +
            field_user_phone + " INTEGER," +
            field_user_gender + " TEXT," +
            field_user_wallet + " INTEGER," +
            field_user_dob + " TEXT)";

    // table product
    public static final String table_product = "Products";
    public static final String field_product_id = "id";
    public static final String field_product_name = "name";
    public static final String field_product_minPlayer = "minPlayer";
    public static final String field_product_maxPlayer = "maxPlayer";
    public static final String field_product_price = "price";
    public static final String field_product_latitude = "latitude";
    public static final String field_product_longitude = "longitude";

    private static final String create_product = "CREATE TABLE IF NOT EXISTS " + table_product + " (" +
            field_product_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            field_product_name + " TEXT," +
            field_product_minPlayer + " INTEGER," +
            field_product_maxPlayer + " INTEGER," +
            field_product_price + " INTEGER," +
            field_product_latitude + " DOUBLE," +
            field_product_longitude + " DOUBLE)";

    // table transaction
    public static final String table_transaction = "Transactions";
    public static final String field_transaction_id = "id";
    public static final String field_transaction_userId = "userId";
    public static final String field_transaction_productId = "productId";
    public static final String field_transaction_date = "date";

    private static final String create_transaction = "CREATE TABLE IF NOT EXISTS " + table_transaction + " (" +
            field_transaction_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            field_transaction_userId + " INTEGER," +
            field_transaction_productId + " INTEGER," +
            field_transaction_date + " TEXT," +
            "FOREIGN KEY (" + field_transaction_userId + ") REFERENCES " + table_user + "(" + field_user_id + ")," +
            "FOREIGN KEY (" + field_transaction_productId + ") REFERENCES " + table_product + "(" + field_product_id + "))";

    public DBHelper(@Nullable Context context) {
        super(context, "Kuufdatabase", null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_user);
        sqLiteDatabase.execSQL(create_product);
        sqLiteDatabase.execSQL(create_transaction);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
