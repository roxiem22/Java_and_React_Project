package ro.tuc.ds2020.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Controller
public class WSController {

    @Autowired
    public SimpMessagingTemplate smt;

    @MessageMapping("/ws")
    public void receiveMessage(String message){
        System.out.println(message);
        smt.convertAndSend("/chat/message/",message);
    }

    @MessageMapping("/typing")
    public void receiveTypingStatus(String typingStatus) {
        System.out.println(typingStatus);
        smt.convertAndSend("/chat/typing/", typingStatus);
    }

}