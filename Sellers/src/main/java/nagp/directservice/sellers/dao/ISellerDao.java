package nagp.directservice.sellers.dao;

import java.util.List;
import java.util.Optional;

import nagp.directservice.sellers.models.Category;
import nagp.directservice.sellers.models.Seller;
import nagp.directservice.sellers.models.ServiceType;

public interface ISellerDao {
	Optional<Seller> getSeller(String id);
	boolean deleteSeller(String id);
	List<Seller> getAllSellers();
	List<Seller> getSellersByCategory(Category category);
	List<Seller> getSellersByService(ServiceType serviceType);
	String addSeller(String name,double experience, Category category, ServiceType service, String state,
			String description);
}
