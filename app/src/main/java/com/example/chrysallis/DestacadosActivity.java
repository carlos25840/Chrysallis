package com.example.chrysallis;

import android.os.Bundle;

import com.example.chrysallis.adapters.ViewPagerAdapter;
import com.example.chrysallis.fragments.FragmentChat;
import com.example.chrysallis.fragments.FragmentExplore;
import com.example.chrysallis.fragments.FragmentHome;
import com.example.chrysallis.fragments.FragmentProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class DestacadosActivity extends AppCompatActivity {
    private MenuItem prevMenuItem;

    private FragmentHome homeFragment;
    private FragmentProfile profileFragment;
    private FragmentExplore exploreFragment;
    private FragmentChat chatFragment;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destacados);
        //Initializing viewPager
        viewPager = findViewById(R.id.viewpager);

        final BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    navView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                navView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        homeFragment=new FragmentHome();
        profileFragment=new FragmentProfile();
        exploreFragment=new FragmentExplore();
        chatFragment=new FragmentChat();
        adapter.addFragment(homeFragment);
        adapter.addFragment(profileFragment);
        adapter.addFragment(exploreFragment);
        adapter.addFragment(chatFragment);
        viewPager.setAdapter(adapter);
    }



    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_profile:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_explore:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_chat:
                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }


    };

}
