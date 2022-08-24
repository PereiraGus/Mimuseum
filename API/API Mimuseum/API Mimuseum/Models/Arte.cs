using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace API_Mimuseum.Models
{
    public class Arte
    {
        public int IDAarte { get; set; }   
        public string NomeArte { get; set; }    
        public string NomeArtista { get; set; } 
        public int AnoArte { get; set; }    
        public string EstiloArte { get; set; } 
        public string UrlArte { get; set; }
        
    }
}