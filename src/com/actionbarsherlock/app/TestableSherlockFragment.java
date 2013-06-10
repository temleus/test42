package com.actionbarsherlock.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import java.lang.reflect.Field;

/**
 * @author Leus Artem
 * @since 10.06.13
 *
 *  Make this fragment properly instantiated during tests,
 *  otherwise fragmentInstance.getView() returns null inside the test, fragment onCreateView() and
 *  others lifecycle methods are never invoked
 *
 *  solution proposed here:
 *  http://stackoverflow.com/questions/16179675/robolectric-how-can-i-test-an-activity-that-contains-a-sherlockfragment/16222199#16222199
 */
public class TestableSherlockFragment extends SherlockFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmFromLayoutField(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setmFromLayoutField(false);
    }

    /**
     * Setting private Fragment field, using reflections */
    private void setmFromLayoutField(boolean b){
        Field field = null;
        try {
            field = Fragment.class.getDeclaredField("mFromLayout");
            field.setAccessible(true);
            field.setBoolean(this, b);
            field.setAccessible(false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
