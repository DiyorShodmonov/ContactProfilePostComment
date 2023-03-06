package com.example.controller;

import com.example.dto.ContactDTO;
import com.example.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody ContactDTO contact){
        ContactDTO contactDTO = contactService.createContact(contact);
        return ResponseEntity.ok(contactDTO);
    }

    @GetMapping("")
    public ResponseEntity<?> getList(){
        List<ContactDTO> contactList = contactService.getContactList();
        return ResponseEntity.ok(contactList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContactById(@PathVariable Integer id){
        ContactDTO contactDTO = contactService.getContactById(id);
        return ResponseEntity.ok(contactDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id){
        Boolean result = contactService.deleteById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@RequestBody ContactDTO contactDTO,
                                        @PathVariable Integer id){
        ContactDTO dto = contactService.updateContact(contactDTO, id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/profile/{profileId}")
    public ResponseEntity<?> getContactByProfileId(@PathVariable Integer profileId){
        ContactDTO contactDTO = contactService.getContactByProfileId(profileId);
        return ResponseEntity.ok(contactDTO);
    }

}