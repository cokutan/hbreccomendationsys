package reccomendationengine.dto;

import java.util.List;

public class BrowserHistoryDTO {
	private String userId;
	 private List<String> products;
	 private String type;
	 
	 public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getProducts() {
		return products;
	}

	public void setProducts(List<String> products) {
		this.products = products;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BrowserHistoryDTO(String userid2, List<String> productList, String type2) {
		userId = userid2;
		products = productList;
		type = type2;
	}

}
