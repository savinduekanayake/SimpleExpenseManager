package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class MyDBHandler extends SQLiteOpenHelper implements TransactionDAO {
    //information of database
    private static final int DATABASE_VERSION = 8;
    private static final String DATABASE_NAME = "170158F";

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public static final String TABLE_NAME_ACCOUNT = "Account_Table";
    public static final String COLUMN_accountNo = "accountNo";
    public static final String COLUMN_bankName = "bankName";
    public static final String COLUMN_accountHolderName = "accountHolderName";
    public static final String COLUMN_balance = "balance";
    //initialize the database for account



    public static final String TABLE_NAME_TRANSACTION = "Transaction_Table";
    public static final String COLUMN_accountNo_Transaction = "accountNo";
    public static final String COLUMN_type= "types";
    public static final String COLUMN_amount = "amount";
    public static final String COLUMN_date = "date";
    //initialize the database for transaction



    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_ACCOUNT = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_ACCOUNT + "(" + COLUMN_accountNo +
                " TEXT PRIMARY KEY," + COLUMN_bankName + " TEXT, " + COLUMN_accountHolderName + " TEXT," + COLUMN_balance + " REAL )";
        db.execSQL(CREATE_TABLE_ACCOUNT);
        //run the string of create_table_account

        String CREATE_TABLE_TRANSACTION = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TRANSACTION + "( ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_accountNo_Transaction +
                " TEXT , " + COLUMN_type + " TEXT, " + COLUMN_amount + " FLOAT, " + COLUMN_date + " TEXT )";
        db.execSQL(CREATE_TABLE_TRANSACTION);
        //run the string of create_table_Transaction
    }



    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_ACCOUNT);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_TRANSACTION);
        onCreate(db);
    }


    public void addAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //add values
        contentValues.put(COLUMN_accountNo,account.getAccountNo());
        contentValues.put(COLUMN_bankName, account.getBankName());
        contentValues.put(COLUMN_accountHolderName, account.getAccountHolderName());
        contentValues.put(COLUMN_balance,account.getBalance());


        //add account to database
        db.insert(TABLE_NAME_ACCOUNT,null,contentValues);
       // System.out.println("data added");
    }

    public Account getAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " + TABLE_NAME_ACCOUNT + " WHERE accountNo = " +accountNo,null);

        //check if there is account exist
        if(res.moveToFirst()){
            //inizialize variables
            String accountNumber=res.getString(0);
            String bankName=res.getString(1);
            String accountHolderName=res.getString(2);
            Double balance=res.getDouble(3);

            //make the relevent object and return
            Account account = new Account(accountNumber,bankName,accountHolderName,balance);
            return account;
        }

        //if not a account return exception
        String msg = "Account " + accountNo + " is invalid.";
        throw new InvalidAccountException(msg);


    }

    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Account account = this.getAccount(accountNo);

        // specific implementation based on the transaction type
        switch (expenseType) {
            case EXPENSE:
                account.setBalance(account.getBalance() - amount);
                break;
            case INCOME:
                account.setBalance(account.getBalance() + amount);
                break;
        }
        contentValues.put(COLUMN_balance, account.getBalance());
        db.update(TABLE_NAME_ACCOUNT, contentValues, COLUMN_accountNo + " = ? ", new String[] {account.getAccountNo()});

    }



    public List<String> getAccountNumbersList() {
        SQLiteDatabase db = this.getWritableDatabase();

        //initialize array list
        List<String> accountNumberList = new ArrayList<>();

        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_NAME_ACCOUNT , null );

        while(res.moveToNext()){
            //putting all account numbers to array list
            accountNumberList.add(res.getString(0));
        }
        return accountNumberList;
    }



    public List<Account> getAccountsList() {
        SQLiteDatabase db = this.getWritableDatabase();

        //initialize array list
        ArrayList accountList = new ArrayList();
        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_NAME_ACCOUNT , null );

        //putting all account numbers to array list
        while(res.moveToNext()){

            String accountNumber = res.getString(res.getColumnIndex("accountNo"));
            String bankName = res.getString(res.getColumnIndex("bankName"));
            String accountHolderName = res.getString(res.getColumnIndex("accountHolderName"));
            Double balance = res.getDouble(res.getColumnIndex("balance"));


            accountList.add(new Account(accountNumber,bankName,accountHolderName,balance));
        }
        System.out.println("a******************");
        return accountList;
    }


    public void removeAccount(String accountNo) throws InvalidAccountException {

    }




    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //initialize and add to table
        contentValues.put(COLUMN_accountNo_Transaction,accountNo);
        contentValues.put(COLUMN_type, String.valueOf(expenseType));
        contentValues.put(COLUMN_amount, Double.valueOf(amount));
        contentValues.put(COLUMN_date, dateFormat.format(date));

        db.insert(TABLE_NAME_TRANSACTION,null,contentValues);
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        SQLiteDatabase db = this.getWritableDatabase();

        //initialize array list
        ArrayList<Transaction> arrayList_transaction = new ArrayList();
        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_NAME_TRANSACTION , null );
        res.moveToFirst();

        while (res.moveToNext()){
            //get data from table and set to variables
            String accountNo = res.getString(0);
            String type = res.getString(1);
            Double amount = res.getDouble(2);

            try {
                Date date_ = new SimpleDateFormat("dd/MM/YYYY").parse(res.getString(3));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String expenseTypeStr = res.getString(1);
            ExpenseType expenseType = ExpenseType.EXPENSE;

            //if expense type is not expese
            if(res.getString(res.getColumnIndex("expenseType"))== "Expense"){
                expenseType=ExpenseType.INCOME;
            }

            try {
                //add to arrayList
                Transaction t = new Transaction(dateFormat.parse(res.getString(3)),accountNo,expenseType,amount);
                arrayList_transaction.add(t);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return arrayList_transaction;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        SQLiteDatabase db = this.getWritableDatabase();

        //initialize array list
        ArrayList<Transaction> arrayList_transaction = new ArrayList();
        //make a quary with limit
        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_NAME_TRANSACTION  + " order by "+ COLUMN_date+ " DESC limit "+ limit ,null );
        res.moveToFirst();

        while (res.moveToNext()){
            //get data from table and set to variables
            String accountNo = res.getString(0);
            String type = res.getString(1);
            Double amount = res.getDouble(2);

            try {
                Date date_ = new SimpleDateFormat("dd/mm/yyyy").parse(res.getString(3));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String expenseTypeStr = res.getString(1);
            ExpenseType expenseType = ExpenseType.EXPENSE;

            //if expense type is not expese
            if(res.getString(res.getColumnIndex("expenseType"))== "Expense"){
                expenseType=ExpenseType.INCOME;
            }

            try {
                //add to arrayList
                Transaction t = new Transaction(dateFormat.parse(res.getString(3)),accountNo,expenseType,amount);
                arrayList_transaction.add(t);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return arrayList_transaction;

    }

}
