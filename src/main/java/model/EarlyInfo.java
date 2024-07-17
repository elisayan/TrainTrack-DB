package model;

public class EarlyInfo {
    private final String codPercorso;
    private final String stazionePartenzaNome;
    private final String stazioneDestinazioneNome;
    private final float mediaMinutiAnticipo;
    private int rank;

    public EarlyInfo(String codPercorso, String stazionePartenzaNome, String stazioneDestinazioneNome,
            float mediaMinutiAnticipo) {
        this.codPercorso = codPercorso;
        this.stazionePartenzaNome = stazionePartenzaNome;
        this.stazioneDestinazioneNome = stazioneDestinazioneNome;
        this.mediaMinutiAnticipo = mediaMinutiAnticipo;
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
