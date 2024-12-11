package Model;



public class Livre {
    private int id;
    private String titre;
    private String auteur;
    private String genre;
    private boolean disponible; // true si disponible, false sinon

    // Constructeur par défaut
    public Livre() {}

    // Constructeur avec tous les attributs
    public Livre(int id, String titre, String auteur, String genre, boolean disponible) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.genre = genre;
        this.disponible = disponible;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Livre{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                ", genre='" + genre + '\'' +
                ", disponible=" + disponible +
                '}';
    }
}