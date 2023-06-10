package com.training.repos;

import java.util.List;

import com.training.models.Rental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepo extends CrudRepository<Rental, Long> {

	List<Rental> findAll();
}
