package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.persistentAccountDAO;

public class PersistentExpenseManager extends ExpenseManager {
    private Context context;
    public PersistentExpenseManager(Context context){
        this.context=context;
        try{
            setup();
        }catch (EnumConstantNotPresentException e){
            e.printStackTrace();
        } catch (ExpenseManagerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setup() throws ExpenseManagerException {
       // MyDBHandler dbHandler = new MyDBHandler(context);
       // AccountDAO persistentAccountDAO = new persistentAccountDAO(dbHandler);

        //setAccountsDAO(persistentAccountDAO);
       // TransactionDAO inMemoryTransactionDAO = new TransactionDAO();
        //setTransactionsDAO(inMemoryTransactionDAO);

    }
}
