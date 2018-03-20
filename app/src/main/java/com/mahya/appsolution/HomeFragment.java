package com.mahya.appsolution;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.UnsupportedEncodingException;

public class HomeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ImageView imageViewOne, imageViewTwo, imageViewThree;
    private TextView textViewTwo, textViewThree;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        imageViewOne = (ImageView) v.findViewById(R.id.imageViewHomeOne);
        imageViewTwo = (ImageView) v.findViewById(R.id.imageViewHomeTwo);
        imageViewThree = (ImageView) v.findViewById(R.id.imageViewHomeThree);
        textViewTwo = (TextView) v.findViewById(R.id.textViewHomeTwo);
        textViewThree = (TextView) v.findViewById(R.id.textViewHomeThree);

        downloadImage("slider4.jpg", imageViewOne);
        downloadImage("home_image_two.jpg", imageViewTwo);
        downloadText("textview_home_two.txt", textViewTwo);

        downloadImage("rsz_slider1.jpg", imageViewThree);
        downloadText("textview_home_three.txt", textViewThree);

        return v;
    }

    // download  image from fireBase
    private void downloadImage(String s, final ImageView iv) {
        Log.e("log", "inside downloading");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://appsolution-669ae.appspot.com").child(s);
        final long ONE_MEGABYTE = 1024 * 1024;

        //download file as a byte array
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                iv.setImageBitmap(bitmap);
                if(bitmap.equals(null)) {
                    Toast.makeText(getActivity(), "Connection Error!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // download text from fireBase
    private void downloadText(String s, final TextView tv) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://appsolution-669ae.appspot.com").child(s);
        final long ONE_MEGABYTE = 1024 * 1024;
        //download file as a byte array
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {

            @Override
            public void onSuccess(byte[] bytes) {
                String str = null;
                try {
                    str = new String(bytes, "ISO-8859-1");
                    tv.setText(str);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
