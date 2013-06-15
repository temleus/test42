package com.task;

import android.view.View;
import android.widget.ImageView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

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
    public void hasEditButtonsTest() throws Exception {
        View userFragmentView = userFragment.getView();
        assert(userFragmentView.findViewById(R.id.editNameAndSurname) instanceof ImageView);
        assert(userFragmentView.findViewById(R.id.editBirthdate) instanceof ImageView);
        assert(userFragmentView.findViewById(R.id.editBio) instanceof ImageView);
        assert(userFragmentView.findViewById(R.id.editEmail) instanceof ImageView);
    }

   /* @Test
   * TODO we are crating dialogs in different way (through DialogFragment) so can't test it. Find another way. ShadowAlertDialog.getLatestAlertDialog() returns null

    public void dialogTitleTest(){
        View userFragmentView = userFragment.getView();
        ImageView editBioBtn = (ImageView) userFragmentView.findViewById(R.id.editBio);

        editBioBtn.performClick();
        AlertDialog alertDialog = ShadowAlertDialog.getLatestAlertDialog();
        assertNotNull(alertDialog);

        ShadowAlertDialog shadowAlertDialog = Robolectric.shadowOf(alertDialog);
        assertEquals("Result", shadowAlertDialog.getTitle(), "Edit your bio");
    }

    @Test
    public void properDialogDismissingTest(){
        View userFragmentView = userFragment.getView();
        ImageView editBioBtn = (ImageView) userFragmentView.findViewById(R.id.editBio);

        editBioBtn.performClick();
        AlertDialog alertDialog = ShadowAlertDialog.getLatestAlertDialog();

        ShadowAlertDialog shadowAlertDialog = Robolectric.shadowOf(alertDialog);
        EditText editText = (EditText) shadowAlertDialog.getView().findViewById(R.id.firstEdit);
        editText.setText("");

        Button saveButton = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
        saveButton.performClick();

        assertTrue(alertDialog.isShowing());
    }
      */


}
