package myRetail.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

	@JsonProperty("id")
	private long id;

	@JsonProperty("name")
	private String title;

	@JsonProperty("current_price")
	private Price price;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the price
	 */
	public Price getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Price price) {
		this.price = price;
	}

}
