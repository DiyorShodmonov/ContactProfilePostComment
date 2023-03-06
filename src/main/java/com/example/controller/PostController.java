package com.example.controller;

import com.example.dto.PostDTO;
import com.example.mapper.PostIdPostTitleProfileIdProfileName;
import com.example.mapper.PostIdProfileIdPostCreatedDate;
import com.example.mapper.ProfileMapper;
import com.example.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("")
    public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO) {
        PostDTO result = postService.createPost(postDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("")
    public ResponseEntity<?> getList() {
        List<PostDTO> postDTOList = postService.getList();
        return ResponseEntity.ok(postDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Integer id) {
        PostDTO result = postService.getPostById(id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        Boolean result = postService.deleteById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Integer id,
                                        @RequestBody PostDTO postDTO) {
        Boolean result = postService.updatePost(postDTO, id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/profileId")
    public ResponseEntity<?> getPostByProfileIdWithPagination(@RequestParam("profileId") Integer profileId,
                                                              @RequestParam("size") Integer size,
                                                              @RequestParam("page") Integer page) {
        Page<PostDTO> postDTOS = postService.getPostByProfileIdWithPagination(profileId, size, page);
        return ResponseEntity.ok(postDTOS);
    }

    @GetMapping("/mapper/profileId")
    public ResponseEntity<?> getPostMapperByProfileIdWithPagination(@RequestParam("profileId") Integer profileId,
                                                                    @RequestParam("size") Integer size,
                                                                    @RequestParam("page") Integer page) {
        Page<ProfileMapper> profileMappers = postService.getPostMapperByProfileIdWithPagination(profileId, size, page);
        return ResponseEntity.ok(profileMappers);
    }

    @GetMapping("/postId/{postId}")
    public ResponseEntity<?> getPostTitleAndCreatedDateByPostId(@PathVariable Integer postId) {
        PostDTO postDTO = postService.getPostTitleAndCreatedDateByPostId(postId);
        return ResponseEntity.ok(postDTO);
    }


    @GetMapping("/title/{profileId}")
    public ResponseEntity<?> getPostTitleListByProfileId(@PathVariable Integer profileId) {
        List<String> titleList = postService.getPostTitleList(profileId);
        return ResponseEntity.ok(titleList);
    }

    @GetMapping("/lastPost/{profileId}")
    public ResponseEntity<?> getLast5PostByProfileId(@PathVariable Integer profileId) {
        List<PostDTO> result = postService.getLast5PostsByProfileId(profileId);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/count/{profileId}")
    public ResponseEntity<?> getCountPostByProfileId(@PathVariable Integer profileId) {
        Integer count = postService.getCountPostByProfileId(profileId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/today/count/{profileId}")
    public ResponseEntity<?> getTodayCountPostByProfileId(@PathVariable Integer profileId) {
        Integer count = postService.getTodayCountPostByProfileId(profileId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/post/profile/date/{profileId}")
    public ResponseEntity<?> getPostIdProfileIdPostDate(@PathVariable Integer profileId){
        List<PostIdProfileIdPostCreatedDate> result = postService.getPostIdProfileIdPostDate(profileId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/post/profile/date/list")
    public ResponseEntity<?> getPostIdProfileIdPostDateList(){
        List<PostIdProfileIdPostCreatedDate> result = postService.getPostIdProfileIdPostDateList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/today/list")
    public ResponseEntity<?> getTodayPostList(){
        List<PostDTO> postDTOS = postService.getTodayList();
        return ResponseEntity.ok(postDTOS);
    }

    @GetMapping("/today/mapperList")
    public ResponseEntity<?> getTodayMapperPostList(){
        List<PostIdPostTitleProfileIdProfileName> getTodayMapperPostList = postService.getTodayMapperPostList();
        return ResponseEntity.ok(getTodayMapperPostList);
    }


}
