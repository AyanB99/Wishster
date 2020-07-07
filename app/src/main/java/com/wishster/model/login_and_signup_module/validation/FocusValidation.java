package com.wishster.model.login_and_signup_module.validation;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.wishster.view.R;
import com.wishster.view.mylogindesign.ValidateString;

public class FocusValidation {
    public void Validate(EditText textView1, TextView textView2, String message) {
        if (!(textView1.getText().toString().trim().length() > 0)) {
            textView2.setVisibility(View.VISIBLE);
            textView1.setError("The " + message + "  field is required.");
            textView2.setText("The " + message + "  field is required.");

        } else if (message.equals("First Name") || message.equals("Last  Name")) {

            if (!(textView1.getText().toString().trim().matches(ValidateString.pattern_name))) {
                textView2.setVisibility(View.VISIBLE);
                textView1.setError("Valid " + message + "  field is required.");
                textView2.setText("Valid " + message + "  field is required.");

            } else if (message.equals("Mobile No")) {
                if (!(textView1.getText().toString().trim().matches(ValidateString.pattern))) {
                    textView2.setVisibility(View.VISIBLE);
                    textView1.setError("Valid " + message + "  field is required.");
                    textView2.setText("Valid " + message + "  field is required.");

                }
            } else if (message.equals("Email")) {
                if (!(textView1.getText().toString().trim().matches(ValidateString.Expn)))
                    textView2.setVisibility(View.VISIBLE);
                textView1.setError("Valid " + message + "  field is required.");
                textView2.setText("Valid " + message + "  field is required.");

            }
            else if (message.equals("Password")) {
                if (!((textView1.getText().toString().trim().length() > 7) && (textView1.getText().toString().trim().length() < 21))) {
                    textView2.setVisibility(View.VISIBLE);
                    textView1.setError("The password Must be 8 to 20 characters in length");
                    textView2.setText("The password Must be 8 to 20 characters in length");
                } else if (!((textView1.getText().toString().trim().matches(ValidateString.pattern_pass)))) {
                    textView2.setVisibility(View.VISIBLE);
                    textView1.setError("Must contain at least one letter and one number and a special character from !@#$%^&*()_+");
                    textView2.setText("Must contain at least one letter and one number and a special character from !@#$%^&*()_+");
                }
            }
            else if (message.equals("Confirm Password")) {
                if (!(textView1.getText().toString().trim().matches(ValidateString.Expn)))
                    textView2.setVisibility(View.VISIBLE);
                textView1.setError("Password not match");
                textView2.setText("Password not match");

            }

        }

    }


    public void Validate(TextView textView1, TextView textView2, String message){
        if (!(textView1.getText().toString().trim().length() > 0)) {
            textView2.setVisibility(View.VISIBLE);
            textView1.setError("The First Name field is required.");
            textView2.setText("The "+message+" Name field is required.");

        } else if (message.equals("First Name") || message.equals("Last  Name")) {

            if (!(textView1.getText().toString().trim().matches(ValidateString.pattern_name))) {
                textView2.setVisibility(View.VISIBLE);
                textView1.setError("Valid " + message + "  field is required.");
                textView2.setText("Valid " + message + "  field is required.");

            } else if (message.equals("Mobile No")) {
                if (!(textView1.getText().toString().trim().matches(ValidateString.pattern))) {
                    textView2.setVisibility(View.VISIBLE);
                    textView1.setError("Valid " + message + "  field is required.");
                    textView2.setText("Valid " + message + "  field is required.");

                }
            } else if (message.equals("Email")) {
                if (!(textView1.getText().toString().trim().matches(ValidateString.Expn)))
                    textView2.setVisibility(View.VISIBLE);
                textView1.setError("Valid " + message + "  field is required.");
                textView2.setText("Valid " + message + "  field is required.");

            }
            else if (message.equals("Password")) {
                if (!((textView1.getText().toString().trim().length() > 7) && (textView1.getText().toString().trim().length() < 21))) {
                    textView2.setVisibility(View.VISIBLE);
                    textView1.setError("The password Must be 8 to 20 characters in length");
                    textView2.setText("The password Must be 8 to 20 characters in length");
                } else if (!((textView1.getText().toString().trim().matches(ValidateString.pattern_pass)))) {
                    textView2.setVisibility(View.VISIBLE);
                    textView1.setError("Must contain at least one letter and one number and a special character from !@#$%^&*()_+");
                    textView2.setText("Must contain at least one letter and one number and a special character from !@#$%^&*()_+");
                }
            }
            else if (message.equals("Confirm Password")) {
                if (!(textView1.getText().toString().trim().matches(ValidateString.Expn)))
                    textView2.setVisibility(View.VISIBLE);
                textView1.setError("Password not match");
                textView2.setText("Password not match");

            }

        }
    }
    public void Validate(AutoCompleteTextView textView1, TextView textView2, String message){
        if (!(textView1.getText().toString().trim().length() > 0)) {
            textView2.setVisibility(View.VISIBLE);
            textView1.setError("The First Name field is required.");
            textView2.setText("The "+message+" Name field is required.");

        } else if (message.equals("First Name") || message.equals("Last  Name")) {

            if (!(textView1.getText().toString().trim().matches(ValidateString.pattern_name))) {
                textView2.setVisibility(View.VISIBLE);
                textView1.setError("Valid " + message + "  field is required.");
                textView2.setText("Valid " + message + "  field is required.");

            } else if (message.equals("Mobile No")) {
                if (!(textView1.getText().toString().trim().matches(ValidateString.pattern))) {
                    textView2.setVisibility(View.VISIBLE);
                    textView1.setError("Valid " + message + "  field is required.");
                    textView2.setText("Valid " + message + "  field is required.");

                }
            } else if (message.equals("Email")) {
                if (!(textView1.getText().toString().trim().matches(ValidateString.Expn)))
                    textView2.setVisibility(View.VISIBLE);
                textView1.setError("Valid " + message + "  field is required.");
                textView2.setText("Valid " + message + "  field is required.");

            }
            else if (message.equals("Password")) {
                if (!((textView1.getText().toString().trim().length() > 7) && (textView1.getText().toString().trim().length() < 21))) {
                    textView2.setVisibility(View.VISIBLE);
                    textView1.setError("The password Must be 8 to 20 characters in length");
                    textView2.setText("The password Must be 8 to 20 characters in length");
                } else if (!((textView1.getText().toString().trim().matches(ValidateString.pattern_pass)))) {
                    textView2.setVisibility(View.VISIBLE);
                    textView1.setError("Must contain at least one letter and one number and a special character from !@#$%^&*()_+");
                    textView2.setText("Must contain at least one letter and one number and a special character from !@#$%^&*()_+");
                }
            }
            else if (message.equals("Confirm Password")) {
                if (!(textView1.getText().toString().trim().matches(ValidateString.Expn)))
                    textView2.setVisibility(View.VISIBLE);
                textView1.setError("Password not match");
                textView2.setText("Password not match");

            }

        }
    }

     public void Validate(TextView textView){
         textView.setText("");
         textView.setVisibility(View.GONE);
    }

    void firstNameValidation() {

    }

}
