package proj.isd2016.cccu.dvdonlinestore;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import proj.isd2016.cccu.dvdonlinestore.database.Moive;
import proj.isd2016.cccu.dvdonlinestore.database.Singleton;

public class LoginActivity extends AppCompatActivity {

    private EditText editText_login_userName;
    private EditText editText_login_userPassword;
    private Button btn_login;
    private TextView tv_signUp;
    private static final String FAIL_LOGIN_MESSAGE = "Wrong password or user name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initActionBar();
        // There are size groups of demo to show how to use the methods I wrote //

        /*
       System.out.println("--Register:");

        final String accountName = "leolam521" ;
        final String passWord = "qiaoqiaoqiao" ;
        final int age = 40;
        if (singleton.register(getApplicationContext(),accountName,passWord,age))
        {
            System.out.println("Register success");
        } else
        {
            System.out.println("Register fail");
        }
        System.out.println("--");
         */


     /*   2.   Login simulation (You can only choose login or register )
               System.out.println("--Login:");
              final String accountName = "leolam521" ;
             final String passWord = "qiaoqiaoqiao" ;
           if (singleton.login(getApplicationContext(), accountName, passWord))
          {
            System.out.println("Login success");
          } else
         {
            System.out.println("Login fail");
         }
        System.out.println("--");
       // */


    /*     3. Get User Normal Data  simulation (These can be used after login or register )
        System.out.println("--GerUserData:");
        System.out.println(singleton.getName());
        System.out.println(singleton.getCredit());
        System.out.println(singleton.getAge());
        System.out.println("--");
        //
        */

    /*     4. Get User Shoppoing Cart Data  simulation (These can be used after login or register )
        System.out.println("--GetShoppingCartData:");
        System.out.println(singleton.getShoppingCart().getCartTotalCredit());
        List<Moive> moiveList = singleton.getShoppingCart().getMoives();
        for (Moive moive : moiveList)
        {
            System.out.println(moive.getMoiveID());
            System.out.println(moive.getMoiveTitle());
            System.out.println(moive.getPrice());
            System.out.println(moive.getRating());
            System.out.println(moive.getSummary());
        }
        System.out.println("--");
         */

      /*    5. Operate User's Shopping Cart simulation (These can be used after login or register )
        System.out.println("--OperateShoppingCart:");
        Moive sampleMoive = new Moive(123,456,"SuperMan",7,"GoodMoive");
        singleton.getShoppingCart().addMoive(sampleMoive);
        singleton.getShoppingCart().deleteMoive(sampleMoive);
        singleton.getShoppingCart().clearShoppingCart();
        System.out.println("--");
        */


    /*     6. Operate User simulation (These can be used after login or register )
        System.out.println("--OperateUser:");
        System.out.println(singleton.pay(getApplicationContext()));
        singleton.logout();
        System.out.println("--");
        */

    }



    private void initActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }


    private void initViews()
    {
        // init login editText //
        editText_login_userName = (EditText) findViewById(R.id.editText_user_name);
        editText_login_userPassword = (EditText) findViewById(R.id.editText_user_password);



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
*/

        //
        tv_signUp = (TextView) findViewById(R.id.tv_signUP);
        btn_login = (Button) findViewById(R.id.btn_login);

        String htmlString="<u>"+tv_signUp.getText()+"</u>";
        tv_signUp.setText(Html.fromHtml(htmlString));

        tv_signUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }


        });

        btn_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = editText_login_userName.getText().toString();
                String password = editText_login_userPassword.getText().toString();

                Singleton singleton = Singleton.getSingleton();

                // TODO: INTPUT VALIDATION
                if (singleton.login(getApplicationContext(), userName, password)) {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, FAIL_LOGIN_MESSAGE, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }


}
