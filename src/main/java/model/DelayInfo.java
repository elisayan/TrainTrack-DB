package model;

public class DelayInfo {
    private String codPercorso;
    private String stazionePartenzaNome;
    private String stazioneDestinazioneNome;
    private float mediaMinutiRitardo;
    private int rank;

    public DelayInfo(String codPercorso, String stazionePartenzaNome, String stazioneDestinazioneNome,
            float mediaMinutiRitardo) {
        this.codPercorso = codPercorso;
        this.stazionePartenzaNome = stazionePartenzaNome;
        this.stazioneDestinazioneNome = stazioneDestinazioneNome;
        this.mediaMinutiRitardo = mediaMinutiRitardo;
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

    public float getMediaMinutiRitardo() {
        return mediaMinutiRitardo;
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

    @Override
    public String toString() {
        return "DelayInfo [codPercorso=" + codPercorso + ", stazionePartenzaNome=" + stazionePartenzaNome
                + ", stazioneDestinazioneNome=" + stazioneDestinazioneNome + ", mediaMinutiRitardo="
                + mediaMinutiRitardo + ", rank=" + rank + "]";
    }
}
