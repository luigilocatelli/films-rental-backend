package com.training.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Film {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="film_id")
	private Long id;

	private String title;

	private String description;

	@With
	private Double rental_rate;

	private Double replacement_cost;

	@OneToOne
	@JoinColumn(name = "category_id", referencedColumnName = "category_id")
	private Category category;

	public Film(Object o) {
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Double getRental_rate() {
		return rental_rate;
	}

	public Category getCategory() {
		return category;
	}

	public Double getReplacement_cost() {
		return replacement_cost;
	}

	public void setReplacement_cost(Double replacement_cost) {
		this.replacement_cost = replacement_cost;
	}
}
