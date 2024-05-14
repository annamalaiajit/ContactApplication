package com.contactApp.ServiceImplTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.contactApp.Event.ContactCreatedEvent;
import com.contactApp.exception.PatternNotMatchingException;
import com.contactApp.model.ContactModel;
import com.contactApp.repository.ContactRepository;
import com.contactApp.serviceImpl.ContactServiceImpl;
/**
 * @author annamalai
 * The test class for all contact service impl testcases created and get contacts functionality
 */
@ExtendWith(MockitoExtension.class)
public class ContactServiceImplTest {

	@Mock
	private ContactRepository contactRepository;

	@Mock
	private ContactCreatedEvent contactCreatedEvent;

	@InjectMocks
	private ContactServiceImpl contactService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetAllContacts() throws InterruptedException, ExecutionException {
		List<ContactModel> contacts = Arrays.asList(new ContactModel(new BigInteger("1"), "Annamalai"),
				new ContactModel(new BigInteger("2"), "Ajith"));

		when(contactRepository.findAll()).thenReturn(contacts);

		CompletableFuture<List<ContactModel>> filteredContacts = contactService.getAllContacts("^J.*$");

		assertEquals(2, filteredContacts.get().size());
		assertEquals("Annamalai", filteredContacts.get().get(0).getName());
	}

	@Test
	public void testGetAllContacts_PatternNotMatchingException() {
		when(contactRepository.findAll()).thenReturn(Arrays.asList());

		assertThrows(PatternNotMatchingException.class, () -> contactService.getAllContacts("^.*[")); // Invalid regex
																										// pattern
	}

	@Test
	public void testCreateContact() throws InterruptedException, ExecutionException {
		ContactModel contactModel = new ContactModel(new BigInteger("1"), "Ajith");

		when(contactRepository.save(any(ContactModel.class))).thenReturn(contactModel);
		CompletableFuture<ContactModel> createdContact = contactService.createContact(contactModel);

		assertEquals(contactModel, createdContact.get());
		verify(contactCreatedEvent, times(1)).send(contactModel);
	}
}
