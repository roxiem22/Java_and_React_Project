package ro.tuc.ds2020.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Controller
public class WSController {

    @Autowired
    public SimpMessagingTemplate smt;

    public void sendNotification(String m, String id){
        System.out.println("/notify/msg/"+id);
        smt.convertAndSend("/notify/msg/"+id,m);
    }
}