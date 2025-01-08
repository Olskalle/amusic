package yap2.indi.amusic.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import yap2.indi.amusic.models.Album;
import yap2.indi.amusic.models.Artist;
import yap2.indi.amusic.models.Genre;
import yap2.indi.amusic.services.AlbumService;
import yap2.indi.amusic.services.ArtistService;
import yap2.indi.amusic.services.GenreService;

@Controller
public class AlbumController {
    private final AlbumService albumsService;
    private final GenreService genreService;
    private final ArtistService artistService;

    @Autowired
    public AlbumController(AlbumService albumsService, GenreService genreService, ArtistService artistService) {
        this.albumsService = albumsService;
        this.genreService = genreService;
        this.artistService = artistService;
    }

    @GetMapping("/albums")
    public String getAlbums(Model model) {
        var info = albumsService.getAlbumsInfo();
        model.addAttribute("info", info);

        return "albums";
    }

    @GetMapping("/albums/edit")
    public String getEditView(@RequestParam Long id, Model model) {

        Optional<Album> optional = albumsService.findById(id);
        if (!optional.isPresent()) {
            return "redirect:/error";
        }

        var album = optional.get();
        return getEditOrAddView(album, model, "edit");
    }

    @PostMapping("/albums/edit")
    public String editAlbum(@RequestParam Long id, @ModelAttribute Album album) {
        albumsService.update(album);
        return "redirect:/albums";
    }

    @GetMapping("/albums/add")
    public String getAddView(Model model) {

        var album = new Album();

        return getEditOrAddView(album, model, "add");
    }

    @PostMapping("/albums/add")
    public String addAlbum(@ModelAttribute Album album) {
        albumsService.save(album);
        return "redirect:/albums";
    }

    @PostMapping("/albums/delete")
    public String deleteAlbum(@RequestParam Long id) {
        albumsService.delete(id);
        return "redirect:/albums";
    }

    private String getEditOrAddView(Album album, Model model, String mode) {
    
        model.addAttribute("album", album);
        
        List<Artist> artists = artistService.getAllArtists();
        model.addAttribute("artists", artists);

        List<Genre> genres = genreService.findAll();
        model.addAttribute("genres", genres);

        model.addAttribute("mode", mode);
        return "album-edit";
    }
}
