# Zabbix Sender
A Zabbix Sender Implementation. Used to push data into the Zabbix Sender.

A host and a trapper item should be created on Zabbix Server to use this sender.

Host and trappers can be created either using the Zabbix Frontend or Zabbix API.

## Usage
### Configuration
Ip and port of the zabbix server must be configured on the yml file.<br />They also can be sent in the request but it is optional. 

Connection and socket timeout can also be configured via the yml file.

### Request
```
{
    "zbxServerIp": "192.168.1.79",
    "zbxServerPort": 10051,
    "data":
    [
        {
            "host": "host",
            "key": "key1",
            "value": "value1"
        },
        {
            "host": "host",
            "key": "key2",
            "value": "value2"
        }
    ]
}
```
zbxServerIp and zbxServerPort are optional if ip and port are configured properly.

Host and keys of each data object must match with the hosts and items in the Zabbix Server.

### Response
```
{
    "requestObject": {
        "request": "sender data",
        "data":     
        [
            {
                "host": "host",
                "key": "key1",
                "value": "value1"
            },
            {
                "host": "host",
                "key": "key2",
                "value": "value2"
            }
        ]
    },
    "responseObject": {
        "response": "success",
        "info": "processed: 1; failed: 1; total: 2; seconds spent: 0.000116"
    }
}
```
Response shows the request object that we sent to the Zabbix Server and response that is coming from the Zabbix Server.

Each request and response from the server includes a 13 byte header that is not shown in the return.

## Resources
Creating host and item | Zabbix Sender and Trapper Docs
---|---
[Creating a host via frontend](https://www.zabbix.com/documentation/current/manual/config/items/itemtypes/trapper) | [Zabbix sender](https://www.zabbix.com/documentation/current/manual/concepts/sender)
[Zabbix API host.create](https://www.zabbix.com/documentation/current/manual/api/reference/host/create) | [Trapper item object](https://www.zabbix.com/documentation/current/manual/appendix/items/trapper)
[Creating a Trapper item via frontend](https://www.zabbix.com/documentation/current/manual/config/items/itemtypes/trapper)| [Zabbix header and data length](https://www.zabbix.com/documentation/current/manual/appendix/protocols/header_datalen)
[Zabbix API item.create](https://www.zabbix.com/documentation/current/manual/config/items/itemtypes/trapper)|
[Zabbix API item object](https://www.zabbix.com/documentation/current/manual/api/reference/item/object)|
|
