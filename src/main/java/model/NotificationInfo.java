package model;

public class NotificationInfo {
    private String codNotifica;
    private String descrizione;
    private String codPercorso;
    private String email;


    public NotificationInfo(String codNotifica, String descrizione, String codPercorso, String email) {
        this.codNotifica = codNotifica;
        this.descrizione = descrizione;
        this.codPercorso = codPercorso;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getcodPercorso() {
        return codPercorso;
    }

    public void setcodPercorso(String codPercorso) {
        this.codPercorso = codPercorso;
    }

    public String getdescrizione() {
        return descrizione;
    }

    public void setdescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getcodNotifica() {
        return codNotifica;
    }

    public void setcodNotifica(String codNotifica) {
        this.codNotifica = codNotifica;
    }

    @Override
    public String toString() {
        return "NotificationInfo [codNotifica = " + codNotifica + ", descrizione = " + descrizione +
                "codPercorso = " + codPercorso + ", email = " + email + "]";
    }

}
