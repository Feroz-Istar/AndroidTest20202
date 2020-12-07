package com.test.androidtest20202.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.smartreply.FirebaseSmartReply;
import com.google.firebase.ml.naturallanguage.smartreply.FirebaseTextMessage;
import com.google.firebase.ml.naturallanguage.smartreply.SmartReplySuggestion;
import com.google.firebase.ml.naturallanguage.smartreply.SmartReplySuggestionResult;
import com.test.androidtest20202.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SmartReplyActivity extends AppCompatActivity {

    @BindView(R.id.input)
    EditText input;

    @BindView(R.id.result)
    TextView resultText;
    FirebaseSmartReply smartReply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_reply);
        ButterKnife.bind(this);
        smartReply = FirebaseNaturalLanguage.getInstance().getSmartReply();
    }

    @OnClick(R.id.submit)
    public void reply(){

        if(input.getText() != null && !input.getText().toString().trim().equalsIgnoreCase("")) {

            List<FirebaseTextMessage> conversation = new ArrayList<>();

            conversation.add(FirebaseTextMessage.createForLocalUser(
                    input.getText().toString(), System.currentTimeMillis()));


            smartReply.suggestReplies(conversation)
                    .addOnSuccessListener(new OnSuccessListener<SmartReplySuggestionResult>() {
                        @Override
                        public void onSuccess(SmartReplySuggestionResult result) {
                            if (result.getStatus() == SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {
                                // The conversation's language isn't supported, so the
                                // the result doesn't contain any suggestions.

                                Toast toast = Toast.makeText(getApplicationContext(), "STATUS_NOT_SUPPORTED_LANGUAGE" +
                                        "", Toast.LENGTH_SHORT);
                                toast.setMargin(50, 50);
                                toast.show();
                            } else if (result.getStatus() == SmartReplySuggestionResult.STATUS_SUCCESS) {
                                // Task completed successfully
                                // ...
                                StringBuffer sb = new StringBuffer();
                                for (SmartReplySuggestion suggestion : result.getSuggestions()) {
                                    sb.append(suggestion.getText() + "\n");
                                }
                                resultText.setText(sb.toString());


                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Task failed with an exception
                            // ...

                            Toast toast = Toast.makeText(getApplicationContext(), "smartReply failure" +
                                    "", Toast.LENGTH_SHORT);
                            toast.setMargin(50, 50);
                            toast.show();
                        }
                    });
        }else{
            Toast toast=Toast.makeText(getApplicationContext(),"Empty string" +
                    "",Toast.LENGTH_SHORT);
            toast.setMargin(50,50);
            toast.show();
        }
    }
}