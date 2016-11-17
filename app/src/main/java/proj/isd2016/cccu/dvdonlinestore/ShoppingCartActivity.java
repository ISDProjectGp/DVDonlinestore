package proj.isd2016.cccu.dvdonlinestore;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import proj.isd2016.cccu.dvdonlinestore.database.Moive;
import proj.isd2016.cccu.dvdonlinestore.database.Singleton;

public class ShoppingCartActivity extends BaseActivity {

    ListView moiveList ;
    Button btn_checkout;
    ShoppingCartAdapter moadapter;

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        if (moadapter!=null)
        {
            moadapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        initActionBar();
        initDrawer();
        initView();

    }

    private void initView()
    {
         final Singleton single = Singleton.getSingleton();
         moiveList = (ListView) findViewById(R.id.cart_list);

         moadapter = new ShoppingCartAdapter(single.getShoppingCart().getMoives(), getApplicationContext(), single.getShoppingCart().getQuatity());
         moiveList.setAdapter(moadapter);

         btn_checkout = (Button) findViewById(R.id.btn_checkout);
         btn_checkout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

               if (!single.isCartEmpty())
               {
                   if ( single.isPayable())
                   {
                       Intent intent = new Intent();
                       intent.setClass(getApplicationContext(), InoviceActivity.class);
                       startActivity(intent);
                   } else
                   {
                       Toast.makeText(ShoppingCartActivity.this, "Sorry , you don't have enough credits", Toast.LENGTH_SHORT).show();
                   }
               } else
               {
                   Toast.makeText(ShoppingCartActivity.this, "Nothing in your shopping cart !!", Toast.LENGTH_SHORT).show();
               }

             }
         });
    }

    private void initActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009900")));
        actionBar.setTitle("Shopping Cart");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_info, menu);
        return true;
    }

    private class ShoppingCartAdapter extends BaseAdapter {

        List<Moive> moiveList ;
        List<Integer> qualist;
        Context context;

        public ShoppingCartAdapter(List<Moive> moiveList , Context context,List<Integer> qualist) {
            this.qualist = qualist;
            this.moiveList = moiveList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return moiveList.size();
        }

        @Override
        public Moive getItem(int position) {
            return moiveList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return moiveList.indexOf(getItem(position));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final int pos = position ;
            LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = layoutInflater .inflate(R.layout.shopping_cart_item, null);

                TextView moive_title = (TextView) convertView.findViewById(R.id.cart_moive_name);
                moive_title.setText(getItem(position).getMoiveTitle());

                ImageView img = (ImageView) convertView.findViewById(R.id.cart_moive_poster);
                loadImageFromStorage(context, getItem(position).getMoiveID(), img);


                TextView price_tv = (TextView) convertView.findViewById(R.id.cart_moive_price);
                price_tv.setText("$" + getItem(position).getPrice());

                TextView quality = (TextView) convertView.findViewById(R.id.quat_tv);
                quality.setText("×" + qualist.get(position));

                ImageView bin = (ImageView) convertView.findViewById(R.id.btn_bin);
                bin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Singleton.getSingleton().getShoppingCart().deleteMoive(getItem(pos));
                        notifyDataSetChanged();
                    }
                });


            return convertView;
        }


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
