package com.movieflix.mivieAPI.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.logging.Level;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer movieId;

    @Column(nullable = false , length = 200)
    @NotBlank(message = "Please Provide Movies title!")
    String title;

    @Column(nullable = false )
    @NotBlank(message = "Please Provide Movies director!")
    String director;
    @Column(nullable = false )
    @NotBlank(message = "Please Provide Movies studio!")
    String studio;

    @ElementCollection
    @CollectionTable(name = "movie_cast")
    Set<String> movieCast;
    @Column(nullable = false )

    Integer releaseYear;

    @Column(nullable = false )
    @NotBlank(message = "Please Provide Movies poster!")
    String poster;

}
