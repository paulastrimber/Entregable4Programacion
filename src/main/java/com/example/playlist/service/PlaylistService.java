package com.example.playlist.service;

import com.example.playlist.model.Video;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PlaylistService {

    private List<Video> videos = new ArrayList<>();

    public List<Video> obtenerVideos() {
        return videos;
    }

    public void agregarVideo(Video video) {
        String url = video.getUrl();
        if (url.contains("watch?v=")) {
            url = url.replace("watch?v=", "embed/");
        }
        video.setUrl(url);
        videos.add(video);
    }


    public void eliminarVideo(String titulo) {
        videos.removeIf(v -> v.getTitulo().equalsIgnoreCase(titulo));
    }

    public void like(String titulo) {
        for (Video v : videos) {
            if (v.getTitulo().equalsIgnoreCase(titulo)) {
                v.setLikes(v.getLikes() + 1);
            }
        }
    }

    public void toggleFavorito(String titulo) {
        for (Video v : videos) {
            if (v.getTitulo().equalsIgnoreCase(titulo)) {
                v.setFavorito(!v.isFavorito());
            }
        }
    }
}
