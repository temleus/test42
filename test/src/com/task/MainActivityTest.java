package com.task;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

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
        assertThat(activity.getString(R.string.app_name), equalTo("TestTask"));
        TestApplication app = (TestApplication) activity.getApplicationContext();
    }

    @Test
    public void hasPictureTest(){
        View userPic = userFragment.getView().findViewById(R.id.default_user_pic);
        assertTrue(userPic instanceof ImageView);
    }

    @Test
    public void hasNeededTextViewsTest() {
        View userView = userFragment.getView();
        View nameView = userView.findViewById(R.id.name);
        View surnameView = userView.findViewById(R.id.surname);
        View birthdateView = userView.findViewById(R.id.birthdate);
        View bioView = userView.findViewById(R.id.bio);
        View emailView = userView.findViewById(R.id.email);
        assert (nameView instanceof TextView
                && surnameView instanceof TextView
                && birthdateView instanceof TextView
                && bioView instanceof TextView
                && emailView instanceof TextView);
    }
}
