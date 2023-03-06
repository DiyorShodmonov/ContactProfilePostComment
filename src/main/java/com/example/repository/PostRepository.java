package com.example.repository;

import com.example.entity.PostEntity;
import com.example.entity.ProfileEntity;
import com.example.mapper.PostIdPostTitleProfileIdProfileName;
import com.example.mapper.PostIdProfileIdPostCreatedDate;
import com.example.mapper.ProfileMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    @Transactional
    @Modifying
    @Query("update PostEntity set title= ?1, content= ?2, profile= ?3 where id= ?4")
    void update(String title, String content, ProfileEntity profile, Integer id);


    Page<PostEntity> findAllByProfile_Id(Integer profileId, Pageable paging);

    @Query("Select new com.example.mapper.ProfileMapper(p.id, p.content, p.title) from PostEntity as p where p.profile.id=?1")
    Page<ProfileMapper> getPostMapperByProfileId(Integer profileId, Pageable paging);

    @Query("from PostEntity as p where p.id=?1")
    PostEntity getPostTitleAndCreatedDateByPostId(Integer postId);

    @Query("Select p.title from PostEntity as p where p.profile.id=?1")
    List<String> getPostTitleListByProfileId(Integer profileId);

    @Query(value = "Select * from post where profile_id=?1 order by created_date desc limit 5 ", nativeQuery = true)
    List<PostEntity> getLast5PostsByProfileId(Integer profileId);

    @Query("select count(p) from PostEntity as p where p.profile.id=?1")
    Integer getCountPostByProfileId(Integer profileId);

    @Query(value = "SELECT count(p) from post as p where profile_id=?1 and (SELECT cast(created_date as date))=current_date", nativeQuery = true)
    Integer getTodayCountPostByProfileId(Integer profileId);

    @Query("Select new com.example.mapper.PostIdProfileIdPostCreatedDate(p.id, p.profile.id, p.createdDate) from PostEntity as p where p.profile.id=?1")
    List<PostIdProfileIdPostCreatedDate> getPostIdProfileIdPostDate(Integer profileId);

    @Query("Select new com.example.mapper.PostIdProfileIdPostCreatedDate(p.id, p.profile.id, p.createdDate) from PostEntity as p")
    List<PostIdProfileIdPostCreatedDate> getPostIdProfileIdPostDateList();

    @Query(value = "SELECT * from post as p where (SELECT cast(created_date as date))=current_date", nativeQuery = true)
    List<PostEntity> getTodayPostList();

        @Query(value = "Select p.id as postId, p.title as postTitle, pr.id as profileId, pr.name as profileName from post as p inner join profile as pr on pr.id=p.profile_id where ((Select cast(p.created_date as date))=current_date)", nativeQuery = true)
//        @Query("Select new com.example.mapper.PostIdPostTitleProfileIdProfileName(p.id, p.title, p.profile.id, p.profile.name) from PostEntity as p inner join p.profile as pr where (Select CAST(created_date as date)=current_date")
//    @Query("SELECT new com.example.mapper.PostIdPostTitleProfileIdProfileName(p.id, p.title, p.profile.id, p.profile.name) from PostEntity as p inner join p.profile where (SELECT cast(created_date as date))=current_date")
    List<PostIdPostTitleProfileIdProfileName> getTodayMapperPostList();

}
