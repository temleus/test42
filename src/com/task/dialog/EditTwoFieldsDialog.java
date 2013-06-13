package com.task.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.task.R;

/**
 * @author Leus Artem
 * @since 10.06.13
 */
public class EditTwoFieldsDialog extends DialogFragment {

    private String val1, val2, dialogTitle;
    private OnDataChangedLitener listener;

    public EditTwoFieldsDialog() {
    }

    public EditTwoFieldsDialog(String val1, String val2, String dialogTitle, OnDataChangedLitener listener) {
        this.val1 = val1;
        this.val2 = val2;
        this.dialogTitle = dialogTitle;
        this.listener = listener;
    }

    public static interface OnDataChangedLitener {
        public void onChanged(String val1, String val2);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.edit_two_fields_dialog, null);

        final EditText firstEdit = (EditText) dialogView.findViewById(R.id.firstEdit);
        final EditText secondEdit = (EditText) dialogView.findViewById(R.id.secondEdit);

        firstEdit.setText(val1);
        secondEdit.setText(val2);

        final AlertDialog dialog = builder.setView(dialogView)
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
                        if (firstEdit.getText().length() == 0 || secondEdit.getText().length() == 0) {
                            Toast.makeText(getActivity(), "Field can't be empty", Toast.LENGTH_SHORT).show();
                        } else {
                            if (!val1.equals(firstEdit.getText()) || !val2.equals(secondEdit.getText())) {
                                listener.onChanged(firstEdit.getText().toString(), secondEdit.getText().toString());
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
