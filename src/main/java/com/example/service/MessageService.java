package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    MessageRepository repository;

    @Autowired
    public MessageService( MessageRepository repository){
        this.repository = repository;
    }

    public Message createNewMessage(Message message){
        if(message.getMessageText().length() < 255 && message.getMessageText() != ""){
           return repository.save(message);
        }
        return null;
    }

    public List<Message> getAllMessages(){
        return repository.findAll();
    }

    public List<Message> getAllMessages(int account_id){
        return repository.findMessagesByPostedBy(account_id);
    }

    public Message getMessage(int messageId){
        Optional<Message> check = repository.findById(messageId);
            if(check.isPresent()){
                return check.get();
            }
            return null; 
    }

    public boolean deleteMessage(int messageId){
        if(getMessage(messageId) != null){
            repository.deleteById(messageId);
            return true;
        }
        return false;
    }

    public boolean updateMessage(String update, int messageId){
        Message original = getMessage(messageId);
        if(original != null){
            original.setMessageText(update);
            repository.save(original);
            return true;
        }
        return false;
    }


}
