package com.example.clean.code.controller;

import com.example.clean.code.service.UserService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.PostsApi;
import org.openapitools.model.ContentDto;
import org.openapitools.model.PostDataDto;
import org.openapitools.model.PostDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostController implements PostsApi {
	private final UserService userService;

	@Override
	public ResponseEntity<Void> _postsIdDelete(Long id) {
		userService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<PostDto> _postsIdGet(Long id) {
		return new ResponseEntity<>(userService.get(id), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<PostDto> _postsIdPut(Long id, ContentDto textDto) {
		return new ResponseEntity<>(userService.update(id, textDto), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<PostDto> _postsPost(PostDataDto postDataDto) {
		return new ResponseEntity<>(userService.create(postDataDto), HttpStatus.OK);
	}
}

