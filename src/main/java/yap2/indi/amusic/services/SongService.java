package yap2.indi.amusic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yap2.indi.amusic.models.Song;
import yap2.indi.amusic.repositories.SongRepository;

@Service
public class SongService extends GenericServiceImpl<Song> {

    @Autowired
    public SongService(SongRepository repo) {
        super(repo);
    }    
}