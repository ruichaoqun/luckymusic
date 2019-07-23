package com.ruichaoqun.luckymusic.view.search;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.basic.BaseToolBarActivity;

public class SearchActivity extends BaseToolBarActivity {

    public static void launchFrom(Context context) {
        Intent intent = bringActivityToFrontIntent(context);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    private static Intent bringActivityToFrontIntent(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

}
