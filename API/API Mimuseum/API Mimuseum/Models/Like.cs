using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using MySql.Data.MySqlClient;

namespace API_Mimuseum.Models
{
    public class Like
    {
        public Like() { }
        public Like (int idUser, int idArt)
        {
            this.IdUser = idUser;
            this.IdArt = idArt;
        }

        public int IdUser { get; set; }
        public int IdArt { get; set; }

        MySqlCommand command = DatabaseHelper.CreateComm();

        public bool likeUnlike(Like like)
        {
            string query = "select * from tblike where (IDUser = 'user' AND IDArte = 'art')";
            query = query.Replace("user", (like.IdUser).ToString());
            query = query.Replace("art", (like.IdArt).ToString());
            command.CommandText = query;
            var reader = command.ExecuteReader();
            if (reader.Read())
            {
                query = "delete from tblike where (IDUser = user AND IDArte = art)";
                query = query.Replace("user", (like.IdUser).ToString());
                query = query.Replace("art", (like.IdArt).ToString());
                command.CommandText = query;
                var executor = command.ExecuteNonQuery();
                return false;
            }
            else
            {
                query = "insert into tblike values (user,art)";
                query = query.Replace("user", (like.IdUser).ToString());
                query = query.Replace("art", (like.IdArt).ToString());
                command.CommandText = query;
                var executor = command.ExecuteNonQuery();
                return true;
            }
        }
        
        public List<Like> seeLikesByUserID(int id)
        {
            string query = "select * from tblike where (IDUser = user)";
            query = query.Replace("user", (id).ToString());
            command.CommandText = query;
            var reader = command.ExecuteReader();
            List<Like> likeList = new List<Like>();
            while (reader.Read())
            {
                Like tempLike = new Like();
                tempLike.IdUser = int.Parse(reader["IDUser"].ToString());
                tempLike.IdArt = int.Parse(reader["IDArte"].ToString());
                likeList.Add(tempLike);
            }
            return likeList;
        }
    }
}