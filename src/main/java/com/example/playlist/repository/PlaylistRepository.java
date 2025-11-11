package com.example.playlist.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.playlist.model.Video;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PlaylistRepository {
    private final ObjectMapper mapper = new ObjectMapper();
    private File archivo;

    public PlaylistRepository(File archivo) {
        this.archivo = archivo;
    }

    public PlaylistRepository() {
        this(new File("playlist.json"));
    }

    public List<Video> cargar() {
        if (!archivo.exists()) return new ArrayList<>();
        try {
            return mapper.readValue(archivo, new TypeReference<List<Video>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void guardar(List<Video> videos) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(archivo, videos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRutaArchivo() {
        return archivo.getAbsolutePath();
    }

}
