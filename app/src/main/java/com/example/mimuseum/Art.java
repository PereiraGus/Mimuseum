package com.example.mimuseum;

public class Art {
    public int IDArte;
    public String NomeArte;
    public String NomeArtista;
    public int AnoArte;
    public String EstiloArte;
    public String UrlArte;

    public Art(){};

    public  Art(int idArte){
        this.IDArte = idArte;
    }

    public Art(int idArte,String nomeArte,String urlArte){
        this.IDArte = idArte;
        this.NomeArte = nomeArte;
        this.UrlArte = urlArte;
    };

    public Art(int iDarte,String nomeArte,String nomeArtista,int anoArte,String estiloArte,String urlArte){
        this.IDArte = iDarte;
        this.NomeArte = nomeArte;
        this.NomeArtista = nomeArtista;
        this.AnoArte = anoArte;
        this.EstiloArte = estiloArte;
        this.UrlArte = urlArte;
    }

}
