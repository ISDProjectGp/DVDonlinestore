package proj.isd2016.cccu.dvdonlinestore.database;

import android.content.Context;
import android.util.Log;

/**
 * Created by cheng on 31/10/2016.
 */
public final class Singleton {

    private static Singleton INSTANCE = null;
    private static Account account;

    public Singleton()
    {

    }

    public static synchronized Singleton getSingleton(){
        if (INSTANCE == null) INSTANCE = new Singleton();
        return INSTANCE;
    }

    public boolean login(Context context,String accountName,String passWord)
    {
        DBHelper dbHelper = new DBHelper(context);
        account = new Account(accountName,passWord);
        if (dbHelper.loginQuery(account))
        {
            return true;
        }
        account = null;
        return false;
    }

    public boolean register(Context context,String accountName,String passWord,int age)
    {
        DBHelper dbHelper = new DBHelper(context);
        account = new Account(accountName,passWord);
        account.setAge(age);
        if (dbHelper.registerQuery(account))
        {
             dbHelper.loginQuery(account);
             return true;
        }
        account = null;
        return false;
    }

    public ShoppingCart getShoppingCart()
    {
       if (account !=null)
       {

           return account.getShoppingCart();
       }
        Log.e(INSTANCE.getClass().getName(),"Fail to Login");
        return null;
    }

    public int getCredit()
    {
        if (account !=null)
        {
            return account.getCredit();
        }
        Log.e(INSTANCE.getClass().getName(),"Fail to Login");
        return -1;
    }

    public int getAge()
    {
        if (account !=null)
        {
            return account.getAge();
        }
        Log.e(INSTANCE.getClass().getName(),"Fail to Login");
        return -1;
    }

    public String getName()
    {
        if (account !=null)
        {
            return account.getName();
        }
        Log.e(INSTANCE.getClass().getName(),"Fail to Login");
        return "ERROR";
    }

    public boolean pay(Context context)
    {
        if (account !=null)
        {
            return account.pay(context);
        }
        Log.e(INSTANCE.getClass().getName(),"Fail to Login");
        return false;
    }

    public void logout()
    {
        if (account !=null)
        {
            account = null;
        }
    }

    public boolean isPayable()
    {
        return account.isPayable();
    }

    public boolean isCartEmpty()
    {
        return account.getShoppingCart().isEmpty();
    }

    public String getMemberID()
    {
        return account.getMemberID();
    }

}
