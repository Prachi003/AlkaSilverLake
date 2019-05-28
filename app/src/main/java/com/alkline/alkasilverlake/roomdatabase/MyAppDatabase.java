package com.alkline.alkasilverlake.roomdatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.alkline.alkasilverlake.roomdatabasedao.MyDao;
import com.alkline.alkasilverlake.roomdatabasedao.MyRecDao;
import com.alkline.alkasilverlake.roomdatabasedao.UserInfoDao;
import com.alkline.alkasilverlake.roomdatabasemodel.AddOrder;
import com.alkline.alkasilverlake.roomdatabasemodel.RecycleOrder;
import com.alkline.alkasilverlake.roomdatabasemodel.UserInfoModel;

/**
 * Created by Ravi Birla on 08,February,2019
 */
@Database(entities = {AddOrder.class, RecycleOrder.class, UserInfoModel.class}, version = 4, exportSchema = false)
public abstract class MyAppDatabase extends RoomDatabase {
    private static MyAppDatabase mAppDatabase;

    synchronized static MyAppDatabase getDatabaseInstance(Context context) {
        if (mAppDatabase == null) {
            mAppDatabase = Room.databaseBuilder(context, MyAppDatabase.class, "AlkaLineDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return mAppDatabase;
    }

    public abstract MyDao myDao();
    public abstract MyRecDao myRecDao();

    public abstract UserInfoDao userInfoDao();

}
