package au.edu.swin.sdmd.rotations;

import android.app.Application;

import androidx.room.Room;

/** An alternate method of creating a database is to put the singleton here in the Application
 * object.
 */

public class Rotations extends Application {
    static AppDatabase database = null;

    /*@Override
    public void onCreate() {
        super.onCreate();
        Rotations.database = Room.databaseBuilder(this, AppDatabase.class,
                "rotations-db").build();
    }*/
}
