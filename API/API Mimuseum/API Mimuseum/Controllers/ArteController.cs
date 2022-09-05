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
        DatabaseHelper db = new DatabaseHelper();

        [HttpGet]
        [ActionName("getAll")]
        public IEnumerable<Arte> GetAllWorksOfArt()
        {
            try
            {
                db.OpenConnec();
                var res = db.GetAllArts();
                db.CloseConnec();
                Arte arte = new Arte();
                return res;
            }
            catch (Exception ex)
            {
                throw new HttpResponseException(HttpStatusCode.Unauthorized);
            }
        }
        [HttpGet]
        [ActionName("getArtByID")]
        public Arte GetArtById(int id)
        {
            try
            {
                db.OpenConnec();
                var res = db.GetArtById(id);
                db.CloseConnec();
                return res;
            }
            catch
            {
                throw new HttpResponseException(HttpStatusCode.Unauthorized);
            }
        }
        [HttpGet]
        [ActionName("getArtsByName")]
        public IEnumerable<Arte> GetArtsByName(string name)
        {
            try
            {
                db.OpenConnec();
                var res = db.GetArtsByParameter("NomeArte",name);
                db.CloseConnec();
                return res;
            }
            catch
            {
                throw new HttpResponseException(HttpStatusCode.Unauthorized);
            }
        }
        [HttpGet]
        [ActionName("getArtsByName")]
        public IEnumerable<Arte> GetArtsByArtist(string artist)
        {
            try
            {
                db.OpenConnec();
                var res = db.GetArtsByParameter("NomeArtista",artist);
                db.CloseConnec();
                return res;
            }
            catch
            {
                throw new HttpResponseException(HttpStatusCode.Unauthorized);
            }
        }
    }
}
