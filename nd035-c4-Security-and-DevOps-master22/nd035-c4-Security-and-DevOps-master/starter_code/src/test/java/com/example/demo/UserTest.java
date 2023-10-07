package com.example.demo;

import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserTest {
	@InjectMocks
	UserController userController;

	@Mock
	UserRepository userRepository;

	@Mock
	CartRepository cartRepository;


	@Test
	void getById() {
		Mockito.doReturn(Optional.of(new User())).when(userRepository).findById(Mockito.any());
		ResponseEntity<User> response = userController.findById(1L);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void getByUserNameFalse() {
		Mockito.doReturn(null).when(userRepository).findByUsername(Mockito.any());
		ResponseEntity<User> response = userController.findByUserName("LocNH14");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void getByUserNameSuccess() {
		Mockito.doReturn(new User()).when(userRepository).findByUsername(Mockito.any());
		ResponseEntity<User> response = userController.findByUserName("LocNH14");
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void createUserWithPasswordLengthThan7() {
		Mockito.doReturn(new Cart()).when(cartRepository).save(Mockito.any());
		CreateUserRequest request = createUserRequest();
		request.setPassword("1234567890");
		ResponseEntity<User> response = userController.createUser(request);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void createUserWithPassWordNotSameConfirmPassword() {
		Mockito.doReturn(new Cart()).when(cartRepository).save(Mockito.any());
		CreateUserRequest request = createUserRequest();
		request.setPassword("1234567890");
		request.setConfirmPassword("1234567890A");
		ResponseEntity<User> response = userController.createUser(request);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void createUserSuccess() {
		Mockito.doReturn(new Cart()).when(cartRepository).save(Mockito.any());
		CreateUserRequest request = createUserRequest();
		request.setPassword("1234567890");
		request.setConfirmPassword("1234567890");
		ResponseEntity<User> response = userController.createUser(request);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	private CreateUserRequest createUserRequest() {
		CreateUserRequest request = new CreateUserRequest();
		request.setUsername("LocNH14");
		return request;
	}
}
