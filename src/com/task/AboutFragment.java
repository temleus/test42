package com.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.TestableSherlockFragment;

/**
 * @author Leus Artem
 * @since 13.06.13
 */
public class AboutFragment extends TestableSherlockFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_fragment, container, false);
    }
}
