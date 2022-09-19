using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using MySql.Data.MySqlClient;
using System.Configuration;
using API_Mimuseum.Models;

namespace API_Mimuseum.Models
{
    public class Arte
    {
        public Arte() { }
        public Arte (int iDAarte, string nomeArte, string nomeArtista, int anoArte, string estiloArte, string urlArte)
        {
            IDArte = iDAarte;
            NomeArte = nomeArte;
            NomeArtista = nomeArtista;
            AnoArte = anoArte;
            EstiloArte = estiloArte;
            UrlArte = urlArte;
        }
        public int IDArte { get; set; }
        public string NomeArte { get; set; }
        public string NomeArtista { get; set; }
        public int AnoArte { get; set; }
        public string EstiloArte { get; set; }
        public string UrlArte { get; set; }
    }
}