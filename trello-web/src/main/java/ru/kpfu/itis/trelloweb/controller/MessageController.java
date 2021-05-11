package ru.kpfu.itis.trelloweb.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import ru.kpfu.itis.trelloapi.dto.MessageDTO;
import ru.kpfu.itis.trelloapi.dto.HelloMessageDTO;

/**
 * @author Roman Leontev
 * 22:54 11.05.2021
 * group 11-905
 */

@Controller
public class MessageController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public MessageDTO greeting(HelloMessageDTO message) throws Exception {
        Thread.sleep(1000);
        return new MessageDTO("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}
