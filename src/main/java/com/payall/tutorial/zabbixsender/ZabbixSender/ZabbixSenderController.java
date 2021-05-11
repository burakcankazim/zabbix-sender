package com.payall.tutorial.zabbixsender.ZabbixSender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payall.tutorial.zabbixsender.ZabbixSender.config.ZabbixProperties;
import com.payall.tutorial.zabbixsender.ZabbixSender.dto.RequestModel;
import com.payall.tutorial.zabbixsender.ZabbixSender.dto.ResponseView;
import com.payall.tutorial.zabbixsender.ZabbixSender.pojo.RequestObject;
import com.payall.tutorial.zabbixsender.ZabbixSender.pojo.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@RestController
@Slf4j
public class ZabbixSenderController
{
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ZabbixProperties properties;

    @RequestMapping(method = RequestMethod.POST, path = "/")
    public ResponseView send(@RequestBody RequestModel requestModel) throws IOException {

        String serverIp = requestModel.getZbxServerIp()==null ? properties.getServerIp() : requestModel.getZbxServerIp();
        int serverPort = requestModel.getZbxServerPort()==null ? properties.getServerPort() : requestModel.getZbxServerPort();

        int socketTimeout = properties.getSocketTimeout();
        int connectionTimeout = properties.getConnectionTimeout();

        RequestObject requestObject = RequestObject.builder()
                .data(requestModel.getData())
                .request("sender data")
                .build();

        byte[] requestDataJsonBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestObject).getBytes();

        //Every Zabbix request starts with header and length bytes.
        //https://www.zabbix.com/documentation/current/manual/appendix/items/trapper
        //https://www.zabbix.com/documentation/current/manual/appendix/protocols/header_datalen
        int length = requestDataJsonBytes.length;
        byte[] requestHeaderBytes = {
                'Z', 'B', 'X', 'D',
                '\1',
                (byte)(length & 0xFF),
                (byte)((length >> 8) & 0x00FF),
                (byte)((length >> 16) & 0x0000FF),
                (byte)((length >> 24) & 0x000000FF),
                '\0','\0','\0','\0'};

        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(serverIp, serverPort), connectionTimeout);
        socket.setSoTimeout(socketTimeout);

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

        dataOutputStream.write(requestHeaderBytes);
        dataOutputStream.write(requestDataJsonBytes);

        byte[] responseBytes = new byte[512];
        int readCount = 0;

        int read;
        while ( (read = dataInputStream.read(responseBytes, readCount, responseBytes.length - readCount)) > 0 ) {
            readCount += read;
        }

        ResponseView responseView = new ResponseView();
        responseView.setRequestObject(requestObject);

        if (readCount < 13)
        {
            //If readCount is less then 13 zabbix returned an empty array.
            log.error("Empty response");
            responseView.setResponseObject(null);
        }
        else
        {
            //Response starts with 13 header and length bytes so we offset by 13.
            //https://www.zabbix.com/documentation/current/manual/appendix/items/trapper
            //https://www.zabbix.com/documentation/current/manual/appendix/protocols/header_datalen
            responseView.setResponseObject(objectMapper.readValue(new String(responseBytes, 13, readCount-13, StandardCharsets.UTF_8), ResponseObject.class));
        }
        return responseView;
    }
}
