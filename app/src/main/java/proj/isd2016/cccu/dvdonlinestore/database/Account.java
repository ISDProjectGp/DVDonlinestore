package proj.isd2016.cccu.dvdonlinestore.database;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 18/10/2016.
 */
public class Account {

    private String name = null;
    private String password = null;

    private int age ;
    private int credit ;
    private int memberID ;

    private ShoppingCart shoppingCart;

    public Account(String name ,String password) {
        this.name = name;
        this.password = password;
        age = credit = memberID = 0;
        this.shoppingCart = new ShoppingCart();
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public int getCredit() {
        return credit;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean pay(Context context)
    {
        if (credit>=shoppingCart.getCartTotalCredit())
        {
            DBHelper dbhelper = new DBHelper(context);
            credit-=shoppingCart.getCartTotalCredit();
            if (dbhelper.updateCreditQuery(this))
            {
                shoppingCart.clearShoppingCart();
                return true;
            }
            credit+=shoppingCart.getCartTotalCredit();
        }
        return false;
    }
}
