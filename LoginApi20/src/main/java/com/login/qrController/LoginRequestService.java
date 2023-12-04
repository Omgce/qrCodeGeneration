//package com.login.qrController;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Service;
//
//import com.login.auth.AuthenticationController;
//
//@Service
//public class LoginRequestService {
//
//    private final LoginQRCodeRepository repository;
//
//    private final SimpMessagingTemplate messagingTemplate;
//    
//    private final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
//
//
//    public LoginRequestService(LoginQRCodeRepository repository,
//                               SimpMessagingTemplate messagingTemplate) {
//        this.repository = repository;
//        this.messagingTemplate = messagingTemplate;
//    }
//
////    public LoginQRCode findByRoom(String room) {
////        return convertIteratorToList(repository.findAll()).stream().
////                filter(p -> p.getRoom().equals(room)).
////                findFirst().orElse(null);
////    }
////
////    public LoginQRCode save(LoginQRCode loginQRCode) {
////        Optional<LoginQRCode> optionalObject = convertIteratorToList(repository.findAll()).stream().
////                filter(p -> p.getRoom().equals(loginQRCode.getRoom())).
////                findFirst();
////
////        if (optionalObject.isPresent()) {
////            var obj = optionalObject.get();
////            log.info(String.format("updated => old: %s  new: %s", obj.getCode(), loginQRCode.getCode()));
////            obj.setCode(loginQRCode.getCode());
////            return repository.save(obj);
////        }
////        return repository.save(loginQRCode);
////    }
////
////    public void deleteByRoom(String room) {
////        var temp = findByRoom(room);
////        if (temp != null) {
////            repository.delete(temp);
////        }
////    }
////
////
////    public void processMessage(String room, LoginQRCode loginQRCode, String sessionID) {
////
////        if (!room.equals(loginQRCode.getRoom())) {
////            throw new GeneralException("rooms are different!", 400);
////        }
////        if (loginQRCode.getType().equals(LoginQRCodeType.CLIENT)) {
////            save(loginQRCode);
////        } else if (loginQRCode.getType().equals(LoginQRCodeType.MOBILE)) {
////            var existing = findByRoom(loginQRCode.getRoom());
////            if (existing != null && existing.getCode().equals(loginQRCode.getCode())) {
////                log.info("YES");
////                //loginQRCode.setMessage("MY JWT TOKEN");
////                loginQRCode.setType(LoginQRCodeType.SERVER);
////                sendMessage(loginQRCode);
////                deleteByRoom(room);
////            } else {
////                log.error("qr code is expired");
////                sendMessage(generateErrorMessage("QR Code is expired!", loginQRCode.getRoom()));
////            }
////        }
////
////    }
////
////    public void sendMessage(LoginQRCode loginQRCode) {
////        messagingTemplate.convertAndSend("/topic/loginListener/" + loginQRCode.getRoom(), loginQRCode);
////    }
////
////    private List<LoginQRCode> convertIteratorToList(Iterable<LoginQRCode> iterator) {
////        List<LoginQRCode> list = new ArrayList<>();
////        iterator.iterator().forEachRemaining(list::add);
////        return list;
////    }
////
////    private LoginQRCode generateErrorMessage(String message, String room) {
////        return LoginQRCode.builder().room(room).message(message).type(LoginQRCodeType.ERROR).build();
////    }
//}
