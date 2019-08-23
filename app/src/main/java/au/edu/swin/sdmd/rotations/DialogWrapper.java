package au.edu.swin.sdmd.rotations;

import android.view.View;
import android.widget.EditText;

public class DialogWrapper {
    EditText nameField = null;

    EditText dobField = null;

    View base = null;

    DialogWrapper(View base) {
        this.base = base;
        dobField = (EditText) base.findViewById(R.id.dob);
    }

    String getName() {
        return (getNameEditText().getText().toString());
    }

    String getDOB() {
        return (getDOBEditText().getText().toString());
    }

    private EditText getNameEditText() {
        if (nameField == null)
        {
            nameField = (EditText) base.findViewById(R.id.name);
        }

        return (nameField);
    }

    private EditText getDOBEditText() {
        if (dobField == null)
        {
            dobField = (EditText) base.findViewById(R.id.name);
        }

        return (dobField);
    }
}
