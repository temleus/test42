package com.task.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import com.task.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Leus Artem
 * @since 03.06.13
 */
public class UserDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TestDb";

    private static final String TABLE_USERS = "users";

    private static final String KEY_ID = "id";
    private static final String KEY_FB_ID = "fb_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_BIRTHDATE = "birthdate";
    private static final String KEY_BIO= "bio";
    private static final String KEY_EMAIL= "email";

    public static class UserEntity {

        public UserEntity(long id, String name, String surname, Date birthdate, String bio, String email, String fbId){
            this(name, surname, birthdate, bio, email, fbId);
            this.id = id;
        }

        public UserEntity(String name, String surname, Date birthdate, String bio, String email, String fbId) {
            this.name = name;
            this.surname = surname;
            this.birthdate = birthdate;
            this.bio = bio;
            this.email = email;
            this.fbId = fbId;
        }

        public String name, surname, bio, email, fbId;
        public Date birthdate;
        public long id = -1;
    }

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT,"
                + KEY_FB_ID + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_SURNAME + " TEXT,"
                + KEY_BIRTHDATE + " TEXT,"
                + KEY_BIO + " TEXT,"
                + KEY_EMAIL + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        // Create tables again
        onCreate(db);
    }

    /**
     * returns Id of just inserted row */
    public long addUser(UserEntity user) {
        SQLiteDatabase db = UserDbHelper.this.getWritableDatabase();
        long insertedId = db.insert(TABLE_USERS, null, userEntityToContentValues(user));
        db.close();
        return insertedId;
    }

    public List<UserEntity> getAllUsers(){
        List<UserEntity> users = new ArrayList<UserEntity>();

        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                users.add(cursorToUserEntity(cursor));
            } while (cursor.moveToNext());
        }
        return users;
    }

    public UserEntity findById(long id){
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE ID = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return cursorToUserEntity(cursor);
        }
        return null;
    }

    public void update(final UserEntity user, final Runnable onPostUpdateRunnable){
        new AsyncTask<UserEntity, Void, Void>(){
            @Override
            protected Void doInBackground(UserEntity... params) {
                SQLiteDatabase db = UserDbHelper.this.getWritableDatabase();
                db.update(TABLE_USERS, userEntityToContentValues(user), KEY_ID + "=" + user.id, null);
                db.close();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(onPostUpdateRunnable != null) onPostUpdateRunnable.run();
            }
        }.execute(user);
    }

   /* public void dropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }   */

    private UserEntity cursorToUserEntity(Cursor cursor){
        int id = cursor.getInt(0);
        String fbId = cursor.getString(1);
        String name = cursor.getString(2);
        String surname = cursor.getString(3);
        String birthdate = cursor.getString(4);
        String bio = cursor.getString(5);
        String email = cursor.getString(6);
        return new UserEntity(id, name, surname, Utils.convertStringToDate(birthdate), bio, email, fbId);
    }

    private ContentValues userEntityToContentValues(UserEntity user){
        ContentValues values = new ContentValues();
        values.put(KEY_FB_ID, user.fbId);
        values.put(KEY_NAME, user.name);
        values.put(KEY_SURNAME, user.surname);
        values.put(KEY_BIRTHDATE, Utils.convertDateToString(user.birthdate));
        values.put(KEY_BIO, user.bio);
        values.put(KEY_EMAIL, user.email);
        return values;
    };

}
