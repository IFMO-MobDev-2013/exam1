package ru.zulyaev.ifmo.exam;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

/**
 * Created by seidhe on 1/14/14.
 */
public class AddDialog extends DialogFragment {
    public static final DialogInterface.OnClickListener NO_OP_LISTENER = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    };
    private final AddListener listener;
    private final List<String> freeTime;

    public AddDialog(AddListener listener, List<String> freeTime) {
        this.listener = listener;
        this.freeTime = freeTime;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_order, null);
        final Spinner time  = (Spinner) view.findViewById(R.id.time);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, freeTime);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time.setAdapter(adapter);

        builder.setView(view);
        builder.setNegativeButton(R.string.cancel, NO_OP_LISTENER);
        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText model = (EditText) view.findViewById(R.id.model);
                EditText color = (EditText) view.findViewById(R.id.color);
                EditText number = (EditText) view.findViewById(R.id.number);
                EditText phone = (EditText) view.findViewById(R.id.phone);

                listener.onAdd(
                        model.getText().toString(),
                        color.getText().toString(),
                        number.getText().toString(),
                        phone.getText().toString(),
                        (String) time.getSelectedItem()
                );
            }
        });
        return builder.create();
    }

    interface AddListener {
        void onAdd(String model, String color, String number, String phone, String time);
    }
}
