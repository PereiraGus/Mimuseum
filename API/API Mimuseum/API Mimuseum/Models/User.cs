using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using MySql.Data.MySqlClient;

namespace API_Mimuseum.Models
{
    public class User
    {
        public User() { }
        public User(string username, string passUser)
        {
            this.Username = username;
            this.PassUser = passUser;
        }

        public User(int idUser, string username, string passUser)
        {
            this.IDUser = idUser;
            this.Username = username;
            this.PassUser = passUser;
        }

        public int IDUser { get; set; }
        public string Username { get; set; }
        public string PassUser { get; set; }

        MySqlCommand command = DatabaseHelper.CreateComm();

        public User GetUserByID(int id)
        {
            User tempUser = new User();
            string query = "select * from tbUser where IDUser = id limit 1";
            query = query.Replace("id", id.ToString());
            command.CommandText = query;
            var reader = command.ExecuteReader();
            if (reader.Read())
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
        public bool ValidadeUser(User user)
        {
            string query = "select * from tbUser where (Username = 'user' AND PassUser = 'pass')";
            query = query.Replace("user", user.Username);
            query = query.Replace("pass", user.PassUser);
            command.CommandText = query;
            var reader = command.ExecuteReader();
            if (reader.Read())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }
}