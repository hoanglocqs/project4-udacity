package com.example.demo;

import com.example.demo.controllers.OrderController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class OrderTest {
	@InjectMocks
	OrderController orderController;

	@Mock
	UserRepository userRepository;

	@Mock
	OrderRepository orderRepository;

	@Test
	void getOrdersByUserSuccess() {
		Mockito.doReturn(new User()).when(userRepository).findByUsername("Username");
		Mockito.doReturn(new ArrayList<User>()).when(orderRepository).findByUser(any());
		ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("Username");
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void getOrdersByUserFalse() {
		Mockito.doReturn(null).when(userRepository).findByUsername("Username");
		ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("Username");
		//
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void submitOrderFalse() {
		Mockito.doReturn(null).when(userRepository).findByUsername("Username wrong");
		ResponseEntity<UserOrder> response = orderController.submit("Username wrong");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void submitOrderSuccess() {
		User user = createUser();
		Cart cart = createCart();
		user.setCart(cart);

		Mockito.doReturn(user).when(userRepository).findByUsername("Username");
		ResponseEntity<UserOrder> response = orderController.submit("Username");
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	private User createUser() {
		User user = new User();
		user.setId(1L);
		user.setUsername("LocNH14");
		user.setPassword("12345678");
		
		return user;
	}

	private Cart createCart() {
		Cart cart = new Cart();
		cart.setId(1L);
		cart.setTotal(BigDecimal.ONE);

		Item item = new Item();
		item.setId(1L);
		item.setName("Item1");
		item.setPrice(BigDecimal.ONE);
		item.setDescription("Itemmmmm summary");

		List<Item> items = new ArrayList<>();
		items.add(item);

		cart.setItems(items);
		return cart;
	}
}
