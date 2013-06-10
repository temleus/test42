package com.task;

import android.view.View;
import android.widget.ImageView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Leus Artem
 * @since 10.06.13
 */
@RunWith(RobolectricTestRunner.class)
public class DataEditTest {

    MainActivity activity;
    UserFragment userFragment;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(MainActivity.class).create().get();

        userFragment = new UserFragment();
        activity.getSupportFragmentManager().beginTransaction().add(userFragment, null).commit();
    }

    @Test
    public void justSimpleTest() throws Exception {
        View userFragmentView = userFragment.getView();
        assert(userFragmentView.findViewById(R.id.editNameAndSurname) instanceof ImageView);
        assert(userFragmentView.findViewById(R.id.editBirthdate) instanceof ImageView);
        assert(userFragmentView.findViewById(R.id.editBio) instanceof ImageView);
        assert(userFragmentView.findViewById(R.id.editEmail) instanceof ImageView);
    }



}
