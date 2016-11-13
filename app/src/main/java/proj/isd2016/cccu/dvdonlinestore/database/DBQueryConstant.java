package proj.isd2016.cccu.dvdonlinestore.database;

/**
 * Created by cheng on 18/10/2016.
 */

public class DBQueryConstant {

    // MEMBER TABLE //
    public static final String MEMBER_TABLE_NAME = "MEMBER";
    // MEMBER TABLE FIELD //
    public static final String MEMBER_ID = "MEMBER_ID";
    public static final String NAME = "NAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String AGE = "AGE";
    public static final String CREDIT = "CREDIT";

    // MOIVE TABLE //
    public static final String MOVIE_TABLE_NAME = "MOVIE";
    // MOIVE TABLE FIELD //
    public static final String MOIVE_ID = "MOIVE_ID";
    public static final String MOVE_TITLE = "MOVE_TITLE";
    public static final String ON_SCREEN_DATE = "ON_SCREEN_DATE";
    public static final String PRICE = "PRICE";
    public static final String RATING = "RATING";
    public static final String SUMMARY = "SUMMARY";

    // SQL CREATE QUERY
    public static final String CREATE_MEMBER_TABLE = "Create table "+ MEMBER_TABLE_NAME +" ( " +
            MEMBER_ID + " int ," +
            NAME + " varchar(40) PRIMARY KEY NOT NULL," +
            PASSWORD + " varchar(40) NOT NULL," +
            AGE + " int," +
            CREDIT + " int NOT NULL DEFAULT 500 " +
            " )";

    public static final String CREATE_MOIVE_TABLE = " Create table "+ MOVIE_TABLE_NAME +" ( " +
            MOIVE_ID + " int ," +
            MOVE_TITLE+ " varchar(40) PRIMARY KEY NOT NULL," +
            ON_SCREEN_DATE+ " int," +
            PRICE+ " int," +
            RATING+ " int," +
            SUMMARY+" text" +
            " )";

}
