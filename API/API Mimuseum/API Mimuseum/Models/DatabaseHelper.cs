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
        public static MySqlCommand CreateComm()
        {
            MySqlCommand command = new MySqlCommand(null, conexao);
            return command;
        }
        public void OpenConnec()
        {
            conexao.Open();
        }
        public void CloseConnec()
        {
            conexao.Close();
        }
    }
}