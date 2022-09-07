using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using MySql.Data.MySqlClient;

namespace API_Mimuseum.Models
{
    public class DatabaseHelper
    {
        static MySqlConnection conexao = new MySqlConnection("server=localhost" +
                                            ";Database=mimuseum" +
                                            ";User ID=root" +
                                            ";Password=Negocios1.;");
        MySqlCommand command = new MySqlCommand(null, conexao);
        public void OpenConnec()
        {
            conexao.Open();
        }
        public Arte AssignArt(MySqlDataReader reader)
        {
            Arte tempArte = new Arte();
            if (reader.Read())
            {
                tempArte.IDAarte = int.Parse(reader["IDArte"].ToString());
                tempArte.NomeArte = reader["NomeArte"].ToString();
                tempArte.NomeArtista = reader["NomeArtista"].ToString();
                tempArte.AnoArte = int.Parse(reader["AnoArte"].ToString());
                tempArte.EstiloArte = reader["EstiloArte"].ToString();
                tempArte.UrlArte = reader["UrlArte"].ToString();
            }
            return tempArte;
        }
        public List<Arte> AssignArts(MySqlDataReader reader)
        {
            List<Arte> artList = new List<Arte>();
            while (reader.Read())
            {
                Arte tempArte = new Arte();
                tempArte.IDAarte = int.Parse(reader["IDArte"].ToString());
                tempArte.NomeArte = reader["NomeArte"].ToString();
                tempArte.NomeArtista = reader["NomeArtista"].ToString();
                tempArte.AnoArte = int.Parse(reader["AnoArte"].ToString());
                tempArte.EstiloArte = reader["EstiloArte"].ToString();
                tempArte.UrlArte = reader["UrlArte"].ToString();
                artList.Add(tempArte);
            }
            return artList;
        }
        public List<Arte> GetAllArts()
        {
            command.CommandText = ("select * from tbArt");
            var reader = command.ExecuteReader();
            List<Arte> arts = this.AssignArts(reader);
            return arts;
        }
        public Arte GetArtById(int id)
        {
            command.CommandText = ("select * from tbArt where IDArte = @id");
            command.Parameters.Add("@id",MySqlDbType.Int64).Value=id;
            var reader = command.ExecuteReader();
            Arte res = this.AssignArt(reader);
            return res;
        }
        public IEnumerable<Arte> GetArtsByParameter(string column, string parVal)
        {
            string query = "select * from tbArt where placeholder like 'parameter%'";
            query = query.Replace("parameter", parVal);
            query = query.Replace("placeholder", column);
            command.CommandText = query;
            var reader = command.ExecuteReader();
            IEnumerable<Arte> res = this.AssignArts(reader);
            return res;
        }
        public void PostNewArt(Arte art)
        {
            string query =
                "insert into tbArt (NomeArte,NomeArtista,AnoArte,EstiloArte,UrlArte) values ('name', 'artist', year, 'style', 'url');";
            query = query.Replace("name", art.NomeArte);
            query = query.Replace("artist", art.NomeArtista);
            query = query.Replace("year", art.AnoArte.ToString());
            query = query.Replace("style", art.EstiloArte);
            query = query.Replace("url", art.UrlArte);
            command.CommandText = query;
            var executor = command.ExecuteNonQuery();
        }
        public void AlterArt(int id, Arte art)
        {
            string query = 
                "update tbArt set NomeArte = 'name', NomeArtista = 'artist', AnoArte = year, EstiloArte = 'style', " +
                "UrlArte = 'url' where IDArte = id ;";
            query = query.Replace("id", id.ToString());
            query = query.Replace("name", art.NomeArte);
            query = query.Replace("artist", art.NomeArtista);
            query = query.Replace("year", art.AnoArte.ToString());
            query = query.Replace("style", art.EstiloArte);
            query = query.Replace("url", art.UrlArte);
            command.CommandText = query;
            var executor = command.ExecuteNonQuery();
        }
        public void DeleteArt(int id)
        {
            string query = "delete from tbArt where IDArte = id;";
            query = query.Replace("id", id.ToString());
            command.CommandText = query;
            var executor = command.ExecuteNonQuery();
        }
        public User GetUserByID(int id)
        {
            User tempUser = new User();
            string query = "select * from tbUser where IDUser = id limit 1";
            query = query.Replace("id", id.ToString());
            command.CommandText = query;
            var reader = command.ExecuteReader();
            if(reader.Read())
            {
                tempUser.IDUser = id;
                tempUser.Username = reader["Username"].ToString();
                tempUser.PassUser = reader["PassUser"].ToString();
            }
            return tempUser;
        }
        public void InsertNewUser(User user)
        {
            string query = "insert into tbUser (Username, PassUser) values ('user', 'pass')";
            query = query.Replace("user", user.Username);
            query = query.Replace("pass", user.PassUser);
            command.CommandText = query;
            var executor = command.ExecuteNonQuery();
        }
        public void CloseConnec()
        {
            conexao.Close();
        }
    }
}