package com.mahya.appsolution;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import static android.content.Context.MODE_PRIVATE;
import static com.mahya.appsolution.SplashActivity.MY_PREFS_NAME;

public class ServiceFragment extends Fragment {

    private ImageView imageViewOne, imageViewTwo, imageViewThree, imageViewFour;
    private TextView textViewTwo, textViewThree, textViewFour;
    private OnFragmentInteractionListener mListener;

    public ServiceFragment() {
        // Required empty public constructor
    }

    public static ServiceFragment newInstance() {
        ServiceFragment fragment = new ServiceFragment();
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
        View v = inflater.inflate(R.layout.fragment_service, container, false);

        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String defaultValue = prefs.getString("language", null);

        imageViewOne = (ImageView) v.findViewById(R.id.imageViewServiceOne);
        imageViewTwo = (ImageView) v.findViewById(R.id.imageViewServiceTwo);
        imageViewThree = (ImageView) v.findViewById(R.id.imageViewServiceThree);
        imageViewFour = (ImageView) v.findViewById(R.id.imageViewServiceFour);
        textViewTwo = (TextView) v.findViewById(R.id.textViewServiceTwo);
        textViewThree = (TextView) v.findViewById(R.id.textViewServiceThree);
        textViewFour = (TextView) v.findViewById(R.id.textViewServiceFour);

        downloadImage("slider5.jpeg", imageViewOne);
        downloadImage("Android_app.png", imageViewTwo);

        downloadImage("android_design.png", imageViewThree);

        downloadImage("support-487504_960_720.jpg", imageViewFour);

        if(defaultValue.matches("fi")) {
            downloadText("textview_service_two_fi.txt", textViewTwo);
            downloadText("textview_service_three_fi.txt", textViewThree);
            downloadText("textview_service_four_fi.txt", textViewFour);
        }
        else if(defaultValue.matches("en")) {
            downloadText("textview_service_two.txt", textViewTwo);
            downloadText("textview_service_three.txt", textViewThree);
            downloadText("textview_service_four.txt", textViewFour);
        }
        else if(defaultValue.matches("sw")) {
            downloadText("textview_service_two_sw.txt", textViewTwo);
            downloadText("textview_service_three_sw.txt", textViewThree);
            downloadText("textview_service_four_sw.txt", textViewFour);
        }

        return v;
    }

    // download  image from fireBase
    private void downloadImage(String s, final ImageView iv) {
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
