package com.movieflix.mivieAPI.dto;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@Builder
public class Moviedto {

    Integer movieId;
    @NotBlank(message = "Please Provide Movies title!")
    String title;


    @NotBlank(message = "Please Provide Movies director!")
    String director;

    @NotBlank(message = "Please Provide Movies studio!")
    String studio;



    Set<String> movieCast;


    Integer releaseYear;

    @Getter
    @NotBlank(message = "Please Provide Movies poster!")
    String poster;


    @NotBlank(message = "Please Provide poster url!")
    String posterUrl;


}
