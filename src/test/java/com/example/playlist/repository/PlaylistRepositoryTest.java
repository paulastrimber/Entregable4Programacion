package com.example.playlist.repository;

import com.example.playlist.model.Video;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistRepositoryTest {

    private final PlaylistRepository repo = new PlaylistRepository();
    private final File archivo = new File("playlist.json");

    @AfterEach
    void limpiar() {
        if (archivo.exists()) archivo.delete();
    }

    @Test
    void guardarYCargar_deberiaPersistirCorrectamente() {
        Video v = new Video("Video test", "https://youtube.com/embed/test");
        repo.guardar(List.of(v));

        List<Video> cargados = repo.cargar();
        assertEquals(1, cargados.size());
        assertEquals("Video test", cargados.get(0).getTitulo());
        assertEquals("https://youtube.com/embed/test", cargados.get(0).getUrl());
    }

    @Test
    void cargarSinArchivo_deberiaRetornarListaVacia() {
        if (archivo.exists()) archivo.delete();
        List<Video> videos = repo.cargar();
        assertNotNull(videos);
        assertTrue(videos.isEmpty());
    }
}
