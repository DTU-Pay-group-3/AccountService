package account.service;

import messaging.implementations.RabbitMqQueue;

// @Author Jacob
public class StartUp {
	public static void main(String[] args) throws Exception {
		new StartUp().startUp();
	}

	private void startUp() throws Exception {
		System.out.println("startup");
		var mq = new RabbitMqQueue("rabbitMq");
		new AccountService(mq);
	}
}
