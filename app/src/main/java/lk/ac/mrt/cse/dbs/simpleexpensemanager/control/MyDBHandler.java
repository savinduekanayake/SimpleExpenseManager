package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper {
    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "JavaProject.db";


    public static final String TABLE_NAME_ACCOUNT = "Account_Table";
    public static final String COLUMN_accountNo = "accountNo";
    public static final String COLUMN_bankName = "bankName";
    public static final String COLUMN_accountHolderName = "accountHolderName";
    public static final String COLUMN_balance = "balance";
    //initialize the database for account


    public static final String TABLE_NAME_TRANSACTION = "Transaction_Table";
    public static final String COLUMN_accountNo_Transaction = "accountNo";
    public static final String COLUMN_type= "type";
    public static final String COLUMN_amount = "amount";
    public static final String COLUMN_date = "date";
    //initialize the database for transaction

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_ACCOUNT = "CREATE TABLE " + TABLE_NAME_ACCOUNT + "(" + COLUMN_accountNo +
                "TEXT PRIMARYKEY," + COLUMN_bankName + "TEXT," + COLUMN_accountHolderName + "TEXT," + COLUMN_balance + "FLOAT )";
        db.execSQL(CREATE_TABLE_ACCOUNT);
        //run the string of create_table_account

        String CREATE_TABLE_TRANSACTION = "CREATE TABLE " + TABLE_NAME_TRANSACTION + "(" + COLUMN_accountNo_Transaction +
                "TEXT PRIMARYKEY," + COLUMN_type + "TEXT," + COLUMN_amount + "FLOAT," + COLUMN_date + "DATE )";
        db.execSQL(CREATE_TABLE_TRANSACTION);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}
//    public String loadHandler() {}
//    public void addHandler(Student student) {}
//    public Student findHandler(String studentname) {}
//    public boolean deleteHandler(int ID) {}
//    public boolean updateHandler(int ID, String name) {}
}
