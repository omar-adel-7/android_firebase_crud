package com.example.firebase_crud.firebase;

import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import modules.general.utils.utils.NotificationUtils;
import com.example.firebase_crud.MainActivity;
import com.example.firebase_crud.R;

import static modules.general.utils.utils.NotificationUtils.ANDROID_CHANNEL_ID;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    NotificationUtils notificationUtils ;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // Verifica se a mensagem contém uma carga útil de dados.
        if (remoteMessage.getData().size() > 0) {
            showNotification(remoteMessage.getData().get("desc"), remoteMessage.getData().get("desc"));
        }

        // Verifica se a mensagem contem payload.
        if (remoteMessage.getNotification() != null) {

        }
    }

    private void showNotification(String titulo, String mensagem) {
        notificationUtils=new NotificationUtils(this);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,ANDROID_CHANNEL_ID)
                .setContentTitle(titulo)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(mensagem)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);


        notificationUtils.getManager().notify(0 /* ID of notification */, notificationBuilder.build());
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
         // Obter o novo InstanceID
        String firebaseToken = s;
        String uid="";
//        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//              uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        }

          uid = firebaseToken;

        FirebaseDatabase.getInstance().getReference().child("users/"+uid+"/FirebaseToken")
                .setValue(firebaseToken);

    }
}