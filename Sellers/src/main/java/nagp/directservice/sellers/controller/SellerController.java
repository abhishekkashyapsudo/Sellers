package nagp.directservice.sellers.controller;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import nagp.directservice.sellers.exceptions.InvalidSellerException;
import nagp.directservice.sellers.models.Seller;
import nagp.directservice.sellers.service.ISellerService;

@RestController
@EnableCircuitBreaker
@RequestMapping("sellers")
public class SellerController {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SellerController.class);

	@Resource(name = "restTemplate")
	private RestTemplate restTemplate;

	@Value("${server.port}")
	private int port;

	@Resource
	ISellerService sellerService;

	/**
	 * Returns the seller with the passed 
	 * @param sellerID
	 * @return
	 * @throws InvalidSellerException 
	 */
	@GetMapping(value = "/{sellerID}")
	Seller getSeller(@PathVariable(name = "sellerID") String sellerID) throws InvalidSellerException {
		logger.info("Working from port " + port + " of seller service");
		Optional<Seller> seller = sellerService.getSeller(sellerID);
		if(seller.isPresent()) {
			return seller.get();
		}
		throw new InvalidSellerException(sellerID);
	}
	
	@GetMapping(value = "/status")
	Seller setStatus(@RequestParam String id, @RequestParam boolean available ) throws InvalidSellerException {
		logger.info("Working from port " + port + " of seller service");
		Optional<Seller> seller = sellerService.getSeller(id);
		if(seller.isPresent()) {
			seller.get().setAvailable(available);
			return seller.get();
		}
		throw new InvalidSellerException(id);
	}
	@GetMapping()
	List<Seller> getAllSellers() {
		logger.info("Working from port " + port + " of seller service");
		return sellerService.getAllSellers();
	}
	
	@GetMapping(value = "category/{cate}")
	List<Seller> getByCategory(@PathVariable(name = "cate") String category) {
		logger.info("Working from port " + port + " of seller service");
		return sellerService.getSellersByCategory(category);
	}
	
	@GetMapping(value = "rating")
	String updateRating(@RequestParam String id,@RequestParam double newRating) {
		logger.info("Working from port " + port + " of seller service");
		double rating = sellerService.addRating(id, newRating);
		if(rating == -1) {
			return "ERROR: No such seller with passed id exists";
		}
		return "SUCCESS: Seller rating updated to " + rating;
	}
	
	@GetMapping(value = "service/{sType}")
	List<Seller> getByService(@PathVariable(name = "sType") String service) {
		logger.info("Working from port " + port + " of seller service");
		return sellerService.getSellersByService(service);
	}
	
	

	@DeleteMapping(value = "/{sellerID}")
	boolean deleteSeller(@PathVariable(name = "sellerID") String sellerID) {
		logger.info("Working from port " + port + " of seller service");
		return sellerService.deleteSeller(sellerID);
	}

	@PostMapping
	String addSeller(@RequestParam String name, @RequestParam double experience, @RequestParam String category
			, @RequestParam String service, @RequestParam String state, @RequestParam String description) {
		logger.info("Working from port " + port + " of seller service");
		String id = null;
		try {
			id = sellerService.addSeller(name, experience, category, service, state, description);
		}
		catch(Exception e) {
			logger.error(e.getMessage(), e);
			return "ERROR: " + e.getMessage();
		}
		return "Seller successfully created with seller id " + id;
	}
	
	@GetMapping(value = "orders/{sellerId}")
	String getOrderCount(@PathVariable(name = "sellerId") String sellerId) throws InvalidSellerException {
		logger.info("Working from port " + port + " of seller service");
		return sellerService.getOrderCount(sellerId);
	}
	
	@GetMapping(value = "allOrders/{sellerId}")
	String getAllOrders(@PathVariable(name = "sellerId") String sellerId) throws InvalidSellerException {
		logger.info("Working from port " + port + " of Consumer service");
		return sellerService.getAllOrders(sellerId);
	}
}
