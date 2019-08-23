package au.edu.swin.sdmd.rotations;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * This is not the cleanest MainActivity; it has been adapted from the original BirthdayDB
 * project which used SQLite directly. One minor difference is the use of a Floating Action Button
 * for adding data, however the context menu for deletion has been kept.
 */

public class MainActivity extends AppCompatActivity {

    ListView lv;
    AppDatabase database;
    List<Person> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = AppDatabase.getInstance(this.getApplicationContext());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

        lv = findViewById(R.id.birthday_list);
        registerForContextMenu(lv);

        showAllPeople();
    }

    /** Add a new person to the database */
    private void processAdd(DialogWrapper wrapper) {

        Person person = new Person();
        person.name = wrapper.getName();
        person.dob = wrapper.getDOB();
        database.personDao().insert(person);
        showAllPeople();
    }

    private void add() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View addView = inflater.inflate(R.layout.add_new, null);
        final DialogWrapper wrapper = new DialogWrapper(addView);

        new AlertDialog.Builder(this)
                .setTitle(R.string.add_person)
                .setView(addView)
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,
                                                int whichButton)
                            {
                                processAdd(wrapper);
                            }
                        })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,
                                                int whichButton)
                            {
                                // ignore, just dismiss
                            }
                        }).show();
    }


    private void showAllPeople() {

        data = database.personDao().getAll();

        CustomAdapter adapter = new CustomAdapter(this, R.layout.row, data);
        lv.setAdapter(adapter);

    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo)
    {
        // add(groupId, itemID, order, title)
        menu.add(Menu.NONE, 0, Menu.NONE, "Delete");
    }

    public boolean onContextItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case 0:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                delete(info.id);
                return (true);
        }

        return (super.onOptionsItemSelected(item));
    }

    private void delete(final long rowId) {
        if (rowId >= 0)
        {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.delete_person)
                    .setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton)
                                {
                                    processDelete(rowId);
                                }
                            })
                    .setNegativeButton(R.string.cancel,
                            new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton)
                                {
                                    // ignore, just dismiss
                                }
                            }).show();
        }
    }

    /** Remove an existing person from the database */
    private void processDelete(long rowId) {
        Person person = data.get((int)rowId);
        database.personDao().delete(person);
        showAllPeople();
    }

    class CustomAdapter extends ArrayAdapter<Person> {

        Context context;
        int layoutResourceId;
        ArrayList<Person> data = null;

        public CustomAdapter(Context context, int resource, List<Person> data) {
            super(context, resource, data);
            this.layoutResourceId = resource;
            this.context = context;
            this.data = (ArrayList) data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            HeaderHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new HeaderHolder();
                holder.name = (TextView) row.findViewById(R.id.name);
                holder.dob = (TextView) row.findViewById(R.id.dob);

                row.setTag(holder);
            } else {
                holder = (HeaderHolder) row.getTag();
            }

            Person item = data.get(position);
            holder.name.setText(item.name);
            holder.dob.setText(item.dob);

            return row;
        }

        private class HeaderHolder {
            public TextView name;
            public TextView dob;
            public TextView id;

        }
    }
}




