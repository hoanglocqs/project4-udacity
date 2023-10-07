package com.example.demo;

import com.example.demo.controllers.ItemController;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ItemTest {
	@InjectMocks
	ItemController itemController;
	
	@Mock
	ItemRepository itemRepository;
	
	@Test
	void getItem() {
		Mockito.doReturn(new ArrayList<Item>()).when(itemRepository).findAll();
		ResponseEntity<List<Item>> responseEntity = itemController.getItems();
		assertEquals(0, responseEntity.getBody().size());
	}
	
	@Test
	void getItemById() {
		Mockito.doReturn(Optional.of(new Item())).when(itemRepository).findById(1L);
		ResponseEntity<Item> responseEntity = itemController.getItemById(1L);
		assertEquals(new Item(), responseEntity.getBody());
	}
	
	@Test
	void getItemByNameFalse() {
		Mockito.doReturn(new ArrayList<Item>()).when(itemRepository).findByName("item");
		ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName("item");
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	void getItemsByNameNotFound() {
		Mockito.doReturn(null).when(itemRepository).findByName("item");
		ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName("item");
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

}
