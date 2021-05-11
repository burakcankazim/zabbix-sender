package com.payall.tutorial.zabbixsender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.payall.tutorial.zabbixsender.ZabbixSender.config")
public class ZabbixSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZabbixSenderApplication.class, args);
	}

}
