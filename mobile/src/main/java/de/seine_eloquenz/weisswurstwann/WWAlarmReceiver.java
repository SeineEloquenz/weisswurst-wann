package de.seine_eloquenz.weisswurstwann;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class WWAlarmReceiver extends BroadcastReceiver {

    public static final int REQUEST_CODE = 12345;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Intent i = new Intent(context, WWIntentService.class);
        context.startService(i);
    }
}
