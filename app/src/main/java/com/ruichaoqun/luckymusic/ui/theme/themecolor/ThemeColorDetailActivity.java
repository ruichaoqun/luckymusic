package com.ruichaoqun.luckymusic.ui.theme.themecolor;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.activity.BaseMvpToolbarActivity;
import com.ruichaoqun.luckymusic.base.adapter.pager.ViewPagerAdapter;
import com.ruichaoqun.luckymusic.theme.core.ThemeConfig;
import com.ruichaoqun.luckymusic.utils.UiUtils;
import com.ruichaoqun.luckymusic.widget.ColorPicker;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

import static android.view.MenuItem.SHOW_AS_ACTION_ALWAYS;

/**
 * 主题自选色页面
 * @author Administrator
 */
public class ThemeColorDetailActivity extends BaseMvpToolbarActivity<ThemeColorDetailContact.Presenter> implements ThemeColorDetailContact.View {
    public static final String INTENT_EXTRA_COLOR = "extra_color";

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.model)
    ImageView mModel;

    private List<Integer> colorList;
    private int currentColor;

    public static void launchFrom(Activity activity,int result) {
        activity.startActivityForResult(new Intent(activity, ThemeColorDetailActivity.class),result);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_theme_color_detail;
    }


    @Override
    protected void initParams() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        setTitle(R.string.theme_color_detail_activity_title);
        colorList = Arrays.asList(new Integer[]{-13552072, -6543440, -10011977, -12627531, -14575885, -16728876, -24576, -4668160, -7617718, -11751600, -16738680, -689152, -769226, -1499549, -4056997, 0});
        int currentThemeId = ThemeConfig.getCurrentThemeId();
        currentColor = currentThemeId == ThemeConfig.THEME_CUSTOM_COLOR?ThemeConfig.getSelectedColor():colorList.get(0).intValue();
        mModel.setColorFilter(currentColor, PorterDuff.Mode.DST_OVER);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new Adapter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu2) {
        menu2.add(0,0,0,R.string.global_confirm).setShowAsAction(SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        ThemeConfig.updateCurrentAndSelectedColor(currentColor,currentColor);
        Intent intent = new Intent();
        intent.putExtra(INTENT_EXTRA_COLOR, currentColor);
        setResult(Activity.RESULT_OK, intent);
        finish();
        return true;
    }

    class Adapter extends PagerAdapter{

        @Override
        public int getCount() {
            return 2;
        }



        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return object == view;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            Context context = container.getContext();
            if(position == 0){
                RecyclerView recyclerView = new RecyclerView(context);
                recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
                container.addView(recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
                recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                        if(position == 0){
                            outRect.set(UiUtils.dp2px(7.0f),0,0,0);
                        }else if(position == colorList.size()-1){
                            outRect.set(UiUtils.dp2px(10.0f),0,UiUtils.dp2px(1.0f),0);
                        }else {
                            outRect.set(UiUtils.dp2px(10.0f),0,0,0);
                        }
                    }
                });
                ThemeColorDetailAdapter adapter = new ThemeColorDetailAdapter(R.layout.item_adapter_theme_color_detail,colorList);
                adapter.setCurrentColor(currentColor);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter a, View view, int position) {
                        if(colorList.get(position) == 0){
                            mViewPager.setCurrentItem(1);
                            return;
                        }
                        currentColor = colorList.get(position);
                        mModel.setColorFilter(currentColor, PorterDuff.Mode.DST_OVER);
                        adapter.setCurrentColor(currentColor);
                    }
                });

                return recyclerView;
            }
            RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.layout_color_picker, container, false);
            relativeLayout.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(0);
                }
            });
            ColorPicker colorPicker = relativeLayout.findViewById(R.id.color_picker);
            colorPicker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
                @Override
                public void onColorChanged(int color) {
                    currentColor = color;
                    mModel.setColorFilter(currentColor, PorterDuff.Mode.DST_OVER);
                }
            });
            container.addView(relativeLayout);
            return relativeLayout;
        }
    }
}