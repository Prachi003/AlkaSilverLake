package com.alkline.alkasilverlake.helper;

/**
 * Created by Ravi Birla on 17,December,2018
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.Toast;

import com.alkline.alkasilverlake.R;

import java.util.regex.Pattern;


public class Validation {
    private Context context;

    Pattern PASSWORD_PATTERN
            = Pattern.compile(
            "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}");
    public Validation(Context context) {
        this.context = context;
    }

    private String getString(EditText editText){
        return editText.getText().toString();
    }








    public boolean isPasswordValid(EditText editText) {
        if (getString(editText).isEmpty()) {
//            editText.setError(context.getString(R.string.passEmptyError));
            editText.requestFocus();

            return false;
        } else if (editText.getText().length() <= 8) {
            editText.requestFocus();
            return true;
        }
        else if (!PASSWORD_PATTERN.matcher(editText.getText().toString()).matches()) {
            editText.requestFocus();
            return true;
        }
        else {
//            editText.setError(context.getString(R.string.passLengthError));
            editText.requestFocus();
            return false;
        }
    }


}
