package com.contactApp.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contactApp.model.ContactModel;

/**
 * @author Annamalai
 */
@Repository
public interface ContactRepository extends JpaRepository<ContactModel, BigInteger> {

}
