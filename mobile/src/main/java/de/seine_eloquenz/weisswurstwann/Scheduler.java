package de.seine_eloquenz.weisswurstwann;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public final class Scheduler {

    private Scheduler() {

    }

    public static void scheduleAlarm(final Context context) {
        Intent intent = new Intent(context, WWAlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(context, WWAlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis();
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert alarm != null;
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, AlarmManager.INTERVAL_DAY, pIntent);
    }
}
