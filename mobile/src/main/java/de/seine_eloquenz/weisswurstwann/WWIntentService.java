package de.seine_eloquenz.weisswurstwann;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.io.IOException;

public class WWIntentService extends IntentService {

    private static int notificationId = 0;

    public WWIntentService() {
        super("WeisswurstService");
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        createNotificationChannel();
        WeisswurstInfo info = new WeisswurstInfo();
        try {
            if (info.checkWeisswurstStatusTomorrow()) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "WeisswurstWann")
                        .setSmallIcon(R.drawable.ww_notif)
                        .setContentTitle("Weißwürste!")
                        .setContentText(info.getNotificationText())
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat not = NotificationManagerCompat.from(getApplicationContext());
                not.notify(notificationId++, builder.build());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "WeisswurstWann";
            String description = "WeisswurstWann";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("WeisswurstWann", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = this.getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
    }
}
