package com.movieflix.mivieAPI.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieflix.mivieAPI.dto.Moviedto;
import com.movieflix.mivieAPI.service.MovieService;
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

    @GetMapping("/get-all-movies")
    public ResponseEntity<List<Moviedto>> getAllMovies(){
        return new ResponseEntity<>(movieService.getAllMovie() , HttpStatus.OK);
    }


    @PutMapping("/{movieId}")
    public  ResponseEntity<Moviedto> updateById(@PathVariable Integer movieId ,
                                                @RequestPart MultipartFile file,
                                                @RequestPart String dto) throws IOException {
        if(file.isEmpty())file= null;
        Moviedto moviedto = convertMovieDto(dto);
        return new ResponseEntity<>(movieService.updateMovie(movieId , moviedto  , file) , HttpStatus.OK);

    }

    @GetMapping("update/{movieId}")
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
          Moviedto dto = convertMovieDto(movieDTO);

      return new ResponseEntity<>(movieService.addMovie( dto , file) , HttpStatus.OK);
    }

    private  Moviedto convertMovieDto(String movieDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Moviedto moviedto = objectMapper.readValue(movieDTO , Moviedto.class);
        return moviedto;
    }
}
