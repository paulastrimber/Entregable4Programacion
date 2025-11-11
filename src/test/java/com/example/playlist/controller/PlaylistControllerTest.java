package com.example.playlist.controller;

import com.example.playlist.model.Video;
import com.example.playlist.repository.PlaylistRepository;
import com.example.playlist.service.PlaylistService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistControllerTest {

    private PlaylistController controller;
    private PlaylistService service;
    private File testFile;

    @BeforeEach
    void setup() {
        // 🧩 Usamos un archivo temporal solo para los tests
        testFile = new File("playlist_test.json");

        // 1️⃣ Borramos cualquier residuo previo antes de iniciar
        if (testFile.exists()) testFile.delete();

        // 2️⃣ Creamos un repositorio apuntando SOLO al archivo de test
        PlaylistRepository repo = new PlaylistRepository(testFile);

        // 3️⃣ Seguridad adicional: verificamos que no apunte al archivo real
        String ruta = repo.getRutaArchivo();
        System.out.println("📁 Usando archivo de test: " + ruta);
        assertTrue(ruta.endsWith("playlist_test.json"),
                "ERROR: el repositorio apunta al archivo real (playlist.json)");

        // 4️⃣ Creamos el service y el controller manualmente (sin contexto Spring)
        service = new PlaylistService(repo);
        controller = new PlaylistController(service);
    }

    @AfterEach
    void cleanup() {
        // 🧹 Limpiar el archivo temporal al final del test
        if (testFile.exists()) testFile.delete();
    }

    @Test
    void verPlaylist_deberiaCargarModeloYDevolverVista() {
        Model model = new ConcurrentModel();

        String vista = controller.verPlaylist(model);

        assertEquals("playlist", vista);
        assertTrue(model.containsAttribute("videos"));
        assertTrue(model.containsAttribute("nuevoVideo"));
    }

    @Test
    void agregarVideo_deberiaRedirigirYAgregarVideo() {
        Video video = new Video("Video Test", "https://youtube.com/embed/xyz");
        String resultado = controller.agregarVideo(video);

        assertEquals("redirect:/", resultado);
        assertEquals(1, service.obtenerVideos().size());
        assertEquals("Video Test", service.obtenerVideos().get(0).getTitulo());
    }

    @Test
    void eliminarVideo_deberiaRedirigirYEliminar() {
        Video video = new Video("A borrar", "url");
        service.agregarVideo(video);

        String resultado = controller.eliminarVideo("A borrar");

        assertEquals("redirect:/", resultado);
        assertTrue(service.obtenerVideos().isEmpty());
    }

    @Test
    void favorito_deberiaCambiarEstadoYRedirigir() {
        Video video = new Video("Favorito test", "url");
        service.agregarVideo(video);

        String resultado = controller.favorito("Favorito test");

        assertEquals("redirect:/", resultado);
        assertTrue(service.obtenerVideos().get(0).isFavorito());
    }

    @Test
    void verFavoritos_deberiaFiltrarSoloVideosFavoritos() {
        Video v1 = new Video("Normal", "url1");
        Video v2 = new Video("Favorito", "url2");
        v2.setFavorito(true);
        service.agregarVideo(v1);
        service.agregarVideo(v2);

        Model model = new ConcurrentModel();
        String vista = controller.verFavoritos(model);

        assertEquals("playlist", vista);

        @SuppressWarnings("unchecked")
        List<Video> videos = (List<Video>) model.getAttribute("videos");

        assertEquals(1, videos.size());
        assertEquals("Favorito", videos.get(0).getTitulo());
    }
}
