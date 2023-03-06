package com.example.controller;

import com.example.dto.ContactDTO;
import com.example.dto.ProfileDTO;
import com.example.mapper.ProfileMapper;
import com.example.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody ProfileDTO profileDTO){
        ProfileDTO profile = profileService.createProfile(profileDTO);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("")
    public ResponseEntity<?> getList(){
        List<ProfileDTO> profileList = profileService.getProfileList();
        return ResponseEntity.ok(profileList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable Integer id){
        ProfileDTO profileDTO = profileService.getProfileById(id);
        return ResponseEntity.ok(profileDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id){
        Boolean result = profileService.deleteById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@RequestBody ProfileDTO profileDTO,
                                        @PathVariable("id") Integer id){
        Boolean result = profileService.updateProfile(profileDTO, id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/contact/{contactId}")
    public ResponseEntity<?> getProfileByContactId(@PathVariable Integer contactId){
        ProfileDTO profileDTO = profileService.getProfileByContactId(contactId);
        return ResponseEntity.ok(profileDTO);
    }

    @GetMapping("/profileId/{profileId}")
    public ResponseEntity<?> getMapperByProfileId(@PathVariable Integer profileId){
        ProfileMapper mapper = profileService.getMapperByProfileId(profileId);
        return ResponseEntity.ok(mapper);
    }

    @GetMapping("/contactId/{contactId}")
    public ResponseEntity<?> getMapperByContactId(@PathVariable Integer contactId){
        ProfileMapper mapper = profileService.getMapperByContactId(contactId);
        return ResponseEntity.ok(mapper);
    }

    @GetMapping("/postId/{postId}")
    public ResponseEntity<?> getProfileByPostId(@PathVariable Integer postId){
        ProfileDTO profileDTO = profileService.getProfileByPostId(postId);
        return ResponseEntity.ok(profileDTO);
    }

}
