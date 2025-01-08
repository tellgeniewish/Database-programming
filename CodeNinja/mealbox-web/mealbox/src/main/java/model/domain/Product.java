package model.domain;

public class Product {
	private int id;
	private String name;
	private String thumb;
	private String description;
	private int price;
	private int totalReview;
	private double averageReview;
	private int stock;
	private int categoryPerson;
	private int categoryType;

	public Product(int id, String name, String thumb, String description, int price, int totalReview, double averageReview, int stock, int categoryPerson, int categoryType) {
		this.id = id;
		this.name = name;
		this.thumb = thumb;
		this.description = description;
		this.price = price;
		this.totalReview = totalReview;
		this.averageReview = averageReview;
		this.stock = stock;
		this.categoryPerson = categoryPerson;
		this.categoryType = categoryType;
	}

	public Product(int id, String name, String thumb, String description, int price, int stock, int categoryPerson, int categoryType) {
		this.id = id;
		this.name = name;
		this.thumb = thumb;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.categoryPerson = categoryPerson;
		this.categoryType = categoryType;
	}

	public Product(String name, String thumb, String description, int price, int stock, int totalReview, double averageReview, int categoryPerson, int categoryType) {
		this.name = name;
		this.thumb = thumb;
		this.description = description;
		this.price = price;
		this.totalReview = totalReview;
		this.averageReview = averageReview;
		this.stock = stock;
		this.categoryPerson = categoryPerson;
		this.categoryType = categoryType;
	}

	public String getName() {
		return name;
	}

	public String getThumb() {
		return thumb;
	}

	public String getDescription() {
		return description;
	}

	public int getPrice() {
		return price;
	}

	public int getTotalReview() {
		return totalReview;
	}

	public double getAverageReview() {
		return averageReview;
	}

	public int getStock() {
		return stock;
	}

	public int getCategoryPerson() {
		return categoryPerson;
	}

	public int getCategoryType() {
		return categoryType;
	}

	public int getId() {
		return id;
	}
	
	public void setAverageReview(double averageReview) {
		this.averageReview = averageReview;
	}
	
}