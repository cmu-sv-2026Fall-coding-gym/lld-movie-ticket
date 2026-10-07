package movie_ticket;

import movie_ticket.location.Cinema;
import movie_ticket.location.Layout;
import movie_ticket.location.Room;
import movie_ticket.location.Seat;
import movie_ticket.rate.NormalRate;
import movie_ticket.showing.Movie;
import movie_ticket.showing.Screening;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieBookingSystemTest {
    @Test
    public void testBrowseAndBuy() {
        System.out.println("\n=== Testing Movie Booking System: Browse and Buy Flow ===");
        MovieBookingSystem bookingSystem = new MovieBookingSystem();
        System.out.println("\n--- Creating Room and Setting Up Seat Pricing ---");
        // create a room with a layout of 10x10 seats with a normal rate of $10.00
        Room room = new Room("1", new Layout(10, 10));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                room.getLayout().getSeatByPosition(i, j).setPricingStrategy(new NormalRate(BigDecimal.valueOf(10.00)));
            }
        }
        System.out.println("✓ Created room '1' with 10x10 seats, each seat priced at $10.00");

        System.out.println("\n--- Adding Cinema to Booking System ---");
        // create a cinema with the room
        bookingSystem.addCinema(new Cinema("Test Cinema", "Test Location"));
        System.out.println("✓ Added cinema: 'Test Cinema' at 'Test Location'");

        System.out.println("\n--- Creating Movie and Screening ---");
        // create a test movie with a test screening in the room
        int length = 180;
        Movie movie = new Movie("Test Movie", "Test Description", length);
        Screening screening = new Screening(movie, room, LocalDateTime.now(), LocalDateTime.now().plusMinutes(length));
        System.out.println("✓ Created movie: 'Test Movie' (180 min)");
        System.out.println("✓ Created screening for 'Test Movie' in room '1'");

        System.out.println("\n--- Adding Movie and Screening to Booking System ---");
        // add the movie and screening to the booking system
        bookingSystem.addMovie(movie);
        bookingSystem.addScreening(movie, screening);
        System.out.println("✓ Added movie and screening to booking system");

        System.out.println("\n--- Verifying Movies and Screenings in System ---");
        // test that the movie and screening are in the booking system
        assertEquals(1, bookingSystem.getAllMovies().size());
        assertEquals(1, bookingSystem.getScreeningsForMovie(movie).size());
        System.out.println("✓ Verified: 1 movie and 1 screening present in system");

        System.out.println("\n--- Checking Available Seats ---");
        // test that the available seats for the screening are correct
        assertEquals(100, bookingSystem.getAvailableSeats(screening).size());
        System.out.println("✓ Verified: 100 seats available for the screening");

        System.out.println("\n--- Booking a Ticket ---");
        // test that the booking system can book a ticket
        bookingSystem.bookTicket(screening, room.getLayout().getSeatByPosition(0, 0));
        assertEquals(1, bookingSystem.getTicketCount(screening));
        System.out.println("✓ Booked seat (0,0) for 'Test Movie' screening");
        System.out.println("✓ Verified: 1 ticket booked for the screening");

        System.out.println("\n--- Checking Ticket Price ---");
        // test the price of the ticket
        assertEquals(BigDecimal.valueOf(10.00), bookingSystem.getTicketsForScreening(screening).get(0).getPrice());
        System.out.println("✓ Verified: Ticket price is $10.00");
        System.out.println("=== Movie Booking System Test Completed Successfully ===\n");
    }
}
