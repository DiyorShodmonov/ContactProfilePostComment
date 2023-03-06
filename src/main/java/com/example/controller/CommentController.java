package com.example.controller;

import com.example.dto.CommentDTO;
import com.example.dto.ProfileDTO;
import com.example.mapper.CommentIdCommentContentProfileIdProfileName;
import com.example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("")
    public ResponseEntity<?> createComment(@RequestBody CommentDTO commentDTO) {
        CommentDTO result = commentService.createComment(commentDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("")
    public ResponseEntity<?> getCommentList() {
        List<CommentDTO> commentDTOS = commentService.getCommentList();
        return ResponseEntity.ok(commentDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable Integer id) {
        CommentDTO commentDTO = commentService.getCommentById(id);
        return ResponseEntity.ok(commentDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        Boolean result = commentService.deleteById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Integer id,
                                           @RequestBody CommentDTO commentDTO) {
        CommentDTO result = commentService.updateComment(commentDTO, id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/list/pagination")
    public ResponseEntity<?> getCommentListWithPagination(@RequestParam("page") Integer page,
                                                          @RequestParam("size") Integer size) {
        Page<CommentDTO> commentDTOS = commentService.getCommentListWithPagination(page, size);
        return ResponseEntity.ok(commentDTOS);
    }

    @GetMapping("/list/profileId/{profileId}")
    public ResponseEntity<?> getCommentListByProfileId(@PathVariable Integer profileId) {
        List<CommentDTO> commentDTOS = commentService.getCommentListByProfileId(profileId);
        return ResponseEntity.ok(commentDTOS);
    }

    @GetMapping("/mapperList/profileId/{profileId}")
    public ResponseEntity<?> getMapperListByProfileId(@PathVariable Integer profileId) {
        List<CommentIdCommentContentProfileIdProfileName> commentDTOS = commentService.getMapperListByProfileId(profileId);
        return ResponseEntity.ok(commentDTOS);
    }

    @GetMapping("/profile/commentId/{commentId}")
    public ResponseEntity<?> getProfileByCommentId(@PathVariable Integer commentId) {
        List<ProfileDTO> commentDTOS = commentService.getProfileByCommentId(commentId);
        return ResponseEntity.ok(commentDTOS);
    }


}
