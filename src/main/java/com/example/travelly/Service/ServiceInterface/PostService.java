package com.example.travelly.Service.ServiceInterface;

import com.example.travelly.Dto.Postdto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    void toggleLike(Integer postId);
    void toggleBookmark(Integer postId);
    void deletePost(Integer postId);
    void addComment(String comment, Integer commenterId, Integer postId);
    void addPost(String content, List<MultipartFile> file);
    Postdto updatePost(String updatedContent, Integer postId, List<MultipartFile> files);
}
