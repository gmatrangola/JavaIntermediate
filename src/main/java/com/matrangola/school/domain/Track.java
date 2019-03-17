package com.matrangola.school.domain;

public class Track {

	private int id;
	private String title;
	private String artist;
	private String album;
	private String duration;
	private String date;

	public Track() {
		super();
	}

	public Track(String title, String artist, String album, String duration, String date) {
		super();
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.duration = duration;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
	@Override
	public String toString() {
		return "Track [id=" + id + ", title=" + title + ", artist=" + artist + ", album=" + album + ", duration="
				+ duration + ", date=" + date + "]";
	}
}
