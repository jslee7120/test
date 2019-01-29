package yaksok.dodream.com.yaksok.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import yaksok.dodream.com.yaksok.fragment.Healthy;
import yaksok.dodream.com.yaksok.fragment.Medical;

public class ContentsPagerAdapter extends FragmentPagerAdapter{
    private int mPageCount;


    public ContentsPagerAdapter(FragmentManager fm, int pageCount) {

        super(fm);

        this.mPageCount = pageCount;

    }

    @Override

    public Fragment getItem(int position) {

        switch (position) {

            case 0:

                Healthy homeFragment = new Healthy();

                return homeFragment;



            case 1:

                Medical gameFragment = new Medical();

                return gameFragment;



            default:

                return null;

        }

    }



    @Override

    public int getCount() {

        return mPageCount;

    }

}
