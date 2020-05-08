package com.ruichaoqun.luckymusic.ui.equalizer.defaultsetting.savepreset;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ruichaoqun.luckymusic.base.activity.BaseMVPActivity;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.activity.SimpleMVPActivity;
import com.ruichaoqun.luckymusic.data.bean.CustomEqBean;
import com.ruichaoqun.luckymusic.media.audioeffect.AudioEffectJsonPackage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Rui Chaoqun
 * @date :2020-5-8 0:41:09
 * description:EqualizerSavePresetActivity
 */
public class EqualizerSavePresetActivity extends SimpleMVPActivity<EqualizerSavePresetContact.Presenter> implements EqualizerSavePresetContact.View{
    private static final String INTENT_EXTRA_AUDIO_EFFECT = "effect;";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private EqualizerSavePresetAdapter mSavePresetAdapter;
    private List<CustomEqBean> mList = new ArrayList<>();
    private AudioEffectJsonPackage mCurrentJsonPackage;

    public static void launchFrom(Activity activity,AudioEffectJsonPackage mCurrentJsonPackage){
        Intent intent = new Intent(activity,EqualizerSavePresetActivity.class);
        intent.putExtra(INTENT_EXTRA_AUDIO_EFFECT,mCurrentJsonPackage);
        activity.startActivityForResult(intent,101);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.equalizer_save_preset_activity;
    }

    @Override
    protected void initParams() {
        mCurrentJsonPackage = getIntent().getParcelableExtra(INTENT_EXTRA_AUDIO_EFFECT);
    }

    @Override
    protected void initView() {
        mSavePresetAdapter = new EqualizerSavePresetAdapter(mList);
        View view = LayoutInflater.from(this).inflate(R.layout.item_adapter_equalizer_save_preset_add,null);
        ImageView imageView = view.findViewById(R.id.add_view);
        mSavePresetAdapter.addHeaderView(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mSavePresetAdapter);
        imageView.setBackgroundResource(R.drawable.bac_add_view);
        view.setOnClickListener(v -> showAddViewDialog());
        mSavePresetAdapter.setOnItemClickListener((adapter, view1, position) -> {
            CustomEqBean customEqBean = mList.get(position);
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < mCurrentJsonPackage.getEq().getEqs().size(); i++) {
                builder.append(mCurrentJsonPackage.getEq().getEqs().get(i));
                if(i < mCurrentJsonPackage.getEq().getEqs().size() - 1){
                    builder.append(",");
                }
            }
            customEqBean.setEqJson(builder.toString());
            mPresenter.saveOrUpdate(customEqBean);
            mCurrentJsonPackage.getEq().setFileName(customEqBean.getEqTitle());
            mPresenter.setAudioEffectJsonPackage(mCurrentJsonPackage);
            setResult(Activity.RESULT_OK);
            finish();
        });
    }

    private void showAddViewDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.equalizer_save_preset_save_title)
                .input(getString(R.string.equalizer_activity_rename_hint),"", false, (dialog, input) -> {
                    if(checkInput(input.toString())){
                        CustomEqBean customEqBean = new CustomEqBean();
                        customEqBean.setEqTitle(input.toString());
                        StringBuilder builder = new StringBuilder();
                        for (int i = 0; i < mCurrentJsonPackage.getEq().getEqs().size(); i++) {
                            builder.append(mCurrentJsonPackage.getEq().getEqs().get(i));
                            if(i < mCurrentJsonPackage.getEq().getEqs().size() - 1){
                                builder.append(",");
                            }
                        }
                        customEqBean.setEqJson(builder.toString());
                        mPresenter.saveOrUpdate(customEqBean);
                        mCurrentJsonPackage.getEq().setFileName(input.toString());
                        mPresenter.setAudioEffectJsonPackage(mCurrentJsonPackage);
                        setResult(Activity.RESULT_OK);
                        finish();
                    }else{
                        showToast(R.string.equalizer_save_preset_name_exit);
                    }
                })
                .cancelable(true)
                .show();
    }

    private boolean checkInput(String toString) {
        for (int i = 0; i < mList.size(); i++) {
            if(TextUtils.equals(mList.get(i).getEqTitle(),toString)){
                return false;
            }
        }
        String[] strings = getResources().getStringArray(R.array.local_eq_title);
        for (int i = 0; i < strings.length; i++) {
            if(TextUtils.equals(strings[i],toString)){
                return false;
            }
        }
        return true;
    }

    @Override
    protected void initData() {
        List<CustomEqBean> list = mPresenter.getAllCustomEq();
        if(list != null){
            mList.addAll(list);
            mSavePresetAdapter.notifyDataSetChanged();
        }
    }
}
