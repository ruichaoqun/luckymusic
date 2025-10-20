package com.ruichaoqun.luckymusic.ui.main.mine;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.databinding.FragmentMineBinding;
import com.ruichaoqun.luckymusic.theme.impl.OnThemeResetListener;
import com.ruichaoqun.luckymusic.ui.localmedia.LocalMediaActivity;
import com.ruichaoqun.luckymusic.ui.main.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment implements MainActivity.DispatchResetThemeInterface, View.OnClickListener {


    public MineFragment() {

    }
    private FragmentMineBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentMineBinding.inflate(inflater,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.layoutLoacal.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_loacal:
                LocalMediaActivity.launchFrom(getActivity());
                break;
        }
    }
}
