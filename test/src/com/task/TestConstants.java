package com.task;

import com.facebook.model.GraphLocation;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import org.json.JSONObject;

import java.util.Map;

/**
 * @author Leus Artem
 * @since 15.06.13
 */
public class TestConstants {

    public static final GraphUser MOCK_USER = new GraphUser() {
        @Override
        public String getId() {
            return "100000311392925";
        }

        @Override
        public void setId(String id) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public String getName() {
            return "Andrew Savchuk";
        }

        @Override
        public void setName(String name) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public String getFirstName() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void setFirstName(String firstName) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public String getMiddleName() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void setMiddleName(String middleName) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public String getLastName() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void setLastName(String lastName) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public String getLink() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void setLink(String link) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public String getUsername() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void setUsername(String username) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public String getBirthday() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void setBirthday(String birthday) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public GraphLocation getLocation() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void setLocation(GraphLocation location) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public <T extends GraphObject> T cast(Class<T> graphObjectClass) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Map<String, Object> asMap() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public JSONObject getInnerJSONObject() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Object getProperty(String propertyName) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void setProperty(String propertyName, Object propertyValue) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void removeProperty(String propertyName) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    };
}
