package proj.isd2016.cccu.dvdonlinestore;

import android.app.SearchManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.sqlite.SQLiteCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import proj.isd2016.cccu.dvdonlinestore.database.DBHelper;
import proj.isd2016.cccu.dvdonlinestore.database.DBQueryConstant;
import proj.isd2016.cccu.dvdonlinestore.database.Moive;
import proj.isd2016.cccu.dvdonlinestore.database.Singleton;

public class MainActivity extends BaseActivity {

    ListView moive_list ;
    Handler mHandler;
    MoiveListAdapter moiveListAdapter;
    AVLoadingIndicatorView loadingview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        loadingview.show();

        initListView();
        initDrawer();
        initActionBar();
        // TODO : search query
        //  Search widget //
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
        }


        // Search widget //

        // handler used to handle the message sent from another thread
        initHandler();
        // Crawl the moive data from IMDB //
        new Thread(new Runnable() {
            public void run() {
                initDataCrawler(getApplicationContext());
            }
        }).start();
        //

    }



    private void initListView()
    {
        moive_list = (ListView) findViewById(R.id.moive_listview);
        DBHelper helper = new DBHelper(getApplicationContext());
        moiveListAdapter = new MoiveListAdapter(getApplicationContext(),helper.getAllMoivesCursor(),0);
        moive_list.setAdapter(moiveListAdapter);

        moive_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Object object = moive_list.getItemAtPosition(position);
                SQLiteCursor cursor = (SQLiteCursor) object;

                Intent intent = new Intent();
                Bundle bundle = new Bundle();

                bundle.putString("moiveId", cursor.getString(cursor.getColumnIndex(DBQueryConstant.MOIVE_ID)));
                System.out.println(cursor.getString(cursor.getColumnIndex(DBQueryConstant.MOIVE_ID)));

                intent.putExtras(bundle);
                intent.setClass(getApplicationContext(), MoiveDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {

        loadingview = (AVLoadingIndicatorView) findViewById(R.id.loading);
    }


    private void initActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getStringArray(R.array.drawer_items)[0]);
    }

    private void initHandler()
    {
        mHandler = new Handler(Looper.getMainLooper())
        {
            @Override
            public void handleMessage(Message inputMessage) {
                if (inputMessage.what == 1)
                {
                    DBHelper helper = new DBHelper(getApplicationContext());
                    moiveListAdapter.changeCursor(helper.getAllMoivesCursor());
                    loadingview.hide();
                    moiveListAdapter.notifyDataSetChanged();
                    moive_list.setVisibility(View.VISIBLE);
                } else
                {
                    loadingview.hide();
                    moive_list.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Sorry , No Network connection , please try again", Toast.LENGTH_LONG).show();
                }
            }

        };
    }

    protected void initDataCrawler(Context context)
    {
        int isConnected = 0;
        final String CRAWL_URL = "http://www.imdb.com/movies-in-theaters/?ref_=nv_mv_inth_1";
        Log.i(getClass().getName(), "Try to crawl the moive data from : " + CRAWL_URL);
        try
        {
            Document doc = Jsoup.connect(CRAWL_URL).get();
            Elements elements_title = doc.select("a[href*='?ref_=inth_ov_tt']");     // html objects with element a and attribute href that contain ?ref_=inth_ov_tt
            Elements elements_summary = doc.select("div[itemprop='description']");   // html objects with element div and attribute itemprop that equal desciption
            Elements elements_picture = doc.select("img[class='poster shadowed']"); // html objects with element image and attribute class that equal poster_shadowed
            Elements elements_ratingExist = doc.select("  div[class*='rating_txt']");

            DBHelper dbHelper = new DBHelper(context);
            Random randomNum = new Random();

            for (int i=0 ; i<elements_summary.size() ;i++)
            {
                final String href = elements_title.get(i).attr("href");
                final String moiveId = href.substring(href.indexOf("/", 2) + 1, href.indexOf("/", 10));

                double rating = 0 ;

                switch(elements_ratingExist.get(i).children().size())
                {
                    case 0: rating = randomNum.nextInt(101); break;
                    case 1: rating = Integer.valueOf(elements_ratingExist.get(i).child(0).child(0).text()); break;
                    case 2: rating = Integer.valueOf(elements_ratingExist.get(i).child(1).child(0).text()); break;
                }
                rating = (rating/100.0)*4.0;

                final String moiveTitle = elements_title.get(i).text().replaceAll("'", "");
                final String moiveSummary = elements_summary.get(i).text();
                final int price = randomNum.nextInt(301)+50;

                // If the moive title not exists in the database
                if (!dbHelper.isMoiveExists(moiveId))
                {
                    Moive moive = new Moive(moiveId,price,moiveTitle,rating,moiveSummary);

                    dbHelper.insertMoive(moive);  // insert the moive data into database
                    Log.i(getClass().getName(), moiveId);
                    Bitmap poster = null;
                    poster = getMoivePoster(elements_picture.get(i).absUrl("src"));  // download the picture //
                    if (poster != null) {
                        StoreMoivePoster(moiveId, poster);  // Store the picture downloaded into internal storage // // Use moive id as the filename of picture //
                    }
                }
            }
            isConnected = 1;
        } catch (java.io.IOException e)
        {
            isConnected = 0;
            Log.i(getClass().getName(), "Fail to connect to website" );
        }

        // UI Handler
        Message message = new Message();
        message.what = isConnected;
        mHandler.sendMessage(message);
    }

    protected void StoreMoivePoster(String filename,Bitmap picture)
    {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        final String posterPath ="movie_posters_"+filename+".jpg";

        // Create imageDir
        File mypath=new File(directory,posterPath);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(mypath);
            picture.compress(Bitmap.CompressFormat.PNG, 100, out);
            // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                Log.i(getClass().getName(), "Fail to save the poster into internal storage");
            }
        }
    }

    protected Bitmap getMoivePoster(String url)
    {
        Bitmap bm = null;
        try {

            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);

            bis.close();
            is.close();

        } catch (java.io.IOException e)
        {
            Log.i(getClass().getName(), "Fail to download the poster");
        }
        return bm;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //

        return true;
    }


}
