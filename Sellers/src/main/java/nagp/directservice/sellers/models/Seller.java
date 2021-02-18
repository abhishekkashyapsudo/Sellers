package nagp.directservice.sellers.models;

import nagp.directservice.sellers.exceptions.InvalidCategoryException;
import nagp.directservice.sellers.exceptions.InvalidRatingException;

public class Seller {
	
	
	private static int currId = 10000000;
	
	/**
	 * Unique id of the seller.
	 * This id is uniquely generated by the application
	 */
	private String sellerId;
	
	/**
	 * Full name of the seller
	 */
	private String name;
	
	/**
	 * Work experience in years
	 */
	private double experience;
	
	/**
	 * Category of service provided by the seller
	 */
	private Category category;
	/**
	 * Type of service provided by the seller
	 */
	private ServiceType service;
	
	/**
	 * Rating of this seller (1-10)
	 */
	private double rating;
	
	/**
	 * Total ratings provided by the users
	 */
	private int totalRatings;
	
	/**
	 * State in which services are provided
	 */
	private String state;
	
	/**
	 * Whether the seller is available or not
	 */
	private boolean isAvailable;
	/**
	 * Some description provided by the seller while registering
	 */
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getExperience() {
		return experience;
	}

	public void setExperience(double experience) {
		this.experience = experience;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public ServiceType getService() {
		return service;
	}

	public void setService(ServiceType service) {
		if (this.category != service.getCatgory())
			throw new InvalidCategoryException();
		this.service = service;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSellerId() {
		return sellerId;
	}

	public double getRating() {
		return rating;
	}

	public int getTotalRatings() {
		return totalRatings;
	}
	
	
	public double addRating(double newRating) {
		if (newRating < 0 || newRating > 10) {
			throw new InvalidRatingException();
		}
		double tempRating = totalRatings * rating;
		totalRatings ++;
		tempRating += newRating;
		rating = tempRating / totalRatings;
		return rating;
	}

	public Seller(String name, double experience, Category category, ServiceType service, String state,
			String description) {
		super();
		this.sellerId = "S" + currId++;
		this.name = name;
		this.experience = experience;
		setCategory(category);
		setService(service);
		this.state = state;
		this.description = description;
		this.setAvailable(true);
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	
	
	
}