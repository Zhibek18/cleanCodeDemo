package com.example.clean.code.controller;

import com.example.clean.code.service.UserService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.UsersApi;
import org.openapitools.model.UserDto;
import org.openapitools.model.UsernameDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController implements UsersApi {
	private final UserService userService;

	@Override
	public ResponseEntity<UserDto> _usersPost(UsernameDto usernameDto) {
		return new ResponseEntity<>(userService.saveUser(usernameDto.getUsername()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> _usersIdDelete(Long id) {
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<UserDto> _usersIdGet(Long id) {
		return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> _usersIdPostIncrementPut(Long id) {
		userService.editPost(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> _usersIdPostDecrementPut(Long id) {
		userService.updatePostCount(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<UserDto> _usersIdPut(Long id,UsernameDto usernameDto) {
		return new ResponseEntity<>(userService.updateUser(id, usernameDto), HttpStatus.OK);
	}
}

