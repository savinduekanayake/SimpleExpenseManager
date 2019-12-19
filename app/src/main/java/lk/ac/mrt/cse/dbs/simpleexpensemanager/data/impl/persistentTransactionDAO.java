package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.MyDBHandler;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class persistentTransactionDAO implements TransactionDAO {
    private MyDBHandler dbHandler;

    public persistentTransactionDAO(MyDBHandler DbHandler){
        this.dbHandler= DbHandler;
    }
    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        dbHandler.logTransaction(date, accountNo, expenseType, amount);
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        return dbHandler.getAllTransactionLogs();
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        return dbHandler.getPaginatedTransactionLogs(limit);
    }
}
