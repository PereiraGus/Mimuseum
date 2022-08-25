using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using MySql.Data.MySqlClient;

namespace API_Mimuseum.Models
{
    public class DatabaseHelper
    {
        public void OpenConnec(string query)
        {
            MySqlConnection conexao = new MySqlConnection("server=localhost" +
                                            ";Database=mimuseum" +
                                            ";User ID=root" +
                                            ";Password=12345678;");
            conexao.Open();

            MySqlCommand comand = new MySqlCommand(query, conexao);
            MySqlDataReader reader;
        }
    }
}