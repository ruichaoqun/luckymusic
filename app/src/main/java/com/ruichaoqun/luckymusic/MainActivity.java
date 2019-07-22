package com.ruichaoqun.luckymusic;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.ruichaoqun.luckymusic.basic.BaseActivity;
import com.ruichaoqun.luckymusic.basic.BaseToolBarActivity;
import com.ruichaoqun.luckymusic.theme.ThemeHelper;

public class MainActivity extends BaseToolBarActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ImageView mDrawIcon;
    private VectorDrawableCompat mDrawerIconDrawable;
    private ActionBarDrawerToggle mDrawerToggle;
    private ScrollView mScrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar();
        transparentStatusBar(true);
        initDraw();
        applyStatusBarCurrentTheme();
        applyToolbarCurrentTheme();
    }

    private void initDraw() {
        this.mDrawerLayout = findViewById(R.id.drawer_layout);
        this.mDrawIcon = findViewById(R.id.iv_back);
        this.mScrollView = findViewById(R.id.scroll_view);
        this.mDrawerIconDrawable = VectorDrawableCompat.create(getResources(), R.drawable.ap, null);
        this.mDrawIcon.setImageDrawable(this.mDrawerIconDrawable);
        this.mDrawerToggle = new ActionBarDrawerToggle(this, this.mDrawerLayout, getToolbar(), R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                double d2 = 0.0d;
                MainActivity.this.onDrawerClosed(drawerView);
                mScrollView.fullScroll(33);
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
        this.mDrawIcon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toggleDrawerMenu();
            }
        });
        this.mDrawerLayout.addDrawerListener(this.mDrawerToggle);
    }

    public void toggleDrawerMenu() {
        if (this.mDrawerLayout != null && this.mScrollView != null) {
            if (this.mDrawerLayout.isDrawerOpen((int) GravityCompat.START)) {
                this.mDrawerLayout.closeDrawer((int) GravityCompat.START);
                return;
            }
//            this.isFromDraggingOpen = false;
            this.mDrawerLayout.openDrawer((int) GravityCompat.START);
        }
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
    public void setToolbarBackIcon() {
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
