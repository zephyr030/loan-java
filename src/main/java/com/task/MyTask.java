package com.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("MyTask")
public class MyTask{
	
	@Scheduled(cron = "10,15,45 * * * * ?")
	public void testTask() {
//		System.out.println("这个是定时任务");
	}
}
