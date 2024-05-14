package com.contactApp.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.contactApp.model.ContactModel;

/**
 * @author Annamalai
 */
public interface ContactService {

	CompletableFuture<List<ContactModel>> getAllContacts(String nameFilter);

	CompletableFuture<ContactModel> createContact(ContactModel contactmodel);

}
