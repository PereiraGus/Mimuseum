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
        User us = new User();
        Arte art = new Arte();
        Like lk = new Like();

        [HttpGet]
        [ActionName("getAll")]
        public IEnumerable<Arte> GetAllWorksOfArt()
        {
            try
            {
                db.OpenConnec();
                var res = art.GetAllArts();
                return res;
            }
            catch
            {
                throw new HttpResponseException(HttpStatusCode.Unauthorized);
            }
            finally
            {
                db.CloseConnec();
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
                    var res = art.GetArtById(id);
                    return res;
                }
                catch
                {
                    throw new HttpResponseException(HttpStatusCode.Unauthorized);
                }
                finally
                {
                    db.CloseConnec();
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
                var res = art.GetArtsByParameter("NomeArte",name);
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
                var res = art.GetArtsByParameter("NomeArtista",artist);
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
                var res = art.GetArtsByParameter("EstiloArte",style);
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
                    art.PostNewArt(art);
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
                    art.AlterArt(id, arte);
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
                    art.DeleteArt(id);
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
                var res = us.GetUserByID(id);
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
                    us.InsertNewUser(user);
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
        [HttpPost]
        [ActionName("validateUser")]
        public bool ValidateUser([FromBody] User user)
        {
            if (user == null)
            {
                throw new HttpResponseException(HttpStatusCode.BadRequest);
            }
            else
            {
                try 
                {
                    db.OpenConnec();
                    bool validator = us.ValidadeUser(user);
                    return validator;
                }
                catch
                {
                    throw new HttpResponseException(HttpStatusCode.BadRequest);
                }
                finally
                {
                    db.CloseConnec();
                }
            }   
        }
        [HttpPost]
        [ActionName("likeUnlike")]
        public HttpResponseMessage LikeUnlike([FromBody] Like like)
        {
            var res = new HttpResponseMessage();
            if (like == null)
            {
                res.StatusCode = HttpStatusCode.BadRequest;
            }
            else
            {
                try
                {
                    db.OpenConnec();
                    lk.likeUnlike(like);
                    res.StatusCode = HttpStatusCode.OK;
                }
                catch
                {
                    res.StatusCode = HttpStatusCode.InternalServerError;
                }
                finally
                {
                    db.CloseConnec();
                }
            }
            return res;
        }
        [HttpGet]
        [ActionName("seeLikesByUserID")]
        public IEnumerable<Like> seeLikesByUserID(int id)
        {
            try
            {
                db.OpenConnec();
                var res = lk.seeLikesByUserID(id);
                return res;
            }
            catch
            {
                throw new HttpResponseException(HttpStatusCode.Unauthorized);
            }
            finally
            {
                db.CloseConnec();
            }
        }
        [HttpGet]
        [ActionName("seeNumOfArts")]
        public int seeNumOfArts()
        {
            try
            {
                db.OpenConnec();
                var res = art.CountArts();
                return res;
            }
            catch
            {
                throw new HttpResponseException(HttpStatusCode.Unauthorized);
            }
            finally
            {
                db.CloseConnec();
            }
        }
    }
}
