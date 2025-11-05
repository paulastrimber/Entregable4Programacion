package com.example.playlist.model;

public class Video {
    private String titulo;
    private String url;
    private int likes;
    private boolean favorito;

    public Video() {}

    public Video(String titulo, String url) {
        this.titulo = titulo;
        this.url = url;
        this.likes = 0;
        this.favorito = false;
    }

    // Getters y setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }

    public boolean isFavorito() { return favorito; }
    public void setFavorito(boolean favorito) { this.favorito = favorito; }
}
