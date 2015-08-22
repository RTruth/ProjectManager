package com.edu.prathm.mybim.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.edu.prathm.mybim.R;

import static com.edu.prathm.mybim.extra.FileOperator.getEntryOfSharedPreference;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);

                    String loginValue = getEntryOfSharedPreference(SplashActivity.this, "isLogin");
                    if (loginValue != null) {
                        Boolean isLogin = Boolean.valueOf(loginValue);
                        if (isLogin) {
                            startActivity(new Intent(SplashActivity.this, Home.class));
                            finish();
                        } else {
                            startActivity(new Intent(SplashActivity.this, Login.class));
                            finish();
                        }

                    } else {
                        startActivity(new Intent(SplashActivity.this, Login.class));
                        finish();
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
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
