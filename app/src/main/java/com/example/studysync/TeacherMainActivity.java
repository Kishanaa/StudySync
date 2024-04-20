package com.example.studysync;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    private int selectedTab=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);

//        Toolbar toolbar=findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        SectionPagerAdapter PagerAdapter=new SectionPagerAdapter(getSupportFragmentManager(),getLifecycle());
//        ViewPager2 pager=findViewById(R.id.viewPager2);
//        pager.setAdapter(PagerAdapter);
//
//        TabLayout tabLayout=findViewById(R.id.tabs);
//
//        new TabLayoutMediator(tabLayout,pager,(tab,position)->{
//            switch (position){
//                case 0:
//                    tab.setIcon(R.drawable.users_alt_24).setText("Students");
//
//                    break;
//                case 1:
//                    tab.setIcon(R.drawable.books_medical_24).setText("Subjects");
//                    break;
//                case 2:
//                    tab.setIcon(R.drawable.chart_user_24).setText("Result");
//                    break;
//            }
//        }).attach();

        final LinearLayout homeLayout=findViewById(R.id.homeLayout);
        final LinearLayout bookmarkLayout=findViewById(R.id.bookmarkLayout);
        final LinearLayout notificationLayout=findViewById(R.id.notificationLayout);

        final ImageView homeImage=findViewById(R.id.home_image);
        final ImageView bookmarkImage=findViewById(R.id.bookmark_image);
        final ImageView notificationImage=findViewById(R.id.notification_image);

        final TextView homeText=findViewById(R.id.home_text);
        final TextView bookmarkText=findViewById(R.id.bookmark_text);
        final TextView notificationText=findViewById(R.id.notification_text);

//        set home fragment by default
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container,Teacher2Fragment.class,null)
                .commit();

        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                check is home is already selected or not
                if (selectedTab!=1){

//                    set home fragment
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container,Teacher2Fragment.class,null)
                            .commit();

//                    unselect other tabs expect home tab

                    bookmarkText.setVisibility(View.GONE);
                    notificationText.setVisibility(View.GONE);

                    bookmarkImage.setImageResource(R.drawable.subject);
                    notificationImage.setImageResource(R.drawable.result);

                    bookmarkLayout.setBackgroundColor(Color.TRANSPARENT);
                    notificationLayout.setBackgroundColor(Color.TRANSPARENT);

//                    select home tab
                    homeText.setVisibility(View.VISIBLE);
                    homeImage.setImageResource(R.drawable.user_selected);
                    homeLayout.setBackgroundResource(R.drawable.round_back_home_100);

//                    Create animation
                    ScaleAnimation scaleAnimation=new ScaleAnimation(0.0f,1.0f,0.0f,1f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    homeLayout.startAnimation(scaleAnimation);

//                    set 1st tab as selected
                    selectedTab=1;
                }

            }
        });

        bookmarkLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                check is home is already selected or not
                if (selectedTab!=2){

//                    set bookmark fragment
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container,Teacher3Fragment.class,null)
                            .commit();

//                    unselect other tabs expect home tab

                    homeText.setVisibility(View.GONE);
                    notificationText.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.user);
                    notificationImage.setImageResource(R.drawable.result);

                    homeLayout.setBackgroundColor(Color.TRANSPARENT);
                    notificationLayout.setBackgroundColor(Color.TRANSPARENT);

//                    select home tab
                    bookmarkText.setVisibility(View.VISIBLE);
                    bookmarkImage.setImageResource(R.drawable.subject_selected);
                    bookmarkLayout.setBackgroundResource(R.drawable.round_back_home_100);

//                    Create animation
                    ScaleAnimation scaleAnimation=new ScaleAnimation(0.0f,1.0f,0.0f,1f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    bookmarkLayout.startAnimation(scaleAnimation);

//                    set 1st tab as selected
                    selectedTab=2;
                }
            }
        });
        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                check is home is already selected or not
                if (selectedTab!=3){

//                    set notification fragment
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container,Teacher4Fragment.class,null)
                            .commit();

//                    unselect other tabs expect home tab

                    bookmarkText.setVisibility(View.GONE);
                    homeText.setVisibility(View.GONE);

                    bookmarkImage.setImageResource(R.drawable.subject);
                    homeImage.setImageResource(R.drawable.user);

                    bookmarkLayout.setBackgroundColor(Color.TRANSPARENT);
                    homeLayout.setBackgroundColor(Color.TRANSPARENT);

//                    select home tab
                    notificationText.setVisibility(View.VISIBLE);
                    notificationImage.setImageResource(R.drawable.result_selected);
                    notificationLayout.setBackgroundResource(R.drawable.round_back_home_100);

//                    Create animation
                    ScaleAnimation scaleAnimation=new ScaleAnimation(0.0f,1.0f,0.0f,1f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    notificationLayout.startAnimation(scaleAnimation);

//                    set 1st tab as selected
                    selectedTab=3;
                }
            }
        });
    }

//    private class SectionPagerAdapter extends FragmentStateAdapter {
//        public SectionPagerAdapter(FragmentManager fm, Lifecycle lifecycle){
//            super(fm,lifecycle);
//        }
//        @Override
//        public int getItemCount(){
//            return 3;
//        }
//        @Override
//        public Fragment createFragment(int position){
//            switch (position){
//                case 0:
//                    return new Teacher2Fragment();
//                case 1:
//                    return new Teacher3Fragment();
//                case 2:
//                    return new Teacher4Fragment();
//            }
//            return null;
//        }
//    }
}