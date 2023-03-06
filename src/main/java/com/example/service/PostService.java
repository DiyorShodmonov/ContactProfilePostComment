package com.example.service;

import com.example.dto.PostDTO;
import com.example.entity.PostEntity;
import com.example.exceptions.ItemNotFoundException;
import com.example.exceptions.PostCreateException;
import com.example.mapper.PostIdPostTitleProfileIdProfileName;
import com.example.mapper.PostIdProfileIdPostCreatedDate;
import com.example.mapper.ProfileMapper;
import com.example.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ProfileService profileService;

    public PostDTO createPost(PostDTO postDTO) {
        postDTO.setCreatedDate(LocalDateTime.now());
        PostEntity postEntity = toEntity(postDTO);

        PostEntity save = postRepository.save(postEntity);
        if(save.getId()==0){
            throw new PostCreateException("Something went wrong");
        }
        postDTO.setId(postEntity.getId());
        return postDTO;
    }

    public List<PostDTO> getList() {
        List<PostEntity> postEntities = postRepository.findAll();

        List<PostDTO> postDTOS = new ArrayList<>();

        for (PostEntity postEntity : postEntities) {
            PostDTO postDTO = toDTO(postEntity);
            postDTOS.add(postDTO);
        }

        return postDTOS;
    }
    public PostDTO getPostById(Integer id) {
        PostEntity postEntity = postRepository.findById(id).orElseThrow(()->{
            throw new ItemNotFoundException("Item not found");
        });
        return toDTO(postEntity);
    }

    public Boolean deleteById(Integer id) {
        get(id);
        postRepository.deleteById(id);
        return true;
    }

    public PostEntity get(Integer id) {
        return postRepository.findById(id).orElseThrow(() -> {
           throw new ItemNotFoundException("Item not found");
        });
    }
    public PostDTO toDTO(PostEntity postEntity) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(postEntity.getId());
        postDTO.setTitle(postEntity.getTitle());
        postDTO.setContent(postEntity.getContent());
        postDTO.setProfile(profileService.toDTO(postEntity.getProfile()));
        postDTO.setCreatedDate(postEntity.getCreatedDate());
        return postDTO;
    }
    public PostEntity toEntity(PostDTO postDTO) {
        PostEntity postEntity = new PostEntity();
        postEntity.setContent(postDTO.getContent());
        postEntity.setTitle(postDTO.getTitle());
        postEntity.setProfile(profileService.get(postDTO.getProfileId()));
        postEntity.setCreatedDate(postDTO.getCreatedDate());
        return postEntity;
    }

    public Boolean updatePost(PostDTO postDTO, Integer id) {
        PostEntity postEntity = get(id);

        postEntity.setTitle(postDTO.getTitle());
        postEntity.setContent(postDTO.getContent());
        postEntity.setProfile(profileService.get(postDTO.getProfileId()));

        postRepository.update(postEntity.getTitle(), postEntity.getContent(),
                postEntity.getProfile(), postEntity.getId());
        return true;
    }

    public Page<PostDTO> getPostByProfileIdWithPagination(Integer profileId,
                                                          Integer size, Integer page) {
//        Sort sort = Sort.by(Sort.Direction.ASC, "createdDate");

        Pageable paging = PageRequest.of(page-1, size);

        Page<PostEntity> postEntities = postRepository.findAllByProfile_Id(profileId, paging);

        List<PostEntity> postEntityList = postEntities.getContent();

        List<PostDTO> postDTOS = new ArrayList<>();

        for (PostEntity postEntity : postEntityList) {
            PostDTO postDTO = toDTO(postEntity);
            postDTOS.add(postDTO);
        }

        Page<PostDTO> response =new PageImpl(postDTOS, paging, postEntities.getTotalElements());

        return response;
    }

    public Page<ProfileMapper> getPostMapperByProfileIdWithPagination(Integer profileId, Integer size, Integer page) {
        Pageable paging = PageRequest.of(page-1, size);

        Page<ProfileMapper> postEntities = postRepository.getPostMapperByProfileId(profileId, paging);

        List<ProfileMapper> list = postEntities.getContent();

        Page<ProfileMapper> response = new PageImpl<>(list, paging, postEntities.getTotalElements());
        return response;
    }

    public PostDTO getPostTitleAndCreatedDateByPostId(Integer postId) {
        get(postId);

        PostEntity post = postRepository.getPostTitleAndCreatedDateByPostId(postId);

        PostDTO postDTO = new PostDTO();
        postDTO.setTitle(post.getTitle());
        postDTO.setCreatedDate(post.getCreatedDate());

        return postDTO;
    }

    public List<String> getPostTitleList(Integer profileId) {
        List<String> titleList = postRepository.getPostTitleListByProfileId(profileId);
        return titleList;
    }

    public List<PostDTO> getLast5PostsByProfileId(Integer profileId) {
        List<PostEntity> postEntities = postRepository.getLast5PostsByProfileId(profileId);

        List<PostDTO> postDTOS = new ArrayList<>();

        for (PostEntity postEntity : postEntities) {
            PostDTO postDTO = toDTO(postEntity);
            postDTOS.add(postDTO);
        }

        return postDTOS;
    }

    public Integer getCountPostByProfileId(Integer profileId) {
        Integer count = postRepository.getCountPostByProfileId(profileId);
        return count;
    }

    public Integer getTodayCountPostByProfileId(Integer profileId) {
        Integer todayCount = postRepository.getTodayCountPostByProfileId(profileId);
        return todayCount;
    }

    public List<PostIdProfileIdPostCreatedDate> getPostIdProfileIdPostDate(Integer profileId) {
        List<PostIdProfileIdPostCreatedDate> list = postRepository.getPostIdProfileIdPostDate(profileId);


        return list;
    }

    public List<PostIdProfileIdPostCreatedDate> getPostIdProfileIdPostDateList() {
        List<PostIdProfileIdPostCreatedDate> list = postRepository.getPostIdProfileIdPostDateList();
        return list;
    }

    public List<PostDTO> getTodayList() {
        List<PostEntity> postEntityList = postRepository.getTodayPostList();

        List<PostDTO> postDTOS = new ArrayList<>();

        for (PostEntity postEntity : postEntityList) {
            PostDTO postDTO = toDTOWithNotDetail(postEntity);
            postDTOS.add(postDTO);
        }
        return postDTOS;
    }

    private PostDTO toDTOWithNotDetail(PostEntity postEntity) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(postEntity.getId());
        postDTO.setContent(postEntity.getContent());
        postDTO.setProfileId(postEntity.getProfile().getId());
        postDTO.setTitle(postEntity.getTitle());
        postDTO.setCreatedDate(postEntity.getCreatedDate());
        return postDTO;
    }

    public List<PostIdPostTitleProfileIdProfileName> getTodayMapperPostList() {
        List<PostIdPostTitleProfileIdProfileName> list = postRepository.getTodayMapperPostList();
        return list;
    }
}
