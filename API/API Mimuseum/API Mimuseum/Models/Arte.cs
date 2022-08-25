using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using MySql.Data.MySqlClient;
using System.Configuration;

namespace API_Mimuseum.Models
{
    public class Arte
    {
        public int IDAarte { get; set; }   
        public string NomeArte { get; set; }    
        public string NomeArtista { get; set; } 
        public int AnoArte { get; set; }    
        public string EstiloArte { get; set; } 
        public string UrlArte { get; set; }

        public Arte() { }
        public Arte (int iDAarte, string nomeArte, string nomeArtista, int anoArte, string estiloArte, string urlArte)
        {
            IDAarte = iDAarte;
            NomeArte = nomeArte;
            NomeArtista = nomeArtista;
            AnoArte = anoArte;
            EstiloArte = estiloArte;
            UrlArte = urlArte;
        }

        public List<Arte> FetchArte()
        {
            reader = comand.ExecuteReader();
            List<Arte> arte = new List<Arte>();
            if (reader.HasRows)
            {
                while (reader.Read())
                {
                    arte.Add(new Arte(int.Parse(reader["IDArte"].ToString()), reader["NomeArte"].ToString(), reader["NomeArtista"].ToString(),
                        int.Parse(reader["AnoArte"].ToString()), reader["EstiloArte"].ToString(), reader["UrlArte"].ToString()));
                }
            }
            conexao.Close();
            return arte;
        }

        /*
        public void InsertArte(Arte arte)
        {
            conexao.Open();
            comand.CommandText = "call InsertUsuario(@UsuNome, @Login, @Senha);";
            comand.Parameters.Add("@NomeArte", MySqlDbType.VarChar).Value = arte.NomeArte;
            comand.Parameters.Add("@NomeArtista", MySqlDbType.VarChar).Value = arte.NomeArtista;
            comand.Parameters.Add("@AnoArte", MySqlDbType.Int64).Value = arte.AnoArte;
            comand.Parameters.Add("@EstiloArte", MySqlDbType.VarChar).Value = arte.EstiloArte;
            comand.Parameters.Add("@UrlArte", MySqlDbType.VarChar).Value = arte.UrlArte;
            comand.Connection = conexao;
            comand.ExecuteNonQuery();
            conexao.Close();
        }*/
    }
}