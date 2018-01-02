package it.polito.tdp.ufo.bean;

import java.sql.Date;
import java.time.LocalDate;
import com.javadocmd.simplelatlng.LatLng;

public class Sighting  {

	private int id;
	private LocalDate datetime;
	private String city;
	private String state;
	private String shape;
	private int duration;
	private String duration_hm;
	private Date date_posted;	
	private LatLng coords;

	
	public LatLng getCoords() {
		return coords;
	}

	public void setCoords(LatLng coords) {
		this.coords = coords;
	}

	public Sighting(int id, LocalDate datetime, String shape) {
		super();
		this.id = id;
		this.datetime = datetime;
		this.shape = shape;
	}
	
	public Sighting(int id, String shape) {
		super();
		this.id = id;
		this.shape = shape;
	}
	public Sighting(int id, LocalDate datetime, String shape, LatLng coords) {
		super();
		this.id = id;
		this.datetime = datetime;
		this.shape = shape;
		this.coords = coords;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getDatetime() {
		return datetime;
	}
	public void setDatetime(LocalDate datetime) {
		this.datetime = datetime;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getDuration_hm() {
		return duration_hm;
	}
	public void setDuration_hm(String duration_hm) {
		this.duration_hm = duration_hm;
	}
	public Date getDate_posted() {
		return date_posted;
	}
	public void setDate_posted(Date date_posted) {
		this.date_posted = date_posted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sighting other = (Sighting) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Sighting [id=" + id + ", datetime=" + datetime + ", shape=" + shape + "] \n ";
	}


	
	
}
