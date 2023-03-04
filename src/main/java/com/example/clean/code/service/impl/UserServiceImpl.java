package com.example.clean.code.service.impl;

import com.example.clean.code.entity.PostEntity;
import com.example.clean.code.entity.UserEntity;
import com.example.clean.code.exception.PostNotFoundException;
import com.example.clean.code.exception.UserNotFoundException;
import com.example.clean.code.repository.PostsRepository;
import com.example.clean.code.repository.UserSaver;
import com.example.clean.code.service.UserService;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.ContentDto;
import org.openapitools.model.PostDataDto;
import org.openapitools.model.PostDto;
import org.openapitools.model.UserDto;
import org.openapitools.model.UsernameDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	//Not only saves the user
	private final UserSaver userSaver;
	private final PostsRepository repository;

	//you know what it does =)
	@Override
	public UserDto saveUser(String username) {
		var u = new UserEntity();
		u.setUsername(username);
		u.setPostsAmount(0);
		var v = userSaver.save(u);

		var userDto = new UserDto();
		userDto.setAmountOfPosts(v.getPostsAmount());
		userDto.setId(v.getId());
		userDto.setUsername(v.getUsername());
		return userDto;
	}

	//	private static UserDto getUserDto(UserEntity savedUser) {
//		UserDto userDto = new UserDto();
//		userDto.setAmountOfPosts(savedUser.getPostsAmount());
//		userDto.setId(savedUser.getId());
//		userDto.setUsername(savedUser.getUsername());
//		return userDto;
//	}

	@Override
	public void deleteUser(long id) {
		Optional<UserEntity> optionalUser = userSaver.findById(id);
		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException();
		}
		userSaver.deleteById(id);
	}

	@Override
	public UserDto updateUser(long id, UsernameDto usernameDto) {
		Optional<UserEntity> optionalUser = userSaver.findById(id);
		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException();
		}
		UserEntity user = optionalUser.get();
		user.setUsername(usernameDto.getUsername());
		UserEntity savedUser = userSaver.save(user);
		UserDto userDto = new UserDto();
		userDto.setAmountOfPosts(savedUser.getPostsAmount());
		userDto.setId(savedUser.getId());
		userDto.setUsername(savedUser.getUsername());
		return userDto;
	}

	@Override
	public UserDto getUser(long id) {
		Optional<UserEntity> optionalUser = userSaver.findById(id);
		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException();
		}
		UserEntity user = optionalUser.get();
		UserDto userDto = new UserDto();
		userDto.setAmountOfPosts(user.getPostsAmount());
		userDto.setId(user.getId());
		userDto.setUsername(user.getUsername());
		return userDto;
	}

	//increment
	@Override
	public void editPost(Long id) {
		Optional<UserEntity> optionalUser = userSaver.findById(id);
		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException();
		}
		UserEntity user = optionalUser.get();
		Integer postsAmount = user.getPostsAmount();
		user.setPostsAmount(postsAmount + 1);
		userSaver.save(user);
	}

	//decrement
	@Override
	public void updatePostCount(Long id) {
		Optional<UserEntity> optionalUser = userSaver.findById(id);
		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException();
		}
		UserEntity user = optionalUser.get();
		Integer postsAmount = user.getPostsAmount();
		user.setPostsAmount(postsAmount - 1);
		userSaver.save(user);
	}

	@Override
	public void delete(Long id) {
		PostEntity postEntity = checkIfExist(id);
		repository.deleteById(id);
		updatePostCount(postEntity.getUserId());
	}

	@Override
	public PostDto get(Long id) {
		PostEntity postEntity = checkIfExist(id);
		return mapToResponse(postEntity);
	}

	private PostEntity checkIfExist(Long id) {
		Optional<PostEntity> postEntity = repository.findById(id);
		if (postEntity.isEmpty()) {
			throw new PostNotFoundException();
		}
		return postEntity.get();
	}

	@Override
	public PostDto update(Long id, ContentDto contentDto) {
		PostEntity postEntity = checkIfExist(id);
		postEntity.setText(contentDto.getText());
		PostEntity savedEntity = repository.save(postEntity);
		return mapToResponse(savedEntity);
	}

	@Override
	public PostDto create(PostDataDto postDataDto) {
		PostEntity postEntity = mapToEntity(postDataDto, new Date());
		PostEntity savedEntity = repository.save(postEntity);
		editPost(postDataDto.getAuthorId());
		return mapToResponse(savedEntity);
	}

	public PostDto mapToResponse(PostEntity entity) {
		PostDto postDto = new PostDto();

		postDto.setAuthorId(entity.getUserId());
		postDto.setId(entity.getId());
		postDto.setText(entity.getText());
		if (entity.getPostedAt() != null) {
			postDto.setPostedAt(LocalDateTime.ofInstant(entity.getPostedAt().toInstant(), ZoneOffset.UTC).toLocalDate());
		}

		return postDto;
	}

	public PostEntity mapToEntity(PostDataDto postDto, Date postedAt) {

		PostEntity postEntity = new PostEntity();

		if (postDto != null) {
			postEntity.setUserId(postDto.getAuthorId());
			postEntity.setText(postDto.getText());
		}
		postEntity.setPostedAt(postedAt);

		return postEntity;
	}
}
