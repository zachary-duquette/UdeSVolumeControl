package model;

import java.io.Serializable;

public class VolumeConfig implements Serializable{
	private static final long serialVersionUID = 4907608852944084713L;
	
	private Integer _id;
	private String _name;
	private Double _longitude;
	private Double _latitude;
    private Integer _radius;
    private Integer _profile;

	public VolumeConfig(Integer id, String name,
			Double longitude, Double latitude, Integer radius, Integer profile) {
		_id = id;
		_name = name;
		_longitude = longitude;
		_latitude = latitude;
		_radius = radius;
		_profile = profile;
	}
	
	public Integer getId() {
		return _id;	
	}
	
	public void setId(Integer id) {
		_id = id;	
	}
	
	public String getName() {
		return _name;	
	}
	
	public void setName(String name) {
		_name = name;	
	}
	
	public Double getLongitude() {
		return _longitude;	
	}
	
	public void setLongitude(Double longitude) {
		_longitude = longitude;	
	}
	
	public Double getLatitude() {
		return _latitude;
	}
	
	public void setLatitude(Double lattitude) {
		_latitude = lattitude;
	}
	
	public Integer getRadius() {
		return _radius;	
	}
	
	public void setRadius(Integer radius) {
		_radius = radius;	
	}
	
	public Integer getProfile() {
		return _profile;	
	}
	
	public void setProfile(Integer profile) {
		_profile = profile;	
	}
}
