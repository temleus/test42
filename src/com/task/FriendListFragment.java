package com.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.TestableSherlockFragment;

/**
 * @author Leus Artem
 * @since 02.06.13
 */
public class FriendListFragment extends TestableSherlockFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.friends_fragment, container, false);
    }
}
