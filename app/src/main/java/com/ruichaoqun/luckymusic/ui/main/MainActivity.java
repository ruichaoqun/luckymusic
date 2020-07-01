package com.ruichaoqun.luckymusic.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.activity.BaseMVPActivity;
import com.ruichaoqun.luckymusic.base.adapter.BaseFragmentStateAdapter;
import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.theme.ui.CustomThemeTabLayout;
import com.ruichaoqun.luckymusic.ui.main.discover.WanAndroidFragment;
import com.ruichaoqun.luckymusic.ui.main.mine.MineFragment;
import com.ruichaoqun.luckymusic.ui.main.video.VideoFragment;
import com.ruichaoqun.luckymusic.ui.search.SearchActivity;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.ruichaoqun.luckymusic.Constants.CHANGED_THEME;

public class MainActivity extends BaseMVPActivity<MainContact.Presenter> implements MainContact.View {
    @BindView(R.id.tab_layout)
    CustomThemeTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private String[] mTabTitles;
    private PagerAdapter mPagerAdapter;
    private MainDrawer mMainDrawer = new MainDrawer(this);
    private BroadcastReceiver updateThemeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
                dispatchResetTheme();
        }
    };




    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initParams() {

    }

    @Override
    protected void initView() {
        initToolBar();
        transparentStatusBar(true);
        initDraw();
        ((ViewGroup) mPlayBarContainer.getParent()).removeView(mPlayBarContainer);
        ((ViewGroup) findViewById(R.id.layout_drawer_content)).addView(mPlayBarContainer, 1);
        applyStatusBarCurrentTheme();
        applyToolbarCurrentTheme();
        registerReceiver(updateThemeReceiver,new IntentFilter(CHANGED_THEME));
    }

    @Override
    protected void initData() {
        mTabTitles = getResources().getStringArray(R.array.main_titles);
        initViewPager();
    }

    private void initDraw() {
        mMainDrawer.initView();
    }

    private void initViewPager() {
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(mTabTitles.length);
    }

    private void dispatchResetTheme() {
        //设置windowBackground
        setWindowBackground();
        ThemeHelper.setEdgeGlowColor(this.mViewPager, ResourceRouter.getInstance().getThemeColor());
        applyStatusBarCurrentTheme();
        applyToolbarCurrentTheme();
        applyRecentTaskPreviewCurrentTheme();
        mMainDrawer.applyDrawerCurrentTheme();
        mTabLayout.onThemeReset();
        applyMiniPlaybarCurrentTheme();
        invalidateOptionsMenu();
        dispatchThemeToFragment(getSupportFragmentManager());
    }

    private void dispatchThemeToFragment(FragmentManager supportFragmentManager) {
        List<Fragment> fragmentList = supportFragmentManager.getFragments();
        for (int i = 0; i < fragmentList.size(); i++) {
            Fragment fragment = fragmentList.get(i);
            if(fragment instanceof DispatchResetThemeInterface){
                ((DispatchResetThemeInterface)fragment).dispatchResetTheme();
                dispatchThemeToFragment(fragment.getChildFragmentManager());
            }
        }
    }

    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public boolean needToolBar() {
        return false;
    }

    @Override
    public boolean needToobarUpIcon() {
        return false;
    }


    @Override
    public void initToolBar() {
        super.initToolBar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void addStatusBarView() {
        ((ViewGroup) findViewById(R.id.ll_content)).addView(this.statusBarView, 0);
    }

    @Override
    public void applyToolbarCurrentTheme() {
        super.applyToolbarCurrentTheme();
    }

    @Override
    public void onBackPressed() {
        if(!mMainDrawer.interuptAndCloseDrawer()){
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                SearchActivity.launchFrom(this);
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(updateThemeReceiver);
        super.onDestroy();
    }

    class PagerAdapter extends FragmentPagerAdapter{

        public PagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return Fragment.instantiate(MainActivity.this,MineFragment.class.getName(),null);
            }else {
                return Fragment.instantiate(MainActivity.this,WanAndroidFragment.class.getName(),null);
            }

        }

        @Override
        public int getCount() {
            return mTabTitles.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];
        }
    }

    public interface DispatchResetThemeInterface{
        void dispatchResetTheme();
    }
}
