using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using API_Mimuseum.Models;

namespace API_Mimuseum.Controllers
{
    public class ArteController : ApiController
    {
        Arte[] artes = new Arte[]
        {
            new Arte { IDAarte = 1, NomeArte = "A Grande Onda de Kanagawa", NomeArtista = "Katsushika Hokusai", AnoArte = 1831,
                EstiloArte = "Gravura", UrlArte = "https://artsandculture.google.com/asset/the-great-wave-off-the-coast-of-kanagawa/fAFp7yddSAtcTQ?hl=pt-BR"
            },
            new Arte { IDAarte = 2, NomeArte = "Mona Lisa", NomeArtista = "Leonardo da Vinci", AnoArte = 1530,
                EstiloArte = "Pintura a óleo", UrlArte = "https://cdn.pariscityvision.com/library/image/5449.jpg"
            },
            new Arte { IDAarte = 3, NomeArte = "A Noite Estrelada", NomeArtista = "Vincent Van Gogh", AnoArte = 1889,
                EstiloArte = "Pintura a óleo", UrlArte = "https://artsandculture.google.com/asset/the-starry-night/bgEuwDxel93-Pg?hl=pt-BR"
            },
            new Arte { IDAarte = 4, NomeArte = "A Persistência da Memória", NomeArtista = "Salvador Dali", AnoArte = 1931,
                EstiloArte = "Pintura a óleo/bronze", UrlArte = "https://cdn.culturagenial.com/imagens/clocks-cke.jpg"
            },
            new Arte { IDAarte = 5, NomeArte = "Meisje met de parel", NomeArtista = "Johannes Vermeer", AnoArte = 1665,
                EstiloArte = "Pintura a óleo", UrlArte = "https://artsandculture.google.com/asset/girl-with-a-pearl-earring/3QFHLJgXCmQm2Q?hl=pt-BR&avm=2"
            }
        };
        public IEnumerable<Arte> GetAllWorksOfArt() { return artes; }
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
        }
    }
}
