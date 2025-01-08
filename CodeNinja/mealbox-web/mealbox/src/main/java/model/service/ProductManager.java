package model.service;

import model.domain.Product;
import java.util.List;
import model.dao.ProductDAO;

public class ProductManager {
	private static ProductManager productMan = new ProductManager();
	private ProductDAO productDao;
	
	private ProductManager() {
		try {
			productDao = new ProductDAO();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ProductManager getInstance() {
		return productMan;
	}
	
	public Product getDetail(int productId) {
		return productDao.getDetail(productId);
	}
	
	public List<Product> findProductList() {
		return productDao.getAllProducts();
	}
	
	public int createProduct(Product product) {
		return productDao.create(product);
	}
	
	public int updateProduct(Product product) {
		return productDao.update(product);
	}
	
	public int removeProduct(String productId) {
		return productDao.delete(Integer.parseInt(productId));
	}
	
	public List<Product> searchProduct(String categoryType, String categoryValue, String orderBy) {
		return productDao.searchProduct(categoryType, categoryValue, orderBy);
	}
	
	 public int decreaseItemQuantity(int productId, int quantity) {
	     return productDao.decreaseQuantity(productId, quantity);
	 }
}