package model;

public class DelayInfo {
    private String codPercorso;
    private String stazionePartenzaCodice;
    private String stazionePartenzaNome;
    private String stazioneDestinazioneCodice;
    private String stazioneDestinazioneNome;
    private float mediaMinutiRitardo;
    private int rank;

    public DelayInfo(String codPercorso, String stazionePartenzaCodice, String stazionePartenzaNome,
            String stazioneDestinazioneCodice, String stazioneDestinazioneNome, float mediaMinutiRitardo) {
        this.codPercorso = codPercorso;
        this.stazionePartenzaCodice = stazionePartenzaCodice;
        this.stazionePartenzaNome = stazionePartenzaNome;
        this.stazioneDestinazioneCodice = stazioneDestinazioneCodice;
        this.stazioneDestinazioneNome = stazioneDestinazioneNome;
        this.mediaMinutiRitardo = mediaMinutiRitardo;
    }

    public String getCodPercorso() {
        return codPercorso;
    }

    public String getStazionePartenzaCodice() {
        return stazionePartenzaCodice;
    }

    public String getStazionePartenzaNome() {
        return stazionePartenzaNome;
    }

    public String getStazioneDestinazioneCodice() {
        return stazioneDestinazioneCodice;
    }

    public String getStazioneDestinazioneNome() {
        return stazioneDestinazioneNome;
    }

    public float getMediaMinutiRitardo() {
        return mediaMinutiRitardo;
    }

    public void setCodPercorso(String codPercorso) {
        this.codPercorso = codPercorso;
    }

    public void setStazionePartenzaCodice(String stazionePartenzaCodice) {
        this.stazionePartenzaCodice = stazionePartenzaCodice;
    }

    public void setStazionePartenzaNome(String stazionePartenzaNome) {
        this.stazionePartenzaNome = stazionePartenzaNome;
    }

    public void setStazioneDestinazioneCodice(String stazioneDestinazioneCodice) {
        this.stazioneDestinazioneCodice = stazioneDestinazioneCodice;
    }

    public void setStazioneDestinazioneNome(String stazioneDestinazioneNome) {
        this.stazioneDestinazioneNome = stazioneDestinazioneNome;
    }

    public void setMediaMinutiRitardo(float mediaMinutiRitardo) {
        this.mediaMinutiRitardo = mediaMinutiRitardo;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
