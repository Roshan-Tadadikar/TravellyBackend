package com.example.travelly.Controller;

import com.example.travelly.Dto.Postdto;
import com.example.travelly.Service.PostServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/post")
@Slf4j
@AllArgsConstructor
public class PostController {
    PostServiceImpl postService;

    // will allow to add a post
    @PostMapping
    public ResponseEntity addPost(@RequestParam String content,
                                  @RequestParam List<MultipartFile> listOfFiles) {
        log.info("*** Inside addPost controller ***");
        postService.addPost(content, listOfFiles);
        return new ResponseEntity("Post added sucessfully", HttpStatus.OK);
    }

    // will allow to update a post
    @PostMapping("/update/{id}")
    public ResponseEntity updatePost(@PathVariable Integer id,
                                     @RequestParam(required = false) String updatedContent,
                                     @RequestParam(required = false) List<MultipartFile> listOfFiles) {
        log.info("*** Inside updatePost controller ***");
        Postdto updatedPost = postService.updatePost(updatedContent, id, listOfFiles);
        return new ResponseEntity(updatedPost, HttpStatus.OK);
    }

    // will allow to delete a post
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Integer id) {
        log.info("*** Inside deletePost controller ***");
        postService.deletePost(id);
        return new ResponseEntity("Post Delete Successfully !!", HttpStatus.OK);
    }

    // will allow to like a post
    @PostMapping("/like/{id}")
    public ResponseEntity<String> toggleLike(@PathVariable Integer id) {
        log.info("*** Inside toggleLike controller ***");
        postService.toggleLike(id);
        return new ResponseEntity("Post Liked Successfully !!", HttpStatus.OK);
    }

    // will allow to bookmark a post
    @PostMapping("/bookmark/{id}")
    public ResponseEntity<String> toggleBookmark(@PathVariable Integer id) {
        log.info("*** Inside toggleBookmark controller ***");
        postService.toggleBookmark(id);
        return new ResponseEntity("Post Bookmarked Successfully !!", HttpStatus.OK);
    }

    // will allow to add comment on a post
    @PostMapping("/comment/{postId}")
    public ResponseEntity<String> addComment(@PathVariable Integer postId, @RequestParam String comment) {
        log.info("*** Inside addComment controller ***");
        postService.addComment(postId, comment);
        return ResponseEntity.ok("Comment Added Successfully !!");
    }

    // will allow to edit a  comment on a post
    @PostMapping("/comment/update/{commentId}")
    public ResponseEntity<String> editComment(@PathVariable Integer commentId, @RequestParam(name = "comment") String comment) {
        log.info("*** Inside editComment controller ***");
        postService.editComment(commentId, comment);
        return new ResponseEntity("Comment Edited Successfully !!", HttpStatus.OK);
    }

    // will allow to delete a comment on a post
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Integer id) {
        log.info("*** Inside deleteComment controller ***");
        postService.deleteComment(id);
        return new ResponseEntity("Comment Deleted Successfully !!", HttpStatus.OK);
    }


}
