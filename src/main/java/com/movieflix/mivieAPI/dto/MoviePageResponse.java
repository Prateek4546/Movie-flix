package com.movieflix.mivieAPI.dto;

import java.util.List;

public record MoviePageResponse(List<Moviedto> moviedtos ,
                                Integer pageNumber,
                                Integer pageSize,
                                long totalElement,
                                int totalPages,
                                boolean isLast) {
}
