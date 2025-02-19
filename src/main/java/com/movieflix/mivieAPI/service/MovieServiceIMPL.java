package com.movieflix.mivieAPI.service;

import com.movieflix.mivieAPI.dto.Moviedto;
import com.movieflix.mivieAPI.entities.Movie;
import com.movieflix.mivieAPI.exception.FileExistsException;
import com.movieflix.mivieAPI.exception.MovieNotFoundException;
import com.movieflix.mivieAPI.repository.MovieRepository;
import com.movieflix.mivieAPI.transformer.MovieTranformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class MovieServiceIMPL implements MovieService{


    private  final FileService fileService;
    private  final MovieRepository movieRepository;
    @Value("${project.poster}")
    private String path;
    @Value("${base.url}")
    private String baseUrl;

    public MovieServiceIMPL(FileService fileService, MovieRepository movieRepository) {
        this.fileService = fileService;
        this.movieRepository = movieRepository;
    }


    @Override
    public Moviedto addMovie(Moviedto moviedto, MultipartFile file) throws IOException{
        // upload the file
        if(Files.exists(Paths.get(path+ File.separator + file.getOriginalFilename()))){
           throw  new FileExistsException("file already exists! Please use another file name");
        }
         String uploadFileName = fileService.uploadeFile(path , file);

        // set the value of field 'poster' as filename
         moviedto.setPoster(uploadFileName);

        //map dto to movie object
        Movie movie =  MovieTranformer.DtoToEntity(moviedto);

        //save the movie obj
         Movie savedMovie =   movieRepository.save(movie);

        //generate poster url
        String postUrl = baseUrl + "/file/" + uploadFileName;

        //map movie obj to dto ans return it
        return MovieTranformer.entitytoDto(movie , postUrl);
    }

    @Override
    public Moviedto getMovie(Integer movieId) {
        // 1. check if data is present in DB or NOt
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found by this ID"));

        // 2. Generate posterUrl
        String posturl = baseUrl + "/file/"+movie.getPoster();

        // 3. Map Movie dto obje and return

        return MovieTranformer.entitytoDto(movie , posturl);
    }

    @Override
    public List<Moviedto> getAllMovie() {
        List<Movie> movies = movieRepository.findAll();
        return MovieTranformer.listEntityToSto(movies);
    }

    @Override
    public Moviedto updateMovie(Integer movieId, Moviedto movieDto, MultipartFile file) throws IOException {

        // 1. check if Movie onb is exits with given movieId
         Movie mv = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("No Movie present by this id"));

        // 2. check if poster is null , do nothing and if  not null the then delete existing file  associated with the record and upload new file
         String fileName = mv.getPoster();
         if(file != null){
             Files.deleteIfExists(Paths.get(path + File.separator + fileName));
             fileName = fileService.uploadeFile(path , file);
         }
        // 3.  set mocieDto poster value according to step 2
        movieDto.setPoster(fileName);
        // 4. map it to movie onject
        Movie movie = new Movie(
                mv.getMovieId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );
        // Instead of creating a new Movie object, update the existing movie entity with the values from moviedto.
        // 5. save movie object and return this object
        Movie movie2 = movieRepository.save(movie);
        // 6. generate poster url for this
        String posterUrl = baseUrl+"/file/"+ movie.getPoster();
        // 7. map movie dto and return it

        return MovieTranformer.entitytoDto(movie2 , posterUrl);
    }

    @Override
    public String deleteMovie(Integer movieId) throws IOException {
        // 1. check if movie exist with this id or not
        Movie mv = movieRepository.findById(movieId).orElseThrow(()-> new MovieNotFoundException("Movie not find by Id"));
        Integer id = mv.getMovieId();
        // 2. delete the file associated with  this object
         String fileName = mv.getPoster();
         Files.deleteIfExists(Paths.get(path + File.separator + fileName));
        // 3. delete the movie object
        movieRepository.delete(mv);
        return "Movie deleted with id "+ id;
    }
}
