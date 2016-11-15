package proj.isd2016.cccu.dvdonlinestore;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import proj.isd2016.cccu.dvdonlinestore.database.DBHelper;
import proj.isd2016.cccu.dvdonlinestore.database.Moive;

public class MoiveDetailActivity extends AppCompatActivity {

    TextView tv_moive_tittle;
    TextView tv_moive_tittle2;
    TextView tv_star;
    TextView tv_summary;
    ImageView im_poster;
    Button btn_purchase;
    TextView tv_win;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moive_detail);
        initView();
        initActionBar();
        getDetailsFromDatabase();
    }

    private void initView()
    {
        tv_moive_tittle = (TextView) findViewById(R.id.tv_moive_name);
        tv_moive_tittle2  = (TextView) findViewById(R.id.tv_moive_name2);
        tv_summary = (TextView) findViewById(R.id.summary);
        im_poster = (ImageView) findViewById(R.id.poster);
        tv_star = (TextView) findViewById(R.id.tv_star);
        btn_purchase = (Button) findViewById(R.id.btn_purchase);
        tv_win = (TextView) findViewById(R.id.tv_win);

    }

    private void initActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void getDetailsFromDatabase()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String moiveid = bundle.getString("moiveId");


        DBHelper dbhelper = new DBHelper(getApplicationContext());
        Moive moive =  dbhelper.getMoivesDetails(moiveid);

        tv_moive_tittle.setText(moive.getMoiveTitle());
        tv_moive_tittle2.setText(moive.getMoiveTitle());
        tv_summary.setText(moive.getSummary());
        tv_star.setText(String.valueOf(moive.getRating()));
        loadImageFromStorage(getApplicationContext(), moiveid, im_poster);
        btn_purchase.setText(btn_purchase.getText() + "  $" + moive.getPrice());

        Random random = new Random();
        tv_win.setText(String.valueOf(random.nextInt(5)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_moive_detail, menu);
        return true;
    }

    private void loadImageFromStorage(Context context,String filename,ImageView img) {
        ContextWrapper cw = new ContextWrapper(context);
        final String posterPath = "movie_posters_" + filename + ".jpg";
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, posterPath);
        try {
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(mypath));
            img.setImageBitmap(b);
        } catch (FileNotFoundException e) {
        }
    }


}
