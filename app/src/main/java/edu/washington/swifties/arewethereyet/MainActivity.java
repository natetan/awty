package edu.washington.swifties.arewethereyet;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

  private EditText messageEditText, phoneEditText, intervalEditText;
  private Button startStopButton;
  private boolean fieldsAreFilled = true;

  public static final String PHONE_NUMBER = "phone_number";
  public static final String MESSAGE = "message";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    messageEditText = (EditText) findViewById(R.id.messageEditText);
    phoneEditText = (EditText) findViewById(R.id.phoneEditText);
    intervalEditText = (EditText) findViewById(R.id.intervalEditText);


    startStopButton = (Button) findViewById(R.id.startStopButton);

    startStopButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // Checks if all the fields are filled out
        if (messageEditText.getText().toString().length() == 0 ||
            phoneEditText.getText().toString().length() == 0 ||
            intervalEditText.getText().toString().length() == 0) {
          fieldsAreFilled = false;
        } else {
          fieldsAreFilled = true;
        }

        // Tells user that fields are not filled
        if (!fieldsAreFilled) {
          Toast.makeText(MainActivity.this, "Cannot start: Not all fields are filled",
              Toast.LENGTH_SHORT).show();
        }

//        Log.d("start", "Message length: " + messageEditText.getText().toString().length());
//        Log.d("start", "Phone length: " + phoneEditText.getText().toString().length());
//        Log.d("start", "interval length: " + messageEditText.getText().toString().length());

        // Do something if all the fields are filled up
        if (fieldsAreFilled) {
          // Passes the user given phone number

          // Starts the alarm service
          // When start is clicked, the text shows stop and vice versa
          // Start text is green and cancel text is red
          if (startStopButton.getText().toString().equals("START")) {
            startStopButton.setText("CANCEL");
            startStopButton.setTextColor(Color.RED);
            startAlarm(MainActivity.this);
          } else {
            startStopButton.setText("START");
            startStopButton.setTextColor(Color.BLUE);
            stopAlarm(MainActivity.this);
          }
        }
      }
    });
  }

  // Begins the alarm manager with the user given interval
  private void startAlarm(Context context) {
    Toast.makeText(MainActivity.this, "You set alarm for " +
        Integer.parseInt(intervalEditText.getText().toString()) * 1000 + " minutes", Toast.LENGTH_SHORT).show();

    Log.d("startAlarm", "You set alarm for " +
        Integer.parseInt(intervalEditText.getText().toString()) + " minutes");

    Intent alarmIntent = new Intent(context, AlarmReceiver.class);
    alarmIntent.putExtra(PHONE_NUMBER, phoneEditText.getText().toString());
    alarmIntent.putExtra(MESSAGE, messageEditText.getText().toString());
    PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
        Integer.parseInt(intervalEditText.getText().toString()) * 1000 * 60, pi);
  }

  // Cancels the alarm (so it stops running in the background as well)
  private void stopAlarm(Context context) {
    Toast.makeText(MainActivity.this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    Intent alarmIntent = new Intent(context, AlarmReceiver.class);
    PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    manager.cancel(pi);
  }
}
