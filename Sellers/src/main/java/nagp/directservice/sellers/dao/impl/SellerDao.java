package nagp.directservice.sellers.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import nagp.directservice.sellers.dao.ISellerDao;
import nagp.directservice.sellers.models.Category;
import nagp.directservice.sellers.models.Seller;
import nagp.directservice.sellers.models.ServiceType;

@Repository
public class SellerDao implements ISellerDao{

	private static final String[] states = {"Andhra Pradesh",
			"Arunachal Pradesh",
			"Assam",
			"Bihar",
			"Chhattisgarh",
			"Goa",
			"Gujarat",
			"Haryana",
			"Himachal Pradesh",
			"Jammu and Kashmir",
			"Jharkhand",
			"Karnataka",
			"Kerala",
			"Madhya Pradesh",
			"Maharashtra",
			"Manipur",
			"Meghalaya",
			"Mizoram",
			"Nagaland",
			"Odisha",
			"Punjab",
			"Rajasthan",
			"Sikkim",
			"Tamil Nadu",
			"Telangana",
			"Tripura",
			"Uttarakhand",
			"Uttar Pradesh",
			"West Bengal",
			"Andaman and Nicobar Islands",
			"Chandigarh",
			"Dadra and Nagar Haveli",
			"Daman and Diu",
			"Delhi",
			"Lakshadweep",
	"Puducherry"};

	private static Random random;

	private static List<Seller> sellers = new ArrayList<>();
	public Optional<Seller> getSeller(String id) {
		return getAllSellers().stream()
				.filter(s -> id.trim().equalsIgnoreCase(s.getSellerId())).findFirst();
	}

	public boolean deleteSeller(String id) {
		return sellers.removeIf(s -> id.trim().equalsIgnoreCase(s.getSellerId()));
	}

	public List<Seller> getAllSellers() {
		return sellers;
	}

	public List<Seller> getSellersByCategory(Category category) {
		return sellers.stream().filter(s -> s.getCategory() == category).collect(Collectors.<Seller>toList());
	}

	public List<Seller> getSellersByService(ServiceType serviceType) {
		return sellers.stream().filter(s -> s.getService() == serviceType).collect(Collectors.<Seller>toList());
	}

	public String addSeller(String name, double experience, Category category, ServiceType service, String state,
			String description) {
		Seller seller = new Seller(name, experience, category, service, state, description);
		sellers.add(seller);
		return seller.getSellerId();
	}

	static {
		random = new Random();
		for (int i = 0; i<random.nextInt(30)+20; i++) {
			sellers.add(randomSeller(i));
		}
	}

	private static Seller randomSeller(int i) {
		ServiceType service = ServiceType.values()[random.nextInt(ServiceType.values().length)];
		String state = states[random.nextInt(states.length)];
		return new Seller("Seller "+(i+1), 2.5, service.getCatgory(), service, state, "A highly talented "+service+".");
	}


}
