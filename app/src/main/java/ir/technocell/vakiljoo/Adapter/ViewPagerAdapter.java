package ir.technocell.vakiljoo.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ir.technocell.vakiljoo.Fragment.FragmentA;
import ir.technocell.vakiljoo.Fragment.FragmentB;





    public class ViewPagerAdapter extends FragmentPagerAdapter {
        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            if (i == 0)
            {
                fragment = new FragmentA();
            }
            else if (i == 1)
                fragment = new FragmentB();
            return fragment;

        }

        @Override
        public int getCount() {
            return 2;
        }

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }


    }