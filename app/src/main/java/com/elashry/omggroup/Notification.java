package com.elashry.omggroup;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Map;

public class Notification extends FirebaseMessagingService {

    private Map<String, String> map;
    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNewVersionNotification(bitmap);

            } else {
                createOldVersionNotification(bitmap);

            }


        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            map = remoteMessage.getData();

            for (String key : map.keySet()) {
                Log.e("key", key + "Value :" + map.get(key));
            }

            String image = map.get("image");
            final String image_url = "http://admin.omgchannel.net/storage/" + image;

            new Handler(Looper.getMainLooper())
                    .postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Picasso.with(Notification.this).load(Uri.parse(image_url)).resize(512, 512).into(target);
                        }
                    }, 1);
        }
    }


    private void createOldVersionNotification(Bitmap bitmap) {
        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.logo_512);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(map.get("title"));
        //builder.setContentText(map.get("msg"));
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setLargeIcon(logo);
        builder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(bitmap)
                .bigLargeIcon(null)
                .setSummaryText(map.get("msg"))
                .setBigContentTitle(map.get("title"))
        );
        Intent intent = new Intent(this, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(1, builder.build());

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNewVersionNotification(Bitmap bitmap) {
        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.logo_512);

        int importance = NotificationManager.IMPORTANCE_HIGH;
        String CHANNEL_ID = "my_channel_02";
        CharSequence CHANNEL_NAME = "my_channel_name";
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        channel.setShowBadge(true);
        channel.enableLights(true);
        channel.setSound(sound, new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).
                        setLegacyStreamType(AudioManager.STREAM_NOTIFICATION).build());
        builder.setChannelId(CHANNEL_ID);
        builder.setContentTitle(map.get("title"));
        //builder.setContentText(map.get("msg"));
        builder.setAutoCancel(true);
        builder.setLargeIcon(logo);

        builder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(bitmap)
                .bigLargeIcon(null)
                .setSummaryText(map.get("msg"))
                .setBigContentTitle(map.get("title"))
        );
        builder.setSmallIcon(R.drawable.ic_notification);

        Intent intent = new Intent(this, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);


        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.createNotificationChannel(channel);
            manager.notify(1, builder.build());

        }


    }
}
