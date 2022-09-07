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
            catch
            {
                throw new HttpResponseException(HttpStatusCode.Unauthorized);
            }
        }
        [HttpGet]
        [ActionName("getArtByID")]
        public Arte GetArtById(int id)
        {
            if (id == 0)
            {
                throw new HttpResponseException(HttpStatusCode.NotFound);
            }
            else
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
        [ActionName("getArtsByArtist")]
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
        [HttpGet]
        [ActionName("getArtsByArtist")]
        public IEnumerable<Arte> GetArtsByStyle(string style)
        {
            try
            {
                db.OpenConnec();
                var res = db.GetArtsByParameter("EstiloArte",style);
                db.CloseConnec();
                return res;
            }
            catch
            {
                throw new HttpResponseException(HttpStatusCode.Unauthorized);
            }
        }
        [HttpPost]
        [ActionName("postNewArt")]
        public HttpResponseMessage PostNewArt([FromBody]Arte art)
        {
            var res = new HttpResponseMessage();
            if(art == null)
            {
                res.StatusCode = HttpStatusCode.BadRequest;
            }
            else
            {
                try
                {
                    db.OpenConnec();
                    db.PostNewArt(art);
                    res.StatusCode = HttpStatusCode.Created;
                }
                catch
                {
                    res.StatusCode = HttpStatusCode.NotAcceptable;
                }
                finally
                {
                    db.CloseConnec();
                }
            }
            return res;
        }
        [HttpPut]
        [ActionName("alterArt")]
        public HttpResponseMessage UpdateArt(int id,[FromBody]Arte arte)
        {
            var res = new HttpResponseMessage();
            if (arte == null)
            {
                res.StatusCode = HttpStatusCode.NotModified;
            }
            else
            {
                try
                {
                    db.OpenConnec();
                    db.AlterArt(id, arte);
                }
                catch
                {
                    res.StatusCode = HttpStatusCode.NotAcceptable;
                }
                finally
                {
                    db.CloseConnec();
                    res.StatusCode = HttpStatusCode.Created;
                }
            }
            return res;
        }
        [HttpDelete]
        [ActionName("deleteArt")]
        public HttpResponseMessage DeleteArt(int id)
        {
            var res = new HttpResponseMessage();
            if(id == 0)
            {
                res.StatusCode = HttpStatusCode.NotFound;
            }
            else
            {
                try
                {
                    db.OpenConnec();
                    db.DeleteArt(id);
                    res.StatusCode = HttpStatusCode.OK;
                }
                catch
                {
                    res.StatusCode = HttpStatusCode.Forbidden;
                }
                finally
                {
                    db.CloseConnec();
                }
            }
            return res;
        }
        [HttpGet]
        [ActionName("getUserByID")]
        public User GetUserById(int id)
        {
            try
            {
                db.OpenConnec();
                var res = db.GetUserByID(id);
                return res;
            }
            catch
            {
                throw new HttpResponseException(HttpStatusCode.NotFound);
            }
            finally
            {
                db.CloseConnec();
            }
        }
        [HttpPost]
        [ActionName("insertUser")]
        public HttpResponseMessage PostNewUser([FromBody]User user)
        {
            var res = new HttpResponseMessage();
            if(user == null)
            {
                res.StatusCode = HttpStatusCode.BadRequest;
            }
            else
            {
                try
                {
                    db.OpenConnec();
                    db.InsertNewUser(user);
                    res.StatusCode = HttpStatusCode.Created;
                }
                catch
                {
                    res.StatusCode = HttpStatusCode.Forbidden;
                }
                finally
                {
                    db.CloseConnec();
                }
            }
            return res;
        }
    }
}
