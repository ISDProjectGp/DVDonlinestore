package proj.isd2016.cccu.dvdonlinestore.database;

/**
 * Created by cheng on 18/10/2016.
 */
public class Moive {

    private int price ;
    private double rating ;

    private String moiveID;
    private String summary;
    private String moiveTitle;

    public Moive(String moiveID, int price,String moiveTitle,double rating,String summary) {
        this.price = price;
        this.moiveID = moiveID;
        this.moiveTitle = moiveTitle;
        this.rating = rating;
        this.summary = summary;
    }

    public String getMoiveID() {
        return moiveID;
    }

    public String getMoiveTitle() {
        return moiveTitle;
    }

    public double getRating() {
        return rating;
    }

    public int getPrice() {
        return price;
    }

    public String getSummary() {
        return summary;
    }


}
