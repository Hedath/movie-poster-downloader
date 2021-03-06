package com.herolds.discreenkt.api.data;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Movie entity for kritikustomeg movies. Created by Benedek Herold on
 * 2017.07.16.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Movie implements Serializable {

	private int KTid;

	private String title;

	private String secondaryTitle;

	private String link;

	private int year;
}
