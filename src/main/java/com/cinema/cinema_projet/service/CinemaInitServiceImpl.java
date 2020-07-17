package com.cinema.cinema_projet.service;

import com.cinema.cinema_projet.dao.*;
import com.cinema.cinema_projet.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService {
    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private TicketRepository  ticketRepository;
    @Override
    public void initVilles() {
        Stream.of("Casablanca","Marrakech","Rabat","Tanger").forEach(nameVille->{
            Ville ville=new Ville();
            ville.setName(nameVille);
            villeRepository.save(ville);
        });
    }

    @Override
    public void initCinemas() {
        villeRepository.findAll().forEach(nameVille->{
            Stream.of("MegaRama","IMAX","FOUNOUN")
                    .forEach(nameCinema->{
                        Cinema cinema =new Cinema();
                        cinema.setName(nameCinema);
                        cinema.setNombreSalle(3+(int)(Math.random()*7));
                        cinema.setVille(nameVille);
                        cinemaRepository.save(cinema);


            });
        });
    }

    @Override
    public void initSalles() {
cinemaRepository.findAll().forEach(cinema->{
    for(int i=0;i<cinema.getNombreSalle();i++){
        Salle salle=new Salle();
        salle.setName("Salle"+(i+1));
        salle.setNombrePlaces(15+(int)(Math.random()*25));
        salle.setCinema(cinema);
        salleRepository.save(salle);
    }

});
    }

    @Override
    public void initSeances() {
        DateFormat dateFormat=new SimpleDateFormat("HH:mm");
    Stream.of("12:00","15:00","17:00","19:00","21:00").forEach(seance1->{
        Seance seance=new Seance();
        try {
            seance.setHeureDebut(dateFormat.parse(seance1));
            seanceRepository.save(seance);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    });

    }

    @Override
    public void initCategorie() {
    Stream.of("Histoire","Actions","Fiction","Drama").forEach(cat->{
        Categorie categorie=new Categorie();
        categorie.setName(cat);
        categorieRepository.save(categorie);
    });
    }

    @Override
    public void initPlaces() {
    salleRepository.findAll().forEach(salle->{
        for(int i=0;i<salle.getNombrePlaces();i++){
            Place place=new Place();
            place.setNumero(i+1);
            place.setSalle(salle);
            placeRepository.save(place);


        }
    });
    }

    @Override
    public void initFilms() {
        double[] durees=new double[]{1,1.5,2.5,3};
        List<Categorie> categorieList=categorieRepository.findAll();
     Stream.of("annem","Avengers","Contagion","Green Book","IT","Joker")
             .forEach(filmName->{
         Film film=new Film();
         film.setTitre(filmName);
         film.setDuree(durees[new Random().nextInt(durees.length)]);
         film.setPhoto(filmName+".jpg");
         film.setCategorie(categorieList.get(new Random().nextInt(categorieList.size())));
         filmRepository.save(film);
     });
    }

    @Override
    public void initProjections() {
        double[]prices=new double[]{30,50,70,90,100};
        List<Film>films=filmRepository.findAll();
        villeRepository.findAll().forEach(villename->{
            villename.getCinemas().forEach(cinemaName->{
                cinemaName.getSalles().forEach(salle->{
                    int index=new Random().nextInt(films.size());
                    Film film=films.get(index);
                      seanceRepository.findAll().forEach(seance->{
                          Projection projection=new Projection();
                          projection.setDateProjection(new Date());
                          projection.setFilm(film);
                          projection.setPrix(prices[new Random().nextInt(prices.length)]);
                          projection.setSalle(salle);
                          projection.setSeance(seance);
                          projectionRepository.save(projection);
                      });
                  });

            });

        });

    }

    @Override
    public void initTickets() {
        projectionRepository.findAll().forEach(projection -> {
            projection.getSalle().getPlaces().forEach(place -> {
                Ticket ticket=new Ticket();
                ticket.setPrix(projection.getPrix());
                ticket.setPlace(place);
                ticket.setProjection(projection);
                ticket.setReserve(false);
                ticketRepository.save(ticket);

            });


        });

    }
}
