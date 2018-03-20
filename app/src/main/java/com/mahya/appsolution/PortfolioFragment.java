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

public class PortfolioFragment extends Fragment {

    private ImageView imageViewOne, imageViewTwo, imageViewThree, imageViewFour;
    private TextView textViewTwo, textViewThree, textViewFour;
    private OnFragmentInteractionListener mListener;

    public PortfolioFragment() {
        // Required empty public constructor
    }

    public static PortfolioFragment newInstance() {
        PortfolioFragment fragment = new PortfolioFragment();
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
        View v = inflater.inflate(R.layout.fragment_portfolio, container, false);

        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String defaultValue = prefs.getString("language", null);

        imageViewOne = (ImageView) v.findViewById(R.id.imageViewPortfolioOne);
        imageViewTwo = (ImageView) v.findViewById(R.id.imageViewPortfolioTwo);
        imageViewThree = (ImageView) v.findViewById(R.id.imageViewPortfolioThree);
        imageViewFour = (ImageView) v.findViewById(R.id.imageViewPortfolioFour);
        textViewTwo = (TextView) v.findViewById(R.id.textViewPortfolioTwo);
        textViewThree = (TextView) v.findViewById(R.id.textViewPortfolioThree);
        textViewFour = (TextView) v.findViewById(R.id.textViewPortfolioFour);

        downloadImage("Portfolio1.JPG", imageViewOne);
        downloadImage("portfolio2.JPG", imageViewTwo);

        downloadImage("portfolio3.JPG", imageViewThree);

        downloadImage("portfolio4.JPG", imageViewFour);

        if(defaultValue.matches("fi")) {
            downloadText("portfolio_two_fi.txt", textViewTwo);
            downloadText("portfolio_three_fi.txt", textViewThree);
            downloadText("portfolio_four_fi.txt", textViewFour);
        }
        else if(defaultValue.matches("en")) {
            downloadText("portfolio_two.txt", textViewTwo);
            downloadText("portfolio_three.txt", textViewThree);
            downloadText("portfolio_four.txt", textViewFour);
        }
        else if(defaultValue.matches("sw")) {
            downloadText("portfolio_two_sw.txt", textViewTwo);
            downloadText("portfolio_three_sw.txt", textViewThree);
            downloadText("portfolio_four_sw.txt", textViewFour);
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
