package com.byters.TigerApp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomeFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private TextView userInfoTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initFirebase();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (isUserSignedIn(user)) {
            performSingOut();
            return;
        }

        initViews(view);
        initToolbar(view);

        showUserInfo(user);
    }

    private void initFirebase() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void initViews(View view) {
        userInfoTextView = view.findViewById(R.id.user_info_textView);

        view.findViewById(R.id.singout_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSingOut();
            }
        });
    }

    private String getReadableUserInfo(FirebaseUser user) {
        return "Display name: " + user.getDisplayName() + "\n" +
                "Email: " + user.getEmail() + "\n" +
                "Phone number: " + user.getPhoneNumber() + "\n" +
                "Photo url: " + user.getPhotoUrl() + "\n";
    }

    private void showUserInfo(final FirebaseUser user) {
        userInfoTextView.setText(getReadableUserInfo(user));
    }

    private void performSingOut() {
        firebaseAuth.signOut();
        Navigation.findNavController(getView()).navigate(
                HomeFragmentDirections.actionHomeFragmentToWelcomeFragment());
    }

    private boolean isUserSignedIn(final FirebaseUser user) {
        return user == null;
    }

    private void initToolbar(@NonNull final View view) {
        // TODO: 17-Feb-20 extract this copy-paste block somewhere
        Toolbar toolbar = view.findViewById(R.id.my_toolbar);
        toolbar.setTitle("HomeFragment");
    }

}
