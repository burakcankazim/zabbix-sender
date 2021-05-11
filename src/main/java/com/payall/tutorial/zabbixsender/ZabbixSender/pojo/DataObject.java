package com.payall.tutorial.zabbixsender.ZabbixSender.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataObject
{
    String host;
    String key;
    String value;
}
