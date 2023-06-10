package com.training;

import com.training.models.*;
import com.training.repos.RentalRepo;
import com.training.repos.FilmRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@DataJpaTest
public class StreamApiTest {

	@Autowired
	private RentalRepo rentalRepo;

	@Autowired
	private FilmRepo filmRepo;


	@Test
	@DisplayName("Obtain a list of films with category = Action ")
	public void exercise1() {
		List<Film> films = filmRepo.findAll().stream()
				.filter(f -> f.getCategory().getName().equals("Action") )
				.collect(Collectors.toList());
		films.forEach(f -> log.info(f.getTitle()));
		log.info("count = " + films.size());
		Assert.isTrue( films.size() == 64, "Attenzione numero di film non previsto");
	}

	@Test
	@DisplayName("Obtain a list of films with category = Action and rental  > 3.99 (using Predicate chaining for filtering)")
	public void exercise2() {
		// Expected result: list size =  17
		List<Film> films = filmRepo.findAll().stream()
				.filter(f -> f.getCategory().getName().equals("Action") && f.getRental_rate() > 3.99)
				.collect(Collectors.toList());
		films.forEach(f -> log.info(f.getTitle()));
		log.info("count = " + films.size());
		Assert.isTrue(films.size() == 17, "Attenzione numero di film non previsto");
	}

	@Test
	@DisplayName("Obtain a list of film with category = Action and price > 3.99 (using Predicate combined with logical operator for filtering)")
	public void exercise2a() {
		// Expected result: list size 17
		List<Film> films = filmRepo.findAll().stream()
				.filter(f -> f.getCategory().getName().equals("Action") && f.getRental_rate() > 3.99)
				.collect(Collectors.toList());
		films.forEach(f -> log.info(f.getTitle()));
		log.info("count = " + films.size());
		Assert.isTrue(films.size() == 17, "Attenzione numero di film non previsto");
	}

	@Test
	@DisplayName("Obtain the income from rented films on the 05/2005 ")
	public void exercise3() {
		// Expected result: amount = 3388.44
		Double dRentalAmount = rentalRepo.findAll().stream()
				.filter(r -> (r.getRentalDate().isAfter(LocalDate.of( 2005 , Month.APRIL , 30 )) && r.getRentalDate().isBefore(LocalDate.of( 2005 , Month.JUNE , 1 ))))
				.map(r -> r.getFilm().getRental_rate())
				.reduce(0d, Double::sum);
		BigDecimal bRentalAmount = new BigDecimal(dRentalAmount);
		BigDecimal bRoundedRentalAmount = bRentalAmount.setScale(2, RoundingMode.HALF_UP);
		Assert.isTrue(3388.44 - bRoundedRentalAmount.doubleValue() == 0, "Attenzione somma errata");
	}

	@Test
	@DisplayName("Obtain a list of film rented between 01-Feb-2006 and 01-Apr-2006")
	public void exercise4() {
		// Expected result: list size 168
		List<Film> films = rentalRepo.findAll().stream()
				.filter(r -> r.getRentalDate().isAfter(LocalDate.of(2006, Month.JANUARY, 31)) && r.getRentalDate().isBefore(LocalDate.of(2006, Month.APRIL, 2)))
				.map(Rental::getFilm)
				.distinct()
				.collect(Collectors.toList());
		log.info("count = " + films.size());
		Assert.isTrue(films.size() == 168, "Attenzione numero di film non previsto");
	}

