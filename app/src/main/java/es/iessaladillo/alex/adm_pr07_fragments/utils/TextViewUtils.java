package es.iessaladillo.alex.adm_pr07_fragments.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import es.iessaladillo.alex.adm_pr07_fragments.R;
import es.iessaladillo.alex.adm_pr07_fragments.ui.profile.ProfileActivityViewModel;


public class TextViewUtils {
    private TextViewUtils() {
    }

    public static void afterTextChanged(EditText text, TextView view, Context context, ProfileActivityViewModel viewModel) {
        text.addTextChangedListener(new TextWatcher() {
            boolean validation;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                validation = text.getText().toString().isEmpty();
                if (validation) {
                    text.setError(context.getString(R.string.main_invalid_data));
                    view.setEnabled(false);
                } else {
                    view.setEnabled(true);
                }
                viewModel.setValid_name(!validation);
            }
        });
    }

    public static void afterTextChanged(EditText text, TextView view, ImageView img, Context context, ProfileActivityViewModel viewModel) {
        text.addTextChangedListener(new TextWatcher() {
            boolean validation;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                validation = !text.getText().toString().isEmpty();
                statusChange(text, view, img, validation, context);
                viewModel.setValid_address(validation);
            }

        });
    }

    private static void statusChange(EditText text, TextView view, ImageView img, boolean valid,Context context) {
        if (!valid) {
            text.setError(context.getString(R.string.main_invalid_data));
            view.setEnabled(false);
            img.setEnabled(false);
        } else {
            view.setEnabled(true);
            img.setEnabled(true);
        }
    }

    public static void onTextChanged(EditText txt, TextView lbl, ImageView img, Context context, ProfileActivityViewModel viewModel) {
        txt.addTextChangedListener(new TextWatcher() {
            boolean validation = false;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (R.id.txtEmail == txt.getId()) {
                    validation = ValidationUtils.isValidEmail(txt.getText().toString());
                    viewModel.setValid_email(validation);
                } else if (R.id.txtPhonenumber == txt.getId()) {
                    validation = ValidationUtils.isValidPhone(txt.getText().toString());
                    viewModel.setValid_phonenumber(validation);
                } else if (R.id.txtWeb == txt.getId()) {
                    validation = ValidationUtils.isValidUrl(txt.getText().toString());
                    viewModel.setValid_web(validation);
                }
                 statusChange(txt, lbl, img, validation, context);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    public static void removeOnTextChanged(EditText txt) {
        txt.removeTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public static void changeFocus(EditText txt, TextView lbl) {
        txt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                lbl.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                lbl.setTypeface(Typeface.DEFAULT);
            }
        });
    }

}
