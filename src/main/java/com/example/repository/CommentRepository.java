package com.example.repository;

import com.example.entity.CommentEntity;
import com.example.entity.ProfileEntity;
import com.example.mapper.CommentIdCommentContentProfileIdProfileName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    @Modifying
    @Transactional
    @Query("update CommentEntity set content=?1 where id=?2")
    int updateComment(String content, Integer id);


    @Query("from CommentEntity ")
    Page<CommentEntity> getAll(Pageable paging);

    @Query("from CommentEntity as c where c.profile.id=?1")
    List<CommentEntity> getCommentListByProfileId(Integer profileId);

    @Query("SELECT new com.example.mapper.CommentIdCommentContentProfileIdProfileName(c.id, c.content, c.profile.id, c.profile.name) from CommentEntity as c where c.profile.id=?1")
    List<CommentIdCommentContentProfileIdProfileName> getMapperListByProfileId(Integer profileId);

    @Query("SELECT p from CommentEntity as c inner join c.profile where c.id=?1")
    ProfileEntity getProfileByCommentId(Integer commentId);
}
