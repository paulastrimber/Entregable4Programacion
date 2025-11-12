package com.example.playlist.service;

import com.example.playlist.model.Video;
import com.example.playlist.repository.PlaylistRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistServiceTest {

    private PlaylistRepository repo;
    private PlaylistService service;

    @BeforeEach
    void setup() {
        File testFile = new File("playlist_test.json");
        repo = new PlaylistRepository(testFile);
        service = new PlaylistService(repo);

        if (testFile.exists()) testFile.delete(); // limpia solo el de test
    }

    @AfterEach
    void cleanup() {
        File testFile = new File("playlist_test.json");
        if (testFile.exists()) testFile.delete();
    }

    @Test
    void agregarVideo_deberiaAgregarYGuardar() {
        Video video = new Video("Video 1", "url1");
        service.agregarVideo(video);

        assertEquals(1, service.obtenerVideos().size());
    }

    @Test
    void eliminarVideo_deberiaQuitarloDeLaLista() {
        Video video = new Video("Video 1", "url1");
        service.agregarVideo(video);
        service.eliminarVideo("Video 1");

        assertTrue(service.obtenerVideos().isEmpty());
    }

    @Test
    void like_deberiaIncrementarLikes() {
        Video video = new Video("Video 1", "url1");
        service.agregarVideo(video);
        service.like("Video 1");

        assertEquals(1, service.obtenerVideos().get(0).getLikes());
    }

    @Test
    void toggleFavorito_deberiaAlternarElEstado() {
        Video video = new Video("Video 1", "url1");
        service.agregarVideo(video);
        service.toggleFavorito("Video 1");

        assertTrue(service.obtenerVideos().get(0).isFavorito());

        service.toggleFavorito("Video 1");
        assertFalse(service.obtenerVideos().get(0).isFavorito());
    }

    @Test
    void obtenerFavoritos_deberiaFiltrarSoloLosFavoritos() {
        Video v1 = new Video("A", "url1");
        Video v2 = new Video("B", "url2");
        v2.setFavorito(true);

        service.agregarVideo(v1);
        service.agregarVideo(v2);

        List<Video> favoritos = service.obtenerFavoritos();
        assertEquals(1, favoritos.size());
        assertEquals("B", favoritos.get(0).getTitulo());
    }
}
