package model;

public class Train {
    private String codTreno;
    private int postiTotali;
    private String tipo;
    private String classe;

    public String getCodTreno() {
        return codTreno;
    }

    public void setCodTreno(String codTreno) {
        this.codTreno = codTreno;
    }

    public int getPostiTotali() {
        return postiTotali;
    }

    public void setPostiTotali(int postiTotali) {
        this.postiTotali = postiTotali;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }
}
