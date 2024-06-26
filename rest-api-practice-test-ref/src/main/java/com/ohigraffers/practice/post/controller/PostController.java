package com.ohigraffers.practice.post.controller;

import com.ohigraffers.practice.post.dto.request.PostCreateRequest;
import com.ohigraffers.practice.post.dto.request.PostUpdateRequest;
import com.ohigraffers.practice.post.dto.response.PostResponse;
import com.ohigraffers.practice.post.dto.response.ResponseMessage;
import com.ohigraffers.practice.post.model.Post;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.core.annotation.MergedAnnotations.from;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/* Swagger 문서화 시 Grouping 작성 */
@RestController
@RequestMapping("/posts")
public class PostController {

    private List<Post> posts;

    public PostController(){
        posts = new ArrayList<>();
        posts.add(new Post(1L, "제목1", "내용1", "홍길동"));
        posts.add(new Post(2L, "제목2", "내용2", "유관순"));
        posts.add(new Post(3L, "제목3", "내용3", "신사임당"));
        posts.add(new Post(4L, "제목4", "내용4", "이순신"));
        posts.add(new Post(5L, "제목5", "내용5", "장보고"));
    }

    /* 1. 전체 포스트 조회 */
    /* Swagger 문서화 시 설명 어노테이션 작성 */
    /* RequestMapping 어노테이션 작성 */
    @Operation(summary = "전체 포스트 조회", description = "포스트의 전체 목록을 조회합니다.")
    @GetMapping("/findAll")
    public ResponseEntity<ResponseMessage> findAllPosts() {

        /* 응답 데이터 설정 */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        /* Post 타입은 PostResponse 타입으로 변환해서 반환 */
        List<PostResponse> postResponse = posts.stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());

        /* hateoas 적용 */
        List<EntityModel<PostResponse>> postWithRel = postResponse.stream().map(
                postResponses ->
                     EntityModel.of(
                             postResponses,
                             linkTo(methodOn(PostController.class).findAllPosts()).withRel("posts"),
                             linkTo(methodOn(PostController.class).findPostByCode(postResponses.getCode())).withSelfRel()
                     )
        ).collect(Collectors.toList());

        /* ResponseEntity 반환 */
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("posts", postWithRel);
        ResponseMessage responseMessage = new ResponseMessage(200, "조회성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    /* 2. 특정 코드로 포스트 조회 */
    /* Swagger 문서화 시 설명 어노테이션 작성 */
    /* RequestMapping 어노테이션 작성 */
    @Operation(summary = "특정 코드로 포스트 조회", description = "입력한 번호의 포스트를 조회합니다.")
    @GetMapping("/findAll/{code}")
    public ResponseEntity<ResponseMessage> findPostByCode(@PathVariable Long code) {

        /* 응답 데이터 설정 */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        /* Post 타입은 PostResponse 타입으로 변환해서 반환 */
        Post foundPost = posts.stream().filter(post -> post.getCode() == code)
                .collect(Collectors.toList()).get(0);
        PostResponse postResponse = PostResponse.from(foundPost);

        /* hateoas 적용 */
        EntityModel<PostResponse> postWithRel = EntityModel.of(
                postResponse,
                linkTo(methodOn(PostController.class).findAllPosts()).withRel("posts"),
                linkTo(methodOn(PostController.class).findPostByCode(code)).withSelfRel()
        );

        /* ResponseEntity 반환 */
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("posts", postWithRel);
        ResponseMessage responseMessage = new ResponseMessage(200, "조회성공", responseMap);

        return ResponseEntity.ok().headers(headers).body(responseMessage);
    }

    /* 3. 신규 포스트 등록 */
    /* Swagger 문서화 시 설명 어노테이션 작성 */
    /* RequestMapping 어노테이션 작성 */
    @Operation(summary = "포스트 등록", description = "새로운 포스트를 등록합니다.")
    @PostMapping("/registPost")
   public ResponseEntity<Void> registPost(@RequestBody PostCreateRequest newPost) {

//        PostCreateRequest newPost = new PostCreateRequest();

       /* 리스트에 추가 */
        Long newCode = posts.get(posts.size() -1).getCode() + 1;
        posts.add(new Post(newCode, newPost.getTitle(), newPost.getContent(), newPost.getWriter()));


        /* ResponseEntity 반환 */
       return ResponseEntity
               .created(URI.create("/posts/registPost"))
               .build();
   }


   /* 4. 포스트 제목과 내용 수정 */
   /* Swagger 문서화 시 설명 어노테이션 작성 */
   /* RequestMapping 어노테이션 작성 */
    @Operation(summary = "포스트 수정", description = "포스트의 제목과 내용을 수정합니다.")
    @PutMapping("/modifyPost/{code}")
    public ResponseEntity<Void> modifyPost(@PathVariable Long code, @RequestBody PostUpdateRequest postUpdateRequest) {

        /* 리스트에서 찾아서 수정 */
        Optional<Post> optionalPost = posts.stream().filter(post -> post.getCode().equals(code)).findFirst();
        /* 수정 메소드 활용 */
        if(optionalPost.isPresent()) {
            Post foundPost = optionalPost.get();
            foundPost.modifyTitleAndContent(postUpdateRequest.getTitle(), postUpdateRequest.getContent());
        }
        /* ResponseEntity 반환 */
        return ResponseEntity
                .created(URI.create("/posts/modifyPost" + code))
                .build();
    }

    /* 5. 포스트 삭제 */
    /* Swagger 문서화 시 설명 어노테이션 작성 */
    /* RequestMapping 어노테이션 작성 */
    @Operation(summary = "포스트 삭제", description = "조회한 번호의 포스트를 삭제 합니다.")
    @DeleteMapping("/deletePost/{code}")
    public ResponseEntity<Void> removeUser(@PathVariable Long code) {

        /* 리스트에서 찾아서 삭제 */
        Post foundPost = posts.stream().filter(post -> post.getCode() == code).toList().get(0);
        posts.remove(foundPost);

        /* ResponseEntity 반환 */
        return ResponseEntity
                .noContent()
                .build();
    }
}
