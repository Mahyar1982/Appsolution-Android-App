package com.mahya.appsolution;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private android.support.v4.app.Fragment selectedFragment;
    private long mLastClickTime = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {
                return false;
            }
            mLastClickTime = SystemClock.elapsedRealtime();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = HomeFragment.newInstance();
                    break;
                case R.id.navigation_Service:
                    selectedFragment = ServiceFragment.newInstance();
                    break;
                case R.id.navigation_portfolio:
                    selectedFragment = PortfolioFragment.newInstance();
                    break;
                case R.id.navigation_team:
                    selectedFragment = TeamFragment.newInstance();
                    break;
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, selectedFragment);
            transaction.commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, HomeFragment.newInstance());
        transaction.commit();

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.language) {
            Intent intent = new Intent(MainActivity.this, LanguageActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
