package nagp.directservice.sellers.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import nagp.directservice.sellers.dao.ISellerDao;
import nagp.directservice.sellers.exceptions.InvalidCategoryException;
import nagp.directservice.sellers.exceptions.InvalidSellerException;
import nagp.directservice.sellers.models.Category;
import nagp.directservice.sellers.models.Seller;
import nagp.directservice.sellers.models.ServiceType;
import nagp.directservice.sellers.service.ISellerService;

@Service
public class SellerService implements ISellerService{

	@Resource
	ISellerDao sellerDao;

	@Autowired
	LoadBalancerClient loadBalancerClient;

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SellerService.class);


	public Optional<Seller> getSeller(String id) {
		return sellerDao.getSeller(id);
	}

	public boolean deleteSeller(String id) {
		return sellerDao.deleteSeller(id);
	}

	public List<Seller> getAllSellers() {
		return sellerDao.getAllSellers();
	}

	public List<Seller> getSellersByCategory(String sCategory) {
		Category category = Category.valueOf(sCategory);
		if(category == null) {
			return new ArrayList<Seller>();
		}
		return sellerDao.getSellersByCategory(category);
	}

	public List<Seller> getSellersByService(String serviceType) {
		ServiceType service = ServiceType.valueOf(serviceType);
		if(service == null) {
			return new ArrayList<Seller>();
		}
		return sellerDao.getSellersByService(service);
	}

	public double addRating(String id, double rating) {
		Optional<Seller> seller = getSeller(id);
		if(seller.isPresent()) {
			return seller.get().addRating(rating);
		}
		return -1;
	}

	public String addSeller(String name, double experience, String sCategory, String serviceType, String state,
			String description) {
		ServiceType service = ServiceType.valueOf(serviceType);
		if(service == null) {
			throw new InvalidCategoryException("Invalid Service name was passed.");
		}
		Category category = Category.valueOf(sCategory);
		if(category == null) {
			throw new InvalidCategoryException("Invalid Category name was passed.");
		}
		return sellerDao.addSeller(name, experience, category, service, state, description);

	}



	@HystrixCommand(fallbackMethod = "getOrdersFallback")
	public String getOrderCount(String sellerId) throws InvalidSellerException{
		Optional<Seller> seller = getSeller(sellerId);
		if(seller.isPresent()) {
			String baseUrl = loadBalancerClient.choose("orders").getUri().toString() + "/orders/seller";
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = null;
			try {
				UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl)
						.queryParam("sellerId", sellerId);
				builder.build();
				response = restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.GET, null,
						String.class);
			} catch (Exception ex) {
				System.out.println(ex);
			}
			return response.getBody();
		}
		else {
			throw new InvalidSellerException(sellerId);
		}

	}

	@HystrixCommand(fallbackMethod = "getOrdersFallback")
	public String getAllOrders(String sellerId) throws InvalidSellerException {
		Optional<Seller> seller = getSeller(sellerId);
		if(seller.isPresent()) {
			String baseUrl = loadBalancerClient.choose("orders").getUri().toString() + "/orders/allSellers";
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = null;
			try {
				UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl)
						.queryParam("sellerId", sellerId);
				response = restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.GET, null,
						String.class);
			} catch (Exception ex) {
				System.out.println(ex);
			}
			return response.getBody();
		}
		else {
			throw new InvalidSellerException(sellerId);
		}
	}


	public String getOrdersFallback(String sellerId){
		logger.warn("Orders Service is down!!! fallback route enabled...");

		return "CIRCUIT BREAKER ENABLED!!! No Response From Orders Service at this moment. " +
		" Service will be back shortly - ";
	}

}
