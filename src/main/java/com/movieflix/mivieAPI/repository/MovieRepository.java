package com.movieflix.mivieAPI.repository;

import com.movieflix.mivieAPI.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie , Integer> {
}
