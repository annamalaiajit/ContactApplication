package com.contactApp.serviceImpl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.contactApp.Event.ContactCreatedEvent;
import com.contactApp.exception.PatternNotMatchingException;
import com.contactApp.model.ContactModel;
import com.contactApp.repository.ContactRepository;
import com.contactApp.service.ContactService;

/**
 * @author Annamalai This class mainly for contact get and create functionality,
 */
@Service
public class ContactServiceImpl implements ContactService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContactServiceImpl.class);
	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private ContactCreatedEvent contactCreatedEvent;

	/**
	 * This method will get all contacts from db and matching the namefilter based
	 * return the data. and I used the async annotation multiple request to opt
	 * simultaneously. I used the cache mechanism if frequently accessing the the
	 * api it should return from spring cache.
	 * 
	 * @param nameFilter
	 * @return CompletableFuture<List<ContactModel>>
	 * @exception PatternNotMatchingException
	 * 
	 */
	@Cacheable("contacts")
	@Async
	@Override
	public CompletableFuture<List<ContactModel>> getAllContacts(String nameFilter) {
		List<ContactModel> listOfContactModel = contactRepository.findAll();
		LOGGER.info("Regex pattern==>{}", nameFilter);
		try {
			Pattern pattern = Pattern.compile(nameFilter);
			return CompletableFuture.completedFuture(listOfContactModel.stream()
					.filter(contact -> !pattern.matcher(contact.getName()).matches()).collect(Collectors.toList()));
		} catch (Exception exception) {
			throw new PatternNotMatchingException(exception.getLocalizedMessage());
		}
	}

	/**
	 * This method will created the contact and saved to the contact table. And I
	 * have to use the cache mechanism like whenever fire the method should be evict
	 * the particular contact cache. I have used the kafka mechanism for producer
	 * once created the contact it trigger in kafka.
	 * 
	 * @param ContactModel
	 * @return CompletableFuture<ContactModel>
	 * 
	 */
	@Caching(evict = { @CacheEvict(value = "contacts", allEntries = true) })
	@Override
	@Async
	public CompletableFuture<ContactModel> createContact(ContactModel contactmodel) {
		CompletableFuture<ContactModel> createContact = CompletableFuture
				.completedFuture(contactRepository.save(contactmodel));
		LOGGER.info("Contact created==>{}", contactmodel.toString());
		contactCreatedEvent.send(contactmodel);
		LOGGER.info("Contact published==>{}", contactmodel.toString());
		return createContact;
	}

}
