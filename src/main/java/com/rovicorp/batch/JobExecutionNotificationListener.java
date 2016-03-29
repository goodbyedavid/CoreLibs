package com.rovicorp.batch;

import javax.management.Notification;
import javax.management.NotificationListener;

public class JobExecutionNotificationListener implements NotificationListener {

	@Override
	public void handleNotification(Notification arg0, Object arg1) {
		System.out.println("notified");
	}

}
