package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity {
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 8.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The functions readTimer() and placeMoleTimer() are to inform the user X seconds before starting and loading new mole.
        - Feel free to modify the function to suit your program.
    */ private static final String TAG = "whack-a-mole";
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    ArrayList<Button> buttonList = new ArrayList<>();
    private Integer advScore = 0;
    private TextView total;
    private CountDownTimer moleCdt;
    private CountDownTimer molePlacer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares the existing score brought over.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
         */



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        button1 =  findViewById(R.id.Button1);
        button2 =  findViewById(R.id.Button2);
        button3 =  findViewById(R.id.Button3);
        button4 =  findViewById(R.id.Button4);
        button5 =  findViewById(R.id.Button5);
        button6 =  findViewById(R.id.Button6);
        button7 =  findViewById(R.id.Button7);
        button8 =  findViewById(R.id.Button8);
        button9 = findViewById(R.id.Button9);

        total = findViewById(R.id.scoreboard2);
        Integer advScore = 0;
        buttonList.add(button1);
        buttonList.add(button2);
        buttonList.add(button3);
        buttonList.add(button4);
        buttonList.add(button5);
        buttonList.add(button6);
        buttonList.add(button7);
        buttonList.add(button8);
        buttonList.add(button9);

        Intent data = getIntent();
        advScore = data.getIntExtra("score", 0);
        Log.v(TAG, "Current User Score: " + advScore.toString());
        total.setText(advScore.toString());




    }
    @Override
    protected void onStart(){
        super.onStart();
        readyTimer();
        setNewMole();

        final View.OnClickListener Pressed = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button verify = (Button) v;
                doCheck(verify);

            }
        };
        button1.setOnClickListener(Pressed);
        button2.setOnClickListener(Pressed);
        button3.setOnClickListener(Pressed);
        button4.setOnClickListener(Pressed);
        button5.setOnClickListener(Pressed);
        button6.setOnClickListener(Pressed);
        button7.setOnClickListener(Pressed);
        button8.setOnClickListener(Pressed);
        button9.setOnClickListener(Pressed);

    }

    private void doCheck(Button checkButton)
    {
        Integer points = Integer.parseInt(total.getText().toString());
        switch (checkButton.getText().toString()){
            case "0" :
                Log.v(TAG, "Missed, score deducted!");
                points -= 1;
                total.setText(points.toString());
                break;
            case "*" :
                Log.v(TAG, "Hit, score added!");
                points += 1;
                total.setText(points.toString());
                break;

            default:
                Log.v(TAG,"select a button");

        }
        setNewMole();
    }

    public void setNewMole()
    {
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);
        buttonList.get(0).setText("0");
        buttonList.get(1).setText("0");
        buttonList.get(2).setText("0");
        buttonList.get(3).setText("0");
        buttonList.get(4).setText("0");
        buttonList.get(5).setText("0");
        buttonList.get(6).setText("0");
        buttonList.get(7).setText("0");
        buttonList.get(8).setText("0");
        buttonList.get(randomLocation).setText("*");
    }

    private void readyTimer(){

        moleCdt = new CountDownTimer(11000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                Long remainingTime = millisUntilFinished/1000;
                Log.v(TAG, "Ready CountDown!" + remainingTime);

                String message = "Get ready in " + remainingTime.toString() + " seconds!";
                final Toast cdtMsg = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                cdtMsg.show();
                runTimer(cdtMsg);


            }

            @Override
            public void onFinish() {

                Log.v(TAG, "Ready Countdown Complete!!" );
                String message = "go!";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                placeMoleTimer();
                moleCdt.cancel();

            }
        };
        moleCdt.start();

    }
    private void runTimer(final Toast cdtMsg){
        Timer toastTime = new Timer();
        toastTime.schedule(new TimerTask() {
            @Override
            public void run() {
                cdtMsg.cancel();
            }
        },1000);
    }



    private void placeMoleTimer(){
        molePlacer = new CountDownTimer(1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v(TAG, "New Mole Location!");
                setNewMole();
            }
            @Override
            public void onFinish() {
                molePlacer.start();
            }
        };
        molePlacer.start();

    }
}

