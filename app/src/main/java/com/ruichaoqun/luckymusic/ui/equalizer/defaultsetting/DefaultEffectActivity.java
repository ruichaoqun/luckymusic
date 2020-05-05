package com.ruichaoqun.luckymusic.ui.equalizer.defaultsetting;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ruichaoqun.luckymusic.base.activity.BaseMVPActivity;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.data.bean.EqualizerPresetBean;
import com.ruichaoqun.luckymusic.data.bean.SongBean;
import com.ruichaoqun.luckymusic.media.MusicService;
import com.ruichaoqun.luckymusic.media.audioeffect.AudioEffectJsonPackage;
import com.ruichaoqun.luckymusic.ui.localmedia.fragment.songs.LocalMediaAdapter;
import com.ruichaoqun.luckymusic.widget.BottomSheetDialog.CustomEqActionSheet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Rui Chaoqun
 * @date :2020-4-29 22:30:54
 * description:DefaultEffectActivity
 */
public class DefaultEffectActivity extends BaseMVPActivity<DefaultEffectContact.Presenter> implements DefaultEffectContact.View{
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private EqualizerPresetAdapter mEqualizerPresetAdapter;
    private List<EqualizerPresetBean> mPresetBeans = new ArrayList<>();
    private AudioEffectJsonPackage mEffectJsonPackage;

    public static void launchFrom(Activity activity){
        activity.startActivityForResult(new Intent(activity,DefaultEffectActivity.class),100);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.default_effect_activity;
    }

    @Override
    protected void initParams() {

    }

    @Override
    protected void initView() {
        setTitle(R.string.equalizer_activity_preset_activity_title);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEqualizerPresetAdapter = new EqualizerPresetAdapter(mPresetBeans);
        mEqualizerPresetAdapter.setEmptyView(R.layout.layout_loading, recyclerView);
        recyclerView.setAdapter(mEqualizerPresetAdapter);
        mEqualizerPresetAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.ll_select:
                        if(mEqualizerPresetAdapter.getItem(position).isChecked()){
                            return;
                        }
                        mEqualizerPresetAdapter.setSelectedPosition(position);
                        AudioEffectJsonPackage.Eq eq = new AudioEffectJsonPackage.Eq();
                        eq.setFileName(mEqualizerPresetAdapter.getItem(position).getTitle());
                        eq.setEqs(mEqualizerPresetAdapter.getItem(position).getmDatas());
                        mEffectJsonPackage.setEq(eq);
                        mPresenter.setAudioEffectJsonPackage(mEffectJsonPackage);
                        updateEqualizer();
                        setResult(Activity.RESULT_OK);
                        break;
                    case R.id.iv_profile:
                        showCustomEqActionSheet(position);
                        break;
                }
            }
        });
    }

    private void showCustomEqActionSheet(final int position) {
        CustomEqActionSheet sheet = new CustomEqActionSheet(this,R.style.f3, new CustomEqActionSheet.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int type) {
                if(type == 0){
                    rename(position);
                }else{
                    delete(position);
                }
            }
        });
        sheet.setText(mEqualizerPresetAdapter.getItem(position).getTitle());
        sheet.show();
    }

    private void delete(int position) {
        if(mEqualizerPresetAdapter.getItem(position).isChecked()){
            mEffectJsonPackage.getEq().setFileName("");
            List<Float> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                list.add(0f);
            }
            mEffectJsonPackage.getEq().setEqs(list);
            mPresenter.setAudioEffectJsonPackage(mEffectJsonPackage);
            updateEqualizer();
            setResult(Activity.RESULT_OK);
        }
        mPresenter.deleteEq(mEqualizerPresetAdapter.getItem(position).getTitle());
        mEqualizerPresetAdapter.getData().remove(position);
        mEqualizerPresetAdapter.notifyItemRemoved(position);
    }

    private void rename(int position) {
        new MaterialDialog.Builder(this)
                .title(R.string.rename)
                .input(getString(R.string.equalizer_activity_rename_hint), mEqualizerPresetAdapter.getItem(position).getTitle(), false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        for (int i = 0; i < mEqualizerPresetAdapter.getData().size(); i++) {
                            if(TextUtils.equals(mEqualizerPresetAdapter.getItem(i).getTitle(),input.toString())){
                                showToast(getString(R.string.equalizer_activity_rename_error));
                                return;
                            }
                        }
                        mPresenter.renameEq(mEqualizerPresetAdapter.getItem(position).getTitle(),input.toString());
                        mEqualizerPresetAdapter.getItem(position).setTitle(input.toString());
                        mEqualizerPresetAdapter.notifyItemChanged(position);
                        if(mEqualizerPresetAdapter.getItem(position).isChecked()){
                            mEffectJsonPackage.getEq().setFileName(input.toString());
                            mPresenter.setAudioEffectJsonPackage(mEffectJsonPackage);
                            setResult(Activity.RESULT_OK);
                        }
                    }
                }).show();
    }

    @Override
    protected void initData() {
        mEffectJsonPackage = mPresenter.getAudioEffectJsonPackage();
        mPresenter.getPresetData(mEffectJsonPackage.getEq());
    }

    @Override
    public boolean isNeedMiniPlayerBar() {
        return false;
    }

    @Override
    public void onLoadPresetDataSuccess(List<EqualizerPresetBean> data) {
        mPresetBeans.addAll(data);
        mEqualizerPresetAdapter.notifyDataSetChanged();
    }

    private void updateEqualizer() {
        if (mControllerCompat != null) {
            mControllerCompat.getTransportControls().sendCustomAction(MusicService.CUSTOM_ACTION_EFFECT, null);
        }
    }
}
