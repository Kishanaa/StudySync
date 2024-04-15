package com.example.studysync;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class TeacherMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);

//        Toolbar toolbar=findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        SectionPagerAdapter PagerAdapter=new SectionPagerAdapter(getSupportFragmentManager(),getLifecycle());
        ViewPager2 pager=findViewById(R.id.viewPager2);
        pager.setAdapter(PagerAdapter);

        TabLayout tabLayout=findViewById(R.id.tabs);

        new TabLayoutMediator(tabLayout,pager,(tab,position)->{
            switch (position){
                case 0:
                    tab.setIcon(R.drawable.users_alt_24).setText("Students");

                    break;
                case 1:
                    tab.setIcon(R.drawable.books_medical_24).setText("Subjects");
                    break;
                case 2:
                    tab.setIcon(R.drawable.chart_user_24).setText("Result");
                    break;
            }
        }).attach();
    }

    private class SectionPagerAdapter extends FragmentStateAdapter {
        public SectionPagerAdapter(FragmentManager fm, Lifecycle lifecycle){
            super(fm,lifecycle);
        }
        @Override
        public int getItemCount(){
            return 3;
        }
        @Override
        public Fragment createFragment(int position){
            switch (position){
                case 0:
                    return new Teacher2Fragment();
                case 1:
                    return new Teacher3Fragment();
                case 2:
                    return new Teacher4Fragment();
            }
            return null;
        }
    }
}