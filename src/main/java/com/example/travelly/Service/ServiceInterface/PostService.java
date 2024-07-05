package com.example.travelly.Service.ServiceInterface;

import com.example.travelly.Dto.Postdto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    void toggleLike(Integer postId);
    void toggleBookmark(Integer postId);
    void deletePost(Integer postId);
    void addPost(String content, List<MultipartFile> file);
    Postdto updatePost(String updatedContent, Integer postId, List<MultipartFile> files);
    void addComment(Integer postId, String comment);
    void editComment(Integer commentId, String comment);
    void deleteComment( Integer commentId);
}
