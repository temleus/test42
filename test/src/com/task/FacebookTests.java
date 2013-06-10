package com.task;

import android.view.View;
import com.facebook.widget.ProfilePictureView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Leus Artem
 * @since 10.06.13
 */
@RunWith(RobolectricTestRunner.class)
public class FacebookTests {

    MainActivity activity;
    UserFragment userFragment;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(MainActivity.class).create().get();

        userFragment = new UserFragment();
        activity.getSupportFragmentManager().beginTransaction().add(userFragment, null).commit();
    }

    @Test
    public void testFbUserPic(){
        View facebookPic = userFragment.getView().findViewById(R.id.profile_pic);
        assertTrue(facebookPic instanceof ProfilePictureView);
    }

    @Test
    public void hasSyncButtonTest(){
        View syncBtn = userFragment.getView().findViewById(R.id.syncButton);
        assertNotNull(syncBtn);
    }

    @Test
    public void syncBtnTest(){
        boolean sessionOpened = userFragment.isSessionOpened();
        View syncBtn = userFragment.getView().findViewById(R.id.syncButton);
        if(sessionOpened){
            assertThat(syncBtn.getVisibility(), equalTo(View.VISIBLE));
        } else {
            assertThat(syncBtn.getVisibility(), equalTo(View.GONE));
        }
    }

    // TODO can't work with facebook LoginActivity, sort it out
   /* @Test
    public void rightIntentTest() throws Exception {
        LoginButton fbLoginButton = (LoginButton) userFragment.getView().findViewById(R.id.authButton);
        fbLoginButton.performClick();

        ShadowActivity shadowActivity = shadowOf(activity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);

//       assertThat(x, equalTo(x));
    }*/
}
