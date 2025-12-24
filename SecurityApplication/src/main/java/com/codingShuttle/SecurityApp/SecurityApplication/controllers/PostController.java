package com.codingShuttle.SecurityApp.SecurityApplication.controllers;

import com.codingShuttle.SecurityApp.SecurityApplication.dtos.PostDTO;
import com.codingShuttle.SecurityApp.SecurityApplication.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public List<PostDTO> getAllPosts(){
        return postService.getAllPosts();

    }

    @PostMapping
    public PostDTO createNewPost(@RequestBody PostDTO inputPost){
        return postService.createNewPost(inputPost);
    }
    @GetMapping("/{postId}")
    public PostDTO getPostById(@PathVariable Long postId){
        return postService.getPostById(postId);


    }
    @PutMapping("/{id}")
    public PostDTO updatePost(@RequestBody PostDTO inputPost,@PathVariable Long id){
        return postService.updatePost(inputPost,id);
    }
}
