package yap2.indi.amusic.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import yap2.indi.amusic.models.Playlist;
import yap2.indi.amusic.models.Song;
import yap2.indi.amusic.models.User;
import yap2.indi.amusic.models.dtos.PlaylistDto;
import yap2.indi.amusic.services.PlaylistService;
import yap2.indi.amusic.services.SongService;

@Controller
public class PlaylistController {

    private final PlaylistService playlistService;
    private final SongService songService;

    @Autowired
    public PlaylistController(PlaylistService playlistService, SongService songService) {
        this.playlistService = playlistService;
        this.songService = songService;
    }

    @GetMapping("/playlists")
    public String getPlaylists(Model model) {

        var playlists = playlistService.findAll();

        var dtos = playlists.stream()
                .map(playlist -> {
                    Long totalDuration = playlist.getSongs().stream()
                            .mapToLong(Song::getDuration)
                            .sum();

                    return new PlaylistDto(
                            playlist.getId(),
                            playlist.getName(),
                            playlist.getDescription(),
                            totalDuration);
                })
                .collect(Collectors.toList());

        model.addAttribute("playlists", dtos);

        return "playlists";
    }

    @GetMapping("/playlists/edit")
    public String getEditPlaylistView(@RequestParam Long id, Model model) {

        var playlistOption = playlistService.findById(id);
        if (!playlistOption.isPresent())
            throw new IllegalArgumentException("Нет такого плейлиста");

        var playlist = playlistOption.get();
        model.addAttribute("playlist", playlist);

        List<Long> playlistSongIds = playlist.getSongs().stream()
                .map(Song::getId)
                .collect(Collectors.toList());

        model.addAttribute("selectedSongs", playlistSongIds);

        model.addAttribute("mode", "edit");
        var songs = songService.findAll();
        model.addAttribute("songOptions", songs);

        return "playlist-edit";
    }

    @GetMapping("/playlists/add")
    public String getAddPlaylistView(Model model) {

        var playlist = new Playlist();
        model.addAttribute("playlist", playlist);

        model.addAttribute("selectedSongs", List.of());

        model.addAttribute("mode", "add");
        var songs = songService.findAll();
        model.addAttribute("songOptions", songs);

        return "playlist-edit";
    }

     @PostMapping("/playlists/edit")
    public String editplaylist(@RequestParam Long id, @ModelAttribute Playlist playlist) {
        setStubUser(playlist);
        playlistService.update(playlist);
        return "redirect:/playlists";
    }

    @PostMapping("/playlists/add")
    public String addplaylist(@ModelAttribute Playlist playlist) {
        setStubUser(playlist);
        playlistService.save(playlist);
        return "redirect:/playlists";
    }
    
    @PostMapping("/playlists/delete")
    public String deletePlaylist(@RequestParam Long id) {
        playlistService.delete(id);
        
        return "redirect:/playlists";
    }

    private void setStubUser(Playlist playlist) {
        var user = new User();
        user.setId(1l);
        playlist.setUser(user);
    }
}
