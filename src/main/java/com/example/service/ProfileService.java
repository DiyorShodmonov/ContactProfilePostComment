package com.example.service;

import com.example.dto.ProfileDTO;
import com.example.entity.ContactEntity;
import com.example.entity.ProfileEntity;
import com.example.exceptions.ItemNotFoundException;
import com.example.mapper.ProfileMapper;
import com.example.repository.ContactRepository;
import com.example.repository.PostRepository;
import com.example.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ContactService contactService;

    @Autowired
    private PostRepository postRepository;

    public ProfileDTO createProfile(ProfileDTO profileDTO) {
        ProfileEntity profileEntity = toEntity(profileDTO);

        profileRepository.save(profileEntity);
        profileDTO.setId(profileEntity.getId());
        return profileDTO;
    }
    public List<ProfileDTO> getProfileList(){
        List<ProfileEntity> profileEntityList = profileRepository.findAll();

        List<ProfileDTO> profileDTOS = new ArrayList<>();

        for (ProfileEntity profileEntity : profileEntityList) {
            ProfileDTO profileDTO = toDTO(profileEntity);
            profileDTOS.add(profileDTO);
        }

        return profileDTOS;
    }
    public ProfileDTO getProfileById(Integer id) {
        ProfileEntity profileEntity = profileRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Item not found");
        });
        return toDTO(profileEntity);
    }
    public Boolean deleteById(Integer id) {
        get(id);
        profileRepository.deleteById(id);
        return true;
    }
    public Boolean updateProfile(ProfileDTO profileDTO, Integer id){
//        profileDTO.setContact(contactService.toDTO(contactService.get(profileDTO.getContactId())));

        ProfileEntity profileEntity = get(id);
        profileEntity.setName(profileDTO.getName());
        profileEntity.setSurname(profileDTO.getSurname());
        profileEntity.setContact(contactService.get(profileDTO.getContactId()));

        profileRepository.update(profileEntity.getName(), profileEntity.getSurname(),
                profileEntity.getContact(), profileEntity.getId());
        return true;
    }
    public ProfileDTO getProfileByContactId(Integer contactId) {
        contactService.get(contactId);
        ProfileEntity profile = profileRepository.findByContact_Id(contactId);
        return toDTO(profile);
    }
    public ProfileDTO getProfileByContactEntity(Integer contactId){
        contactRepository.findById(contactId);
     //   ProfileEntity profile = profileRepository.getProfileByContact(contactEntity);
      //  return toDTO(profile);
     return null;
    }







    public ProfileEntity toEntity(ProfileDTO profileDTO) {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setName(profileDTO.getName());
        profileEntity.setSurname(profileDTO.getSurname());
        profileEntity.setContact(contactService.get(profileDTO.getContactId()));
        return profileEntity;
    }
    public ProfileDTO toDTO(ProfileEntity profileEntity) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(profileEntity.getId());
        profileDTO.setName(profileEntity.getName());
        profileDTO.setSurname(profileEntity.getSurname());
        profileDTO.setContact(contactService.toDTO(profileEntity.getContact()));
        return profileDTO;
    }

    public ProfileEntity get(Integer id){
        return profileRepository.findById(id).orElseThrow(()->{
            throw new ItemNotFoundException("Item not found");
        });
    }

    public ProfileMapper getMapperByProfileId(Integer profileId) {
        profileRepository.findById(profileId).orElseThrow(() -> {
            throw new ItemNotFoundException("Item not found");
        });

        ProfileMapper profile = profileRepository.getMapperByProfileId(profileId);
        return profile;
    }

    public ProfileMapper getMapperByContactId(Integer contactId) {
        profileRepository.findById(contactId).orElseThrow(() -> {
            throw new ItemNotFoundException("Item not found");
        });

        ProfileMapper profile = profileRepository.getMapperByContactId(contactId);
        return profile;    }

    public ProfileDTO getProfileByPostId(Integer postId) {
        postRepository.findById(postId).orElseThrow(() -> {
            throw new ItemNotFoundException("Item not found");
        });

        ProfileEntity profile = profileRepository.getProfileByPostId(postId);
        return toDTO(profile);
    }
}
