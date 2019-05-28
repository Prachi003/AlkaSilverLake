package com.alkline.alkasilverlake.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.alkline.alkasilverlake.Session;
import com.alkline.alkasilverlake.roomdatabase.AppDataManager;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;


/**
 * Created by Ravi Birla on 31,December,2018
 */
public class Alkasilverlake extends Application{
    public static Alkasilverlake mInstance;

    private Session session;
    private String guestIdAdd="";
    public static double LATITUDE;
    public static double LONGITUDE;

    private String guestRecycle="";
    private static AppDataManager appInstance;



    //service tag
    private SharedPreferences mSharedPreferences;
    private static final String SHARED_PREF_NAME = "menuz_tag_preferences";

    public static Alkasilverlake getInstance() {
        if (mInstance.mSharedPreferences == null) {
             mInstance.mSharedPreferences = mInstance.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        }
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
       // Fabric.with(this, new Crashlytics());
        appInstance = AppDataManager.getInstance(this);

        mInstance = this;
        mInstance.getSessionManager();
        session.setIsOutCallFilter(false);


    }


    public Session getSessionManager() {
        if (session == null)
            session = new Session(getApplicationContext());
        return session;
    }

    public static AppDataManager getDataManager() {
        return appInstance;
    }


    /* public ArrayList<TaggedPhoto> getTaggedPhotos() {
String json = getString(Keys.TAGGED_PHOTOS.getKeyName());
ArrayList<TaggedPhoto> taggedPhotoArrayList;
if (!json.equals("")) {
taggedPhotoArrayList =
new Gson().fromJson(json, new TypeToken<ArrayList<TaggedPhoto>>() {
}.getType());
} else {
taggedPhotoArrayList = new ArrayList<>();
}
return taggedPhotoArrayList;
}

public void setTaggedPhotos(ArrayList<TaggedPhoto> taggedPhotoArrayList) {
putString(Keys.TAGGED_PHOTOS.getKeyName(), toJson(taggedPhotoArrayList));
}*/

    public void clear() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public String getGuestIdAdd() {
        return guestIdAdd;
    }

    public void setGuestIdAdd(String guestIdAdd) {
        this.guestIdAdd = guestIdAdd;
    }

    public String getGuestRecycle() {
        return guestRecycle;
    }

    public void setGuestRecycle(String guestRecycle) {
        this.guestRecycle = guestRecycle;
    }


    private enum Keys {
        TAGGED_PHOTOS("TAGGED_PHOTOS");
        private final String keyName;

        Keys(String label) {
            this.keyName = label;
        }

        public String getKeyName() {
            return keyName;
        }
    }


    public static String toJson(Object object) {
        try {
//            Gson gson = new Gson();
//            return gson.toJson(object);
        } catch (Exception e) {
            Log.e(SHARED_PREF_NAME, "Error In Converting ModelToJson", e);
        }
        return "";
    }
}
