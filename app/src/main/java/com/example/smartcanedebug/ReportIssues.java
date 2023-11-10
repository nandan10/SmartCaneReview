package com.example.smartcanedebug;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
        import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;

        import java.util.ArrayList;
        import java.util.Collections;

public class ReportIssues extends AppCompatActivity {

    // initialize variables
    TextView textView;
    boolean[] selectedLanguage;
    Button Submit;
    private EditText Others1;
    ArrayList<Integer> langList = new ArrayList<>();
    String[] langArray = {" Device is not functioning properly",
            "My device has stopped working",
            "My device is not getting charged",
            "My device broke after falling",
            "My cane is damaged",
            "My cane length is not according to my height",
            "I am unable to connect my device with the app",
            "I am unable to configure my App",
            "I need training support",
            "My reason is not listed"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportissues);
        Others1 = findViewById(R.id.Others);
        Submit = findViewById(R.id.Submit);

        // assign variable
        textView = findViewById(R.id.textView);

        // initialize selected language array
        selectedLanguage = new boolean[langArray.length];

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(ReportIssues.this);

                // set title
                builder.setTitle("Select");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(langArray, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position in lang list
                            langList.add(i);
                            // Sort array list
                            Collections.sort(langList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            langList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < langList.size(); j++) {
                            // concat array value
                            stringBuilder.append(langArray[langList.get(j)]);
                            // check condition
                            if (j != langList.size() - 1) {
                                // When j value not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        textView.setText(stringBuilder.toString());
                    }
                });



                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedLanguage.length; j++) {
                            // remove all selection
                            selectedLanguage[j] = false;
                            // clear language list
                            langList.clear();
                            // clear text view value
                            textView.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            //  private String msg;


            @Override
            public void onClick(View v) {
                // Intent i=new Intent(getApplicationContext(),NavigationActivity.class);
                //  Log.d("SELECT", String.valueOf(i));
                // i.putExtra("key",sel_spinner);
                // startActivity(i);
                String msg = Others1.getText().toString();
                // String msg = "";

                String msgothers =   textView.getText() + "\n" + msg;
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                //  i.setData(Uri.parse("kumari.singh@gmail.com"));
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"kumari.singh@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "REPORT GRIEVANCE");

                i.putExtra(Intent.EXTRA_TEXT, msgothers);

                startActivity(Intent.createChooser(i, "Choose an Email client :"));


            }
        });

    }
   // @Override
   // public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // on below line we are displaying toast message for selected item
       // Toast.makeText(ReportIssues.this, "" + selectedLanguage[position] + " Selected..", Toast.LENGTH_SHORT).show();

      //  String sel_spinner = String.valueOf(textView);


        // @Override
        //public void onNothingSelected(AdapterView<?> adapterView) {

        // }

    public void goBackPressed(View view) {
        onBackPressed();
    }
}




