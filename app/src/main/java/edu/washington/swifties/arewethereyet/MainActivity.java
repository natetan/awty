package edu.washington.swifties.arewethereyet;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

  private EditText messageEditText, phoneEditText, intervalEditText;
  private Button startStopButton;
  private boolean fieldsAreFilled = true;

  public static final String PHONE_NUMBER = "phone_number";
  public static final int INTERVAL = 10000;

  private PendingIntent pi;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    messageEditText = (EditText) findViewById(R.id.messageEditText);
    phoneEditText = (EditText) findViewById(R.id.phoneEditText);
    intervalEditText = (EditText) findViewById(R.id.intervalEditText);

    final Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
    pi = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);

    startStopButton = (Button) findViewById(R.id.startStopButton);

    startStopButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (messageEditText.getText().length() == 0 ||
            phoneEditText.getText().length() == 0 ||
            intervalEditText.getText().length() == 0) {
          fieldsAreFilled = false;
        }

        alarmIntent.putExtra(PHONE_NUMBER, phoneEditText.getText().toString());

        // Do something if all the fields are filled up
        if (fieldsAreFilled) {
          // Starts the service
          if (startStopButton.getText().toString().equals("START")) {
            startStopButton.setText("CANCEL");
            startStopButton.setTextColor(Color.RED);
            startAlarm();
          } else {
            startStopButton.setText("START");
            startStopButton.setTextColor(Color.GREEN);
            stopAlarm();
          }
        }
      }
    });

  }

  private void startAlarm() {
    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), INTERVAL, pi);
  }

  private void stopAlarm() {
    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    manager.cancel(pi);
  }
}
