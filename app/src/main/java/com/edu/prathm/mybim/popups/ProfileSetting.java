package com.edu.prathm.mybim.popups;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.edu.prathm.mybim.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileSetting extends DialogFragment {


    public ProfileSetting() {
        // Required empty public constructor
    }

    Button cancel,update;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_setting, container, false);
        TextView title = (TextView) getDialog().findViewById(android.R.id.title);
        title.setTextColor(getResources().getColor(R.color.primary));
        getDialog().setTitle("Add Project Details");
        cancel = (Button) view.findViewById(R.id.cancel);
 update =(Button)view.findViewById(R.id.update);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileSetting.this.getDialog().cancel();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //update settings
                ProfileSetting.this.getDialog().cancel();
            }
        });
        return view;
    }

  /*  @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog, null))
                // Add action buttons
                .setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ProfileSetting.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
*/
}
