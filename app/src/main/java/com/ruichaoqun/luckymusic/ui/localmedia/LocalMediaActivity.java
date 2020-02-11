package com.ruichaoqun.luckymusic.ui.localmedia;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.activity.BaseMVPActivity;
import com.ruichaoqun.luckymusic.ui.localmedia.fragment.album.AlbumListFragment;
import com.ruichaoqun.luckymusic.ui.localmedia.fragment.artist.ArtistListFragment;
import com.ruichaoqun.luckymusic.ui.localmedia.fragment.songs.LocalMediaFragment;

import butterknife.BindView;

/**
 * @author Rui Chaoqun
 * @date :2019-12-26 9:42:11
 * description:LocalMediaActivity
 */
public class LocalMediaActivity extends BaseMVPActivity<LocalMediaContact.Presenter> implements LocalMediaContact.View{
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private String[] mStrings;

    public static void launchFrom(Context context) {
        context.startActivity(new Intent(context, LocalMediaActivity.class));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.local_media_activity;
    }

    @Override
    protected void initParams() {

    }

    @Override
    protected void initView() {
        setTitle(R.string.local_music);
        mStrings = getResources().getStringArray(R.array.local_music_tab);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onMediaServiceConnected() {
        super.onMediaServiceConnected();
        LocalMediaPagerAdapter adapter = new LocalMediaPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(mStrings.length);
    }

    class LocalMediaPagerAdapter extends FragmentPagerAdapter {
        public LocalMediaPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return Fragment.instantiate(LocalMediaActivity.this, LocalMediaFragment.class.getName(), null);
                case 1:
                    return Fragment.instantiate(LocalMediaActivity.this, ArtistListFragment.class.getName(), null);
                case 2:
                    return Fragment.instantiate(LocalMediaActivity.this, AlbumListFragment.class.getName(), null);
                default:
                    Fragment instantiate = Fragment.instantiate(LocalMediaActivity.this, LocalMediaFragment.class.getName(), null);
                    return instantiate;
            }
        }

        @Override
        public CharSequence getPageTitle(int i) {
            return LocalMediaActivity.this.mStrings[i];
        }

        @Override
        public int getCount() {
            return LocalMediaActivity.this.mStrings.length;
        }
    }


}
