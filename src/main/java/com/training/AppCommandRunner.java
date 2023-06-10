package com.training;

import com.training.repos.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Slf4j
@Component
public class AppCommandRunner implements CommandLineRunner {

	@Autowired
	private RentalRepo customerRepos;
	
	@Autowired
	private FilmRepo filmRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Transactional
	@Override
	public void run(String... args) throws Exception {

		log.info("Films:");
		filmRepo.findAll()
				.forEach(p -> log.info(p.toString()));
		log.info("Category:");
		categoryRepo.findAll()
				.forEach(p -> log.info(p.toString()));
	}
}
