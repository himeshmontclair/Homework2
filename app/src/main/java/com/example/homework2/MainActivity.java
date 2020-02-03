package com.example.homework2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Float.NaN;

public class MainActivity extends AppCompatActivity {

    TextView showAverage, showGPA;
    EditText grade1, grade2, grade3, grade4, grade5, grade6;
    Button button;
    View top, middle, bottom;
    float sum, averageGrade;
    int numOfCourse = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showAverage = findViewById(R.id.showAverage);
        showGPA = findViewById(R.id.showGPA);
        button = findViewById(R.id.button);
        grade1 = findViewById(R.id.grade1);
        grade2 = findViewById(R.id.grade2);
        grade3 = findViewById(R.id.grade3);
        grade4 = findViewById(R.id.grade4);
        grade5 = findViewById(R.id.grade5);
        grade6 = findViewById(R.id.grade6);
        top = findViewById(R.id.top);
        middle = findViewById(R.id.middle);
        bottom = findViewById(R.id.bottom);

        grade1.setFilters(new InputFilter[] {new MinMaxFilter("0", "100")});
        grade2.setFilters(new InputFilter[] {new MinMaxFilter("0", "100")});
        grade3.setFilters(new InputFilter[] {new MinMaxFilter("0", "100")});
        grade4.setFilters(new InputFilter[] {new MinMaxFilter("0", "100")});
        grade5.setFilters(new InputFilter[] {new MinMaxFilter("0", "100")});
        grade6.setFilters(new InputFilter[] {new MinMaxFilter("0", "100")});

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                try {
                    String[] grade = new String[]{grade1.getText().toString(), grade2.getText().toString(),
                            grade3.getText().toString(), grade4.getText().toString(),
                            grade5.getText().toString(), grade6.getText().toString()};
                    for (String s : grade) {
                        if (!s.equals("0")) {
                            sum += Float.valueOf(s);
                        }else{
                            numOfCourse--;
                        }
                    }
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, R.string.Fill, Toast.LENGTH_SHORT).show();
                    sum = 0;
                    showAverage.setText(R.string.DefaultGrade);
                    showGPA.setText(R.string.DefaultGPA);
                    return;
                }

                
                averageGrade = sum / numOfCourse;
                if (averageGrade == NaN){
                    Toast.makeText(MainActivity.this, R.string.Doge, Toast.LENGTH_SHORT).show();
                    middle.setBackgroundResource(R.drawable.doge);
                    showGPA.setText(R.string.Doge);
                    showAverage.setText(R.string.Doge);
                    button.setText(R.string.Doge);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clean();
                            recreate();
                        }
                    });
                }

                if (averageGrade >= 80 && averageGrade <= 100){
                    top.setBackgroundResource(R.color.colorGreen);
                    bottom.setBackgroundResource(R.color.colorGreen);
                    middle.setBackgroundResource(R.drawable.stroke_green);
                    button.setBackgroundResource(R.drawable.button_green);
                    Toast.makeText(MainActivity.this, R.string.Keep, Toast.LENGTH_SHORT).show();
                    button.setText(R.string.Clear);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clean();
                            recreate();
                        }
                    });
                }
                else if (averageGrade >= 61 && averageGrade <80){
                    top.setBackgroundResource(R.color.colorYellow);
                    bottom.setBackgroundResource(R.color.colorYellow);
                    middle.setBackgroundResource(R.drawable.stroke_yellow);
                    button.setBackgroundResource(R.drawable.button_yellow);
                    Toast.makeText(MainActivity.this, R.string.AlmostA, Toast.LENGTH_SHORT).show();
                    button.setText(R.string.Clear);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clean();
                            recreate();
                        }
                    });
                }
                else {
                    top.setBackgroundResource(R.color.colorRed);
                    bottom.setBackgroundResource(R.color.colorRed);
                    middle.setBackgroundResource(R.drawable.stroke_red);
                    button.setBackgroundResource(R.drawable.button_red);
                    Toast.makeText(MainActivity.this, R.string.WorkHard, Toast.LENGTH_SHORT).show();
                    button.setText(R.string.Clear);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clean();
                            recreate();
                        }
                    });
                }
                if (averageGrade >= 90 && averageGrade <= 100){
                    showGPA.setText(R.string.A);
                }
                else if (averageGrade >= 80 && averageGrade <90){
                    showGPA.setText(R.string.B);
                }
                else if (averageGrade >= 70 && averageGrade <80){
                    showGPA.setText(R.string.C);
                }else if (averageGrade >= 60 && averageGrade <70){
                    showGPA.setText(R.string.D);
                }
                else {
                    showGPA.setText(R.string.F);
                }
                showAverage.setText(Float.toString(averageGrade));
                numOfCourse = 6;
                sum = 0;
                averageGrade = 0;
            }
        });
    }

    public void clean(){
        grade1.setText("");
        grade2.setText("");
        grade3.setText("");
        grade4.setText("");
        grade5.setText("");
        grade6.setText("");
    }

    public class MinMaxFilter implements InputFilter {

        private float Min, Max;

        public MinMaxFilter(String minValue, String maxValue) {
            this.Min = Float.parseFloat(minValue);
            this.Max = Float.parseFloat(maxValue);
        }
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                Float input = Float.parseFloat(dest.toString() + source.toString());
                if (isInRange(Min, Max, input))
                    return null;
            } catch (NumberFormatException nfe) { }
            return "";
        }
        private boolean isInRange(float a, float b, float c) {
            return c <= b && c >= a;
        }
    }
}
