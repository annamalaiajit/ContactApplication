package com.contactApp.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.contactApp.model.ContactModel;
import com.contactApp.service.ContactService;

import jakarta.validation.Valid;

/**
 * @author Annamalai
 */
@RestController
public class ContactController {

	@Autowired
	private ContactService contactService;

	/**
	 * 
	 * @param nameFilter
	 * @return
	 */

	@GetMapping("/hello/contacts")
	public CompletableFuture<ResponseEntity<List<ContactModel>>> getAllContacts(@RequestParam(value = "nameFilter", required = true) String nameFilter) {

		return contactService.getAllContacts(nameFilter)
				.thenApply(result -> new ResponseEntity<>(result, HttpStatus.OK));
	}

	/**
	 * 
	 * @param contactmodel
	 * @return
	 */
	@PostMapping("/hello/contact")
	public CompletableFuture<ResponseEntity<ContactModel>> saveContact(@Valid @RequestBody ContactModel contactmodel){
		
		return contactService.createContact(contactmodel).thenApply(result->new ResponseEntity<>(result, HttpStatus.CREATED));
	}

}
