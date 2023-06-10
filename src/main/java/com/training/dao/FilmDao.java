package com.training.dao;

import com.training.models.Category;
import com.training.models.Film;
import java.util.List;

public interface FilmDao {

    public List<Film> getAllFilms();

    public List<Film> getAllNotRentedFilmsByCustomer(Long customerId);

    public List<Film> getAllRentedFilmsByCustomer(Long customerId);

    public List<Film> getFilmsByCategoryId(int categoryId);

    public List<Film> getNotRentedFilmsByCustomerAndCategoryId(int categoryId, Long customerId);

    public List<Film> getRentedFilmsByCustomerAndCategoryId(int categoryId, Long customerId);

    public Film getById(Long filmId);
}
