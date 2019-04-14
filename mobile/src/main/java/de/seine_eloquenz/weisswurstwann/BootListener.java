package de.seine_eloquenz.weisswurstwann;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * This class is used to instantiate the service checking for the weisswursts whenever the user reboots his phone
 */
public class BootListener extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Scheduler.scheduleAlarm(context);
        }
    }
}
