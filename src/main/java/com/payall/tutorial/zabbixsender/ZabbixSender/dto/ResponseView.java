package com.payall.tutorial.zabbixsender.ZabbixSender.dto;

import com.payall.tutorial.zabbixsender.ZabbixSender.pojo.RequestObject;
import com.payall.tutorial.zabbixsender.ZabbixSender.pojo.ResponseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseView
{
    RequestObject requestObject;
    ResponseObject responseObject;
}
