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
        int moiveID = cursor.getInt(cursor.getColumnIndexOrThrow(DBQueryConstant.MOIVE_ID));

        TextView moive_title = (TextView) view.findViewById(R.id.tv_moive_list_row_title);
        moive_title.setText(moiveTitle);

        ImageView img = (ImageView) view.findViewById(R.id.imgeview_poster);
        loadImageFromStorage(context,String.valueOf(moiveID),img);
        System.out.println(moiveID);
    }

    private void loadImageFromStorage(Context context,String filename,ImageView img)
    {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"moive"+filename+".jpg");
        try {
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(mypath));
            img.setImageBitmap(b);
            System.out.println("set");
        }
        catch (FileNotFoundException e)
        {
            System.out.println("fail");
        }

    }

}
