package com.matrangola.school.dao.inmemory;

import com.matrangola.school.dao.BaseDAO;
import com.matrangola.school.domain.Track;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTrackDAO implements BaseDAO<Track> {

	private Map<Integer, Track> tracks = new HashMap<Integer, Track>();
	private static int nextId = 0;
	
	@Override
	public void update(Track updateObject) {
		if(tracks.containsKey(updateObject.getId())) {
			tracks.put(updateObject.getId(), updateObject);
		}
	}

	@Override
	public void delete(Track track) {
		tracks.remove(track.getId());
	}

	@Override
	public Track create(Track newObject) {
		//Create a new Id
		int newId = ++nextId;
		newObject.setId(newId);
		tracks.put(newId, newObject);
		
		return newObject;
	}

	@Override
	public Track get(int id) {
		return tracks.get(id);
	}

	@Override
	public List<Track> getAll() {
		return new ArrayList<Track>(tracks.values());
	}
	
	public Map<Integer, Track> getTracks() {
		nextId = 0;
		return tracks;
	}

	public void setTracks(Map<Integer, Track> tracks) {
		this.tracks = tracks;
	}
}
