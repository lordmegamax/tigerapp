package com.byters.TigerApp;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;


public class SmsVerificationFragment extends Fragment {
    private static final String TAG = "SmsVerificationFragment";

    private FirebaseAuth mAuth;
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sms_verification, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mView = null;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar(view);

        mView = view;
        mAuth = FirebaseAuth.getInstance();

        final SmsVerificationFragmentArgs args = SmsVerificationFragmentArgs.fromBundle(getArguments());
        final PhoneAuthProvider.ForceResendingToken token = args.getToken();
        final String verificationId = args.getVerificationId();

        // TODO: 18-Feb-20 ask the user to enter the code
        EditText et = view.findViewById(R.id.code_editText);
        final String code = et.getText().toString();

        if (code.isEmpty()) {
            toast("Empty code is not allowed");
        } else {
            final PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            signInWithPhoneAuthCredential(credential);
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            toast("signInWithCredential:success");

                            // FirebaseUser user = task.getResult().getUser();

                            navigateToNotificationScreen();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            toast("signInWithCredential:failure " + task.getException().getMessage());

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                toast("The verification code entered was invalid: " + task.getException().getMessage());
                            }
                        }
                    }
                });
    }

    private void toast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void navigateToNotificationScreen() {
        Navigation.findNavController(mView).navigate(
                SmsVerificationFragmentDirections.actionSmsVerificationFragmentToNotificationsFragment()
        );
    }

    private void initToolbar(@NonNull final View view) {
        // TODO: 17-Feb-20 extract this copy-paste block somewhere
        Toolbar toolbar = view.findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });
    }
}