	@Test
	@DisplayName("Get the 3 films with most expensive rental in the Documentary category")
	public void exercise5() {
		// Expected result: rental price = 4,99  films ids = 1,40,85
		List<Film> films = filmRepo.findAll().stream()
				.filter(f -> f.getCategory().getName().equals("Documentary"))
				.sorted(Comparator.comparing(Film::getRental_rate).reversed())
				.limit(3)
				.collect(Collectors.toList());
		films.forEach(f -> log.info(f.getId() + "/" + f.getRental_rate()));
		log.info("count = " + films.size());
		Assert.isTrue(films.size() == 3, "Attenzione numero di film non previsto");
		Assert.isTrue(films.get(0).getRental_rate() == 4.99, "Attenzione rental price non previsto");
		Assert.isTrue(films.get(0).getId() == 156, "Attenzione id film non previsto");
		Assert.isTrue(films.get(1).getRental_rate() == 4.99, "Attenzione rental price non previsto");
		Assert.isTrue(films.get(1).getId() == 219, "Attenzione id film non previsto");
		Assert.isTrue(films.get(2).getRental_rate() == 4.99, "Attenzione rental price non previsto");
		Assert.isTrue(films.get(2).getId() == 248, "Attenzione id film non previsto");
	}

	@Test
	@DisplayName("Get the 3 most recent rented films")
	public void exercise6() {
		// Expected result: films ids = 445,440,338
		List<Film> films = rentalRepo.findAll().stream()
				.sorted(Comparator.comparing(Rental::getRentalDate).reversed())
				.map(Rental::getFilm)
				.limit(3)
				.collect(Collectors.toList());
		films.forEach(f -> log.info("film id = " + f.getId()));
		Assert.isTrue(films.size() == 3, "Attenzione numero di film non previsto");
		Assert.isTrue(films.get(0).getId() == 445, "Attenzione id film non previsto");
		Assert.isTrue(films.get(1).getId() == 440, "Attenzione id film non previsto");
		Assert.isTrue(films.get(2).getId() == 338, "Attenzione id film non previsto");
	}

	@Test
	@DisplayName("Get statistics related to film rental rates")
	public void exercise7() {
		//Use  DoubleSummaryStatistics class
		DoubleSummaryStatistics stats = filmRepo.findAll().stream()
				.collect(Collectors.summarizingDouble(Film::getRental_rate));
		log.info("stats = " +stats);
		Assert.isTrue(stats.getCount() == 1000, "Attenzione count non previsto");
		Assert.isTrue(stats.getSum() == 2980.000000, "Attenzione sum non previsto");
		Assert.isTrue(stats.getMin() == 0.990000, "Attenzione min non previsto");
		Assert.isTrue(stats.getAverage() == 2.980000, "Attenzione average non previsto");
		Assert.isTrue(stats.getMax() == 4.990000, "Attenzione max previsto");
	}

	@Test
	@DisplayName("Obtain a map composed by a Staff object and a list of his rented films")
	public void exercise8() {
		// Expected result: Mike 8040 films, Jon 8004 films
		Map<Staff, List<Rental>> rentalsPerStaff = rentalRepo.findAll().stream()
				.collect(Collectors.groupingBy(Rental::getStaff));
		rentalsPerStaff.forEach((s,r) -> log.info(s.getFirstName() + "/" + r.size()));
		List<Staff> staffList = new ArrayList<>(rentalsPerStaff.keySet());
		Assert.isTrue(staffList.size() == 2, "Attenzione numero persone staff non previsto");
		Assert.isTrue(rentalsPerStaff.get(staffList.get(0)).size() == 8040, "Attenzione numero noleggi film non previsto per la chiave = " + staffList.get(0).getFirstName());
		Assert.isTrue(rentalsPerStaff.get(staffList.get(1)).size() == 8004, "Attenzione numero noleggi film non previsto per la chiave = " + staffList.get(1).getFirstName());
	}

