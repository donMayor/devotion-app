package com.iqube.api;


import android.content.res.AssetManager;
import android.os.Handler;
import android.util.Log;
import com.iqube.devotionapp.R;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class SampleLinesStyledLayout extends BaseSampleActivity {

    public static ArrayList<HashMap<String, String>> All_DailyDevotions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themed_lines);
        
        mAdapter = new TestFragmentAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);




        mIndicator = (LinePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int i, float v, int i2) {
                Log.d("selected", "selected");
                if(!last_scrolled)
                    Splash_timer();
            }



            @Override
            public void onPageSelected(int i) {

                if(i == 2){
                    last_scrolled = true;
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ArrayList<HashMap<String, String>> result = ParseFileString(ReadDevotionAssetFile(
                this.getAssets(), devotion_filename), devotion_search_string, devotion_Key);

        DataBaseHandler db= new DataBaseHandler(this);
        db.Upgrade();
        db.InsertIntoDB(result,true);


    }


    public void Splash_timer(){
        new Handler().postDelayed(new Runnable() {

        /*
         * Showing splash screen with a timer. This will be useful when you
         * want to show case your app logo / company
         */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                SampleLinesStyledLayout.mPager.setCurrentItem(SampleLinesStyledLayout.mPager.getCurrentItem() + 1);
            }
        }, 3000);
    }

    public String ReadDevotionAssetFile(AssetManager asset, String filename){
        AssetManager assetManager= asset;
        InputStream input;
        try {
            input = assetManager.open(filename);

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            // byte buffer into a string
            String text = new String(buffer);
            return text;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }

    }

    public ArrayList<HashMap<String, String>> ParseFileString(String fileString, String[] search_string, String[] Key ){
        //Log.d("String", fileString);
        ArrayList<HashMap<String, String>> devotion_key_value = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> HashMap_dayContent= new HashMap<String, String>();

        int counter =0;
        for(int i=0; i<8; i++){
            HashMap_dayContent = new HashMap<String, String>();
            for (int k=0; k<search_string.length; ){

                String dayContent = fileString.substring(fileString.indexOf(search_string[k], counter), fileString.indexOf(search_string[k+1], counter));
                HashMap_dayContent.put(Key[k/2], TrimString(dayContent, search_string[k]));
                Log.d(Key[k/2], TrimString(dayContent, search_string[k]));
                if(k==28){
                    counter = fileString.indexOf("</intercession_ii>", counter)+1;
                    HashMap_dayContent.put(DataBaseHandler.KEY_BOOKMARK, "0");
                }
                k+=2;
            }

            devotion_key_value.add(HashMap_dayContent);
        }

        return devotion_key_value;
    }

    public String TrimString(String trim_string, String to_remove){
        trim_string = trim_string.replace(to_remove, "");
        trim_string = trim_string.replace("\n", " ");

        return  trim_string;

    }



}