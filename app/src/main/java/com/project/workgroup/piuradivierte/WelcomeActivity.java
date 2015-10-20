package com.project.workgroup.piuradivierte;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.*;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.project.workgroup.piuradivierte.util.PrefUtils;
import com.project.workgroup.piuradivierte.view.activities.MainActivity;

public class WelcomeActivity extends ActionBarActivity {


    static final int NUM_PAGES = 3;
    ViewPager pager;
    PagerAdapter pagerAdapter;
    LinearLayout circles;
    Button skip;
    Button done;
    ImageButton next;
    public static LoginButton loginButton;


    /*
        This is nasty but as the transparency of the fragments increases when swiping the underlying
        Activity becomes visible, so we change the pager opacity on the last slide in
        setOnPageChangeListener below
     */
    boolean isOpaque = true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /*


            Setting this makes sure we draw fullscreen, without this the transparent Activity shows
            the bright orange notification header from the main Activity below
        */
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        setContentView(R.layout.activity_welcome);

        //getSupportActionBar().hide();

        skip = Button.class.cast(findViewById(R.id.skip));
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
                //startActivity(i);
                finish();
            }
        });
        next = ImageButton.class.cast(findViewById(R.id.next));
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pager.getCurrentItem() + 1, true);
            }
        });
        done = Button.class.cast(findViewById(R.id.done));
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.markTosAccepted(WelcomeActivity.this);
                Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setPageTransformer(true, new CrossfadePageTransformer());
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //See note above for why this is needed
                if(position == NUM_PAGES - 1 && positionOffset > 0){
                    if(isOpaque) {
                        pager.setBackgroundColor(Color.TRANSPARENT);
                        isOpaque = false;
                    }
                }else{
                    if(!isOpaque) {
                        pager.setBackgroundColor(getResources().getColor(R.color.background_opaque));
                        isOpaque = true;
                    }
                }
            }
            @Override
            public void onPageSelected(int position) {
                setIndicator(position);
                if(position == NUM_PAGES - 1){
                    skip.setVisibility(View.GONE);
                    next.setVisibility(View.GONE);
                    done.setVisibility(View.VISIBLE);
                }else if(position < NUM_PAGES - 1){
                    skip.setVisibility(View.VISIBLE);
                    next.setVisibility(View.VISIBLE);
                    done.setVisibility(View.GONE);
                }
                /*else if(position == NUM_PAGES - 1){
                    endTutorial();
                }*/
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                //Unused
            }
        });
        buildCircles();
    }

    /*
        The last fragment is transparent to enable the swipe-to-finish behaviour seen on Google's apps
        So our viewpager circle indicator needs to show NUM_PAGES - 1
     */
    private void buildCircles(){
        circles = LinearLayout.class.cast(findViewById(R.id.circles));

        float scale = getResources().getDisplayMetrics().density;
        int padding = (int) (5 * scale + 0.5f);

        for(int i = 0 ; i < NUM_PAGES ; i++){
            ImageView circle = new ImageView(this);
            circle.setImageResource(R.drawable.circle);
            circle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            circle.setAdjustViewBounds(true);
            circle.setPadding(padding, 0, padding, 0);
            circles.addView(circle);
        }
        setIndicator(0);
    }

    private void setIndicator(int index){
        if(index < NUM_PAGES){
            for(int i = 0 ; i < NUM_PAGES ; i++){
                ImageView circle = (ImageView) circles.getChildAt(i);
                if(i == index){
                    circle.setImageResource(R.drawable.circle_selected);
                }else {
                    circle.setImageResource(R.drawable.circle);
                }
            }
        }
    }

    private void endTutorial(){
        finish();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(pager.getCurrentItem());
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        // Shows the debug warning, if this is a debug build and the warning has not been shown yet
        if (Config.IS_DOGFOOD_BUILD && !PrefUtils.wasDebugWarningShown(this)) {
            new AlertDialog.Builder(this)
                    .setTitle(Config.DOGFOOD_BUILD_WARNING_TITLE)
                    .setMessage(Config.DOGFOOD_BUILD_WARNING_TEXT)
                    .setPositiveButton(android.R.string.ok, null).show();
            PrefUtils.markDebugWarningShown(this);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment tp = new Fragment();
            switch(position){
                case 0:
                    tp = FragmentOne.newInstance();
                    break;
                case 1:
                    tp = FragmentTwo.newInstance();
                    break;
                case 2:
                    tp = FragmentThree.newInstance();
                    break;
                /*
                case 3:
                    tp = PanelFragment.newInstance(R.layout.fragment_one);
                    break;
                    */
                /*
                case 4:
                    tp = PanelFragment.newInstance(R.layout.fragment_one);
                    break;
                    */
            }

            return tp;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


    public class CrossfadePageTransformer implements ViewPager.PageTransformer {

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public void transformPage(View page, float position) {
            int pageWidth = page.getWidth();

            View backgroundView = page.findViewById(R.id.background);
            //View text = page.findViewById(R.id.content_fragment);
            View phone = page.findViewById(R.id.phone);
            View map = page.findViewById(R.id.maps);
            //View mountain = page.findViewById(R.id.mountain);
            //View mountainNight = page.findViewById(R.id.mountain_night);
            //View rain = page.findViewById(R.id.rain);
            View hands = page.findViewById(R.id.in_hand);


            if (position <= 1) {
                page.setTranslationX(pageWidth * -position);
            }

            if(position <= -1.0f || position >= 1.0f) {
            } else if( position == 0.0f ) {
            } else {
                if(backgroundView != null) {
                    backgroundView.setAlpha(1.0f - Math.abs(position));
                }



                //Text both translates in/out and fades in/out
               /*
                if (text != null) {
                    text.setTranslationX(pageWidth * position);
                    text.setAlpha(1.0f - Math.abs(position));
                }
                */
                //Map + phone - map simple translate, phone parallax effect
                if(map != null){
                    map.setTranslationX(pageWidth * position);
                }

                if(phone != null){
                    phone.setTranslationX((float)(pageWidth/1.2 * position));
                }
                /*
                //Mountain day - fade in/out
                if(mountain != null){
                    mountain.setAlpha(1.0f - Math.abs(position));
                }
                */
                //Mountain night - fade in, but translate out, rain fades in but parallax translate out
                /*
                if(mountainNight != null){
                    if(position < 0){
                        mountainNight.setTranslationX(pageWidth * position);
                    }else{
                        mountainNight.setAlpha(1.0f - Math.abs(position));
                    }
                }
                */

                /*
                if(rain != null){
                    if(position < 0){
                        rain.setTranslationX((float)(pageWidth/1.2 * position));
                    }else{
                        rain.setAlpha(1.0f - Math.abs(position));
                    }
                }
                */

                //Long click device + hands - translate both way but only fade out
                if(hands != null){
                    hands.setTranslationX(pageWidth * position);
                    if(position < 0) {
                        hands.setAlpha(1.0f - Math.abs(position));
                    }
                }


            }
        }
    }

    public static class FragmentOne extends Fragment{

        public static FragmentOne newInstance(){

            FragmentOne fragmentOne = new FragmentOne();
            return fragmentOne;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if(loginButton != null){
                loginButton.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_one , container , false);
            return rootView;
        }
    }
    public static class FragmentTwo extends Fragment{

        public static FragmentTwo newInstance(){

            FragmentTwo fragmentTwo = new FragmentTwo();
            return fragmentTwo;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if(loginButton != null){
                loginButton.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_two , container , false);
            return rootView;
        }

    }
    public static class  FragmentThree extends Fragment{

        CallbackManager mCallbackManager;

        FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                displayWelcomeMenssage(profile);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        };

        public static FragmentThree newInstance(){
            FragmentThree fragmentThree = new FragmentThree();
            return fragmentThree;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            loginButton = (LoginButton) view.findViewById(R.id.login_button);
            loginButton.setVisibility(View.VISIBLE);
            loginButton.setReadPermissions("user_friends");
            loginButton.setFragment(this);
            loginButton.registerCallback(mCallbackManager, mCallback);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
            mCallbackManager = CallbackManager.Factory.create();

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_three , container , false);
            return rootView;
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }

        @Override
        public void onResume() {
            super.onResume();
            Profile profile = Profile.getCurrentProfile();
            displayWelcomeMenssage(profile);
        }

        public void displayWelcomeMenssage(Profile profile){

            if(profile !=null){

            }
        }
    }



}