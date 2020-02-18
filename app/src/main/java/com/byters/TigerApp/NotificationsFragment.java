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


public class NotificationsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initToolbar(view);

        // TODO: 18-Feb-20 save YES answer from the user somewhere (db, firebase conf, shared prefs, etc.)
        view.findViewById(R.id.yes_button).setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        NotificationsFragmentDirections.actionNotificationsFragmentToHomeFragment()));

        // TODO: 18-Feb-20 save NO answer from the user
        view.findViewById(R.id.no_button).setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        NotificationsFragmentDirections.actionNotificationsFragmentToHomeFragment()));
    }

    private void initToolbar(@NonNull final View view) {
        // TODO: 17-Feb-20 extract this copy-paste block somewhere
        Toolbar toolbar = view.findViewById(R.id.my_toolbar);
        toolbar.setTitle("NotificationsFragment");
    }
}
