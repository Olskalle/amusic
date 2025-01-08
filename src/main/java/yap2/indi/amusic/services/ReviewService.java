package yap2.indi.amusic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yap2.indi.amusic.models.Review;
import yap2.indi.amusic.repositories.ReviewRepository;

@Service
public class ReviewService extends GenericServiceImpl<Review> {

    @Autowired
    public ReviewService(ReviewRepository repo) {
        super(repo);
    }    
}