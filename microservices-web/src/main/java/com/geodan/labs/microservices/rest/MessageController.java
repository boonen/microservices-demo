package com.geodan.labs.microservices.rest;

import com.geodan.labs.microservices.entity.DemoMessage;
import com.geodan.labs.microservices.entity.MessageStatus;
import com.geodan.labs.microservices.service.AMQPMessageSender;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by janb on 12-11-2014.
 */
@RestController
@RequestMapping("/messages")
public class MessageController {

    private int i = 1;

    @Resource
    private AMQPMessageSender messageSender;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public DemoMessage getMessageById(@PathVariable UUID id) {
        return DemoMessage.Builder.demoMessage().build();
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public MessageStatus postMessage(@RequestBody DemoMessage message) {
        DemoMessage message2 = DemoMessage.Builder.demoMessage().withMessage("Message " + i).build();
        MessageStatus result = messageSender.send(message);
        i++;
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<DemoMessage> getMessages() {
        return Collections.emptyList();
    }

}
