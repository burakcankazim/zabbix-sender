package com.payall.tutorial.zabbixsender.ZabbixSender.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject
{
    String response;
    String info;
}
