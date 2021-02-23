package nagp.directservice.sellers.service;

import java.util.List;
import java.util.Optional;


import nagp.directservice.sellers.exceptions.InvalidSellerException;
import nagp.directservice.sellers.models.Seller;

public interface ISellerService {
	Optional<Seller> getSeller(String id);
	boolean deleteSeller(String id);
	List<Seller> getAllSellers();
	List<Seller> getSellersByCategory(String category);
	List<Seller> getSellersByService(String serviceType);
	double addRating(String id, double rating);
	String addSeller(String name,double experience, String category, String service, String state,
			String description);
	String getOrderCount(String sellerId) throws InvalidSellerException;
	String getAllOrders(String sellerId) throws InvalidSellerException;
}
