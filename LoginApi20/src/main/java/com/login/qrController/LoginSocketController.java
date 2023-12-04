//package com.login.qrController;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.login.auth.AuthenticationController;
//
//@RestController
//public class LoginSocketController {
//
//    private final SimpMessagingTemplate messagingTemplate;
//    private final LoginRequestService service;
//    private final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
//
//    public LoginSocketController(SimpMessagingTemplate messagingTemplate,
//                                 LoginRequestService service) {
//        this.messagingTemplate = messagingTemplate;
//        this.service = service;
//    }
//    
//    @MessageMapping("/login/{qrToken}")
//    public void messageHandler(@DestinationVariable String qrToken,
//                               @Payload LoginQRCode loginQRCode,
//                               @Header("simpSessionId") String sessionId) {
//        log.info(String.format("qrToken: %s, model: %s ", qrToken, loginQRCode.toString()));
//        //service.processMessage(room, loginQRCode, sessionId);
//        //messagingTemplate.convertAndSend("/topic/fileStatus/" + id, msg);
//
//    }
//
////    @MessageMapping("/login/{qrToken}")
////    public void messageHandler(@DestinationVariable String qrToken,
////                               @Payload LoginQRCode loginQRCode,
////                               @Header("simpSessionId") String sessionId) {
////        log.info(String.format("Room: %s, model: %s ", qrToken, loginQRCode.toString()));
////        //service.processMessage(room, loginQRCode, sessionId);
////        //messagingTemplate.convertAndSend("/topic/fileStatus/" + id, msg);
////
////    }
//
//}
