package com.matrangola.school.service;

import com.matrangola.school.dao.BaseDAO;
import com.matrangola.school.dao.inmemory.InMemoryTrackDAO;
import com.matrangola.school.domain.Track;

import java.util.List;

public class TrackService {

	private BaseDAO<Track> trackDAO;
	
	public TrackService() {
		trackDAO = new InMemoryTrackDAO();
	}
	
	public Track createTrack(String title) {
		Track track = new Track(title, null, null, null, null);
		track = trackDAO.create(track);
		return track;
	}

	public Track createTrack(String title, String artist, String album, String duration, String date) {
		Track track = new Track(title, artist, album, duration, date);
		track = trackDAO.create(track);
		return track;
	}
	
	public Track createTrack(Track track) {
		track = trackDAO.create(track);
		
		return track;
	}
	
	public void deleteTrack(int id) {
		Track track = trackDAO.get(id);
		if(track != null) {
			trackDAO.delete(track);
		}
	}
	
	public Track getTrack(int id) {
		return trackDAO.get(id);
	}
	
	public List<Track> getAllTracks() {
		return trackDAO.getAll();
	}
	
	public BaseDAO<Track> getTrackDAO() {
		return trackDAO;
	}

	public void setTrackDAO(BaseDAO<Track> trackDAO) {
		this.trackDAO = trackDAO;
	}
}
