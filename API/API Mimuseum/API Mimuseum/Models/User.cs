using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

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
    }
}