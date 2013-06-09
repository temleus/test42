package com.task;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

/**
 * @author Leus Artem
 * @since 02.06.13
 */
public class MainActivity extends SherlockFragmentActivity {

    private SimpleFragmentPagerAdapter adapter;
    private ViewPager pager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        adapter = new SimpleFragmentPagerAdapter(this);

        adapter.addFragment("User", UserFragment.class, null);
        adapter.addFragment("Friends", FriendListFragment.class, null);

        pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);

        pager.setCurrentItem(0);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        super.addContentView(view, params);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public static class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

        private final Context mContext;
        private final ArrayList<FragmentInfo> mFragments = new ArrayList<FragmentInfo>();

        static final class FragmentInfo {
            private final String title;
            private final Class<?> clss;
            private final Bundle args;

            FragmentInfo(String _title, Class<?> _class, Bundle _args) {
                title = _title;
                clss = _class;
                args = _args;
            }
        }

        public SimpleFragmentPagerAdapter(SherlockFragmentActivity activity) {
            super(activity.getSupportFragmentManager());
            mContext = activity;
        }

        public void addFragment(String title, Class<?> clss, Bundle args) {
            FragmentInfo info = new FragmentInfo(title, clss, args);
            mFragments.add(info);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            FragmentInfo info = mFragments.get(position);
            return Fragment.instantiate(mContext, info.clss.getName(), info.args);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragments.get(position).title;
        }
    }
}