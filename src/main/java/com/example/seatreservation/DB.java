package com.example.seatreservation;

import javafx.scene.image.Image;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Objects;
import java.util.StringTokenizer;

public class DB {
    private static Connection connection;
    String url = "jdbc:mysql://localhost:3306/seatreservation";
    String username = "root";
    String pass = "root";

    public DB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, pass);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    /////////////////////////////////////////////////////
    /// creditCard
    /////////////////////////////////////////////////
    public static void addCreditCard(CreditCard creditCard) {
        try {
            String query = "INSERT INTO CreditCard (creditCardID, expireDate, cardNumber, cardHolderName, cvv) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, creditCard.getID());
                statement.setDate(2, Date.valueOf(creditCard.getExpDate()));
                statement.setString(3, creditCard.getCardNumber());
                statement.setString(4, creditCard.getHolderName());
                statement.setInt(5, creditCard.getCVV());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void editCreditCardByID(CreditCard creditCard) {
        try {
            String query = "UPDATE CreditCard SET expireDate = ?, cardNumber = ?, cardHolderName = ?, cvv = ? WHERE creditCardID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setDate(1, Date.valueOf(creditCard.getExpDate()));
                statement.setString(2, creditCard.getCardNumber());
                statement.setString(3, creditCard.getHolderName());
                statement.setInt(4, creditCard.getCVV());
                statement.setInt(5, creditCard.getID());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static CreditCard getCreditCardByID(int id) {
        try {
            String query = "SELECT * FROM CreditCard WHERE creditCardID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapResultSetToCreditCard(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static CreditCard getCreditCardByUserID(int userId) {
        try {
            String query = "SELECT CreditCard.* FROM CreditCard " +
                    "JOIN _User ON CreditCard.creditCardID = _User.creditCardID " +
                    "WHERE _User.userID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapResultSetToCreditCard(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static LinkedList<CreditCard> getAllCreditCards() {
        LinkedList<CreditCard> creditCards = new LinkedList<>();
        try {
            String query = "SELECT * FROM CreditCard";
            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    creditCards.add(mapResultSetToCreditCard(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return creditCards;
    }
    public static void deleteCreditCardByID(int id) {
        try {
            String query = "DELETE FROM CreditCard WHERE creditCardID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static CreditCard mapResultSetToCreditCard(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("creditCardID");
        LocalDate expDate = resultSet.getDate("expireDate").toLocalDate();
        String cardNumber = resultSet.getString("cardNumber");
        String holderName = resultSet.getString("cardHolderName");
        int cvv = resultSet.getInt("cvv");

        return new CreditCard(id, cardNumber, cvv, expDate, holderName);
    }

    //////////////////////////////////////////////////////////////////
    ///Movies
    ///////////////////////////////////////////////////////////
    public static void addMovie(Movie movie) {
        try {


            String query = "INSERT INTO Movie (movieID, _name, _description, genre, duration, releaseDate, posterLink) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, movie.getID());
                statement.setString(2, movie.getMovieName());
                statement.setString(3, movie.getDescription());
                statement.setString(4, movie.getGenre());
                statement.setInt(5, movie.getScreenTime());
                statement.setDate(6, Date.valueOf(movie.getReleaseDate()));
                statement.setString(7, movie.getImg().getUrl());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Movie getMovieByID(int id) {
        try {
            String query = "SELECT * FROM Movie WHERE movieID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapResultSetToMovie(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static LinkedList<Movie> getAllMovies() {
        LinkedList<Movie> movies = new LinkedList<>();
        try {
            String query = "SELECT * FROM Movie";
            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    movies.add(mapResultSetToMovie(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }
    public static void deleteMovieByID(int id) {
        try {
            String query = "DELETE FROM Movie WHERE movieID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void editMovie(Movie movie) {
        try {
            String query = "UPDATE Movie SET _name = ?, _description = ?, genre = ?, duration = ?, releaseDate = ? WHERE movieID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, movie.getMovieName());
                statement.setString(2, movie.getDescription());
                statement.setString(3, movie.getGenre());
                statement.setInt(4, movie.getScreenTime());
                statement.setDate(5, Date.valueOf(movie.getReleaseDate())); // assuming 'releaseDate' is a String in the 'yyyy-MM-dd' format
                statement.setInt(6, movie.getID());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Movie getMovieByTicketId(int ticketId) {
        try {
            String query = "SELECT Movie.* FROM Movie " +
                    "JOIN Party ON Movie.movieID = Party.movieID " +
                    "JOIN Ticket ON Party.partyID = Ticket.partyID " +
                    "WHERE Ticket.ticketID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, ticketId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapResultSetToMovie(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    static Movie mapResultSetToMovie(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("movieID");
        String movieName = resultSet.getString("_name");
        String description = resultSet.getString("_description");
        String genre = resultSet.getString("genre");
        int duration = resultSet.getInt("duration");
        LocalDate releaseDate = resultSet.getDate("releaseDate").toLocalDate();
        String posterLink = resultSet.getString("posterLink");

        Image image=new Image(posterLink);
        return new Movie(id, duration, movieName, description, image, releaseDate, genre);
    }

    ////////////////////////////////////////////////////////////////////////////
    ///party
    ////////////////////////////////////////////////////////////////////////
    public static void addParty(Party party) {
    try {
        String query = "INSERT INTO Party (partyID, startTime, endTime, movieID, hallID, numberOfBookedSeats) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, party.getID());
            statement.setTimestamp(2, Timestamp.valueOf(party.getStartTime()));
            statement.setTimestamp(3, Timestamp.valueOf(party.getEndTime()));
            statement.setInt(4, party.getMovieID());
            statement.setInt(5, party.getHallID());
            statement.setInt(6, party.getNumberOfBookedSeats());

            statement.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public static void editPartyByID(Party party) {
        try {
            String query = "UPDATE Party SET startTime = ?, endTime = ?, movieID = ?, hallID = ?, numberOfBookedSeats = ? WHERE partyID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setTimestamp(1, Timestamp.valueOf(party.getStartTime()));
                statement.setTimestamp(2, Timestamp.valueOf(party.getEndTime()));
                statement.setInt(3, party.getMovieID());
                statement.setInt(4, party.getHallID());
                statement.setInt(5, party.getNumberOfBookedSeats());
                statement.setInt(6, party.getID());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Party getPartyByID(int id) {
        try {
            String query = "SELECT * FROM Party WHERE partyID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapResultSetToParty(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static LinkedList<Party> getAllParties() {
        LinkedList<Party> parties = new LinkedList<>();
        try {
            String query = "SELECT * FROM Party";
            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    parties.add(mapResultSetToParty(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parties;
    }
    public static LinkedList<Party> getPartiesByMovieID(int movieID) {
        LinkedList<Party> parties = new LinkedList<>();
        try {
            String query = "SELECT * FROM Party WHERE movieID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, movieID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        parties.add(mapResultSetToParty(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parties;
    }
    public static void deletePartyByID(int id) {
        try {
            String query = "DELETE FROM Party WHERE partyID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Party getPartyByTicketId(int ticketId) {
        Party party = null;
        try {
            String query = "SELECT Party.* FROM Party " +
                    "JOIN Ticket ON Party.partyID = Ticket.partyID " +
                    "WHERE Ticket.ticketID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, ticketId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapResultSetToParty(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return party;
    }
    static Party mapResultSetToParty(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("partyID");
        LocalDateTime startTime = resultSet.getTimestamp("startTime").toLocalDateTime();
        LocalDateTime endTime = resultSet.getTimestamp("endTime").toLocalDateTime();
        int movieId = resultSet.getInt("movieID");
        int hallId = resultSet.getInt("hallID");
        int numberOfBookedSeats = resultSet.getInt("numberOfBookedSeats");

        return new Party(id, startTime, endTime, movieId, hallId, numberOfBookedSeats);
    }


    //////////////////////////////////////////////////////////////////////
    //ticket
    /////////////////////////////////////////////////////////////////////

    public static void addTicket(Ticket ticket) {
        try {
            String query = "INSERT INTO Ticket (ticketID, issueTime, userId, partyId, seatId) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, ticket.getID());
                statement.setTimestamp(2, Timestamp.valueOf(ticket.getIssueTime()));
                statement.setInt(3, ticket.getUserId());
                statement.setInt(4, ticket.getPartyId());
                statement.setInt(5, ticket.getSeatId());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void editTicketByID(Ticket ticket) {
        try {
            String query = "UPDATE Ticket SET issueTime = ?, userId = ?, partyId = ?, seatId = ? WHERE ticketID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setTimestamp(1, Timestamp.valueOf(ticket.getIssueTime()));
                statement.setInt(2, ticket.getUserId());
                statement.setInt(3, ticket.getPartyId());
                statement.setInt(4, ticket.getSeatId());
                statement.setInt(5, ticket.getID());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Ticket getTicketByID(int id) {
        try {
            String query = "SELECT * FROM Ticket WHERE ticketID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapResultSetToTicket(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static LinkedList<Ticket> getAllTickets() {
        LinkedList<Ticket> tickets = new LinkedList<>();
        try {
            String query = "SELECT * FROM Ticket";
            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    tickets.add(mapResultSetToTicket(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }
    public static LinkedList<Ticket> getTicketsByUserID(int userID) {
        LinkedList<Ticket> tickets = new LinkedList<>();
        try {
            String query = "SELECT * FROM Ticket WHERE userId = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, userID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        tickets.add(mapResultSetToTicket(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }
    public static void deleteTicketByID(int id) {
        try {
            String query = "DELETE FROM Ticket WHERE ticketID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static Ticket mapResultSetToTicket(ResultSet resultSet) throws SQLException {
        int reservationID = resultSet.getInt("ticketID");
        LocalDateTime issueTime = resultSet.getTimestamp("issueTime").toLocalDateTime();
        int userId = resultSet.getInt("userId");
        int partyId = resultSet.getInt("partyId");
        int seatId = resultSet.getInt("seatId");

        return new Ticket(reservationID, issueTime, userId, partyId, seatId);
    }




    //////////////////////////////////////////////////////////////////
    //user
    //////////////////////////////////////////////////////////////////
    public static void addUser(int id, String name, String email, String password, String phoneNumber, int creditCardID) {
        try {
            String query = "INSERT INTO _User (userID, firstName, email, password, lastName) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                statement.setString(2, name);
                statement.setString(3, email);
                statement.setString(4, password);
                statement.setString(5, phoneNumber);

                statement.executeUpdate();
            }

            // Link the user with the CreditCard
            linkUserWithCreditCard(id, creditCardID);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static User getUserByID(int id) {
        try {
            String query = "SELECT * FROM _User WHERE userID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapResultSetToUser(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static LinkedList<User> getAllUsers() {
        LinkedList<User> users = new LinkedList<>();
        try {
            String query = "SELECT * FROM _User";
            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    users.add(mapResultSetToUser(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    public static void deleteUserByID(int id) {
        try {
            String query = "DELETE FROM _User WHERE userID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void editUserByID(int id, String name, String email, String password, String phoneNumber, int creditCardID) {
        try {
            String query = "UPDATE _User SET firstName = ?, email = ?, password = ?, lastName = ? WHERE userID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, name);
                statement.setString(2, email);
                statement.setString(3, password);
                statement.setString(4, phoneNumber);
                statement.setInt(5, id);

                statement.executeUpdate();
            }

            // Link the user with the CreditCard
            linkUserWithCreditCard(id, creditCardID);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("userID");
        String name = resultSet.getString("firstName");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String phoneNumber = resultSet.getString("lastName");

        // Get the linked CreditCard ID
        int creditCardID = getLinkedCreditCardID(id);

        return new User(id, name, email, password, phoneNumber, creditCardID);
    }
    private static void linkUserWithCreditCard(int userID, int creditCardID) {
        try {
            String query = "UPDATE _User SET CreditCardID = ? WHERE userID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, creditCardID);
                statement.setInt(2, userID);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static int getLinkedCreditCardID(int userID) {
        try {
            String query = "SELECT CreditCardID FROM _User WHERE userID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, userID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("CreditCardID");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if no CreditCard is linked
    }
    public static User getUserByTicketId(int ticketId) {
        User user = null;
        try {
            String query = "SELECT User.*, Ticket.userID, Ticket.creditCardID FROM User " +
                    "JOIN Ticket ON User.userID = Ticket.userID " +
                    "WHERE Ticket.ticketID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, ticketId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        user = new User(
                                resultSet.getInt("userID"),
                                resultSet.getString("name"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getString("phoneNumber"),
                                resultSet.getInt("creditCardID")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    //////////////////////////////////////////////
    //Seat
    ////////////////////////////////////////////////

    public static void addSeat(Seat seat) {
        try {
            String query = "INSERT INTO Seat (seatID, isBooked, _row, _column, price, hallID) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, seat.getID());
                preparedStatement.setBoolean(2, seat.isBooked());
                preparedStatement.setInt(3, seat.getRow());
                preparedStatement.setInt(4, seat.getColumn());
                preparedStatement.setDouble(5, seat.getSeatPrice());
                preparedStatement.setInt(6, seat.getHallID());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Seat getSeatById(int seatId) {
        Seat seat = null;
        try {
            String query = "SELECT * FROM Seat WHERE seatID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, seatId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        seat = new Seat(
                                resultSet.getInt("seatID"),
                                resultSet.getBoolean("isBooked"),
                                resultSet.getInt("_row"),
                                resultSet.getInt("_column"),
                                resultSet.getDouble("price"),
                                resultSet.getInt("hallID")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seat;
    }

    public static Seat getSeatByCoordinates(int x, int y, int hallId){
        try {
            String query = "SELECT * FROM Seat WHERE hallID = ? AND _row=? AND _column= ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, hallId);
                preparedStatement.setInt(2, x);
                preparedStatement.setInt(3, y);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return new Seat(
                                resultSet.getInt("seatID"),
                                resultSet.getBoolean("isBooked"),
                                resultSet.getInt("_row"),
                                resultSet.getInt("_column"),
                                resultSet.getDouble("price"),
                                resultSet.getInt("hallID")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LinkedList<Seat> getAllSeats() {
        LinkedList<Seat> seats = new LinkedList<>();
        try {
            String query = "SELECT * FROM Seat";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    Seat seat = new Seat(
                            resultSet.getInt("seatID"),
                            resultSet.getBoolean("isBooked"),
                            resultSet.getInt("_row"),
                            resultSet.getInt("_column"),
                            resultSet.getDouble("price"),
                            resultSet.getInt("hallID")
                    );
                    seats.add(seat);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    public static void updateSeat(Seat seat) {
        try {
            String query = "UPDATE Seat SET isBooked=?, _row=?, _column=?, price=?, hallID=? WHERE seatID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setBoolean(1, seat.isBooked());
                preparedStatement.setInt(2, seat.getRow());
                preparedStatement.setInt(3, seat.getColumn());
                preparedStatement.setDouble(4, seat.getSeatPrice());
                preparedStatement.setInt(5, seat.getHallID());
                preparedStatement.setInt(6, seat.getID());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteSeat(int seatId) {
        try {
            String query = "DELETE FROM Seat WHERE seatID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, seatId);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<Seat> getSeatsByHallId(int hallId) {
        LinkedList<Seat> seats = new LinkedList<>();
        try {
            String query = "SELECT * FROM Seat WHERE hallID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, hallId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Seat seat = new Seat(
                                resultSet.getInt("seatID"),
                                resultSet.getBoolean("isBooked"),
                                resultSet.getInt("_row"),
                                resultSet.getInt("_column"),
                                resultSet.getDouble("price"),
                                resultSet.getInt("hallID")
                        );
                        seats.add(seat);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }



    /////////////////////////////////////////////////////////////
    ///hall
    //////////////////////////////////////////////////////////////

    public static void addHall(Hall hall) {
        try {
            String query = "INSERT INTO Hall (hallID, numberOfRows, numberOfColumns, hallName) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, hall.getID());
                preparedStatement.setInt(2, hall.getRows());
                preparedStatement.setInt(3, hall.getColumns());
                preparedStatement.setString(4, hall.getName());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Hall getHallById(int hallId) {
        Hall hall = null;
        try {
            String query = "SELECT * FROM Hall WHERE hallID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, hallId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        hall = new Hall(
                                resultSet.getInt("hallID"),
                                resultSet.getString("hallName"),
                                resultSet.getInt("numberOfRows"),
                                resultSet.getInt("numberOfColumns")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hall;
    }

    public static LinkedList<Hall> getAllHalls() {
        LinkedList<Hall> halls = new LinkedList<>();
        try {
            String query = "SELECT * FROM Hall";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    Hall hall = new Hall(
                            resultSet.getInt("hallID"),
                            resultSet.getString("hallName"),
                            resultSet.getInt("numberOfRows"),
                            resultSet.getInt("numberOfColumns")
                    );
                    halls.add(hall);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return halls;
    }

    public static void updateHall(Hall hall) {
        try {
            String query = "UPDATE Hall SET numberOfRows=?, numberOfColumns=?, hallName=? WHERE hallID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, hall.getRows());
                preparedStatement.setInt(2, hall.getColumns());
                preparedStatement.setString(3, hall.getName());
                preparedStatement.setInt(4, hall.getID());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteHall(int hallId) {
        try {
            String query = "DELETE FROM Hall WHERE hallID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, hallId);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Hall getHallByTicketId(int ticketId) {
        Hall hall = null;
        try {
            String query = "SELECT Hall.* FROM Hall " +
                    "JOIN Party ON Hall.hallID = Party.hallID " +
                    "JOIN Ticket ON Party.partyID = Ticket.partyID " +
                    "WHERE Ticket.ticketID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, ticketId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        hall = new Hall(
                                resultSet.getInt("hallID"),
                                resultSet.getString("hallName"),
                                resultSet.getInt("numberOfRows"),
                                resultSet.getInt("numberOfColumns")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hall;
    }

    public static Hall getHallByPartyId(int partyId) {
        Hall hall = null;
        try {
            String query = "SELECT Hall.* FROM Hall " +
                    "JOIN Party ON Hall.hallID = Party.hallID " +
                    "WHERE Party.partyID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, partyId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        hall = new Hall(
                                resultSet.getInt("hallID"),
                                resultSet.getString("hallName"),
                                resultSet.getInt("numberOfRows"),
                                resultSet.getInt("numberOfColumns")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hall;
    }

}
