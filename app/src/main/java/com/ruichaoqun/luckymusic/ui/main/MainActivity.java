package com.ruichaoqun.luckymusic.ui.main;

import android.os.Bundle;

import com.google.android.material.internal.ScrimInsetsFrameLayout;
import com.google.android.material.tabs.TabLayout;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;


import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.activity.BaseMVPActivity;
import com.ruichaoqun.luckymusic.base.adapter.BaseFragmentStateAdapter;
import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.ui.main.discover.WanAndroidFragment;
import com.ruichaoqun.luckymusic.ui.main.mine.MineFragment;
import com.ruichaoqun.luckymusic.ui.main.video.VideoFragment;
import com.ruichaoqun.luckymusic.ui.search.SearchActivity;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseMVPActivity<MainContact.Presenter> implements MainContact.View {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;
    @BindView(R.id.iv_back)
    ImageView mDrawIcon;
    private VectorDrawableCompat mDrawerIconDrawable;
    private ActionBarDrawerToggle mDrawerToggle;
    private List<Fragment> mFragments;
    private MainDrawer mMainDrawer = new MainDrawer(this);


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
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        Observable.fromIterable(list)
                .flatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Integer integer) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<Integer>() {
                            @Override
                            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                                Thread.sleep(2000);
                                emitter.onNext(integer);
                            }
                        });
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.w("AAAA",integer+"");
                    }
                });
    }

    @Override
    protected void initData() {
        initViewPager();
    }

    private void initDraw() {
        mMainDrawer.initView();
        this.mDrawerIconDrawable = VectorDrawableCompat.create(getResources(), R.drawable.icon_menu, null);
        this.mDrawIcon.setImageDrawable(this.mDrawerIconDrawable);
        this.mDrawerToggle = new ActionBarDrawerToggle(this, this.mDrawerLayout, getToolbar(), R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                MainActivity.this.onDrawerClosed(drawerView);
                mScrollView.fullScroll(View.FOCUS_UP);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };
        this.mDrawerLayout.addDrawerListener(this.mDrawerToggle);
    }

    private void initViewPager() {
        mFragments = new ArrayList<>();
        mFragments.add(new MineFragment());
        mFragments.add(new WanAndroidFragment());
        mFragments.add(new VideoFragment());
        mViewPager.setAdapter(new BaseFragmentStateAdapter(getSupportFragmentManager(), mFragments, getResources().getStringArray(R.array.main_titles)));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public void toggleDrawerMenu() {
        if (this.mDrawerLayout != null && this.mScrollView != null) {
            if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                this.mDrawerLayout.closeDrawer(GravityCompat.START);
                return;
            }
//            this.isFromDraggingOpen = false;
            this.mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        toggleDrawerMenu();
    }

    private void onDrawerClosed(View drawerView) {

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
        this.mDrawIcon.setImageDrawable(ThemeHelper.configDrawableThemeUseTint(this.mDrawerIconDrawable.getConstantState().newDrawable(), getResourceRouter().getToolbarIconColor()));
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

}
