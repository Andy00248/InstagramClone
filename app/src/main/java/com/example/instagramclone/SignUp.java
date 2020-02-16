package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity {

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;
    private Button getAll;
    private TextView getData;
    private String allKickBoxers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Save the current Installation to Back4App
        ParseInstallation.getCurrentInstallation().saveInBackground();

        editText1 = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        editText5 = findViewById(R.id.editText5);
        getData = findViewById(R.id.getData);
        getAll = findViewById(R.id.getAllButton);

        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("kickBoxer");
                parseQuery.getInBackground("ncuPI9FQhv", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if(object!=null && e==null) {
                            getData.setText(object.get("name").toString() + " - " + "Punch Power: " +
                                    object.get("punchPower").toString());
                        } else {
                            FancyToast.makeText(SignUp.this, e.getMessage() + "Data Fetch Failed",Toast.LENGTH_SHORT,FancyToast.ERROR,false);
                        }
                    }
                });
            }
        });

        getAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allKickBoxers = "";
                ParseQuery<ParseObject> parseAll = ParseQuery.getQuery("kickBoxer");
                parseAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(e==null) {
                            if(objects.size()>0) {
                                for(ParseObject kickBoxer : objects) {
                                    allKickBoxers = allKickBoxers + kickBoxer.get("name") + "\n";
                                }
                                FancyToast.makeText(SignUp.this, allKickBoxers, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                            } else {
                                FancyToast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                            }
                        } else {
                            FancyToast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        }
                    }
                });
            }
        });
    }

    public void saveButtonClicked(View view) {

//        ParseObject boxer = new ParseObject("boxer");
//        boxer.put("punch_speed", 200);
//        boxer.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e==null){
//                    Toast.makeText(SignUp.this, "Boxer object is saved successfully", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        try {
            final ParseObject kickBoxer = new ParseObject("kickBoxer");
            kickBoxer.put("name", editText1.getText().toString());
            kickBoxer.put("punchSpeed", Integer.parseInt(editText2.getText().toString()));
            kickBoxer.put("punchPower", Integer.parseInt(editText3.getText().toString()));
            kickBoxer.put("kickSpeed", Integer.parseInt(editText4.getText().toString()));
            kickBoxer.put("kickPower", Integer.parseInt(editText5.getText().toString()));
            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        FancyToast.makeText(SignUp.this, kickBoxer.get("name") + " is saved to the server", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
//                    Toast.makeText(SignUp.this, kickBoxer.get("name")+" is saved to the server",Toast.LENGTH_SHORT).show();
                    } else {
                        FancyToast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
//                        Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            FancyToast.makeText(SignUp.this, e.getMessage() + " is saved to the server", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
        }
    }
}
