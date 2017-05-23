package com.masaibar.smartclipboard.notifications;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.masaibar.smartclipboard.R;
import com.masaibar.smartclipboard.views.MainActivity;

public class ResidentNotification {

    private static int ID_RESIDENT_NOTIFICAION = 237;

    private Context mContext;
    private NotificationManager mManager;

    public ResidentNotification(Context context) {
        mContext = context;
        mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void notifyIfNeeded(String title, String content) {
        Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(mContext)
                        .setLargeIcon(icon)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setOngoing(true)
                        .setAutoCancel(false)
                        .setContentIntent(PendingIntent.getActivity(
                                mContext,
                                ID_RESIDENT_NOTIFICAION,
                                MainActivity.getLaunchIntent(mContext),
                                PendingIntent.FLAG_CANCEL_CURRENT)
                        );

        cancel();
        mManager.notify(ID_RESIDENT_NOTIFICAION, builder.build());
    }

    public void cancel() {
        mManager.cancel(ID_RESIDENT_NOTIFICAION);
    }
}
