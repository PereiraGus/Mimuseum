using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using API_Mimuseum.Models;
using MySql.Data.MySqlClient;
using System.Configuration;

namespace API_Mimuseum.Controllers
{
    public class ArteController : ApiController
    {
        [HttpGet]
        [ActionName("getAll")]
        public IEnumerable<Arte> GetAllWorksOfArt()
        {
            try
            {
                DatabaseHelper db = new DatabaseHelper();
                var res = db.FetchArte();
                db.CloseConnec();
                Arte arte = new Arte();
                return res;
            }
            catch(Exception ex)
            {
                throw new HttpResponseException(HttpStatusCode.Unauthorized); 
            }
        }
        /*
        public IEnumerable<Arte> GetArtsByName(string name)
        {
            var res = artes.Where((p) => p.NomeArte.Contains(name));
            return res;
        }*/
    }
}
