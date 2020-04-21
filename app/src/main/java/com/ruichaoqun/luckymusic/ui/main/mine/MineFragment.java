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
import com.ruichaoqun.luckymusic.ui.equalizer.EqualizerActivity;
import com.ruichaoqun.luckymusic.ui.localmedia.LocalMediaActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {
    @BindView(R.id.layout_loacal)
    LinearLayout layoutLoacal;
    @BindView(R.id.tv_test)
    TextView tvTest;

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
        mLogBuilder = new SpannableStringBuilder();
        addLog("asdasda",0);
        addLog("asdasda",0);
        addLog("asdasda",0);
        addLog("asdasda",0);
        addLog("asdasda",0);
        addLog("asdasda",0);
        addLog("asdasda",0);
        addLog("asdasda",1);
        addLog("asdasda",0);
        addLog("asdasda",1);
        addLog("asdasda",0);
        addLog("asdasda安达科技数据库的回复看电视",1);
    }

    @OnClick(R.id.layout_loacal)
    public void onViewClicked() {
        EqualizerActivity.launchFrom(getActivity());
    }

    private SpannableStringBuilder mLogBuilder;

    private void addLog(String log,int type) {
        mLogBuilder.append(log);
        if(type == 1){
            mLogBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#ffaa2222")),mLogBuilder.length()-log.length(),mLogBuilder.length(),Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }else{
            mLogBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#ff333333")),mLogBuilder.length()-log.length(),mLogBuilder.length(),Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        mLogBuilder.append("\n");
        tvTest.setText(mLogBuilder);
    }
}
