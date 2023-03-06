package com.example.repository;

import com.example.entity.ContactEntity;
import com.example.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContactRepository extends JpaRepository<ContactEntity, Integer> {

//    @Query("from  ContactEntity as c inner join c.profile as p where p.id= :profileId")
    ContactEntity findByProfile_Id(Integer profileId);

    ContactEntity findByProfile(ProfileEntity profileEntity);

}
