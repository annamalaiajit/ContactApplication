package com.contactApp.model;

import java.io.Serializable;
import java.math.BigInteger;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Table(name = "Contacts")
@Entity
public class ContactModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private BigInteger id;

	@NotBlank(message = "Contact name can't be left empty and not null")
	private String name;

	/**
	 * @return the id
	 */
	public BigInteger getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(BigInteger id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ContactModel [id=" + id + ", name=" + name + "]";
	}

	public ContactModel(BigInteger id, @NotBlank(message = "Contact name can't be left empty and null") String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public ContactModel() {
		super();
	}

}
