package com.nda.blum;

import android.media.audiofx.DynamicsProcessing;
import android.media.session.MediaSession;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FCM extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        Log.e("token", "Mi token es:"+s);
        guardartoken(s);

    }

    private void guardartoken(String s) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("token");
        ref.child("Leono").setValue(s);

    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from = remoteMessage.getFrom();

        Log.e("TAG", "Mensaje reecibido de" +from);

        if (remoteMessage.getNotification() != null) {
            Log.e("TAG","El Titulo es" +remoteMessage.getNotification().getTitle());
            Log.e("TAG","El detalle es" +remoteMessage.getNotification().getBody());

        }
    }
}
