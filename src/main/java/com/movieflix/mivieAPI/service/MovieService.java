package com.movieflix.mivieAPI.service;

import com.movieflix.mivieAPI.dto.MoviePageResponse;
import com.movieflix.mivieAPI.dto.Moviedto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {


    Moviedto addMovie(Moviedto moviedto , MultipartFile file) throws IOException;

    Moviedto getMovie(Integer MovieId);

    List<Moviedto> getAllMovie();

    Moviedto updateMovie(Integer movieId , Moviedto moviedto , MultipartFile file) throws IOException;

    String deleteMovie(Integer movieId) throws IOException;
    MoviePageResponse getAllMovieWithPagination(Integer pageNumber , Integer pageSize);
    MoviePageResponse getAllMovieWithPaginationAndSorting(Integer pageNumber , Integer pageSize , String sortBy , String dir);
}
