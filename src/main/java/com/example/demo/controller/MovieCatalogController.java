package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.model.CatalogItem;
import com.example.demo.model.Movie;
import com.example.demo.model.Rating;
import com.example.demo.model.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient.Builder builder;

	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalogItem(@PathVariable("userId") String userId) {
		
		List<Rating> ratings = Arrays.asList(
				new Rating("1234", 5),
				new Rating("5678", 4)
				);
		
		return ratings.stream().map(rating -> {
			 Movie movie = restTemplate.getForObject("http://localhost:8083/movies/" + rating.getMovieId(),
					Movie.class);
			/*
			 * Movie movie = builder.build() .get() .uri("http://localhost:8083/movies/" +
			 * rating.getMovieId()) .retrieve() .bodyToMono(Movie.class) .block();
			 */
			return new CatalogItem(movie.getName(), "Desc", rating.getRating());
		}).collect(Collectors.toList());
		
		
		//return Collections.singletonList(new CatalogItem("Titanic","Test",4));
	}
	@GetMapping("/user/{userid}")
	public List<CatalogItem> getCatalogitem(@PathVariable("userid") String userid) {
		UserRating ratings = restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userid, UserRating.class);
		return ratings.getUserRatings().stream().map(rating -> {
			Movie movie = restTemplate.getForObject("http://movie-infom-service/movies/" + rating.getMovieId(), Movie.class);
			return new CatalogItem(movie.getName(), "Desc", rating.getRating());
		}).collect(Collectors.toList());
			
			
		
		
	}
	
}
