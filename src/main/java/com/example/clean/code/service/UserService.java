package com.example.clean.code.service;

import org.openapitools.model.ContentDto;
import org.openapitools.model.PostDataDto;
import org.openapitools.model.PostDto;
import org.openapitools.model.UserDto;
import org.openapitools.model.UsernameDto;

public interface UserService {

	 UserDto saveUser(String username);

	 void deleteUser(long id);

	 UserDto updateUser(long id, UsernameDto usernameDto);

	 UserDto getUser(long id);

	void editPost(Long id);

	void updatePostCount(Long id);

	void delete(Long id);

	PostDto get(Long id);

	PostDto update(Long id, ContentDto contentDto);

	PostDto create(PostDataDto postDataDto);
}
