package model;

public class EarlyInfo {
    private String codPercorso;
    private String stazionePartenzaNome;
    private String stazioneDestinazioneNome;
    private float mediaMinutiAnticipo;
    private int rank;

    public EarlyInfo(String codPercorso, String stazionePartenzaNome, String stazioneDestinazioneNome,
            float mediaMinutiAnticipo) {
        this.codPercorso = codPercorso;
        this.stazionePartenzaNome = stazionePartenzaNome;
        this.stazioneDestinazioneNome = stazioneDestinazioneNome;
        this.mediaMinutiAnticipo = mediaMinutiAnticipo;
    }

    public String getCodPercorso() {
        return codPercorso;
    }

    public void setCodPercorso(String codPercorso) {
        this.codPercorso = codPercorso;
    }

    public String getStazionePartenzaNome() {
        return stazionePartenzaNome;
    }

    public void setStazionePartenzaNome(String stazionePartenzaNome) {
        this.stazionePartenzaNome = stazionePartenzaNome;
    }

    public String getStazioneDestinazioneNome() {
        return stazioneDestinazioneNome;
    }

    public void setStazioneDestinazioneNome(String stazioneDestinazioneNome) {
        this.stazioneDestinazioneNome = stazioneDestinazioneNome;
    }

    public float getMediaMinutiAnticipo() {
        return mediaMinutiAnticipo;
    }

    public void setMediaMinutiAnticipo(float mediaMinutiAnticipo) {
        this.mediaMinutiAnticipo = mediaMinutiAnticipo;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "EarlyInfo [codPercorso=" + codPercorso + ", stazionePartenzaNome=" + stazionePartenzaNome
                + ", stazioneDestinazioneNome=" + stazioneDestinazioneNome + ", mediaMinutiAnticipo="
                + mediaMinutiAnticipo + ", rank=" + rank + "]";
    }
}
