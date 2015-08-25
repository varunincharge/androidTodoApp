package com.example.vjobanputra.simpletodo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.app.AlertDialog;


public class PriorityAlertDialog extends DialogFragment {
    public PriorityAlertDialog() {
        // Empty constructor required for DialogFragment
    }

    interface AlertPositiveListener {
        public void onPositiveClick(int position);
    }

    public static PriorityAlertDialog newInstance(String title, int position) {
        PriorityAlertDialog frag = new PriorityAlertDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("position", position);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        String position = getArguments().getString("position");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setSingleChoiceItems(Priority.options, 1, null);
        alertDialogBuilder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertPositiveListener alertPositiveListener = (AlertPositiveListener)getActivity();
                AlertDialog alert = (AlertDialog)dialog;
                int position = alert.getListView().getCheckedItemPosition();
                alertPositiveListener.onPositiveClick(position);
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return alertDialogBuilder.create();
    }
}