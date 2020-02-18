package com.byters.TigerApp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


public class WelcomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initToolbar(view);

        view.findViewById(R.id.next_button)
                .setOnClickListener(
                        Navigation.createNavigateOnClickListener(
                                WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment())
                );
    }

    private void initToolbar(@NonNull final View view) {
        // TODO: 17-Feb-20 extract this copy-paste block somewhere
        Toolbar toolbar = view.findViewById(R.id.my_toolbar);
        toolbar.setTitle("WelcomeFragment");
    }
}
