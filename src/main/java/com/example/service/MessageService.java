package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    private AccountService accountService;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountService accountService){
        this.messageRepository = messageRepository;
        this.accountService = accountService;
    }

     /**
     * A method to check if validated the message text
     * @param text Text to be validated
     * @return true if the text is valid and false otherwise
     */
    private Boolean isValidaMessage(String text){
        return (text!=null && !text.equals("") && text.length()<=255);
    }

    /**
     * A method to create a new message into the database
     * @param msg The message object to create
     * @return The created message, or null if failed
     */
    public Message createMessage(Message msg){

        if(isValidaMessage(msg.getMessageText()) && accountService.isValidUser(msg.getPostedBy()))
           return messageRepository.save(msg);
        return null;

    }

    /**
     * A method to retrieve all messages available in the database
     */
     public List<Message> retrieveAllMessages(){
        return messageRepository.findAll();
      }

    /**
     * A method to retrieve a message given the message_id
     * @param message_id The message_id for the message to be retrieved
     * @return The retrieved message or null if not found
     */
    public Message retrieveMessageByID(int message_id){
        return messageRepository.findById(message_id).orElse(null);
    }

    /**
     * A method to retrieve all messages posted by a given user
     * @param user_id The user's account_id
     * @return a list of all messages posted by the given user
     */
    public List<Message> retrieveAllMessagesForUser(int user_id){
        return messageRepository.findAllBypostedBy(user_id);//messageDao.retrieveAllMessagesForUser(user_id);
    }

    /**
     * A method to delete a given message given it's message_id
     * @param message_id the message_id for the message to delete
     * @return The deleted message, or null if the message wasn't found in the database
     */
    public Message deleteMessageByMessageID(int message_id){
        Message message = retrieveMessageByID(message_id);
        if(message!= null){// && messageDao.deleteMessageByMessageID(message_id)){
            return message;

        }
        return null;
    }

    public Integer deleteMessageByID(int message_id) {
        Message msg = messageRepository.findById(message_id).orElse(null);

        if(msg != null){
            messageRepository.deleteById(message_id);
            return 1;
        }
            return 0;
        
    }
    

}
