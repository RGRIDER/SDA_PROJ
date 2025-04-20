package com.example.sda_combine;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class Database {
    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/sda_proj"; // Replace with your database name
    private static final String USER = "root"; // Replace with your MySQL username
    private static final String PASSWORD = "2016"; // Replace with your MySQL password

    // Method to register a user in the database
    public void registerUser(String userId, String firstName, String lastName, String email, String password, String userType) {
        Connection connection = null;
        PreparedStatement insertUserStmt = null;

        try {
            // Establish a connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            String insertUserQuery = "INSERT INTO Users (user_id, first_name, last_name, email, password, user_type) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            insertUserStmt = connection.prepareStatement(insertUserQuery);
            insertUserStmt.setString(1, userId); // Save the userId
            insertUserStmt.setString(2, firstName);
            insertUserStmt.setString(3, lastName);
            insertUserStmt.setString(4, email);
            insertUserStmt.setString(5, password);
            insertUserStmt.setString(6, userType);
            insertUserStmt.executeUpdate();

            System.out.println("New user registered successfully with role: " + userType);

        } catch (SQLException e) {
            System.err.println("An error occurred while registering the user: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (insertUserStmt != null) insertUserStmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("An error occurred while closing resources: " + e.getMessage());
            }
        }
    }

    // Method to save event details in the database
    public void saveEventDetails(Event event) {
        Connection connection = null;
        PreparedStatement insertEventStmt = null;

        try {
            // Establish a connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            String insertEventQuery = "INSERT INTO Event (eventId, name, organizerEmail, description, venue, teamSize, teamLimit, registrationFee, accountNumber, date, startTime, endTime) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            insertEventStmt = connection.prepareStatement(insertEventQuery);
            insertEventStmt.setInt(1, event.getEventId()); // eventId as INT
            insertEventStmt.setString(2, event.getName());
            insertEventStmt.setString(3, event.getOrganizerEmail());
            insertEventStmt.setString(4, event.getDescription());
            insertEventStmt.setString(5, event.getVenue());
            insertEventStmt.setInt(6, event.getTeamSize());
            insertEventStmt.setInt(7, event.getTeamLimit());
            insertEventStmt.setInt(8, event.getRegistrationFee());
            insertEventStmt.setString(9, event.getAccountNumber());
            insertEventStmt.setDate(10, Date.valueOf(event.getDateTime().getDate())); // Assuming date is in YYYY-MM-DD format
            insertEventStmt.setTime(11, Time.valueOf(event.getDateTime().getStartTime())); // Assuming time is in HH:MM:SS format
            insertEventStmt.setTime(12, Time.valueOf(event.getDateTime().getEndTime())); // Assuming time is in HH:MM:SS format

            // Execute the update to insert event details
            insertEventStmt.executeUpdate();
            System.out.println("Event details saved successfully: " + event.getName());

        } catch (SQLException e) {
            System.err.println("An error occurred while saving event details: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (insertEventStmt != null) insertEventStmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("An error occurred while closing resources: " + e.getMessage());
            }
        }
    }


    // Fetch the maximum eventId from the database
    // Fetch the maximum eventId from the database
    public int fetchMaxEventIdFromDatabase() {
        int maxEventId = 0;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT MAX(eventId) AS max_event_id FROM Event")) {

            if (resultSet.next()) {
                maxEventId = resultSet.getInt("max_event_id");
            }

        } catch (SQLException e) {
            System.err.println("Error fetching max eventId from database: " + e.getMessage());
        }

        return maxEventId;
    }



    // Fetch the maximum user ID from the database
    public int fetchMaxUserIdFromDatabase() {
        int maxUserId = 0;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT MAX(CAST(user_id AS UNSIGNED)) AS max_id FROM Users")) {

            if (resultSet.next()) {
                maxUserId = resultSet.getInt("max_id");
            }

        } catch (SQLException e) {
            System.err.println("Error fetching max user ID from database: " + e.getMessage());
        }

        return maxUserId;
    }

    // Fetch all admins from the database
    public List<Admin> fetchAdmins() {
        List<Admin> admins = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Users WHERE user_type = 'Admin'")) {

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                admins.add(new Admin(userId, firstName, lastName, email, password));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching admins: " + e.getMessage());
        }
        return admins;
    }

    // Fetch all participants from the database
    public List<Participant> fetchParticipants() {
        List<Participant> participants = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Users WHERE user_type = 'Participant'")) {

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                participants.add(new Participant(userId, firstName, lastName, email, password));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching participants: " + e.getMessage());
        }
        return participants;
    }

    // Fetch all organizers from the database
    public List<Organizer> fetchOrganizers() {
        List<Organizer> organizers = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Users WHERE user_type = 'Organizer'")) {

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                organizers.add(new Organizer(userId, firstName, lastName, email, password));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching organizers: " + e.getMessage());
        }
        return organizers;
    }

    // Fetch all events from the database
    public List<Event> fetchEvents() {
        List<Event> events = new ArrayList<>();

        String query = "SELECT eventId, name, organizerEmail, description, venue, teamSize, teamLimit, " +
                "registrationFee, accountNumber, date, startTime, endTime FROM Event";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                int eventId = resultSet.getInt("eventId");
                String name = resultSet.getString("name");
                String organizerEmail = resultSet.getString("organizerEmail");
                String description = resultSet.getString("description");
                String venue = resultSet.getString("venue");
                int teamSize = resultSet.getInt("teamSize");
                int teamLimit = resultSet.getInt("teamLimit");
                int registrationFee = resultSet.getInt("registrationFee");
                String accountNumber = resultSet.getString("accountNumber");
                String date = resultSet.getDate("date").toString(); // Assuming the date is in SQL Date format
                String startTime = resultSet.getTime("startTime").toString(); // Assuming the time is in SQL Time format
                String endTime = resultSet.getTime("endTime").toString(); // Assuming the time is in SQL Time format

                // Create an Event object and add it to the list
                Event event = new Event(eventId, name, organizerEmail, description, venue, teamSize, teamLimit,
                        registrationFee, accountNumber, date, startTime, endTime);
                events.add(event);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching events: " + e.getMessage());
        }

        return events;
    }

    public void updateEventInDatabase(int eventId, String startTime, String endTime, String venue, String description) {
        String query = "UPDATE event SET startTime = ?, endTime = ?, venue = ?, description = ? WHERE eventId = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the parameters for the query
            preparedStatement.setString(1, startTime);
            preparedStatement.setString(2, endTime);
            preparedStatement.setString(3, venue);
            preparedStatement.setString(4, description);
            preparedStatement.setInt(5, eventId);

            // Execute the update query
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Event with ID " + eventId + " updated successfully in the database.");
            } else {
                throw new SQLException("Failed to update event. No rows affected.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating event in database: " + e.getMessage());
        }
    }

    public boolean insertAnnouncement(int eventId, String organizerEmail, String message) {
        String query = "INSERT INTO announcements (eventId, organizerEmail, message) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set query parameters
            preparedStatement.setInt(1, eventId);
            preparedStatement.setString(2, organizerEmail);
            preparedStatement.setString(3, message);

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0; // Return true if the insertion was successful

        } catch (SQLException e) {
            System.err.println("Error inserting announcement into the database: " + e.getMessage());
            return false;
        }
    }

    public Map<Integer, List<String>> getAnnouncements() {
        String query = "SELECT eventId, message FROM announcements";
        Map<Integer, List<String>> announcementsMap = new HashMap<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery()) {

            // Process the result set
            while (resultSet.next()) {
                int eventId = resultSet.getInt("eventId");
                String message = resultSet.getString("message");

                // Add the announcement to the map
                announcementsMap
                        .computeIfAbsent(eventId, k -> new ArrayList<>())
                        .add(message);
            }

            return announcementsMap;

        } catch (SQLException e) {
            System.err.println("Error fetching announcements from the database: " + e.getMessage());
            return null;
        }
    }

    public boolean addTeamToDatabase(String eventName, String teamName, List<String> memberEmails) {
        String insertTeamQuery = "INSERT INTO Teams (team_name, event_name, member_emails) VALUES (?, ?, ?)";
        String memberEmailsString = String.join(",", memberEmails); // Convert List to comma-separated string

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertTeamQuery)) {

            // Set the parameters for the query
            preparedStatement.setString(1, teamName);
            preparedStatement.setString(2, eventName);
            preparedStatement.setString(3, memberEmailsString);

            // Execute the insert query
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Team " + teamName + " added to event " + eventName + " successfully.");
                return true; // Return true if insertion succeeds
            } else {
                System.err.println("Failed to add team. No rows affected.");
                return false; // Return false if no rows were inserted
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error adding team to database: " + e.getMessage());
            return false; // Return false if an exception occurs
        }
    }

    public List<String> getAnnouncementsByEventId(int eventId) {
        List<String> announcements = new ArrayList<>();
        String query = "SELECT message FROM announcements WHERE eventId = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the eventId in the query
            preparedStatement.setInt(1, eventId);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Iterate through the results and add messages to the list
                while (resultSet.next()) {
                    announcements.add(resultSet.getString("message"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error for debugging
        }

        return announcements;
    }

    public List<Map<String, Object>> getAllTeams() {
        List<Map<String, Object>> teams = new ArrayList<>();

        String query = "SELECT team_name, member_emails, event_name FROM Teams";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Map<String, Object> teamData = new HashMap<>();

                // Extract team name
                String teamName = resultSet.getString("team_name");
                teamData.put("teamName", teamName);

                // Extract member emails and split into a list
                String memberEmails = resultSet.getString("member_emails");
                List<String> memberEmailList = Arrays.asList(memberEmails.split(","));
                teamData.put("memberEmails", memberEmailList);

                // Extract event name
                String eventName = resultSet.getString("event_name");
                teamData.put("eventName", eventName);

                // Add the team data to the list
                teams.add(teamData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teams;
    }

    public boolean addFeedback(int eventId, String comments, int rating) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isSuccess = false;

        try {
            // Establish connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // SQL query to insert feedback
            String query = "INSERT INTO Feedback (eventId, comments, rating) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);

            // Set the parameters
            preparedStatement.setInt(1, eventId);
            preparedStatement.setString(2, comments);
            preparedStatement.setInt(3, rating);

            // Execute the insert query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                isSuccess = true; // Feedback successfully added
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }

    public List<String> getFeedbackByEventId(int eventId) {
        List<String> feedbackList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Establish connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // SQL query to retrieve all feedbacks for a specific eventId
            String query = "SELECT comments, rating FROM Feedback WHERE eventId = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, eventId); // Set the eventId parameter

            resultSet = preparedStatement.executeQuery();

            // Loop through the result set and add each feedback as a string
            while (resultSet.next()) {
                String comments = resultSet.getString("comments");
                int rating = resultSet.getInt("rating");

                // Format the feedback string
                String feedbackString = "Rating: " + rating + " | Comments: " + comments;
                feedbackList.add(feedbackString);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return feedbackList; // Return the list of formatted feedback strings
    }


}
