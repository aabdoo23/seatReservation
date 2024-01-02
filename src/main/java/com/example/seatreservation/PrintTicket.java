package com.example.seatreservation;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

public class PrintTicket implements Printable {
    Ticket ticket;

    public PrintTicket(Ticket ticket) {
        this.ticket = ticket;
    }
    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) {
        if (pageIndex != 0) {
            return NO_SUCH_PAGE;
        }

        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.setColor(Color.BLACK);

        int x = (int) pf.getImageableX();
        int y = (int) pf.getImageableY();

        int textX = x + 50;
        int textY = y + 50;

        String tab = "    ";

        if(ticket!=null){
            User user=DB.getUserByTicketId(ticket.getID());
            Seat seat=DB.getSeatById(ticket.getSeatId());
            Party party=DB.getPartyByID(ticket.getPartyId());
            Movie movie=DB.getMovieByID(party.getMovieID());
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            FontMetrics fm = g.getFontMetrics();
            g.drawString(tab + "Ticket Issue time:\n"+ ticket.getIssueTime(), textX, textY);
            textY += fm.getHeight() + 5;
            g.setFont(new Font("Arial", Font.PLAIN, 14));
            fm = g.getFontMetrics();
            g.drawString(tab + "Ticket ID: "+ticket.getID(), textX, textY);
            textY += fm.getHeight() + 5;
            g.setFont(new Font("Arial", Font.BOLD, 14));
            fm = g.getFontMetrics();
            g.drawString(tab + "Ticket Holder Name:\n"+user.getName(), textX, textY);
            textY += fm.getHeight() + 5;
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            fm = g.getFontMetrics();
            g.drawString(tab + "Movie:\n"+ movie.getMovieName(), textX, textY);
            textY += fm.getHeight() + 5;
            g.drawString(tab + "Time:\n"+ party.getStartTime(), textX, textY);
            textY += fm.getHeight() + 5;
            g.drawString(tab + "Price:\n"+ ticket.getPrice(), textX, textY);
            textY += fm.getHeight() + 5;
            g.drawString(tab + "Seats:\n"+ seat, textX, textY);
            textY += fm.getHeight() + 5;
            g.drawString(tab + "Hall:\n"+ DB.getHallByTicketId(ticket.getID()).getName(), textX, textY);


        }


        return PAGE_EXISTS;
    }


}
