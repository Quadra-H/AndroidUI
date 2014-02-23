package com.example.diymarket.content.circle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.diymarket.R;
import com.example.diymarket.content.tab.IconPagerAdapter;

public class TestFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
    public static final String[] CONTENT = new String[] { "This", "Is", "A", "Test", };
    public static final int[] ICONS = new int[] {
            R.drawable.perm_group_best,
            R.drawable.perm_group_new,
            R.drawable.perm_group_my,
            R.drawable.perm_group_like
    };

    private int mCount = CONTENT.length;

    public TestFragmentAdapter(FragmentManager fm) {
        super(fm);//
    }

    @Override
    public Fragment getItem(int position) {
        return TestFragment.newInstance(CONTENT[position % CONTENT.length]);
    }//

    @Override
    public int getCount() {
        return mCount;//
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return TestFragmentAdapter.CONTENT[position % CONTENT.length];
    }

    @Override
    public int getIconResId(int index) {
      return ICONS[index % ICONS.length];
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}