package com.task;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.task.db.UserDbHelper;

import java.util.Date;

/**
 * @author Leus Artem
 * @since 03.06.13
 */
public class TestApplication extends Application {

    public boolean dialogShown = false;
    public UserFragment userFragment;

    @Override
    public void onCreate() {
        super.onCreate();

        UserDbHelper userDb = new UserDbHelper(getBaseContext());
        // first launch case
        if(userDb.getAllUsers().isEmpty()){
            long selfId = userDb.addUser(new UserDbHelper.UserEntity("SpongeBob", "SquarePants", new Date(),
                    "I'm bob, my pants is most likely square", "bob@underwater.sea", null));
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            preferences.edit().putLong(Constants.SELF_DB_ID, selfId).commit();
        }
        userDb.close();
    }


}
