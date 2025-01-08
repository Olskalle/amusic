package yap2.indi.amusic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yap2.indi.amusic.models.Genre;
import yap2.indi.amusic.repositories.GenreRepository;

@Service
public class GenreService extends GenericServiceImpl<Genre> {

    @Autowired
    public GenreService(GenreRepository repo) {
        super(repo);
    }    
}