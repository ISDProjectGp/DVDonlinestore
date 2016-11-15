package proj.isd2016.cccu.dvdonlinestore;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import proj.isd2016.cccu.dvdonlinestore.database.DBQueryConstant;


/**
 * Created by cheng on 5/11/2016.
 */
public class MoiveListAdapter extends CursorAdapter {


    public MoiveListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.moivelist_row, parent, false);
    }

    public void bindView(View view, Context context, Cursor cursor) {

        String moiveTitle = cursor.getString(cursor.getColumnIndexOrThrow(DBQueryConstant.MOVE_TITLE));
        String moiveID = cursor.getString(cursor.getColumnIndexOrThrow(DBQueryConstant.MOIVE_ID));
        double rating = cursor.getDouble(cursor.getColumnIndexOrThrow(DBQueryConstant.RATING));

        TextView moive_title = (TextView) view.findViewById(R.id.tv_moive_list_row_title);
        moive_title.setText(moiveTitle);

        ImageView img = (ImageView) view.findViewById(R.id.imgeview_poster);
        loadImageFromStorage(context, moiveID, img);

        TextView rating_text = (TextView) view.findViewById(R.id.tv_rating);
        rating_text.setText(rating + " / 4");

        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar_moive_list_row);
        ratingBar.setRating((float)rating);
        ratingBar.setFocusable(false);
    }

    private void loadImageFromStorage(Context context,String filename,ImageView img)
    {
        ContextWrapper cw = new ContextWrapper(context);
        final String posterPath ="movie_posters_"+filename+".jpg";
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,posterPath);
        try {
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(mypath));
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
        }

    }

}
