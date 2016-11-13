package proj.isd2016.cccu.dvdonlinestore.database;

/**
 * Created by cheng on 18/10/2016.
 */
public class Moive {

    private int price ;
    private int rating ;

    private int moiveID;
    private String summary;
    private String moiveTitle;

    public Moive(int moiveID, int price,String moiveTitle,int rating,String summary) {
        this.price = price;
        this.moiveID = moiveID;
        this.moiveTitle = moiveTitle;
        this.rating = rating;
        this.summary = summary;
    }

    public int getMoiveID() {
        return moiveID;
    }

    public String getMoiveTitle() {
        return moiveTitle;
    }

    public int getRating() {
        return rating;
    }

    public int getPrice() {
        return price;
    }

    public String getSummary() {
        return summary;
    }


}
