package com.movieflix.mivieAPI.transformer;

import com.movieflix.mivieAPI.dto.Moviedto;
import com.movieflix.mivieAPI.entities.Movie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieTranformer {

    private static String baseUrl;

    @Value("${base.url}")
    public  void setBaseUrl(String url){
        baseUrl = url;
    }
    public  static Movie  DtoToEntity(Moviedto moviedto){
        return Movie.builder()
                .title(moviedto.getTitle())
                .director(moviedto.getDirector())
                .studio(moviedto.getStudio())
                .movieCast(moviedto.getMovieCast())
                .releaseYear(moviedto.getReleaseYear())
                .poster(moviedto.getPoster())
                .build();
    }
    public  static Moviedto entitytoDto(Movie movie , String posturl){
      return  Moviedto.builder()
                      .movieId(movie.getMovieId())
              .title(movie.getTitle())
              .director(movie.getDirector())
              .studio(movie.getStudio())
              .movieCast(movie.getMovieCast())
              .releaseYear(movie.getReleaseYear())
              .poster(movie.getPoster())
              .posterUrl(posturl)
              .build();
    }
    public static List<Moviedto> listEntityToSto(List<Movie> movies){
        List<Moviedto> list = new ArrayList<>();
        for(Movie movie :movies){
            String posturl = baseUrl + "/file/"+movie.getPoster();
            list.add(entitytoDto(movie , posturl));
        }
        return list;
    }
}
