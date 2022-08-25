﻿using System;
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
                Arte arte = new Arte();
                var res = arte.FetchArte();
                return res;
            }
            catch(Exception ex)
            {
                throw new HttpResponseException(HttpStatusCode.Unauthorized); 
            }
        }
        /*
        public Arte GetArtById(int id)
        {
            var arte = artes.FirstOrDefault((p) => p.IDAarte == id);
            if (arte == null)
            {
                var asw = new HttpResponseMessage(HttpStatusCode.NotFound);
                throw new HttpResponseException(asw);
            }
            return arte;
        }
        public IEnumerable<Arte> GetArtsByName(string name)
        {
            var res = artes.Where((p) => p.NomeArte.Contains(name));
            return res;
        }*/
    }
}