package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.MyDBHandler;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;

import java.util.*;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.*;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class persistentAccountDAO  implements AccountDAO {
    private  MyDBHandler dbHandler;

    public persistentAccountDAO(MyDBHandler DbHandler){
        this.dbHandler= DbHandler;
    }

    @Override
    public List<String> getAccountNumbersList() {
        return dbHandler.getAccountNumbersList();
    }


    public void onCreate(SQLiteDatabase db) {
        dbHandler.onCreate(db);
    }


    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        dbHandler.onUpgrade(db,i,i1);
    }

    @Override
    public List<Account> getAccountsList() {
        return dbHandler.getAccountsList();
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {

    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
         dbHandler.updateBalance(accountNo,expenseType,amount);
    }



    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        return dbHandler.getAccount(accountNo);
    }

    @Override
    public void addAccount(Account account) {
         dbHandler.addAccount(account);
    }
}
