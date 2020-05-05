package com.ruichaoqun.luckymusic.media.audioeffect;

import android.animation.ValueAnimator;
import android.media.audiofx.DynamicsProcessing;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.RequiresApi;

import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.DefaultControlDispatcher;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.gson.Gson;
import com.ruichaoqun.luckymusic.data.DataRepository;
import com.ruichaoqun.luckymusic.media.MusicService;

import java.util.List;

public class AudioEffectProvider implements MediaSessionConnector.CustomActionProvider {
    private DataRepository mDataRepository;
    private int mSessionId;
    private LuckyControlDispatcher mLuckyControlDispatcher;

    private DynamicsProcessing dp;
    private DynamicsProcessing.Eq mEq;
    private DynamicsProcessing.Mbc mMbc;
    private static final int mVariant = 0;
    private static final int mChannelCount = 1;
    private static final int[] bandVal = {31, 62, 125, 250,  500, 1000, 2000, 4000, 8000, 16000};
    private static final int maxBandCount = bandVal.length;
    private static final int PRIORITY = Integer.MAX_VALUE;

    public AudioEffectProvider(int sessionId,DataRepository dataRepository,MediaSessionConnector connector) {
        this.mDataRepository = dataRepository;
        this.mSessionId = sessionId;
        if(Build.VERSION.SDK_INT >= 28){
            DynamicsProcessing.Config.Builder builder = new DynamicsProcessing.Config.Builder(mVariant, mChannelCount, true, maxBandCount, true, maxBandCount, true, maxBandCount, true);
            dp = new DynamicsProcessing(PRIORITY, sessionId, builder.build());
            mEq = new DynamicsProcessing.Eq(true, true, maxBandCount);
            dp.setEnabled(true);
            mLuckyControlDispatcher = new LuckyControlDispatcher();
            connector.setControlDispatcher(mLuckyControlDispatcher);
            init();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void init() {
        boolean enable = mDataRepository.isEffectEnable();
        if(enable){
            mEq.setEnabled(true);
            String effectData = mDataRepository.getEffectData();
            if(!TextUtils.isEmpty(effectData)){
                AudioEffectJsonPackage jsonPackage = new Gson().fromJson(effectData,AudioEffectJsonPackage.class);
                AudioEffectJsonPackage.Eq eq = jsonPackage.getEq();
                if(eq.isOn()){
                    List<Float> list = eq.getEqs();
                    for (int i = 0; i < list.size(); i++) {
                        mEq.getBand(i).setCutoffFrequency(bandVal[i]);
                        mEq.getBand(i).setGain(list.get(i));
                    }
                    dp.setPreEqAllChannelsTo(mEq);
                    dp.setPostEqAllChannelsTo(mEq);
                }
            }
        }else{
            mEq.setEnabled(false);
            dp.setPreEqAllChannelsTo(mEq);
            dp.setPostEqAllChannelsTo(mEq);
        }
    }

    @Override
    public void onCustomAction(Player player, ControlDispatcher controlDispatcher, String action, Bundle extras) {
        if(Build.VERSION.SDK_INT >= 28){
            init();
        }
    }

    @Override
    public PlaybackStateCompat.CustomAction getCustomAction(Player player) {
        return new PlaybackStateCompat.CustomAction.Builder(MusicService.CUSTOM_ACTION_EFFECT,MusicService.CUSTOM_ACTION_EFFECT,-1).build();
    }

    public class LuckyControlDispatcher extends DefaultControlDispatcher{
        @Override
        public boolean dispatchSetPlayWhenReady(Player player, boolean playWhenReady) {
            if(playWhenReady){
                player.setPlayWhenReady(playWhenReady);
                if(Build.VERSION.SDK_INT >= 28 && dp != null){
                    ValueAnimator animator = ValueAnimator.ofInt(-50,0);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.setDuration(1000);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = (int) animation.getAnimatedValue();
                            dp.setInputGainAllChannelsTo(value);
                        }
                    });
                    animator.start();
                }
            }else{
                if(Build.VERSION.SDK_INT >= 28 && dp != null){
                    ValueAnimator animator = ValueAnimator.ofInt(0,-50);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.setDuration(1000);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = (int) animation.getAnimatedValue();
                            dp.setInputGainAllChannelsTo(value);
                            if(value == -50){
                                player.setPlayWhenReady(playWhenReady);
                            }
                        }
                    });
                    animator.start();
                }
            }
            return true;
        }
    }
}
