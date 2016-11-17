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
    private String memberID ;

    private ShoppingCart shoppingCart;

    public Account(String name ,String password) {
        this.name = name;
        this.password = password;
        age = credit = 0 ;
        String memberID = "NaN";
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

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
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

    public boolean isPayable()
    {
        return credit>=shoppingCart.getCartTotalCredit();
    }

    public boolean pay(Context context)
    {
        if (isPayable())
        {
            DBHelper dbhelper = new DBHelper(context);
            credit-=shoppingCart.getCartTotalCredit();
            System.out.println("patt");
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
