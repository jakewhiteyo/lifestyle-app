package com.example.lifestyle_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.lifestyle_app.databinding.FragmentFirstBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    private TextView profileText;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileText = (TextView) view.findViewById(R.id.profile_text);

        File file = new File(getActivity().getFilesDir(), "Profile");

        String name = "first name: ";
        if(file.exists()) {
            try {
                Scanner scanner = new Scanner(file);
                while(scanner.hasNextLine()) {
                    name += scanner.nextLine();
                }
            } catch (Exception e) {

            }


            profileText.setText(name);
            //first_name_text.setText(first_name);

        }

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_ProfileFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}