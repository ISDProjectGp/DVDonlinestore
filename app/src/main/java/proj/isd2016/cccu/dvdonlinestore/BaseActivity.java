package proj.isd2016.cccu.dvdonlinestore;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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

import proj.isd2016.cccu.dvdonlinestore.database.DBHelper;
import proj.isd2016.cccu.dvdonlinestore.database.Moive;
import proj.isd2016.cccu.dvdonlinestore.database.ShoppingCart;
import proj.isd2016.cccu.dvdonlinestore.database.Singleton;

/**
 * Created by cheng on 5/11/2016.
 */
public class BaseActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    protected void initDrawer()
    {
        List<Map<String,Object>> drawerDataList = new ArrayList<>();
        // Init the data in drawerDataList
        String[] drawer_text_items = getResources().getStringArray(R.array.drawer_items);
        int[] drawer_image_items =  {R.drawable.ic_movie_white_18dp,R.drawable.ic_account_circle_white_18dp,R.drawable.ic_shopping_cart_white_18dp,R.drawable.ic_exit_to_app_white_18dp};
        for (int i=0;i<drawer_text_items.length;i++)
        {
            Map<String,Object> item = new HashMap<>();
            item.put("text",drawer_text_items[i]);
            item.put("image",drawer_image_items[i]);
            drawerDataList.add(item);
        }
        //

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        SimpleAdapter drawerAdapter =  new SimpleAdapter(this, drawerDataList,R.layout.nvd_row,new String[]{"text","image"},new int[]{R.id.drawer_item_text,R.id.drawer_item_imageview});
        mDrawerList.setAdapter(drawerAdapter);
        mDrawerList.setOnItemClickListener(new DrawerOnItemClickListener());

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }



    private class DrawerOnItemClickListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

    // The action after the drawer's item clicked
    private void selectItem(int position) {

        Intent intent = new Intent();
        switch(position)
        {
            case 0:
                intent.setClass(getApplicationContext(), MainActivity.class);
                break;
            case 1:
                intent.setClass(getApplicationContext(),UserInfoActivity.class); //
                break;
            case 2:
                intent.setClass(getApplicationContext(),ShoppingCartActivity.class); //
                break;
            case 3:
                Singleton singleton= Singleton.getSingleton();
                singleton.logout();
                intent.setClass(getApplicationContext(), LoginActivity.class);
                break;
        }
        startActivity(intent);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
