package com.ruichaoqun.luckymusic.ui.main.mine;


import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.theme.impl.OnThemeResetListener;
import com.ruichaoqun.luckymusic.ui.equalizer.EqualizerActivity;
import com.ruichaoqun.luckymusic.ui.localmedia.LocalMediaActivity;
import com.ruichaoqun.luckymusic.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment implements MainActivity.DispatchResetThemeInterface {
    @BindView(R.id.layout_loacal)
    LinearLayout layoutLocal;

    public MineFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(value = {R.id.layout_loacal})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.layout_loacal:
                LocalMediaActivity.launchFrom(getActivity());
                break;
        }
    }

    @Override
    public void dispatchResetTheme() {
        resetTheme(getView());
    }

    private void resetTheme(View view) {
        if(view instanceof OnThemeResetListener){
            ((OnThemeResetListener) view).onThemeReset();
        }
        if(view instanceof ViewGroup){
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                resetTheme(((ViewGroup) view).getChildAt(i));
            }
        }
    }
}
