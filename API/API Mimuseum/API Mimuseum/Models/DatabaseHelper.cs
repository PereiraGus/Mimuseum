using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using MySql.Data.MySqlClient;

namespace API_Mimuseum.Models
{
    public class DatabaseHelper
    {
        MySqlConnection conexao = new MySqlConnection("server=localhost" +
                                            ";Database=mimuseum" +
                                            ";User ID=root" +
                                            ";Password=12345678;");
        public DatabaseHelper()
        {
            conexao.Open();
        }
        public List<Arte> FetchArte()
        {
            MySqlCommand command = new MySqlCommand("select * from tbArt", conexao);
            MySqlDataReader reader;
            reader = command.ExecuteReader();
            List<Arte> arte = new List<Arte>();
            if (reader.HasRows)
            {
                while (reader.Read())
                {
                    arte.Add(new Arte(int.Parse(reader["IDArte"].ToString()), reader["NomeArte"].ToString(), reader["NomeArtista"].ToString(),
                        int.Parse(reader["AnoArte"].ToString()), reader["EstiloArte"].ToString(), reader["UrlArte"].ToString()));
                }
            }
            return arte;
        }
        public Arte GetArtById(int id)
        {
            MySqlCommand command = new MySqlCommand("select * from tbArt where IDArte = @id",conexao);
            command.Parameters.Add("@id",MySqlDbType.Int64).Value=id;
            var reader = command.ExecuteReader();
            Arte TempArte = new Arte();
            if (reader.Read())
            {
                TempArte.IDAarte = int.Parse(reader["IDArte"].ToString());
                TempArte.NomeArte = reader["NomeArte"].ToString();
                TempArte.NomeArtista = reader["NomeArtista"].ToString();
                TempArte.AnoArte = int.Parse(reader["AnoArte"].ToString());
                TempArte.EstiloArte = reader["EstiloArte"].ToString();
                TempArte.UrlArte = reader["UrlArte"].ToString();
            }
            return TempArte;
        }
        public void CloseConnec()
        {
            conexao.Close();
        }
    }
}