package wattary.com.wattary;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import static wattary.com.wattary.Controller.CHANNEL_1_ID;

public class AlertReceiver extends BroadcastReceiver {

    private NotificationManagerCompat notificationManagerCompat;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        notificationManagerCompat= NotificationManagerCompat.from(context);


        String title="title2";
        String massage="massage";
        String longmassage="Line 1";

        Intent actvityIntent=new Intent(context,Recommendation.class);
        PendingIntent contentIntent=PendingIntent.getActivity(context,0,actvityIntent,0);



        // Bitmap LargeIcon = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background);

        Notification notification = new NotificationCompat.Builder(context,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.wattary)
                .setContentTitle(title)
                .setContentText(massage)
                // .setLargeIcon(LargeIcon)
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine(longmassage)
                        .addLine("line 2")
                        .addLine("line 3")
                        .setBigContentTitle("Big Content Title")
                        .setSummaryText("Summary Text"))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();
        notificationManagerCompat.notify(2,notification);
    }
}
