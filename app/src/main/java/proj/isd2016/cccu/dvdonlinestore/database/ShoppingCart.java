package proj.isd2016.cccu.dvdonlinestore.database;

import java.util.List;

/**
 * Created by cheng on 18/10/2016.
 */
public class ShoppingCart {

    private int CartTotalCredit ;

    private List<Moive> Moives;

    public ShoppingCart() {
        CartTotalCredit = 0;
    }

    public boolean addMoive(Moive moive) {
        if (moive == null) return false;

        if (Moives.add(moive)){
            CartTotalCredit += moive.getPrice();
            return true;
        }
        return false;
    }

    public boolean deleteMoive(Moive moive) {
        if (moive == null ) return false ;

        if (Moives.remove(moive)){
            CartTotalCredit -= moive.getPrice();
            return true;
        }
        return false;
    }

    public int getCartTotalCredit() {
        return CartTotalCredit;
    }

    public List<Moive> getMoives() {
        return Moives;
    }

    /*public void setMoives(List<Moive> moives) {
        Moives = moives;
    }*/

    public void clearShoppingCart()
    {
        Moives.clear();
        CartTotalCredit = 0;
    }
}



