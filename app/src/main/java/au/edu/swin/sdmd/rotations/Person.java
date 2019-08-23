package au.edu.swin.sdmd.rotations;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Room has three components: database, entity and DAO. This is an entity object that represents
 * a table in the database. Ideally it should have a primary key, and can also have getters and
 * setters if you don't want the column variables to be public.
 */

@Entity(tableName = "person")
public class Person {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "dob")
    public String dob;
}
