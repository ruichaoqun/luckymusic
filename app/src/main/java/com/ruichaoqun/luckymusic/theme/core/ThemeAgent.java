package com.ruichaoqun.luckymusic.theme.core;

import android.app.Activity;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.ruichaoqun.luckymusic.LuckyMusicApp;
import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.theme.ThemeInfo;

import java.util.Map;

import static com.ruichaoqun.luckymusic.Constants.CHANGE_THEME;

/**
 * 主题切换代理
 */
public class ThemeAgent {
    public static final String EXTRA_THEME_ID = "theme_id";


    private static ThemeAgent sDownloadThemeAgent;

    private ThemeAgent() {
    }

    public static synchronized ThemeAgent getInstance() {
        ThemeAgent themeAgent;
        synchronized (ThemeAgent.class) {
            if (sDownloadThemeAgent == null) {
                sDownloadThemeAgent = new ThemeAgent();
            }
            themeAgent = sDownloadThemeAgent;
        }
        return themeAgent;
    }

    public void switchTheme(Activity activity, final ThemeInfo themeInfo, boolean isInternal) {
        int id = themeInfo.getId();
//        for (Map.Entry<Integer, DownloadThemeTask> value : this.mDownloadThemeTasks.entrySet()) {
//            ((DownloadThemeTask) value.getValue()).cancel(false);
//        }
        if (isInternal) {
            sendChangeThemeCommand(id);
        }
//        else if (!k.e(activity)) {
//            if (!aj.c() || c.c()) {
//                downloadTheme(themeInfo);
//            } else {
//                MaterialDialogHelper.materialDialogWithPositiveBtn(activity, Integer.valueOf(R.string.afc), Integer.valueOf(R.string.a74), new View.OnClickListener() {
//                    public void onClick(View view) {
//                        ThemeAgent.this.downloadTheme(themeInfo);
//                    }
//                });
//            }
//        }
    }

    private void sendChangeThemeCommand(int id) {
        Intent intent = new Intent(CHANGE_THEME);
        intent.putExtra(EXTRA_THEME_ID, id);
        LocalBroadcastManager.getInstance(LuckyMusicApp.getInstance()).sendBroadcast(intent);
    }


}
