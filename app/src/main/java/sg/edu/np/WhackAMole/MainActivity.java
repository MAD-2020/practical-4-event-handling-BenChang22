package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - Feel free to modify the function to suit your program.
    */
    private static final String TAG = "whack-a-mole";
    private Button button1;
    private Button button2;
    private Button button3;
    ArrayList<Button> buttonList = new ArrayList<>();
    private TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.Button1);
        button2 = (Button) findViewById(R.id.Button2);
        button3 = (Button) findViewById(R.id.Button3);
        score = (TextView) findViewById(R.id.scoreboard);
        buttonList.add(button1);
        buttonList.add(button2);
        buttonList.add(button3);
        Log.v(TAG, "Finished Pre-Initialisation!");
    }

    @Override
    protected void onStart() {
        super.onStart();
        setNewMole();
        final View.OnClickListener Pressed = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button verify = (Button) v;
                switch (v.getId()) {
                    case R.id.Button1:
                        Log.v(TAG, "Left Button Clicked!");
                        break;
                    case R.id.Button2:
                        Log.v(TAG, "Middle Button Clicked!");
                        break;
                    case R.id.Button3:
                        Log.v(TAG, "Right Button Clicked!");
                        break;
                    default:
                        Log.v(TAG, "No Input Found!");
                }
                Integer points = Integer.parseInt(score.getText().toString());
                switch (verify.getText().toString()) {
                    case "*":
                        points += 1;
                        score.setText(points.toString());
                        Log.v(TAG, "Hit, score added!");
                        break;
                    case "0":
                        points -= 1;
                        score.setText(points.toString());
                        Log.v(TAG, "Missed, score deducted!");
                        break;

                }
                setNewMole();
                switch (score.getText().toString()) {
                    case "0":
                        break;

                    case "10":
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Warning! insane whack a mole coming");
                        builder.setMessage("would you like to advance to advance mole");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent advance = new Intent(MainActivity.this, Main2Activity.class);
                                advance.putExtra("score", Integer.parseInt(score.getText().toString()));
                                startActivity(advance);
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setNewMole();
                            }

                        });
                        AlertDialog alert = builder.create();
                        alert.setTitle("Warning! insane whack a mole coming");
                        alert.show();


                }


            }

        };
        button1.setOnClickListener(Pressed);
        button2.setOnClickListener(Pressed);
        button3.setOnClickListener(Pressed);
        Log.v(TAG, "Starting GUI!");
    }


    public void setNewMole() {
        Random ran = new Random();
        int randomLocation = ran.nextInt(3);
        buttonList.get(0).setText("0");
        buttonList.get(1).setText("0");
        buttonList.get(2).setText("0");
        buttonList.get(randomLocation).setText("*");
    }
}