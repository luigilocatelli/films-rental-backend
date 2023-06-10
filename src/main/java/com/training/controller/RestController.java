package com.training.controller;

import com.training.dao.CategoryDao;
import com.training.dao.FilmDao;
import com.training.dao.RentalDao;
import com.training.dao.impl.CustomerDaoImpl;
import com.training.dao.impl.RentalDaoImpl;
import com.training.dao.impl.StaffDaoImpl;
import com.training.models.Category;
import com.training.models.Film;
import com.training.models.Rental;
import com.training.models.security.request.PostRequest;
import com.training.repos.RentalRepo;
import com.training.utils.Functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@CrossOrigin(origins = { "http://localhost:8081" })
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    @Autowired
    private FilmDao filmDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CustomerDaoImpl customerDao;

    @Autowired
    private StaffDaoImpl staffDao;

    @Autowired
    private RentalDaoImpl rentalDao;

    @Autowired
    private RentalRepo rentalRepo;

    @RequestMapping("/unprotected/getAllFilms")
    public List<Film> getAllFilms() {
        return filmDao.getAllFilms();
    }

    @RequestMapping("/unprotected/getAllCategories")
    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    @RequestMapping("/unprotected/getFilmsByCategoryId")
    public List<Film> getFilmsByCategoryId(@RequestParam("categoryId") int categoryId) {
        return filmDao.getFilmsByCategoryId(categoryId);
    }

    @GetMapping("/protected")
    public ResponseEntity<?> getProtectedResource() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return ResponseEntity.ok("Hello Logged-In User:" + currentPrincipalName + ", you can access this resource, because JWT was valid");
    }

    @RequestMapping("/protected/getAllNotRentedFilmsByCustomer")
    public List<Film> getAllNotRentedFilms(@RequestParam("customerId") Long customerId) {
        return filmDao.getAllNotRentedFilmsByCustomer(customerId);
    }

    @RequestMapping("/protected/getAllRentedFilmsByCustomer")
    public List<Film> getAllRentedFilms(@RequestParam("customerId") Long customerId) {
        return filmDao.getAllRentedFilmsByCustomer(customerId);
    }

    @RequestMapping("/protected/getNotRentedFilmsByCustomerAndCategoryId")
    public List<Film> getNotRentedFilmsByCustomerAndCategoryId(@RequestParam("categoryId") int categoryId, @RequestParam("customerId") Long customerId) {
        return filmDao.getNotRentedFilmsByCustomerAndCategoryId(categoryId, customerId);
    }

    @RequestMapping("/protected/getRentedFilmsByCustomerAndCategoryId")
    public List<Film> getRentedFilmsByCustomerAndCategoryId(@RequestParam("categoryId") int categoryId, @RequestParam("customerId") Long customerId) {
        return filmDao.getRentedFilmsByCustomerAndCategoryId(categoryId, customerId);
    }

    @PostMapping("/protected/insertNewFilmRentalForCustomer")
    public ResponseEntity<?> insertNewFilmRentalForCustomer(@RequestBody PostRequest request) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7);

        Rental rental = new Rental();
        rental.setRentalDate(Functions.convertDateToLocalDate(new Date()));
        rental.setReturnDate(Functions.convertDateToLocalDate(calendar.getTime()));
        rental.setCustomer(customerDao.getById(request.getCustomerId()));
        rental.setFilm(filmDao.getById(request.getFilmId()));
        rental.setStaff(staffDao.getById(1L)); //TODO: rendere dinamico
        rental.setLastUpdate(Functions.convertDateToLocalDate(new Date()));

        rentalRepo.save(rental);

        return ResponseEntity.ok("Film rental requested successfully");
    }

    @PostMapping("/protected/cancelFilmRentalForCustomer")
    public ResponseEntity<?> cancelFilmRentalForCustomer(@RequestBody PostRequest request) {
        System.out.println(request.getFilmId() + "/" + request.getCustomerId());

        rentalDao.cancelFilmRentalForCustomer(request.getFilmId(), request.getCustomerId());

        return ResponseEntity.ok("Film rental canceled successfully");
    }

}
