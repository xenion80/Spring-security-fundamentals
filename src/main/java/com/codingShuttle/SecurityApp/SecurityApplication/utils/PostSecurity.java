package com.codingShuttle.SecurityApp.SecurityApplication.utils;

import com.codingShuttle.SecurityApp.SecurityApplication.dtos.PostDTO;
import com.codingShuttle.SecurityApp.SecurityApplication.entities.PostEntity;
import com.codingShuttle.SecurityApp.SecurityApplication.entities.User;
import com.codingShuttle.SecurityApp.SecurityApplication.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSecurity {
    private final PostService postService;

    public boolean isOwnerOfPost(Long postId){
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDTO post=postService.getPostById(postId);
        return post.getAuthor().getId().equals(user.getId());
    }
}
