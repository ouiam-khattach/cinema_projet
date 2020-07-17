package com.cinema.cinema_projet.controleur;

import com.cinema.cinema_projet.dao.FilmRepository;

import com.cinema.cinema_projet.dao.TicketRepository;
import com.cinema.cinema_projet.entities.Film;
import com.cinema.cinema_projet.entities.Ticket;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin("*")
public class CinemaRestController {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @GetMapping(path = "/imageFilm/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(
        @PathVariable(name="id")Long id) throws IOException {
        Film film=filmRepository.findById(id).get();
        String photoName= film.getPhoto();
        File file=new File(System.getProperty("user.home")+"/Cinema/image/"+photoName);

        Path path= Paths.get(file.toURI());
        return Files.readAllBytes(path);


    }

@PostMapping("/payerTickets")
@Transactional
    public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm){
        List<Ticket>ticketList=new ArrayList<>();
        ticketForm.getTickets().forEach(idTicket->{
            Ticket ticket=ticketRepository.findById(idTicket).get();
            ticket.setNomClient(ticketForm.getNomClient());
            ticket.setReserve(true);
            ticket.setCodePayement(ticketForm.getCodePayement());
            ticketRepository.save(ticket);
            ticketList.add(ticket);
        });
        return ticketList;

    }
}
@Data
class TicketForm{
    private String nomClient;
    private int codePayement;
    private List<Long> tickets=new ArrayList<>();
}
