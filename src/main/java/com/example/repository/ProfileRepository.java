package com.example.repository;

import com.example.entity.ContactEntity;
import com.example.entity.ProfileEntity;
import com.example.mapper.ProfileMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {

    @Transactional
    @Modifying
    @Query("update ProfileEntity set name= ?1, surname= ?2, contact= ?3 where id= ?4")
    void update(String name, String surname,
                         ContactEntity contact, Integer id);

    ProfileEntity findByContact_Id(Integer contactId);

    @Query("select p from ProfileEntity as p inner join p.contact where p.contact=?1")
    ProfileEntity getProfileByContact(ContactEntity contact);

    @Query("SELECT new com.example.mapper.ProfileMapper(p.id, p.name, p.contact.name) from ProfileEntity as p " +
            "where p.id=?1")
    ProfileMapper getMapperByProfileId(Integer profileId);

    @Query("SELECT new com.example.mapper.ProfileMapper(p.id, p.name, p.contact.name) from ProfileEntity as p " +
            "where p.contact.id=?1")
    ProfileMapper getMapperByContactId(Integer contactId);

    @Query("from ProfileEntity as pf inner join pf.post as pt where pt.id=?1")
    ProfileEntity getProfileByPostId(Integer postId);
}



