package proj.isd2016.cccu.dvdonlinestore.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 18/10/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dvdonlinestoreDB";
    private static final int DATABASE_VERSION = 1;
    private static SQLiteDatabase database = null;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (this.database==null) {
            db.execSQL(DBQueryConstant.CREATE_MEMBER_TABLE);
            db.execSQL(DBQueryConstant.CREATE_MOIVE_TABLE);
            this.database = db;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // If there are any version update of database , please update the version and input the sql //
            String sql = "";
            db.execSQL(sql);
    }

    public boolean registerQuery(Account account)
    {
        if (account == null | account.getName()== null || account.getPassword() == null || account.getAge() == 0 ) return false;

        ContentValues cv = new ContentValues();
        cv.put(DBQueryConstant.NAME,account.getName());
        cv.put(DBQueryConstant.PASSWORD, account.getPassword());
        cv.put(DBQueryConstant.AGE, account.getAge());

        long result = getWritableDatabase().insert(DBQueryConstant.MEMBER_TABLE_NAME, null, cv) ;
        if (result > 0)
        {
            return loginQuery(account);
        }
        return false;
    }

    public boolean updateCreditQuery(Account account)
    {
        if (account == null | account.getName()== null || account.getPassword() == null) return false;

        ContentValues cv = new ContentValues();
        cv.put(DBQueryConstant.CREDIT,account.getCredit());

        String selections = DBQueryConstant.MEMBER_ID + " ="+ account.getMemberID() ;
        long result = getWritableDatabase().update(DBQueryConstant.MEMBER_TABLE_NAME, cv, selections, null);

        return result > 0;
    }

  /*  //TODO
    public List<Moive> getAllMoivesName()
    {

    }*/

    /*public Moive getMoive(int moiveID)
    {


    }*/
//TODO USE IMDB ID TO DO
    private int getNumberOfMoive()
    {
        String[] columns = {DBQueryConstant.MOIVE_ID};

        Cursor cursor = getReadableDatabase().query(
                DBQueryConstant.MOVIE_TABLE_NAME,
                columns, null, null, null, null, null, null);
        int num = 0;
        while (cursor.moveToNext()) {
            num++;
        }
        return num;
    }

    public String getMoiveID(String moiveTittle)
    {
        String[] columns = {DBQueryConstant.MOIVE_ID};

        String selections = DBQueryConstant.MOVE_TITLE + " = '"+ moiveTittle +"'";

        Cursor cursor = getReadableDatabase().query(
                DBQueryConstant.MOVIE_TABLE_NAME,
                columns, selections, null, null, null, null, null);

        while (cursor.moveToNext()) {
            return String.valueOf(cursor.getInt(0));
        }
        return null;
    }

    public boolean insertMoive(String moiveTitle , String summary)
    {
        //if (moive == null | moive.getMoiveTitle() == null || moive.getSummary() == null ) return false;
        if ( moiveTitle == null || summary == null ) return false;

        ContentValues cv = new ContentValues();
        cv.put(DBQueryConstant.MOIVE_ID,getNumberOfMoive());
        cv.put(DBQueryConstant.MOVE_TITLE,moiveTitle);
        cv.put(DBQueryConstant.SUMMARY, summary);
        //cv.put(DBQueryConstant.PRICE, account.getAge());

        long result = getWritableDatabase().insert(DBQueryConstant.MOVIE_TABLE_NAME, null, cv) ;

        return result>0;
    }


    public Cursor getAllMoivesCursor()
    {
        /*String[] columns = {DBQueryConstant.MOIVE_ID,
                           DBQueryConstant.MOVE_TITLE,
                           DBQueryConstant.PRICE,
                           DBQueryConstant.SUMMARY ,
                           DBQueryConstant.RATING,
                           DBQueryConstant.ON_SCREEN_DATE

        };*///TODO : MAKE IT AS RESULT SET
        Cursor cursor = getReadableDatabase().rawQuery( "select rowid as _id,"
                +DBQueryConstant.MOVE_TITLE+","
                +DBQueryConstant.MOIVE_ID
                +" from "
                +DBQueryConstant.MOVIE_TABLE_NAME, null);

        return cursor;
    }

    public boolean loginQuery(Account account)
    {
        if (account == null | account.getName()== null || account.getPassword() == null) return false;

        String[] columns = {DBQueryConstant.NAME,
                DBQueryConstant.AGE,DBQueryConstant.MEMBER_ID, DBQueryConstant.CREDIT};

        String selections = DBQueryConstant.NAME + " = '"+ account.getName() +"'"
                +" AND " + DBQueryConstant.PASSWORD + " = '"+ account.getPassword() +"'";
        Cursor cursor = getReadableDatabase().query(
                DBQueryConstant.MEMBER_TABLE_NAME,
                columns, selections, null, null, null, null, null);

        while (cursor.moveToNext()) {
            account.setName(cursor.getString(0));
            account.setAge(cursor.getInt(1));
            account.setMemberID(cursor.getInt(2));
            account.setCredit(cursor.getInt(3));
        }
      return cursor.getCount() >= 1;
    }

}
