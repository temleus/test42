package com.task.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.task.R;

/**
 * @author Leus Artem
 * @since 11.06.13
 */
public class EditFieldDialog extends DialogFragment {

    private String val1, dialogTitle;
    private OnDataChangedLitener listener;
    private int inputType;

    public EditFieldDialog() {
    }

    public EditFieldDialog(String val1, String dialogTitle, OnDataChangedLitener listener) {
        this(val1, dialogTitle,  InputType.TYPE_CLASS_TEXT, listener);
    }

    public EditFieldDialog(String val1, String dialogTitle, int inputType, OnDataChangedLitener listener) {
        this.val1 = val1;
        this.dialogTitle = dialogTitle;
        this.inputType = inputType;
        this.listener = listener;
    }

    public static interface OnDataChangedLitener {
        public void onChanged(String val1);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.edit_field_dialog, null);

        final EditText firstEdit = (EditText) dialogView.findViewById(R.id.firstEdit);
        firstEdit.setInputType(inputType);
        firstEdit.setText(val1);

        final AlertDialog dialog =  builder.setView(dialogView)
                .setTitle(dialogTitle)
                .setPositiveButton("Save", null) // will be overridden
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // nothing here
                    }
                }).create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button b = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (firstEdit.getText().length() == 0) {
                            Toast.makeText(getActivity(), "Field can't be empty", Toast.LENGTH_SHORT).show();
                        } else {
                            if (!val1.equals(firstEdit.getText())) {
                                listener.onChanged(firstEdit.getText().toString());
                            }
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        return dialog;
    }
}
