package tk.kolyanov.reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.HashMap;

public class RemindReceiver extends BroadcastReceiver {

    public RemindReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationUtils utils = new NotificationUtils(context);
        utils.createInfoNotification(intent.getStringExtra("header"),
                intent.getStringExtra("description"), intent.getLongExtra("id", 0));
    }

    class NotificationUtils {

        private Context context;
        private NotificationManager manager; // Системная утилита, упарляющая уведомлениями
        private HashMap<Integer, Notification> notifications; //массив ключ-значение на все отображаемые пользователю уведомления

        NotificationUtils(Context context) {
            manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notifications = new HashMap<Integer, Notification>();
            this.context = context;
        }

        public void createInfoNotification(String header, String description, long id){
            Intent notificationIntent = new Intent(context, MainActivity.class); // по клику на уведомлении откроется HomeActivity
            NotificationCompat.Builder nb = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher) //иконка уведомления
                    .setAutoCancel(true) //уведомление закроется по клику на него
                    .setTicker("Напоминание") //текст, который отобразится вверху статус-бара при создании уведомления
                    .setContentText(description) // Основной текст уведомления
                    .setContentIntent(PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT))
                    .setWhen(System.currentTimeMillis()) //отображаемое время уведомления
                    .setContentTitle(header) //заголовок уведомления
                    .setDefaults(Notification.DEFAULT_ALL); // звук, вибро и диодный индикатор выставляются по умолчанию

            Notification notification = nb.getNotification(); //генерируем уведомление
            manager.notify(((int) id), notification); // отображаем его пользователю.
            notifications.put(((int) id), notification); //теперь мы можем обращаться к нему по id
        }

    }
}
