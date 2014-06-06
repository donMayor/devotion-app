package com.iqube.api;


import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import com.iqube.devotionapp.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


class TestFragmentAdapter extends FragmentPagerAdapter {
	
    protected static final Integer[] CONTENT = new Integer[] { R.drawable.image4, R.drawable.image2, R.drawable.image3};
    
    private int mCount = CONTENT.length;

    public TestFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
    	if(position ==2){
            return AuthenticateScreen.newInstance();
        }
        else{
            return TestFragment.newInstance(CONTENT[position % mCount]);
        }
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        //Splash_timer();
        return super.isViewFromObject(view, object);
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
        }, 4000);
    }

}

