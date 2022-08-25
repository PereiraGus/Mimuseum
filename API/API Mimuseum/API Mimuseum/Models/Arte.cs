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

        MySqlConnection conexao = new MySqlConnection(ConfigurationManager.ConnectionStrings["conexao"].ConnectionString);
        MySqlCommand comand = new MySqlCommand();

        public String FetchArte()
        {
            conexao.Open();
            IEnumerable<Arte> arte = new List<Arte>();
            comand.CommandText = ("select * from tbArt");
            string res = comand.ExecuteScalar().ToString();
            conexao.Close();
            return res;
        }

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
        }

    }
}