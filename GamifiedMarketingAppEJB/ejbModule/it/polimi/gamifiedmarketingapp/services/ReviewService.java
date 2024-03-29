package it.polimi.gamifiedmarketingapp.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import it.polimi.gamifiedmarketingapp.entities.Product;
import it.polimi.gamifiedmarketingapp.entities.RegisteredUser;
import it.polimi.gamifiedmarketingapp.entities.Review;
import it.polimi.gamifiedmarketingapp.exceptions.EntryNotFoundException;
import it.polimi.gamifiedmarketingapp.exceptions.FieldLengthException;

@Stateless
public class ReviewService {
	
	@PersistenceContext(unitName = "GamifiedMarketingAppEJB")
	private EntityManager em;
	
	public ReviewService() {}
	
	public List<Review> findLimitedNumberOfReviewsByProductId(Integer productId, Integer limit) {
		if (productId == null)
			throw new IllegalArgumentException("Product ID can't be null");
		if (limit != null && limit <= 0)
			throw new IllegalArgumentException("Limit can't be negative or equal to zero");
		Product product = em.find(Product.class, productId);
		if (product == null)
			throw new EntryNotFoundException("Product not found");
		TypedQuery<Review> query = em.createNamedQuery("Review.findReviewsByProductId", Review.class)
				.setParameter("productId", productId);    
		if (limit != null)
			query.setMaxResults(limit);
		List<Review> result = query.getResultList();
		if (result == null || result.size() == 0)
			return null;
		return result;
	}
	
	public void addReview(String title, String text, Integer registeredUserId, Integer productId) {
		if (registeredUserId == null)
			throw new IllegalArgumentException("Registered user ID can't be null");
		if (productId == null)
			throw new IllegalArgumentException("Product ID can't be null");
		if (title == null) 
			throw new IllegalArgumentException("Title can't be null");
		if (title.length() > Review.TITLE_LENGTH)
			throw new FieldLengthException("Title too long");
		if (text != null && text.length() > Review.TEXT_LENGTH)
			throw new FieldLengthException("Text too long");
		RegisteredUser registeredUser = em.find(RegisteredUser.class, registeredUserId);
		if (registeredUser == null)
			throw new EntryNotFoundException("Registered user not found");
		Product product = em.find(Product.class, productId);
		if (product == null)
			throw new EntryNotFoundException("Product not found");
		Review review = new Review(title, text, registeredUser, product);
		product.addReview(review);
		em.persist(review);
	}
	
	public void removeReview(Integer reviewId) {
		if (reviewId == null)
			throw new IllegalArgumentException("Review ID can't be null");
		Review review = em.find(Review.class, reviewId);
		if (review == null)
			throw new IllegalArgumentException("Review not found");
		Product product = review.getProduct();
		product.removeReview(review);
		em.remove(review);
	}

}
