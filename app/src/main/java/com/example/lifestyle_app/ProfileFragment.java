package com.example.lifestyle_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lifestyle_app.databinding.ActivityMainBinding;
import com.example.lifestyle_app.databinding.ProfileFragmentBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileFragment extends Fragment {

    private ProfileFragmentBinding binding;

    private String first_name;

    private Button submit_button;

    private static final int CAPTURE_IMAGE_REQUEST_CODE = 1888;

    Bitmap thumbnailImage;

    // The ImageView that holds the profile pic
    ImageView mIvPic;


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
                if (first_name.matches("")) {
                    Toast.makeText(getActivity(), "Enter a name first!", Toast.LENGTH_SHORT).show();
                } else {
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

        binding.takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAPTURE_IMAGE_REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Gets the thumbnail of the image
            Bitmap bmp = (Bitmap) data.getExtras().get("data");
            thumbnailImage = bmp;

            // Puts the thumbnail on the ImageView
            mIvPic = (ImageView) getView().findViewById(R.id.iv_pic);
            mIvPic.setImageBitmap(thumbnailImage);

            //Open file and write to it
            if (isExternalStorageWritable()) {
                String filePathStr = saveImage(thumbnailImage);

            } else {
                Toast.makeText(getActivity(), "External storage not writable.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private String saveImage(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File dir = new File(root + "/saved_images");
        dir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "Thumbnail_" + timeStamp + ".jpg";

        File file = new File(dir, fileName);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(getActivity(), "File saved!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }

        return false;
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