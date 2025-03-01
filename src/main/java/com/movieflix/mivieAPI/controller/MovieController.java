package com.movieflix.mivieAPI.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieflix.mivieAPI.dto.MoviePageResponse;
import com.movieflix.mivieAPI.dto.Moviedto;
import com.movieflix.mivieAPI.exception.EmptyFileException;
import com.movieflix.mivieAPI.service.MovieService;
import com.movieflix.mivieAPI.utils.AppConstants;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @GetMapping("/all-movie-pages")
    public ResponseEntity<MoviePageResponse> getMovieWithPagination(
            @RequestParam (defaultValue = AppConstants.PAGE_NUMBER ,required = false)Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE ,required = false)Integer pageSize
                                                                    )
    {
         return ResponseEntity.ok(movieService.getAllMovieWithPagination(pageNumber , pageSize));
    }


    @GetMapping("/get-all-movies")
    public ResponseEntity<List<Moviedto>> getAllMovies(){
        return new ResponseEntity<>(movieService.getAllMovie() , HttpStatus.OK);
    }

    @GetMapping("/allMoviePageSort")
    public  ResponseEntity<MoviePageResponse> getALlMovieWithPaginationAndSorting(
            @RequestParam (defaultValue = AppConstants.PAGE_NUMBER ,required = false)Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE ,required = false)Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_BY , required = false)String  sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR , required = false)String dir
    ){

        return ResponseEntity.ok(movieService.getAllMovieWithPaginationAndSorting(pageNumber , pageSize , sortBy , dir));
    }


    @PutMapping("/update/{movieId}")
    public  ResponseEntity<Moviedto> updateById(@PathVariable Integer movieId ,
                                                @RequestPart(required = false) MultipartFile file,
                                                @RequestPart String dto) throws IOException {
//        if(file.isEmpty())file= null;
        Moviedto moviedto = convertMovieDto(dto);
        return new ResponseEntity<>(movieService.updateMovie(movieId , moviedto  , file) , HttpStatus.OK);

    }

    @GetMapping("/{movieId}")
    public ResponseEntity<Moviedto> getById(@PathVariable Integer movieId){
        Moviedto moviedto = movieService.getMovie(movieId);
        return new ResponseEntity<>(moviedto , HttpStatus.OK);
    }

    @DeleteMapping("/delete/{movieId}")
    public  ResponseEntity<String> deleteById(@PathVariable Integer movieId) throws IOException {
        return new ResponseEntity<>(movieService.deleteMovie(movieId) , HttpStatus.OK);
    }
    @PostMapping("/add-movie")
    public ResponseEntity<Moviedto> addMovieHandler(@RequestPart MultipartFile file,
                                                    @RequestPart String movieDTO ) throws IOException {
          if(file.isEmpty()){
              throw  new EmptyFileException("File is Empty put the File here");
          }
          Moviedto dto = convertMovieDto(movieDTO);

      return new ResponseEntity<>(movieService.addMovie( dto , file) , HttpStatus.OK);
    }

    private  Moviedto convertMovieDto(String movieDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Moviedto moviedto = objectMapper.readValue(movieDTO , Moviedto.class);
        return moviedto;
    }
}