	@Test
	@DisplayName("Obtain a map composed by a Staff LastName object and a list of his rented films")
	public void exercise9() {
		// Expected result: Mike 8040 films, Jon 8004 films
		Map<String, List<Rental>> rentalsPerStaffSurname = rentalRepo.findAll().stream()
				.collect(Collectors.groupingBy(s -> s.getStaff().getLastName()));
		rentalsPerStaffSurname.forEach((s,r) -> log.info(s + "/" + r.size()));
		List<String> staffSurnamesList = new ArrayList<>(rentalsPerStaffSurname.keySet());
		Assert.isTrue(staffSurnamesList.size() == 2, "Attenzione numero persone staff non previsto");
		Assert.isTrue(rentalsPerStaffSurname.get(staffSurnamesList.get(0)).size() == 8004, "Attenzione numero noleggi film non previsto per la chiave = " + staffSurnamesList.get(0));
		Assert.isTrue(rentalsPerStaffSurname.get(staffSurnamesList.get(1)).size() == 8040, "Attenzione numero noleggi film non previsto per la chiave = " + staffSurnamesList.get(1));

	}

	@Test
	@DisplayName("Get the most expensive film rental rate per category")
	public void exercise10() {
		// Expected result: 16 categories with 29,99
		Map<Category, List<Film>> filmsPerCategory = filmRepo.findAll().stream()
				.sorted(Comparator.comparing(Film::getRental_rate).reversed())
				.collect(Collectors.groupingBy(Film::getCategory));
		Map<Category, Double> mostExpensiveFilmRentalRatePerCategory = new LinkedHashMap<>();
		List<Category> categoryList = new ArrayList<>(filmsPerCategory.keySet());
		Assert.isTrue(categoryList.size() == 16, "Attenzione numero categorie film non previsto");
		for (Category category : categoryList) {
			mostExpensiveFilmRentalRatePerCategory.put(category, filmsPerCategory.get(category).get(0).getRental_rate());
			Assert.isTrue(mostExpensiveFilmRentalRatePerCategory.get(category) == 4.99, "Attenzione rental rate film non previsto per la chiave = " + category.getName());
		}
		mostExpensiveFilmRentalRatePerCategory.forEach((c,f) -> log.info(c.getName() + "/" + f));
	}


	@Test
	@DisplayName("Get the top 5 customers - those one who rented more")
	public void exercise11() {
		/* /Expected result:
			148 Eleanor Hunt 147.54
			526	Karl Seal 138.55
			144	Clara Shaw 135.58
			178	Marion Snyder 134.61
			459	Tommy Collazo 131.62
		*/
		List<Object[]> rentalRateTotalAmountPerCustomer = rentalRepo.findAll().stream()
				.collect(Collectors.groupingBy(Rental::getCustomer))
				.entrySet().stream()
				.map(rentalsListCollection -> {
					Object[] result = new Object[2];
					result[0] = rentalsListCollection.getKey().getFirstName() + " "  + rentalsListCollection.getKey().getLastName();
					result[1] = rentalsListCollection.getValue().stream()
							.map(rentalsList -> rentalsList.getFilm().getRental_rate())
							.reduce(0d, Double::sum);
					return result; })
				.sorted((object1, object2) -> ((Double) object2[1]).compareTo((Double) object1[1]))
				.limit(5)
				.collect(Collectors.toList());
		Assert.isTrue(rentalRateTotalAmountPerCustomer.size() == 5, "Attenzione numero elementi non previsto");
		List<String> topFiveHighestRentalRateCustomers = Arrays.asList("ELEANOR HUNT", "KARL SEAL", "CLARA SHAW", "MARION SNYDER", "TOMMY COLLAZO");
		List<Double> topFiveRentalRateTotalAmounts = Arrays.asList(147.54, 138.55, 135.58, 134.61, 131.62);
		for(int i = 0; i < rentalRateTotalAmountPerCustomer.size(); i++) {
			Assert.isTrue(rentalRateTotalAmountPerCustomer.get(i)[0].equals(topFiveHighestRentalRateCustomers.get(i)) , "Attenzione top 5 clienti con rate noleggi più alto non corretta");
			Assert.isTrue(BigDecimal.valueOf((Double) rentalRateTotalAmountPerCustomer.get(i)[1]).setScale(2, RoundingMode.HALF_UP).doubleValue() == topFiveRentalRateTotalAmounts.get(i), "Attenzione totali top 5 clienti con rate noleggi più alto non corretti");
		}
	}
}
