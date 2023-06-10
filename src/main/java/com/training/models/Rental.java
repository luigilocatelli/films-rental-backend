package com.training.models;

import  com.training.models.Staff;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Rental {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rental_id")
	private Long id;

	@Column(name="rental_date")
	private LocalDate rentalDate;

	@OneToOne
	@JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
	private Customer customer;

	@Column(name="return_date")
	private LocalDate returnDate;

	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;

	@OneToOne
	@JoinColumn(name = "staff_id", referencedColumnName = "staff_id")
	private Staff staff;

	@OneToOne
	@JoinColumn(name = "film_id", referencedColumnName = "film_id")
	private Film film;

	@Column(name="last_update")
	private LocalDate lastUpdate;

	public Long getId() {
		return id;
	}

	public LocalDate getRentalDate() {
		return rentalDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Staff getStaff() {
		return staff;
	}

	public Film getFilm() {
		return film;
	}

	public Customer getCustomer() {
		return customer;
	}

	public LocalDate getLastUpdate() {
		return lastUpdate;
	}
}
