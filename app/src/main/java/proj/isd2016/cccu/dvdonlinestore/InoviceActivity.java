package proj.isd2016.cccu.dvdonlinestore;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import proj.isd2016.cccu.dvdonlinestore.R;
import proj.isd2016.cccu.dvdonlinestore.database.Moive;
import proj.isd2016.cccu.dvdonlinestore.database.Singleton;

public class InoviceActivity extends AppCompatActivity {

    private TextView inovice_tv;
    private TextView date_tv;
    private TextView total_tv;
    private TextView subtotal_tv;
    private ListView invoice_list;
    private TextView tv_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inovice);
        initView();
        initActionBar();

    }

    private void initActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009900")));
        actionBar.setTitle("Inovice Details");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    private void initView()
    {
        Singleton single = Singleton.getSingleton();
        double dueCredit = single.getCredit()-single.getShoppingCart().getCartTotalCredit();

        inovice_tv = (TextView) findViewById(R.id.tv_due);
        inovice_tv.setText("$"+Double.toString(dueCredit));

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        date_tv  = (TextView) findViewById(R.id.tv_due_date);
        date_tv.setText(dateFormat.format(date));

        subtotal_tv  = (TextView) findViewById(R.id.subtotal_tv);
        subtotal_tv.setText("$"+String.valueOf(single.getShoppingCart().getCartTotalCredit()));

        total_tv  = (TextView) findViewById(R.id.total);
        total_tv.setText("$"+String.valueOf(single.getShoppingCart().getCartTotalCredit()));

        invoice_list = (ListView) findViewById(R.id.invoice_list);
        invoice_list.setAdapter(new InoviceAdapter(single.getShoppingCart().getMoives(), getApplicationContext(), single.getShoppingCart().getQuatity()));

    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        Singleton.getSingleton().pay(getApplicationContext());
    }


    private class InoviceAdapter extends BaseAdapter {

        List<Moive> moiveList;
        List<Integer> qualist;
        Context context;

        public InoviceAdapter(List<Moive> moiveList, Context context, List<Integer> qualist) {
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
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.cart_items, null);

            TextView moive_title = (TextView) convertView.findViewById(R.id.inovice_item_name);
            moive_title.setText(getItem(position).getMoiveTitle());

            TextView price_tv = (TextView) convertView.findViewById(R.id.invoice_item_pricee);
            double cost = getItem(position).getPrice()*qualist.get(position);
            price_tv.setText("$" + cost);

            TextView tv_details = (TextView) convertView.findViewById(R.id.tv_cart_detail);
            String quality = qualist.get(position)+" × " + "$" + getItem(position).getPrice();
            tv_details.setText(quality);

            return convertView;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inovice, menu);
        return true;
    }


}
