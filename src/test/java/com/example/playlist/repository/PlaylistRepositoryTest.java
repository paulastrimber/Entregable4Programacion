package com.example.playlist.repository;

import com.example.playlist.model.Video;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistRepositoryTest {

    private File archivoTest;
    private PlaylistRepository repo;

    @BeforeEach
    void setup() {
        archivoTest = new File("playlist_test.json");
        repo = new PlaylistRepository(archivoTest);
        if (archivoTest.exists()) archivoTest.delete(); // limpiar antes de cada test
    }

    @AfterEach
    void cleanup() {
        if (archivoTest.exists()) archivoTest.delete(); // eliminar archivo de test
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
        if (archivoTest.exists()) archivoTest.delete();

        List<Video> videos = repo.cargar();

        assertNotNull(videos);
        assertTrue(videos.isEmpty());
    }
}
