 package com.example.smartcanedebug;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.Spinner;

        import androidx.appcompat.app.AppCompatActivity;

public class Training extends AppCompatActivity {
    private Button trainingGuide;
    private Button bookTraining;
    private Button smartcaneDiaries;
    private Button trainingCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.training);
        initButtons();
    }


    private void initButtons() {
        this.bookTraining = findViewById(R.id.bookTraining);
        this.bookTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent book = new Intent(getBaseContext(), Book.class);

                startActivity(book);
            }
        });

        this.smartcaneDiaries = findViewById(R.id.smartcaneDiaries);
        this.smartcaneDiaries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent diary = new Intent(getBaseContext(), DiariesActivity.class);

                startActivity(diary);
            }
        });
        this.trainingGuide = findViewById(R.id.trainingGuide);
        this.trainingGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent guide = new Intent(getBaseContext(),GuideActivity.class);

                startActivity(guide);
            }
        });
        this.trainingCourses = findViewById(R.id.trainingCourses);
        this.trainingCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent guide = new Intent(getBaseContext(),CoursesActivity.class);

                startActivity(guide);
            }
        });
    }
    public void goBackPressed(View view) {
        onBackPressed();
    }
}

