package com.task;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockListFragment;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

import java.util.List;

/**
 * @author Leus Artem
 * @since 02.06.13
 */
public class FriendListFragment extends SherlockListFragment {

    private static final String TAG = "FriendListFragment";
    private FriendListAdapter adapter;
    private List<GraphUser> users;

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String userId = (String) view.getTag();
            startActivity(getOpenFacebookIntent(getActivity(), userId));
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchFriendListData(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.friends_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(users == null){
            fetchFriendListData(new Runnable() {
                @Override
                public void run() {
                    adapter.addAll(users);
                }
            });
        }
        Log.i(TAG, "onResume" + TAG);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new FriendListAdapter(getActivity());
        setListAdapter(adapter);
        getListView().setOnItemClickListener(onItemClickListener);

        if(adapter.isEmpty()){
            if(users != null && !users.isEmpty()){
                adapter.addAll(users);
            }
        }
    }

    private void fetchFriendListData(final Runnable onPostExecuteRunnable){
        Session session = Session.getActiveSession();
        // Asynchronously fetching friends list and filling adapter if it's already instantiated
        if (session != null && session.isOpened()) {
            Request.executeMyFriendsRequestAsync(session, new Request.GraphUserListCallback() {
                @Override
                public void onCompleted(List<GraphUser> users, Response response) {
                    /* invoked in UI thread*/
                    if (adapter != null) {
                        adapter.addAll(users);
                    }
                    FriendListFragment.this.users = users;
                    if(onPostExecuteRunnable != null) {
                        onPostExecuteRunnable.run();
                    }
                    Log.i(TAG, "Friend list fetched");
                    for(GraphUser user: users) Log.i(TAG, user.toString());
                }
            });
        }
    }

    /** If user has facebook app installed than returns app launch intent
     * otherwise browser will be launched
     * */
    private Intent getOpenFacebookIntent(Context context, String userId) {
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/"+userId));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+userId));
        }
    }

    public static class FriendListAdapter extends ArrayAdapter<GraphUser> {

        public FriendListAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GraphUser user = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.friend_list_row, null);
            }

            ProfilePictureView profilePic = (ProfilePictureView) convertView.findViewById(R.id.userPic);
            profilePic.setProfileId(user.getId());

            TextView nameView = (TextView) convertView.findViewById(R.id.name);
            nameView.setText(user.getName());

            convertView.setTag(user.getId());

            return convertView;
        }
    }
}
