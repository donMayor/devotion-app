package com.iqube.api;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.iqube.devotionapp.MainActivity;
import com.iqube.devotionapp.R;

/**
 * Created by don_mayor on 5/16/14.
 */
public class AuthenticateScreen extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.authenticate, container, false );
        Button button = (Button) rootView.findViewById(R.id.authenticate);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }

    public static Fragment newInstance(){
        AuthenticateScreen authScreen = new AuthenticateScreen();
        return authScreen;
    }


}
