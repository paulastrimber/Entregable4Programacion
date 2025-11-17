package com.example.playlist.service;

import com.example.playlist.model.Video;
import com.example.playlist.repository.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final List<Video> videos;

    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
        this.videos = new ArrayList<>(playlistRepository.cargar());
    }


    public List<Video> obtenerVideos() {
        return videos;
    }

    public void agregarVideo(Video video) {
        if (video.getUrl().contains("watch?v=")) {
            video.setUrl(video.getUrl().replace("watch?v=", "embed/"));
        }
        videos.add(video);
        playlistRepository.guardar(videos);
    }

    public void eliminarVideo(String titulo) {
        videos.removeIf(v -> v.getTitulo().equalsIgnoreCase(titulo));
        playlistRepository.guardar(videos);
    }

    public void like(String titulo) { 
        for (Video v : videos) { 
            if (v.getTitulo().equalsIgnoreCase(titulo)) 
            { v.setLikes(v.getLikes() + 1); } 
        } 
        playlistRepository.guardar(videos); }

    public void toggleFavorito(String titulo) { 
        for (Video v : videos) { 
            if (v.getTitulo().equalsIgnoreCase(titulo)) { 
                v.setFavorito(!v.isFavorito()); 
            } 
        } 
        playlistRepository.guardar(videos); }

    public List<Video> obtenerFavoritos() {
        List<Video> favoritos = new ArrayList<>();
        for (Video v : videos) {
            if (v.isFavorito()) {
                favoritos.add(v);
            }
        }
        return favoritos;
    }
}