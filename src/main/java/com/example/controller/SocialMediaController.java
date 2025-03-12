package com.example.controller;

import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.UserReqDto;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {


    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;

    }


    @PostMapping("/register")
    public ResponseEntity<String> signUp(@RequestBody Account acc) {
        
        Account registeredAcc = accountService.register(acc);
        if(registeredAcc==null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User not registred");
        }
        return ResponseEntity.status(HttpStatus.OK).body("User registred");

    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account acc) {
        
        Account lAcc = accountService.login(acc);
        if(lAcc==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(lAcc);
        }
        return ResponseEntity.status(HttpStatus.OK).body(lAcc);
    }


    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        Message msg = messageService.createMessage(message);
        if(msg!=null)
           return ResponseEntity.status(HttpStatus.OK).body(msg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);

    }


    @GetMapping("/messages")
    public ResponseEntity<List<Message>> retrieveAllMessages(){
        List<Message> msgList = messageService.retrieveAllMessages();
        return ResponseEntity.status(HttpStatus.OK).body(msgList);
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> retrieveMessagesById(@PathVariable int message_id){
        Message msg = messageService.retrieveMessageByID(message_id);
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }


    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessagesById(@PathVariable int message_id){
        Integer res = messageService.deleteMessageByID(message_id);
        if(res>0)
           return ResponseEntity.status(HttpStatus.OK).body(res);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
     
    @GetMapping("/accounts/{user_id}/messages")
    public ResponseEntity<List<Message>> getAllMessageForUser(@PathVariable int user_id){
        List<Message> msgs = messageService.retrieveAllMessagesForUser(user_id);
        return ResponseEntity.status(HttpStatus.OK).body(msgs);

    }
}
