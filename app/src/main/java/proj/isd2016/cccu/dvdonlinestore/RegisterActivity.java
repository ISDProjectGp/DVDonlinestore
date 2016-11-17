package proj.isd2016.cccu.dvdonlinestore;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import proj.isd2016.cccu.dvdonlinestore.database.Singleton;

public class RegisterActivity extends AppCompatActivity {

    private EditText editText_login_userName;
    private EditText editText_login_userPassword;
    private EditText editText_login_re_enter;
    private TextView tv_back;
    private Button btn_register;
    private EditText editText_age;
    private static final String FAIL_REGISTER_MESSAGE = "User name already exists";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        initActionBar();


    }

    private void initActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    private void initViews() {
        // init login editText //
        editText_login_userName = (EditText) findViewById(R.id.editText_user_name);
        editText_login_userPassword = (EditText) findViewById(R.id.editText_user_password);
        editText_login_re_enter = (EditText) findViewById(R.id.editText_user_re_enter_password);
        editText_age =  (EditText) findViewById(R.id.tv_age);

       /* editText_login_userName.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Drawable img = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_face_white_18dp);
                        img.setBounds(0, 0, 120,  120);
                        editText_login_userName.setCompoundDrawables(img, null, null, null);
                        editText_login_userName.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });

        editText_login_userPassword.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Drawable img = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_lock_outline_white_18dp);
                        img.setBounds(0, 0, 120,  120);
                        editText_login_userPassword.setCompoundDrawables(img, null, null, null);
                        editText_login_userPassword.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });

        editText_login_re_enter.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Drawable img = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_lock_outline_white_18dp);
                        img.setBounds(0, 0, 120,  120);
                        editText_login_re_enter.setCompoundDrawables(img, null, null, null);
                        editText_login_re_enter.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });*/

        //
        btn_register = (Button) findViewById(R.id.btn_register);
        tv_back = (TextView) findViewById(R.id.tv_back);

        String htmlString="<u>"+tv_back.getText()+"</u>";
        tv_back.setText(Html.fromHtml(htmlString));

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
        });

        btn_register.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {


                String age =  editText_age.getText().toString();
                String userName = editText_login_userName.getText().toString();
                String password = editText_login_userPassword.getText().toString();


                Singleton singleton = Singleton.getSingleton();

            if (editText_login_re_enter.getText().toString().equals(password))
            {
                // TODO: INTPUT VALIDATION
                if (singleton.register(getApplicationContext(),userName,password,Integer.valueOf(age)))
                {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(RegisterActivity.this, "Welcome to Moive", Toast.LENGTH_SHORT).show();
                } else
                {
                    Toast.makeText(RegisterActivity.this, FAIL_REGISTER_MESSAGE, Toast.LENGTH_SHORT).show();
                }
            } else
            {
                Toast.makeText(RegisterActivity.this, "Re-enter password is not equal to password", Toast.LENGTH_SHORT).show();
            }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
