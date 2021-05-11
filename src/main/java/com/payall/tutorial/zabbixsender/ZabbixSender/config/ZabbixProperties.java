package com.payall.tutorial.zabbixsender.ZabbixSender.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("zabbix")
@Getter
@Setter
public class ZabbixProperties
{
    private String serverIp;
    private Integer serverPort;
    private Integer socketTimeout;
    private Integer connectionTimeout;
}
