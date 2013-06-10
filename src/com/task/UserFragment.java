package com.task;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.TestableSherlockFragment;
import com.facebook.*;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;
import com.task.db.UserDbHelper;

import java.lang.reflect.Field;

/**
 * @author Leus Artem
 * @since 02.06.13
 */
public class UserFragment extends TestableSherlockFragment {

    private static final String TAG = "UserFragment";

    private UiLifecycleHelper uiHelper;
    private TextView nameView, surnameView, birthdateView, bioView, emailView;
    private ProfilePictureView profilePictureView;
    private ImageView defaultProfilePic, syncButton;

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(this.getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);

        TestApplication app = (TestApplication) getActivity().getApplicationContext();
        // check connection availability
        if (!Utils.isOnline(getActivity())) {
            Toast.makeText(getActivity(), "No internet connection!", Toast.LENGTH_SHORT);
        } else {
            // show dialog in case user isn't logged in FB
            Session currentSession = Session.getActiveSession();
            if (!app.dialogShown && (currentSession == null || !currentSession.getState().isOpened())) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DialogFragment dialog = new IntroductionDialog();
                app.dialogShown = true;
                dialog.show(fm, "dialog");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, container, false);

        LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
        authButton.setReadPermissions(Constants.FB_PERMISSIONS);
        authButton.setFragment(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameView = (TextView) view.findViewById(R.id.name);
        surnameView = (TextView) view.findViewById(R.id.surname);
        birthdateView = (TextView) view.findViewById(R.id.birthdate);
        bioView = (TextView) view.findViewById(R.id.bio);
        emailView = (TextView) view.findViewById(R.id.email);

        profilePictureView = (ProfilePictureView) view.findViewById(R.id.profile_pic);
        defaultProfilePic = (ImageView) view.findViewById(R.id.default_user_pic);
        syncButton = (ImageView) view.findViewById(R.id.syncButton);
        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syncWithFb(Session.getActiveSession(), new Runnable() {
                    @Override
                    public void run() {
                        updateUserUI();
                    }
                });
            }
        });
        TestApplication app = (TestApplication) getActivity().getApplicationContext();
        app.userFragment = this;
        updateUserUI();
    }

    private void updateUserUI(){
        UserDbHelper.UserEntity iAm = getMySelfAtDb();
        if(iAm == null) return;
        if(iAm.fbId != null){    // user is already synchronized with FB so we can use his FB photo
            profilePictureView.setProfileId(iAm.fbId);
            profilePictureView.setVisibility(View.VISIBLE);
            defaultProfilePic.setVisibility(View.INVISIBLE);
        }
        if(iAm.name != null) nameView.setText(iAm.name);
        if(iAm.surname != null) surnameView.setText(iAm.surname);
        if(iAm.birthdate != null) birthdateView.setText(Utils.convertDateToString(iAm.birthdate));
        if(iAm.bio != null) bioView.setText(iAm.bio);
        if(iAm.email != null) emailView.setText(iAm.email);
    }


    private void syncWithFb(Session session, final Runnable onCompleteRunnable){
        Bundle params = new Bundle();
        params.putString("fields", "first_name,last_name,birthday,bio,email");
        new Request(session, "me", params, HttpMethod.GET, new Request.Callback() {
            @Override
            public void onCompleted(Response response) {
                GraphUser fbUser = response.getGraphObjectAs(GraphUser.class);

                UserDbHelper.UserEntity iAm = getMySelfAtDb();            // TODO : should probably be async
                iAm.name = fbUser.getFirstName();
                iAm.surname = fbUser.getLastName();
                iAm.birthdate = Utils.convertStringToDate(fbUser.getBirthday());
                iAm.bio = (String) fbUser.getProperty("bio");
                iAm.email = (String) fbUser.getProperty("email");
                iAm.fbId = fbUser.getId();

                UserDbHelper userDb = new UserDbHelper(getActivity().getBaseContext());
                userDb.update(iAm, new Runnable() {
                    @Override
                    public void run() {
                        onCompleteRunnable.run();
                    }
                });
                userDb.close();

                onCompleteRunnable.run();
            }
        }).executeAsync();
    }

    private UserDbHelper.UserEntity getMySelfAtDb(){
        UserDbHelper userDb = new UserDbHelper(getActivity().getBaseContext());
        long selfId = PreferenceManager.getDefaultSharedPreferences(getActivity()).getLong(Constants.SELF_DB_ID, -1);
        UserDbHelper.UserEntity iAm = userDb.findById(selfId);
        userDb.close();
        return iAm;
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");
            syncButton.setVisibility(View.VISIBLE);
        } else if (state.isClosed()) {
            syncButton.setVisibility(View.GONE);
            Log.i(TAG, "Logged out...");
        }
    }

    public boolean isSessionOpened(){
        return Session.getActiveSession() == null ? false : Session.getActiveSession().isOpened();
    }

    @Override
    public void onResume() {
        super.onResume();
        // For scenarios where the main activity is launched and user
        // session is not null, the session state change notification
        // may not be triggered. Trigger it if it's open/closed.
        Session session = Session.getActiveSession();
        if (session != null &&
                (session.isOpened() || session.isClosed()) ) {
            onSessionStateChange(session, session.getState(), null);
        }

        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
}
