package com.example.playlist.service;

import com.example.playlist.model.Video;
import com.example.playlist.repository.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository repo;
    private List<Video> videos;

    public PlaylistService(PlaylistRepository repo) {
        this.repo = repo;
        this.videos = repo.cargar();
    }

    public List<Video> obtenerVideos() {
        return videos;
    }

    public void agregarVideo(Video video) {
        if (video.getUrl().contains("watch?v=")) {
            video.setUrl(video.getUrl().replace("watch?v=", "embed/"));
        }
        videos.add(video);
        repo.guardar(videos);
    }

    public void eliminarVideo(String titulo) {
        videos.removeIf(v -> v.getTitulo().equalsIgnoreCase(titulo));
        repo.guardar(videos);
    }

    public void like(String titulo) {
        for (Video v : videos) {
            if (v.getTitulo().equalsIgnoreCase(titulo)) {
                v.setLikes(v.getLikes() + 1);
            }
        }
        repo.guardar(videos);
    }

    public void toggleFavorito(String titulo) {
        for (Video v : videos) {
            if (v.getTitulo().equalsIgnoreCase(titulo)) {
                v.setFavorito(!v.isFavorito());
            }
        }
        repo.guardar(videos);
    }
}