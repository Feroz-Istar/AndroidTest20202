package com.test.androidtest20202.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;
import com.test.androidtest20202.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TranslatorActivity extends AppCompatActivity {
    private static final String TAG = "TranslatorActivity";



    @BindView(R.id.editTextTextPersonName)
    EditText input;

    @BindView(R.id.result)
    TextView result;
    FirebaseTranslator englishGermanTranslator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator);
        ButterKnife.bind(this);

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build();
        FirebaseTranslatorOptions options =
                new FirebaseTranslatorOptions.Builder()
                        .setSourceLanguage(FirebaseTranslateLanguage.EN)
                        .setTargetLanguage(FirebaseTranslateLanguage.HI)
                        .build();
        englishGermanTranslator =
                FirebaseNaturalLanguage.getInstance().getTranslator(options);
        englishGermanTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                Log.d(TAG,"Model is ready");
                                // Model downloaded successfully. Okay to start translating.
                                // (Set a flag, unhide the translation UI, etc.)
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG," Model couldn’t be downloaded or other internal error");

                                // Model couldn’t be downloaded or other internal error.
                                // ...
                            }
                        });




    }

    @OnClick(R.id.submit)
    public void translate(){
        if(input.getText() != null && !input.getText().toString().trim().equalsIgnoreCase("")){
            englishGermanTranslator.translate(input.getText().toString())
                    .addOnSuccessListener(
                            new OnSuccessListener<String>() {
                                @Override
                                public void onSuccess(@NonNull String translatedText) {
                                    // Translation successful.
                                    result.setText(translatedText);
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Error.
                                    // ...
                                    Log.d(TAG,"exception");
                                }
                            });
        }else{
            Toast toast=Toast.makeText(getApplicationContext(),"Empty string" +
                    "",Toast.LENGTH_SHORT);
            toast.setMargin(50,50);
            toast.show();          }
    }
}