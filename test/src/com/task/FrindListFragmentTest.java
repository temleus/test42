package com.task;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Leus Artem
 * @since 14.06.13
 */
@RunWith(RobolectricTestRunner.class)
public class FrindListFragmentTest {

    MainActivity activity;
    FriendListFragment friendListFragment;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(MainActivity.class).create().start().resume().get();

        friendListFragment = new FriendListFragment();
        activity.getSupportFragmentManager().beginTransaction().add(friendListFragment, null).commit();
    }

    @Test
    public void hasNeededViewTest(){
        ListView listView = friendListFragment.getListView();
        assertNotNull(listView);
        FriendListFragment.FriendListAdapter adapter = (FriendListFragment.FriendListAdapter) listView.getAdapter();
        assertNotNull(adapter);
        adapter.add(TestConstants.MOCK_USER);

        View listRowView = adapter.getView(0, null, null);
        assertNotNull(listRowView);
        assertNotNull(listRowView.findViewById(R.id.userPic));
        assertTrue(listRowView.findViewById(R.id.name) instanceof TextView);
    }

    @Test
    public void hasListTest(){
        Assert.assertNotNull(friendListFragment.getView().findViewById(android.R.id.list));
    }

    @Test
    public void hasClickListenerTest(){
        ListView listView = friendListFragment.getListView();
        assertNotNull(listView);

        FriendListFragment.FriendListAdapter adapter = (FriendListFragment.FriendListAdapter) listView.getAdapter();
        assertNotNull(adapter);
        adapter.add(TestConstants.MOCK_USER);

        assertTrue(listView.performItemClick(listView, 0, 0));
    }


}
