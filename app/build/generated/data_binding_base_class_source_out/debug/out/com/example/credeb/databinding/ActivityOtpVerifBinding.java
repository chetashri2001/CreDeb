// Generated by view binder compiler. Do not edit!
package com.example.credeb.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.credeb.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityOtpVerifBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button GETOTP;

  @NonNull
  public final EditText Verification;

  @NonNull
  public final LinearLayout codeL1;

  @NonNull
  public final ImageView phoneIcon;

  @NonNull
  public final LinearLayout phoneL1;

  @NonNull
  public final EditText phoneNumber;

  @NonNull
  public final TextView resendCode;

  @NonNull
  public final TextView textView5;

  @NonNull
  public final TextView textView6;

  @NonNull
  public final Button verifyBt;

  private ActivityOtpVerifBinding(@NonNull LinearLayout rootView, @NonNull Button GETOTP,
      @NonNull EditText Verification, @NonNull LinearLayout codeL1, @NonNull ImageView phoneIcon,
      @NonNull LinearLayout phoneL1, @NonNull EditText phoneNumber, @NonNull TextView resendCode,
      @NonNull TextView textView5, @NonNull TextView textView6, @NonNull Button verifyBt) {
    this.rootView = rootView;
    this.GETOTP = GETOTP;
    this.Verification = Verification;
    this.codeL1 = codeL1;
    this.phoneIcon = phoneIcon;
    this.phoneL1 = phoneL1;
    this.phoneNumber = phoneNumber;
    this.resendCode = resendCode;
    this.textView5 = textView5;
    this.textView6 = textView6;
    this.verifyBt = verifyBt;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityOtpVerifBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityOtpVerifBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_otp_verif, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityOtpVerifBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.GETOTP;
      Button GETOTP = rootView.findViewById(id);
      if (GETOTP == null) {
        break missingId;
      }

      id = R.id.Verification;
      EditText Verification = rootView.findViewById(id);
      if (Verification == null) {
        break missingId;
      }

      id = R.id.codeL1;
      LinearLayout codeL1 = rootView.findViewById(id);
      if (codeL1 == null) {
        break missingId;
      }

      id = R.id.phone_icon;
      ImageView phoneIcon = rootView.findViewById(id);
      if (phoneIcon == null) {
        break missingId;
      }

      id = R.id.phoneL1;
      LinearLayout phoneL1 = rootView.findViewById(id);
      if (phoneL1 == null) {
        break missingId;
      }

      id = R.id.phoneNumber;
      EditText phoneNumber = rootView.findViewById(id);
      if (phoneNumber == null) {
        break missingId;
      }

      id = R.id.resend_code;
      TextView resendCode = rootView.findViewById(id);
      if (resendCode == null) {
        break missingId;
      }

      id = R.id.textView5;
      TextView textView5 = rootView.findViewById(id);
      if (textView5 == null) {
        break missingId;
      }

      id = R.id.textView6;
      TextView textView6 = rootView.findViewById(id);
      if (textView6 == null) {
        break missingId;
      }

      id = R.id.verifyBt;
      Button verifyBt = rootView.findViewById(id);
      if (verifyBt == null) {
        break missingId;
      }

      return new ActivityOtpVerifBinding((LinearLayout) rootView, GETOTP, Verification, codeL1,
          phoneIcon, phoneL1, phoneNumber, resendCode, textView5, textView6, verifyBt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
