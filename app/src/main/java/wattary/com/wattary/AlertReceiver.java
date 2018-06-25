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

    Recommendation recommendation;
    private NotificationManagerCompat notificationManagerCompat;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        notificationManagerCompat= NotificationManagerCompat.from(context);


        String title="Recommendation";
        String massage="Wattary have some recommended actions for you ";
       // String longmassage=recommendation.Air_degree;

        Intent actvityIntent=new Intent(context,Recommendation.class);
        PendingIntent contentIntent=PendingIntent.getActivity(context,0,actvityIntent,0);



        // Bitmap LargeIcon = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background);

        Notification notification = new NotificationCompat.Builder(context,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.wattaryw)
                .setContentTitle(title)
                .setContentText(massage)
                .setVibrate(new long[] { 1000, 1000 })
                //.setLights(Color.RED, 3000, 3000)
               // .setSound(Uri.parse("uri://sadfasdfasdf.mp3"))
                // .setLargeIcon(LargeIcon)
                .setStyle(new NotificationCompat.InboxStyle()
                         //.addLine(longmassage)
                        //.addLine("line 2")
                        //.addLine("line 3")
                        .setBigContentTitle(" Wattary have some recommended actions for you ")
                        .setSummaryText("Summary Text"))
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLACK)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();
        notificationManagerCompat.notify(2,notification);
    }
}
