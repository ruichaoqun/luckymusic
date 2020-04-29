package com.ruichaoqun.luckymusic.media.audioeffect;

import android.content.res.AssetManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.ruichaoqun.luckymusic.LuckyMusicApp;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.utils.IoUtils;
import com.ruichaoqun.luckymusic.utils.UiUtils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AudioEffectJsonPackage {
    private Eq mEq;

    public AudioEffectJsonPackage() {
        mEq = new Eq();
        mEq.setFileName("");
        List<Float> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(0.0f);
        }
        mEq.setEqs(list);
    }

    public Eq getEq() {
        return mEq;
    }

    public void setEq(Eq eq) {
        mEq = eq;
    }

    public static class Eq {
        private List<Float> mEqs;
        private String mFileName;
        private boolean mOn = false;

        public Eq() {
        }

        public Eq(List<Float> list) {
            setEqs(list);
        }


        public List<Float> getEqs() {
            return mEqs;
        }

        public void setEqs(List<Float> eqs) {
            mEqs = eqs;
            if (eqs == null) {
                this.mOn = false;
                return;
            }
            for (int i2 = 0; i2 < eqs.size(); i2++) {
                if (eqs.get(i2).floatValue() != 0.0f) {
                    this.mOn = true;
                    return;
                }
            }
        }

        public String getFileName() {
            return mFileName;
        }

        public void setFileName(String fileName) {
            mFileName = fileName;
        }

        public boolean isOn() {
            return mOn;
        }

        public void setOn(boolean on) {
            mOn = on;
        }

        public static List<Eq> getDefaultEqs() {
            try {
                LuckyMusicApp musicApp = LuckyMusicApp.getInstance();
                String[] list = musicApp.getAssets().list("audioeffect/eq");
                ArrayList arrayList = new ArrayList();
                byte[] bArr = new byte[8096];
                for (String str : list) {
                    if (!TextUtils.isEmpty(str)) {
                        InputStream open = musicApp.getAssets().open("audioeffect/eq" + "/" + str);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(200);
                        while (true) {
                            int read = open.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            byteArrayOutputStream.write(bArr, 0, read);
                        }
                        Eq eq = (Eq) new Gson().fromJson(new String(byteArrayOutputStream.toByteArray()), Eq.class);
                        eq.mFileName = str;
                        arrayList.add(eq);
                        IoUtils.close(open);
                    }
                }
                return arrayList;
            } catch (IOException e2) {
                e2.printStackTrace();
                return Collections.emptyList();
            } catch (Throwable th) {
                throw th;
            }
        }

        public static Eq getDefaultEqByIndex(int i2) {
            try {
                LuckyMusicApp musicApp = LuckyMusicApp.getInstance();
                String[] stringArray = musicApp.getResources().getStringArray(R.array.local_eq_file_name);
                if (i2 >= stringArray.length) {
                    return new Eq(new ArrayList<>());
                }
                byte[] bArr = new byte[8096];
                AssetManager assets = musicApp.getAssets();
                InputStream open = assets.open("audioeffect/eq" + "/" + stringArray[i2]);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(200);
                while (true) {
                    int read = open.read(bArr);
                    if (read != -1) {
                        byteArrayOutputStream.write(bArr, 0, read);
                    } else {
                        Eq eq = (Eq) new Gson().fromJson(new String(byteArrayOutputStream.toByteArray()), Eq.class);
                        eq.mFileName = stringArray[i2];
                        IoUtils.close(open);
                        return eq;
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                return new Eq(new ArrayList<>());
            } catch (Throwable th) {
                throw th;
            }
        }

    }



}
