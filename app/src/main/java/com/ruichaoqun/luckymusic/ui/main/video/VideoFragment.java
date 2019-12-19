package com.ruichaoqun.luckymusic.ui.main.video;


import android.os.Bundle;


import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruichaoqun.luckymusic.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {

    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

}
