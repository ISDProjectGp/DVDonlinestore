package proj.isd2016.cccu.dvdonlinestore.database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 18/10/2016.
 */
public class ShoppingCart {

    private int CartTotalCredit ;

    private List<Moive> Moives;
    private List<Integer> quatity;

    public ShoppingCart() {

        quatity = new ArrayList<>();
        Moives = new ArrayList<>();
        CartTotalCredit = 0;
    }

    public boolean addMoive(Moive moive) {
        if (moive == null) return false;
        CartTotalCredit += moive.getPrice();
        if (!isExist(moive))
        {
            quatity.add(1);
            Moives.add(moive);
        } else {
            int position = indexOf(moive);
            quatity.set(position, quatity.get(position) + 1);
        }
        return true;
    }

    private boolean isExist(Moive moive)
    {
        for (Moive moiv:Moives)
        {
            if (moiv.getMoiveID().equals(moive.getMoiveID()))
            {
                return true;
            }
        }
        return false;
    }

    private int indexOf(Moive moive)
    {
        int index = 0 ;
        for (Moive moiv:Moives)
        {
            if (moiv.getMoiveID().equals(moive.getMoiveID()))
            {
                return index;
            }
            ++index;
        }
        return -1;
    }


    public boolean deleteMoive(Moive moive) {
        if (moive == null ) return false ;

        int position = indexOf(moive);
        if (Moives.remove(moive)){
            quatity.remove(position);
            CartTotalCredit -= moive.getPrice();
            return true;
        }
        return false;
    }

    public int getCartTotalCredit() {
        return CartTotalCredit;
    }

    public final List<Moive> getMoives() {
        return Moives;
    }
    public final List<Integer> getQuatity(){ return quatity; }

    /*public void setMoives(List<Moive> moives) {
        Moives = moives;
    }*/

    public void clearShoppingCart()
    {
        quatity.clear();
        Moives.clear();
        CartTotalCredit = 0;
    }

    public boolean isEmpty()
    {
         return Moives.size() == 0;
    }
}



