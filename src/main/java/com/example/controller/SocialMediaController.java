package com.example.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    //Constructor and dependency injection
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;

    }

    
    /**
     * This is an endpoint to create a new user
     * @param acc Account object
     * @return ResposeEntity object containing response information 
     */
    @PostMapping("/register")
    public ResponseEntity<String> signUp(@RequestBody Account acc) {
        
        Account registeredAcc = accountService.register(acc);
        if(registeredAcc==null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User not registred");
        }
        return ResponseEntity.status(HttpStatus.OK).body("User registred");

    }

    /**
     * This is the endpoint to login a user
     * @param acc Account to be logged in
     * @return ResposeEntity object containing response information 
     */
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account acc) {
        
        Account lAcc = accountService.login(acc);
        if(lAcc==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(lAcc);
        }
        return ResponseEntity.status(HttpStatus.OK).body(lAcc);
    }


    /**
     * This is the endpoint to create a new message
     * @param message message to be created
     * @return ResposeEntity object containing response information 
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        Message msg = messageService.createMessage(message);
        if(msg!=null)
           return ResponseEntity.status(HttpStatus.OK).body(msg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);

    }


    /**
     * This is the endpoint to retrieve all messages
     * @return ResponseEntity object containing retrieved messages
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> retrieveAllMessages(){
        List<Message> msgList = messageService.retrieveAllMessages();
        return ResponseEntity.status(HttpStatus.OK).body(msgList);
    }


    /**
     * THis is the endpoint to retrieve a message given its messageId
     * @param messageId the ID of the message to be retrieved
     * @return ResponseEntity object containing retrieved message
     */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> retrieveMessagesById(@PathVariable int messageId){
        Message msg = messageService.retrieveMessageByID(messageId);
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }


    /**
     * This is the endpoint to delete a message given its messageId 
     * @param message_id The ID of the message to be deleted
     * @return ResponseEntity object containing response information
     */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessagesById(@PathVariable int messageId){
        Integer res = messageService.deleteMessageByID(messageId);
        if(res>0)
           return ResponseEntity.status(HttpStatus.OK).body(res);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * This is the endpoint to retrieve all messages for a specific user
     * @param userId The userId for the messages to be retrieved
     * @return ResponseEntity object containing retrieved message
     */ 
    @GetMapping("/accounts/{userId}/messages")
    public ResponseEntity<List<Message>> getAllMessageForUser(@PathVariable int userId){
        List<Message> msgs = messageService.retrieveAllMessagesForUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(msgs);

    }


    /**
     * This is the endpoint to update the messageText for a specific message
     * @param userId The userId of the message to be updated
     * @param body The object containing the new messageText
     * @return ResponseEntity object containing updated message
     * @throws JsonProcessingException
     */
    @PatchMapping("/messages/{userId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int userId, @RequestBody String body) throws  JsonProcessingException{

        ObjectMapper oMapper = new ObjectMapper();
        String text = oMapper.readTree(body).get("messageText").asText();
   
        int res = messageService.updateMessageText(userId, text);
        if(res==1)  
          return ResponseEntity.status(HttpStatus.OK).body(res);
        
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
}
