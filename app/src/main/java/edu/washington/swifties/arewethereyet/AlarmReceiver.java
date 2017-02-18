package edu.washington.swifties.arewethereyet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by yulong on 2/18/17.
 */

public class AlarmReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    String phoneNumber = intent.getStringExtra(MainActivity.PHONE_NUMBER);
    Toast.makeText(context, phoneNumber + ": Are we there yet?", Toast.LENGTH_SHORT).show();
  }
}
