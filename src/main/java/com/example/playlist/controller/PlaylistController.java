package com.example.playlist.controller;

import com.example.playlist.model.Video;
import com.example.playlist.service.PlaylistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PlaylistController {
    private final PlaylistService playlistService;
    

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("/")
    public String verPlaylist(Model model) {
        model.addAttribute("videos", playlistService.obtenerVideos());
        model.addAttribute("nuevoVideo", new Video());
        return "playlist";
    }

    @PostMapping("/agregar")
    public String agregarVideo(@ModelAttribute Video nuevoVideo) {
        playlistService.agregarVideo(nuevoVideo);
        return "redirect:/";
    }

    @GetMapping("/eliminar/{titulo}")
    public String eliminarVideo(@PathVariable String titulo) {
        playlistService.eliminarVideo(titulo);
        return "redirect:/";
    }

    @GetMapping("/like/{titulo}")
    public String like(@PathVariable String titulo) {
        playlistService.like(titulo);
        return "redirect:/";
    }

    @GetMapping("/favorito/{titulo}")
    public String favorito(@PathVariable String titulo) {
        playlistService.toggleFavorito(titulo);
        return "redirect:/";
    }

    @GetMapping("/favoritos")
    public String verFavoritos(Model model) {
        model.addAttribute("videos", playlistService.obtenerFavoritos());
        model.addAttribute("nuevoVideo", new Video());
        return "playlist";
    }

    
}
