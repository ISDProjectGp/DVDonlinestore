package proj.isd2016.cccu.dvdonlinestore;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import proj.isd2016.cccu.dvdonlinestore.database.Singleton;

public class UserInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initDrawer();
        initActionBar();
        initViews();
    }

    private void initViews() {
        EditText editText_userid = (EditText) findViewById(R.id.edt_userid);
        EditText editText_credit = (EditText) findViewById(R.id.edt_credit);
        EditText editText_age = (EditText) findViewById(R.id.edt_age);
        EditText editText_username = (EditText) findViewById(R.id.edt_username);

        Singleton singleton = Singleton.getSingleton();
        editText_username.setText(singleton.getName());
        editText_credit.setText(String.valueOf(singleton.getCredit()));

        //TODO AGE AND ID
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getStringArray(R.array.drawer_items)[1]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_info, menu);
        return true;
    }
}
