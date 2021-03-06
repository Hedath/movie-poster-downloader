package com.herolds.discreenkt.api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.herolds.discreenkt.api.data.Movie;
import com.herolds.discreenkt.api.data.MovieFactory;

/**
 * Class for parsing kritikustomeg html file into movie list. Created by Benedek
 * Herold on 2017.07.15.
 */
public class MovieListParser {
	
	private MovieFactory movieFactory;
	
	@Inject
	public MovieListParser(MovieFactory movieFactory) {
		this.movieFactory = movieFactory;
	}
	
	public int getMaxPage(String movieListUrl) throws IOException {
		Document document = Jsoup.connect(movieListUrl).get();
		
		Elements paging = document.select("ul.paging>li:last-child");
		
		for(Element page : paging) {
			return Integer.parseInt(page.text());
		}
		
		return 0;
	}
	
	public List<Movie> getMovieLinks(String movieListUrl) throws IOException {
		List<Movie> movies = new ArrayList<>();

		Document document = Jsoup.connect(movieListUrl).get();

		Elements tables = document.select("table.fullsize");
		for (Element table : tables) {
			Elements links = table.select("tr td:first-child a");
			for (Element link : links) {
				Optional<Movie> movie = createMovie(link);
				movie.ifPresent(movies::add);
			}
		}

		return movies;
	}

	private Optional<Movie> createMovie(Element link) {
		String title = link.text();

		if (StringUtils.isEmpty(title)) {
			return Optional.empty();
		}

		String movieLink = link.attr("href");
		String secondaryTitle = null;

		Elements spans = link.siblingElements().select("span");
		if (!spans.isEmpty()) {
			Element firstSpan = spans.get(0);
			secondaryTitle = firstSpan.text();
		}

		return Optional.of(movieFactory.create(title, secondaryTitle, movieLink));
	}
}
