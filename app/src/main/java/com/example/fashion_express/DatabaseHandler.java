package com.example.fashion_express;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "FashionExpress";
    //Table Name
    private static final String TABLE_USERS = "Users";
    private static final String TABLE_PRODUCTS = "Product";
    private static final String TABLE_HISTORY = "History";
    //Column Table User
    private static final String KEY_ID_USER = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    //Column Table History
    private static final String KEY_ID_HISTORY = "id";
    private static final String KEY_ID_History_USER = "userId";
    private static final String KEY_ID_HISTORY_PRODUCT = "productId";
    private static final String KEY_DATE = "transactionDate";
    private static final String KEY_AMOUNTPRODUCT = "amountProduct";
    private static final String KEY_TOTALPRICE = "totalPrice";
    //Column Table Product
    private static final String KEY_ID_PRODUCT = "id";
    private static final String KEY_PRODUCT_NAME = "productName";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_PRODUCT_PRICE = "price";
    private static final String KEY_STORE_NAME = "storeName";
    private static final String KEY_STOK = "stok";
    private static final String KEY_IMGURL = "imageURL";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "(" +
                KEY_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                KEY_USERNAME + " TEXT," +
                KEY_PASSWORD + " TEXT)";
        String CREATE_HISTORY_TABLE = "CREATE TABLE " + TABLE_HISTORY + "(" +
                KEY_ID_HISTORY + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                KEY_ID_History_USER + " INTEGER NOT NULL," +
                KEY_ID_HISTORY_PRODUCT + " INTEGER NOT NULL," +
                KEY_DATE + " TEXT," +
                KEY_AMOUNTPRODUCT + " INTEGER," +
                KEY_TOTALPRICE + " INTEGER)";
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                KEY_ID_PRODUCT + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                KEY_PRODUCT_NAME + " TEXT NOT NULL," +
                KEY_CATEGORY + " TEXT NOT NULL," +
                KEY_PRODUCT_PRICE + " INTEGER," +
                KEY_STORE_NAME + " TEXT," +
                KEY_STOK + " INTEGER," +
                KEY_IMGURL + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_HISTORY_TABLE);
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        onCreate(db);
    }

    //Metode menambahkan data user
    public long addRecordUserAccount() {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, "marvel");
        values.put(KEY_PASSWORD, "marvel123");
        if (db.insert(TABLE_USERS, null, values) == -1) {
            db.close();
            return -1;
        }
        db.close();
        return 1;
    }

    //Menghapus seluruh data produk
    public void deleteAllProducts() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PRODUCTS, null, null);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE name='" + TABLE_PRODUCTS + "'");
        db.close();
    }

    //Menambahkan data dummy pada produk
    // Method to insert dummy product records
    public void insertDummyProducts() {
        deleteAllProducts();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        // Dummy Product 1
        values.put(KEY_PRODUCT_NAME, "Casual Basic Hat");
        values.put(KEY_CATEGORY, "Hat");
        values.put(KEY_PRODUCT_PRICE, 50000);
        values.put(KEY_STORE_NAME, "Herta Cappy Store");
        values.put(KEY_STOK, 10);
        values.put(KEY_IMGURL, "https://www.pngplay.com/wp-content/uploads/2/Black-Bowler-Hat-PNG-Images-HD.png");
        db.insert(TABLE_PRODUCTS, null, values);

        // Dummy Product 2
        values.put(KEY_PRODUCT_NAME, "Casual Basic Jeans");
        values.put(KEY_CATEGORY, "Jeans");
        values.put(KEY_PRODUCT_PRICE, 80000);
        values.put(KEY_STORE_NAME, "Denim Shop");
        values.put(KEY_STOK, 5);
        values.put(KEY_IMGURL, "https://www.pngplay.com/wp-content/uploads/9/Jeans-PNG-HD-Quality.png");
        db.insert(TABLE_PRODUCTS, null, values);

        // Dummy Product 3
        values.put(KEY_PRODUCT_NAME, "Casual Basic Shirt");
        values.put(KEY_CATEGORY, "Shirt");
        values.put(KEY_PRODUCT_PRICE, 50000);
        values.put(KEY_STORE_NAME, "Astral Express");
        values.put(KEY_STOK, 8);
        values.put(KEY_IMGURL, "https://www.pngplay.com/wp-content/uploads/2/Polo-Shirt-Transparent-Image.png");
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    //Menampilkan Seluruh Produk
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> productList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String[] columns = {KEY_ID_PRODUCT, KEY_PRODUCT_NAME, KEY_CATEGORY, KEY_PRODUCT_PRICE, KEY_STORE_NAME, KEY_STOK, KEY_IMGURL};
        Cursor cursor = db.query(TABLE_PRODUCTS, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int productId = cursor.getInt(cursor.getColumnIndex(KEY_ID_PRODUCT));
            String productName = cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_NAME));
            String category = cursor.getString(cursor.getColumnIndex(KEY_CATEGORY));
            int price = cursor.getInt(cursor.getColumnIndex(KEY_PRODUCT_PRICE));
            String storeName = cursor.getString(cursor.getColumnIndex(KEY_STORE_NAME));
            int stock = cursor.getInt(cursor.getColumnIndex(KEY_STOK));
            String imageURL = cursor.getString(cursor.getColumnIndex(KEY_IMGURL));

            Product product = new Product(productId, productName, category, price, storeName, stock, imageURL);
            productList.add(product);
        }
        cursor.close();
        db.close();
        return productList;
    }

    //metode autentikasi login
    public boolean authentication(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();

        String[] parameter = {KEY_USERNAME, KEY_PASSWORD};
        String selection = KEY_USERNAME + " = ? AND " + KEY_PASSWORD + "= ?";
        String[] selectArgs = {username, password};
        Cursor cursor = db.query(TABLE_USERS, parameter, selection, selectArgs, null, null, null);
        boolean result = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return result;
    }
    //Menampilkan produk berdasarkan kategori
    public ArrayList<Product> getProductsByCategory(String category) {
        ArrayList<Product> productList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String[] parameters = {KEY_ID_PRODUCT, KEY_PRODUCT_NAME, KEY_CATEGORY, KEY_PRODUCT_PRICE, KEY_STORE_NAME, KEY_STOK, KEY_IMGURL};
        String selection = KEY_CATEGORY + " = ?";
        String[] selectionArgs = {category};
        Cursor cursor = db.query(TABLE_PRODUCTS, parameters, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int productId = cursor.getInt(cursor.getColumnIndex(KEY_ID_PRODUCT));
                String productName = cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_NAME));
                String productCategory = cursor.getString(cursor.getColumnIndex(KEY_CATEGORY));
                int productPrice = cursor.getInt(cursor.getColumnIndex(KEY_PRODUCT_PRICE));
                String storeName = cursor.getString(cursor.getColumnIndex(KEY_STORE_NAME));
                int stock = cursor.getInt(cursor.getColumnIndex(KEY_STOK));
                String imageURL = cursor.getString(cursor.getColumnIndex(KEY_IMGURL));

                Product product = new Product(productId, productName, productCategory, productPrice, storeName, stock, imageURL);
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return productList;
    }

    //mencari data userId berdasarkan username
    public int getUserIdByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        int userId = -1; // Default value if user is not found

        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID_USER},
                KEY_USERNAME + "=?", new String[]{username},
                null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex(KEY_ID_USER));
            cursor.close();
        }
        db.close();
        return userId;
    }

    //Menambahkan History baru
    public long insertHistory(int userId, int productId, String currentDate, int amountProduct, int totalPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_History_USER, userId);
        values.put(KEY_ID_HISTORY_PRODUCT, productId);
        values.put(KEY_DATE, currentDate);
        values.put(KEY_AMOUNTPRODUCT, amountProduct);
        values.put(KEY_TOTALPRICE, totalPrice);
        long historyId = db.insert(TABLE_HISTORY, null, values);
        db.close();
        return historyId;
    }

    //Menagupdate stok barnag
    public boolean updateProductStock(int productId, int newStock) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_STOK, newStock);

        // Update the product record
        int rowsAffected = db.update(TABLE_PRODUCTS, values, KEY_ID_PRODUCT + " = ?",
                new String[]{String.valueOf(productId)});

        db.close();
        // Check if the update was successful
        return rowsAffected > 0;
    }

    //menampilkan seluruh data history
    public ArrayList<History> getAllHistory() {
        ArrayList<History> historyList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_HISTORY;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int historyId = cursor.getInt(cursor.getColumnIndex(KEY_ID_HISTORY));
                int userId = cursor.getInt(cursor.getColumnIndex(KEY_ID_History_USER));
                int productId = cursor.getInt(cursor.getColumnIndex(KEY_ID_HISTORY_PRODUCT));
                String date = cursor.getString(cursor.getColumnIndex(KEY_DATE));
                int amountProduct = cursor.getInt(cursor.getColumnIndex(KEY_AMOUNTPRODUCT));
                int totalPrice = cursor.getInt(cursor.getColumnIndex(KEY_TOTALPRICE));

                History history = new History(historyId, userId, productId, date, amountProduct, totalPrice);
                historyList.add(history);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return historyList;
    }

    //Menghapus seluruh data History
    public void deleteAllHistory() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("history", null, null);
        db.close();
    }
}
