package com.ruichaoqun.luckymusic.ui.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;


import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.activity.BaseToolBarActivity;
import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.utils.UiUtils;

public class SearchActivity extends BaseToolBarActivity {
    private SearchView mSearchView;
    private AutoCompleteTextView mCompleteTextView;
    private ImageView mImageView;


    public static void launchFrom(Context context) {
        Intent intent = bringActivityToFrontIntent(context);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search;
    }


    @Override
    protected void initParams() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    private static Intent bringActivityToFrontIntent(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mSearchView = new SearchView(this);
        MenuItem searchItem = menu.add(1, 10, 10, R.string.global_search);
        MenuItemCompat.setActionView(searchItem, this.mSearchView);
        MenuItemCompat.setShowAsAction(searchItem, MenuItem.SHOW_AS_ACTION_ALWAYS|MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

        MenuItem add2 = menu.add(0, 2, 0, "");
        this.mImageView = new ImageView(this);
        this.mImageView.setPadding(UiUtils.dp2px(14f), 0,UiUtils.dp2px(16f), 0);
        this.mImageView.setImageResource(R.drawable.icon_menu);
        this.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ThemeHelper.configDrawableTheme(this.mImageView.getDrawable(), getResourceRouter().getToolbarIconColor());
        MenuItemCompat.setActionView(add2, (View) this.mImageView);
        MenuItemCompat.setShowAsAction(add2, MenuItem.SHOW_AS_ACTION_ALWAYS);


        this.mCompleteTextView =  this.mSearchView.findViewById(R.id.search_src_text);
//        this.mCompleteTextView.setAdapter(new onColorGet(this));
        this.mCompleteTextView.setHint(getString(R.string.global_search_hint));
        this.mCompleteTextView.setDropDownBackgroundDrawable(new ColorDrawable(getResourceRouter().getPopupBackgroundColor()));
        this.mCompleteTextView.setDropDownVerticalOffset(UiUtils.dp2px(5.0f));
        this.mCompleteTextView.setThreshold(1);
        this.mSearchView.setSubmitButtonEnabled(false);
        this.mSearchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int i) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int i) {
                return false;
            }
        });
        this.mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                finish();
                return false;
            }
        });
        MenuItemCompat.expandActionView(searchItem);
        ThemeHelper.configSearchViewTheme(toolbar,mSearchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return super.onOptionsItemSelected(menuItem);
    }


}
