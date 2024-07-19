package model;

public class JourneyInfo {
    private String codPercorso;
    private String stazionePartenzaNome;
    private String stazioneArrivoNome;
    private String orarioPartenzaPrevisto;
    private int binario;
    private String statoArrivo;
    
    public JourneyInfo(String codPercorso, String stazionePartenzaNome, String stazioneArrivoNome, String orarioPartenzaPrevisto, int binario, String statoArrivo) {
        this.codPercorso = codPercorso;
        this.stazionePartenzaNome = stazionePartenzaNome;
        this.stazioneArrivoNome = stazioneArrivoNome;
        this.orarioPartenzaPrevisto = orarioPartenzaPrevisto;
        this.binario = binario;
        this.statoArrivo = statoArrivo;
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

    public String getStazioneArrivoNome() {
        return stazioneArrivoNome;
    }

    public void setStazioneArrivoNome(String stazioneArrivoNome) {
        this.stazioneArrivoNome = stazioneArrivoNome;
    }

    public String getOrarioPartenzaPrevisto() {
        return orarioPartenzaPrevisto;
    }

    public void setOrarioPartenzaPrevisto(String orarioPartenzaPrevisto) {
        this.orarioPartenzaPrevisto = orarioPartenzaPrevisto;
    }

    public int getBinario() {
        return binario;
    }

    public void setBinario(int binario) {
        this.binario = binario;
    }

    public String getStatoArrivo() {
        return statoArrivo;
    }

    public void setStatoArrivo(String statoArrivo) {
        this.statoArrivo = statoArrivo;
    }

    @Override
    public String toString() {
        return "JourneyInfo[codPercorso = " + codPercorso + ", stazionePartenzaNome = " + stazionePartenzaNome + 
                ", stazioneArrivoNome = " + stazioneArrivoNome + ", orarioPartenzaPrevisto = " + orarioPartenzaPrevisto + 
                ", binario = " + binario + ", statoArrivo = " + statoArrivo + "]";
    }
}

