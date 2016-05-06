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


import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.models.BodyNotification;
import projeto.les.doevida.doevida.models.Form;
import projeto.les.doevida.doevida.views.AcceptedOrderActivity;
import projeto.les.doevida.doevida.views.DonationOrderActivity;

public class GcmIntentService extends IntentService {

    public static final int NOTIFICATION_ID = 1;
    public static final String TAG = "GCM Demo";

    private NotificationManager mNotificationManager;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        final Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            try {
                if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                    //sendNotification(extras.getString("titleNotification"), form);
                } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {

                  //  sendNotification(extras.getString("titleNotification"), form);
                    // If it's a regular GCM message, do some work.
                } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                    String title = extras.getString("titleNotification");
                    JSONObject body = new JSONObject(extras.getString("bodyNotification"));
                    sendNotification(title, body);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String title, JSONObject body) throws JSONException {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = null;

        if(title.equals("Solicitacao de sangue")){
            notificationIntent = new Intent(this, DonationOrderActivity.class);

            String loginDest = body.getString("login");
            String patient = body.getString("patientName");
            String hospital = body.getString("hospitalName");
            String city = body.getString("city");
            String state = body.getString("state");
            String typeOfBlood = body.getString("bloodType");

            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date date = null;
            try {
                date = format.parse(body.getString("deadline"));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                Form form = new Form(loginDest, patient, hospital, city, state, typeOfBlood, date);
                notificationIntent.putExtra("FORMORDER", form);
                startActivity(notificationIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(title.equals("Pedido aceito")){
            notificationIntent = new Intent(this, AcceptedOrderActivity.class);

            String loginDest = body.getString("login");
            String donorName = body.getString("donorName");
            String bloodTypeName = body.getString("bloodTypeDonor");
            String patientName = body.getString("patientName");
            String bloodTypePatient = body.getString("bloodTypePatient");

            try {
                BodyNotification bodyNotification = new BodyNotification(loginDest, donorName, bloodTypeName, patientName, bloodTypePatient);
                notificationIntent.putExtra("BODYNOTIFICATION", bodyNotification);
                startActivity(notificationIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_laucher)
                .setContentTitle("Doe Vida")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(title))
                .setContentText(title);

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