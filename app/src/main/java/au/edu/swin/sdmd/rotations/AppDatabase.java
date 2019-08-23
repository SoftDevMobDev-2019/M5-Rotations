package au.edu.swin.sdmd.rotations;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Room has three components: database, entity and DAO. This is the database object where the
 * database is created and stored. We can also add some data to the database when it is created
 * for the first time.
 */

@Database(entities = {Person.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PersonDao personDao();

    private static final String DB_NAME = "birthdays.db";
    private static AppDatabase instance;

    static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DB_NAME)
                            .allowMainThreadQueries() // not ideal
                            .build();
                }
                instance.populateDatabase(); // there are a few methods for pre-population;
                // another is to use callbacks.
            }
        }
        return instance;
    }

    private void populateDatabase() {
        if (personDao().count() == 0) {
            runInTransaction(new Runnable() {
                @Override
                public void run() {
                    Person person = new Person();
                    person.name = "Darth Vader";
                    person.dob = "23/11/2045";
                    instance.personDao().insert(person);

                    Person person2 = new Person();
                    person2.name = "George Swinburne";
                    person2.dob = "03/02/1861";
                    instance.personDao().insert(person2);
                }
            });
        }
    }
}
