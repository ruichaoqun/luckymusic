package com.ruichaoqun.luckymusic.view;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.util.UiUtils;

public class TestActivity extends AppCompatActivity {
    private SearchView mSearchView;
    private AutoCompleteTextView mCompleteTextView;
    private ImageView mImageView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mSearchView = new SearchView(this);
        MenuItem searchItem = menu.add(0, 1, 0, R.string.global_search);
        searchItem.setActionView(this.mSearchView);
        searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS|MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

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
//        this.mCompleteTextView.setAdapter(new a(this));
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
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
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
//        searchItem.expandActionView();
        ThemeHelper.configSearchViewTheme(toolbar,mSearchView);
        return true;
    }

    public ResourceRouter getResourceRouter() {
        return ResourceRouter.getInstance();
    }
}
