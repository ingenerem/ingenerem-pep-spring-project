package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

/**This is a service class for Message based business operations */
@Service
public class MessageService {

    private MessageRepository messageRepository;

    private AccountService accountService;

    //Constructor for dependency injection
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountService accountService){
        this.messageRepository = messageRepository;
        this.accountService = accountService;
    }

     /**
     * A method to validate the messagetext
     * @param text Text to be validated
     * @return true if the text is valid and false otherwise
     */
    private Boolean isValidaMessage(String text){
        return (text!=null && !text.equals("") && text.length()<=255);
    }

    /**
     * A method to create a new message into the database
     * @param msg The message object to be created
     * @return The created message, or null if failed
     */
    public Message createMessage(Message msg){

        //Check if the message is valid and the user exists in the database
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
    public Message retrieveMessageByID(int messageId){
        return messageRepository.findById(messageId).orElse(null);
    }


    /**
     * A method to retrieve all messages posted by a given user
     * @param userId The user's account_id
     * @return a list of all messages posted by the given user
     */
    public List<Message> retrieveAllMessagesForUser(int userId){
        return messageRepository.findAllBypostedBy(userId);
    }


    /**
     * A method to delete a given message given it's messageId
     * @param messageId the messageId of the message to be deleted
     * @return 1 if the message was deleted, or 0 if the message wasn't found in the database
     */
   
    public Integer deleteMessageByID(int messageId) {
        //Retrieve the message to be deleted
        Message msg = messageRepository.findById(messageId).orElse(null);
        if(msg != null){
            //Delete the message if it was found
            messageRepository.deleteById(messageId);
            return 1;
        }
            return -1;
        
    }

  
    /**
     * A method to update a specific message text in the database
     * @param messageId the messageId of the message to be updated
     * @param text the new text
     * @return 1 if the message was updated or -1 otherwise
     */
    public int updateMessageText(int messageId, String text){
        //Retrieve the message to be updated
        Message msg = messageRepository.findById(messageId).orElse(null);
        if(msg!=null && isValidaMessage(text)){
            //Update message if message found
            msg.setMessageText(text);
            messageRepository.save(msg);
            return 1;
        }
        return -1;


    }
    

}
