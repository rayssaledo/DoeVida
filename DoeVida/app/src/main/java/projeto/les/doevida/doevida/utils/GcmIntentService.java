package projeto.les.doevida.doevida.utils;

/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import com.google.android.gms.gcm.GoogleCloudMessaging;

//import com.projetoles.controller.UsuarioController;
//import com.projetoles.dao.OnRequestListener;
//import com.projetoles.model.Usuario;

import android.app.AlertDialog;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.views.NaoConectadoActivity;

/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code GcmBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class GcmIntentService extends IntentService {

    public static final int NOTIFICATION_ID = 1;
    public static final String TAG = "GCM Demo";

    private NotificationManager mNotificationManager;
    private HttpUtils mHttp;

    public GcmIntentService() {
        super("GcmIntentService");
        //mHttp = new HttpUtils(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        final Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM will be
             * extended in the future with new message types, just ignore any message types you're
             * not interested in, or that you don't recognize.
             */

            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification(extras.getString("titulo"), extras.getString("mensagem"), extras.getString("dataCriacao"), extras.getString("enderecado"));
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification(extras.getString("titulo"), extras.getString("mensagem"), extras.getString("dataCriacao"), extras.getString("enderecado"));
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

                // This loop represents the service doing some work.
                for (int i = 0; i < 5; i++) {
                    Log.i(TAG, "Working... " + (i + 1)
                            + "/5 @ " + SystemClock.elapsedRealtime());
                    try {

                        Thread.sleep(5000);

                    } catch (InterruptedException e) {

                    }
                }
                Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());


                /*mHttp.post(url, json.toString(), new HttpListener() {
                    @Override
                    public void onSucess(JSONObject result) throws JSONException {
                        if (result.getInt("ok") == 0) {
                            new AlertDialog.Builder(DonorsActivity.this)
                                    .setTitle("Erro")
                                    .setMessage(result.getString("msg"))
                                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            mLoading.setVisibility(View.GONE);
                                        }
                                    })
                                    .create()
                                    .show();
                        } else {
                            new AlertDialog.Builder(DonorsActivity.this)
                                    .setMessage("Fomulário criado com sucesso")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            // dialog.dismiss();
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                    }

                    @Override
                    public void onTimeout() {
                        new AlertDialog.Builder(DonorsActivity.this)
                                .setTitle("Erro")
                                .setMessage("Conexão não disponível")
                                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        mLoading.setVisibility(View.GONE);
                                    }
                                })
                                .create()
                                .show();
                    }
                });


                gettingNotification(new HttpListener(){
                    @Override
                    public void onSucess(JSONObject jsonObject){

                    }

                    @Override
                    public void onTimeout() {

                    }

                });*/



                // Post notification of received message.
//                final UsuarioController controller = new UsuarioController(GcmIntentService.this);
//
//                controller.get(extras.getString("titulo"), new OnRequestListener<Usuario>(this) {
//
//                    @Override
//                    public void onSuccess(Usuario result) {
//                        sendNotification(result.getNome(), extras.getString("mensagem"), extras.getString("dataCriacao"), extras.getString("enderecado"));
//
//                        controller.getUsuarioLogado(new OnRequestListener<Usuario>(GcmIntentService.this) {
//
//                            @Override
//                            public void onSuccess(Usuario usuario) {
//                                if (extras.getString("enderecado").equals(usuario.getId()))
//                                    usuario.getNotificacoes().add(extras.getString("_id"), Long.valueOf(extras.getString("date")));
//                            }
//
//                            @Override
//                            public void onError(String errorMessage) {
//                                Toast.makeText(GcmIntentService.this, errorMessage, Toast.LENGTH_LONG).show();
//                            }
//
//                            @Override
//                            public void onTimeout() {
//                                Toast.makeText(GcmIntentService.this, "Ocorreu um erro com a sua requisição. Verifique sua conexão com a internet.", Toast.LENGTH_LONG).show();
//                            }
//                        }, null);
//
//                        Log.i(TAG, "Received: " + extras.toString());
//                    }
//
//                    @Override
//                    public void onError(String errorMessage) {
//                        Log.e(TAG, errorMessage);
//                    }
//
//                    @Override
//                    public void onTimeout() {
//                        Log.e(TAG, "TIMEOUT");
//                    }
//                });
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void gettingNotification(HttpListener httpListener){

    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String titulo, String msg, String string, String enderecado) {

        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(this, NaoConectadoActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_laucher)
                .setContentTitle("Doe Vida")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(titulo + msg))
                .setContentText(titulo + msg);

        mBuilder.setContentIntent(contentIntent);

        Notification n = mBuilder.build();
        n.vibrate = new long[]{150, 300, 150, 600};
        n.flags = Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(NOTIFICATION_ID, n);

        try{
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(this, som);
            toque.play();
        }
        catch(Exception e){}
    }
}