package com.example.service;

import com.example.dto.CommentDTO;
import com.example.dto.ProfileDTO;
import com.example.entity.CommentEntity;
import com.example.entity.ProfileEntity;
import com.example.exceptions.CommentCreateException;
import com.example.exceptions.ItemNotFoundException;
import com.example.mapper.CommentIdCommentContentProfileIdProfileName;
import com.example.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private ProfileService profileService;

    public CommentDTO createComment(CommentDTO commentDTO) {
        commentDTO.setCreated_date(LocalDateTime.now());

        CommentEntity commentEntity = toEntity(commentDTO);
        commentRepository.save(commentEntity);

        commentDTO.setId(commentEntity.getId());
        return commentDTO;
    }

    public List<CommentDTO> getCommentList() {
        List<CommentEntity> commentEntities = commentRepository.findAll();

        List<CommentDTO> commentDTOS = new ArrayList<>();

        for (CommentEntity commentEntity : commentEntities) {
            CommentDTO commentDTO = toDTO(commentEntity);
            commentDTOS.add(commentDTO);
        }

        return commentDTOS;
    }

    public CommentDTO getCommentById(Integer id) {
        CommentEntity commentEntity = commentRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Item not found");
        });
        return toDTO(commentEntity);
    }




    private CommentDTO toDTO(CommentEntity commentEntity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPost(postService.toDTO(commentEntity.getPost()));
        commentDTO.setProfile(profileService.toDTO(commentEntity.getProfile()));
        commentDTO.setContent(commentEntity.getContent());
        commentDTO.setCreated_date(commentEntity.getCreated_date());
        commentDTO.setId(commentEntity.getId());
        return commentDTO;
    }

    private CommentEntity toEntity(CommentDTO commentDTO) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(commentDTO.getContent());
        commentEntity.setPost(postService.get(commentDTO.getPostId()));
        commentEntity.setProfile(profileService.get(commentDTO.getProfileId()));
        commentEntity.setCreated_date(commentDTO.getCreated_date());
        return commentEntity;
    }


    public Boolean deleteById(Integer id) {
        CommentEntity commentEntity = get(id);
        commentRepository.deleteById(id);
        return true;
    }

    private CommentEntity get(Integer id) {
        return commentRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Item not found");
        });
    }

    public CommentDTO updateComment(CommentDTO commentDTO, Integer id) {
        int update = commentRepository.updateComment(commentDTO.getContent(), id);

        if(update==0){
            throw new CommentCreateException("Comment create exception");
        }
        commentDTO.setId(id);
        return commentDTO;
    }

    public Page<CommentDTO> getCommentListWithPagination(Integer page, Integer size) {

        Pageable paging = PageRequest.of(page-1, size);

        Page<CommentEntity> commentEntities = commentRepository.getAll(paging);

        List<CommentEntity> commentEntityList = commentEntities.getContent();

        List<CommentDTO> dtoList = new ArrayList<>();

        for (CommentEntity commentEntity : commentEntityList) {
            CommentDTO commentDTO = toDTO(commentEntity);
            dtoList.add(commentDTO);
        }

        Page<CommentDTO> response = new PageImpl<>(dtoList, paging, commentEntities.getTotalElements());
        return response;
    }

    public List<CommentDTO> getCommentListByProfileId(Integer profileId) {
        List<CommentEntity> commentList = commentRepository.getCommentListByProfileId(profileId);

        List<CommentDTO> commentDTOS = new ArrayList<>();

        for (CommentEntity commentEntity : commentList) {
            CommentDTO commentDTO = toDTO(commentEntity);
            commentDTOS.add(commentDTO);
        }
        return commentDTOS;
    }

    public List<CommentIdCommentContentProfileIdProfileName> getMapperListByProfileId(Integer profileId) {
        List<CommentIdCommentContentProfileIdProfileName> list = commentRepository.getMapperListByProfileId(profileId);


        return list;
    }

    public List<ProfileDTO> getProfileByCommentId(Integer commentId) {
        ProfileEntity profile = commentRepository.getProfileByCommentId(commentId);


        return null;
    }
}
