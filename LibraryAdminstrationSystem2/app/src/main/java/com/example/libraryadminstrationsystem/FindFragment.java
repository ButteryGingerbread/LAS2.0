package com.example.libraryadminstrationsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.button.MaterialButton;

public class FindFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);

        MaterialButton btnFind = view.findViewById(R.id.btnAdd);
        MaterialButton btnUpdate = view.findViewById(R.id.btnUpdate);
        MaterialButton btnDelete = view.findViewById(R.id.btnDelete);
        MaterialButton btnView = view.findViewById(R.id.btnView);

        btnFind.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnView.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v.getId() == R.id.btnAdd) {
            intent = new Intent(getActivity(), ActivityAdd.class);
        } else if (v.getId() == R.id.btnUpdate) {
            intent = new Intent(getActivity(), ActivityUpdate.class);
        } else if (v.getId() == R.id.btnDelete) {
            intent = new Intent(getActivity(), ActivityDelete.class);
        } else if (v.getId() == R.id.btnView) {
            intent = new Intent(getActivity(), ActivityView.class);
        } else {
            return;
        }
        startActivity(intent);
    }
}
