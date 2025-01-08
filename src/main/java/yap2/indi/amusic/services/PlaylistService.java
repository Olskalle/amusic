package yap2.indi.amusic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yap2.indi.amusic.models.Playlist;
import yap2.indi.amusic.repositories.PlaylistRepository;

@Service
public class PlaylistService extends GenericServiceImpl<Playlist> {

    @Autowired
    public PlaylistService(PlaylistRepository repo) {
        super(repo);
    }    
}