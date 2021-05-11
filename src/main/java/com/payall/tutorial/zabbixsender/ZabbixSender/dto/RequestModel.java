package com.payall.tutorial.zabbixsender.ZabbixSender.dto;

import com.payall.tutorial.zabbixsender.ZabbixSender.pojo.DataObject;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestModel
{
    String zbxServerIp;
    Integer zbxServerPort;
    @NonNull
    List<DataObject> data;
}
