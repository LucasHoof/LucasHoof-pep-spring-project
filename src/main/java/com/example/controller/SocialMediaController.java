package com.example.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.example.service.*;
import com.example.entity.*;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    //ACCOUNT SERVICE ENDPOINTS//

    @PostMapping("/register")
    public ResponseEntity registration(@RequestBody Account account){
        if(accountService.usernameCheck(account)){
            if(accountService.userRegistration(account) != null){
                return ResponseEntity.status(200).body(null);
            }
            return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.status(409).body(null);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Account account){
        Account check = accountService.login(account);
        if(check != null){
            return ResponseEntity.status(200).body(check);
        }
        return ResponseEntity.status(401).body(null);
    }

    //MESSAGE SERVICE ENDPOINTS//

    @PostMapping("/messages")
    public ResponseEntity createNewMessage(@RequestBody Message message){
        if(accountService.verify(message.getPostedBy())){
            Message created = messageService.createNewMessage(message);
            if(created != null){
                return ResponseEntity.status(200).body(created);
            }
            return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.status(400).body(null);
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity getMessage(@PathVariable Integer message_id){
        return ResponseEntity.status(200).body(messageService.getMessage(message_id));
    }

    @GetMapping("/messages")
    public ResponseEntity getAllMessages(){
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity getAllMessagesById(@PathVariable Integer account_id){
        return ResponseEntity.status(200).body(messageService.getAllMessages(account_id));
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity deleteMessage(@PathVariable Integer message_id){
        if(messageService.deleteMessage(message_id)){
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(200).body(null);
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity updateMessage(@PathVariable Integer message_id, @RequestBody Message message){
        if(messageService.updateMessage(message.getMessageText(), message_id)){
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(400).body(null);
    }

}
