package com.aurelia.loaning.service.notification;

import android.os.Bundle;

public interface INotificationManager {

	void addNotification(Bundle bundle);

	void updateNotification(Bundle bundle);

	void cancelNotification(Bundle bundle);

	void cancelAllNotifications(Bundle bundle);

}
