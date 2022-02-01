package com.example.lifestyle_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lifestyle_app.databinding.ActivityMainBinding;
import com.example.lifestyle_app.databinding.ProfileFragmentBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

public class ProfileFragment extends Fragment {

    private ProfileFragmentBinding binding;

    private String first_name;

    private Button submit_button;
    private EditText first_name_text;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ProfileFragmentBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        first_name_text = (EditText) view.findViewById(R.id.first_name);

//        if(!(first_name.matches(""))) {
//            first_name_text.setText(first_name);
//        }

        submit_button = (Button) getView().findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first_name = first_name_text.getText().toString();
                if(first_name.matches("")) {
                    Toast.makeText(getActivity(), "Enter a name first!", Toast.LENGTH_SHORT).show();
                }
                else {
                    saveFile(first_name);
                    NavHostFragment.findNavController(ProfileFragment.this)
                            .navigate(R.id.action_ProfileFragment_to_FirstFragment);
                }
            }
        });

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProfileFragment.this)
                        .navigate(R.id.action_ProfileFragment_to_FirstFragment);
            }
        });
    }

    private void saveFile(String first_name) {
        File directory = getActivity().getFilesDir();
        try {
            File file = new File(directory, "Profile");
                //Toast.makeText(getActivity(), "doesn't exist", Toast.LENGTH_SHORT).show();
            FileOutputStream writer = new FileOutputStream(file);
            writer.write(first_name.getBytes());
            writer.close();

        } catch (Exception e) {

        }
    }


}