package com.payall.tutorial.zabbixsender.ZabbixSender.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestObject
{
     String request;
     List<DataObject> data;
}
