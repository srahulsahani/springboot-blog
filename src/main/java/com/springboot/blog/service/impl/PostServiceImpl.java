package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }



    /* Create a new POST*/
    @Override
    public PostDto createPost(PostDto postDto) {
        //convert DTO to entity
        Post post = mapToPost(postDto);
        Post newPost = postRepository.save(post);

        //Convert Entity to DTO
        PostDto postResponse = mapToDto(newPost);
        return postResponse;
    }


    /* Search all POST */
    @Override
    public List<PostDto> getAllPost() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
    }

    /* Search each POST by id*/
    @Override
    public PostDto getPostById(long id) {
        // Find the post by id
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id", id));
        return  mapToDto(post);
    }

    /* Updating the post by id*/
    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        // Find the post by id
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    /* Delete the post by id */
    @Override
    public void deletePost(long id) {
        // Find the post by id
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id", id));
        postRepository.delete(post);

    }

    ;

    //Entity to DTO converter
    private PostDto mapToDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }

    //Dto to Entity
    private Post mapToPost(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return  post;
    }
}
