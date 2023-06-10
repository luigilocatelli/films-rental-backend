package com.training.repos;

import com.training.models.Film;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FilmRepo extends CrudRepository<Film, Long> {

	List<Film> findAll();
}
