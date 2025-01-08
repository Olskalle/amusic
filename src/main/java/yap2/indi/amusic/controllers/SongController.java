package yap2.indi.amusic.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import yap2.indi.amusic.models.Album;
import yap2.indi.amusic.models.Artist;
import yap2.indi.amusic.models.Song;
import yap2.indi.amusic.services.AlbumService;
import yap2.indi.amusic.services.ArtistService;
import yap2.indi.amusic.services.SongService;

@Controller
public class SongController {
    private final SongService songsService;
    private final ArtistService artistService;
    private final AlbumService albumService;

    @Autowired
    public SongController(SongService songsService, ArtistService artistService, AlbumService albumService) {
        this.songsService = songsService;
        this.artistService = artistService;
        this.albumService = albumService;
    }

    @GetMapping("/songs")
    public String getSongs(Model model) {
        var songs = songsService.findAll();
        model.addAttribute("songs", songs);

        return "songs";
    }

    @GetMapping("/songs/edit")
    public String getEditView(@RequestParam Long id, Model model) {

        Optional<Song> optional = songsService.findById(id);
        if (!optional.isPresent()) {
            return "redirect:/error";
        }

        var song = optional.get();
        return getEditOrAddView(song, model, "edit");
    }

    @PostMapping("/songs/edit")
    public String editsong(@RequestParam Long id, @ModelAttribute Song song, BindingResult bindingResult, Model model) {

        if (song.getArtist() == null) {

            bindingResult.rejectValue("artist", "artist.empty", "Исполнитель не может быть пустым");
            model.addAttribute("errorMessage", "Исполнитель не может быть пустым");

            return "song-edit";
        }
        
        if (song.getAlbum() == null) {

            bindingResult.rejectValue("album", "album.empty", "Альбом не может быть пустым");
            model.addAttribute("errorMessage", "Альбом не может быть пустым");

            return "song-edit";
        }

        song.setReleaseYear(song.getAlbum().getReleaseYear());

        songsService.update(song);

        return "redirect:/songs";
    }

    @GetMapping("/songs/add")
    public String getAddView(Model model) {

        var song = new Song();

        return getEditOrAddView(song, model, "add");
    }

    @PostMapping("/songs/add")
    public String addsong(@ModelAttribute Song song) {

        song.setReleaseYear(song.getAlbum().getReleaseYear());
        songsService.save(song);
        return "redirect:/songs";
    }

    @PostMapping("/songs/delete")
    public String deletesong(@RequestParam Long id) {
        songsService.delete(id);
        return "redirect:/songs";
    }

    private String getEditOrAddView(Song song, Model model, String mode) {

        model.addAttribute("song", song);

        List<Artist> artists = artistService.getAllArtists();
        model.addAttribute("artists", artists);

        List<Album> albums = albumService.findAll();
        model.addAttribute("albums", albums);

        model.addAttribute("mode", mode);
        return "song-edit";
    }
}
