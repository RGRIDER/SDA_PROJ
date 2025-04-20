package com.example.sda_combine;
import java.util.Map;
import java.util.HashMap;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    // Singleton instance
    private static Controller instance;

    // Private constructor to prevent direct instantiation
    private Controller() {

        initializeUserLists();

    }

    // Public method to provide access to the singleton instance
    public static Controller getInstance() {
        if (instance == null) {
            synchronized (Controller.class) { // Ensures thread safety
                if (instance == null) {
                    instance = new Controller();
                }
            }
        }
        return instance;
    }

    private List<Admin> admins = new ArrayList<>();
    private List<Participant> participants = new ArrayList<>();
    private List<Organizer> organizers = new ArrayList<>();
    private final Database db = new Database();
    private List<Event> events = new ArrayList<>(); // Initialize events list
    private  List<Team> teams= new ArrayList<>();

    public void initializeUserLists() {
        this.admins = db.fetchAdmins();          // Populate the admins list
        this.participants = db.fetchParticipants(); // Populate the participants list
        this.organizers = db.fetchOrganizers();     // Populate the organizers list
        this.events=db.fetchEvents();
        fetchAnnouncements();


        initializeTeamsFromDatabase();

        System.out.println("ALL the lists initialized from the database.");
    }

    public boolean eventExistsByName(String eventName) {

        for (Event event : events) {
            if (event.getName().equalsIgnoreCase(eventName)) {
                return true;
            }
        }
        return false; // Event not found
    }


    public Event createEvent(String organizerEmail, String eventName, String date, String startTime,
                             String endTime, String venueName, String description, int teamSize,
                             int teamLimit, int registrationFee, String accountNumber) {
        // Fetch the max eventId from the database
        int maxEventId = db.fetchMaxEventIdFromDatabase();

        // Generate the next event ID (maxEventId + 1)
        int nextEventId = maxEventId + 1;

        // Create Event
        int eventId = nextEventId;
        String venue = new String(venueName); // Assuming a Venue class with name as an attribute
        DateTime dateTime = new DateTime(date, startTime, endTime);
        Event event = new Event(eventId, eventName, organizerEmail, description, venue, dateTime, teamSize, teamLimit, registrationFee, accountNumber);

        // Save event details to the database
        db.saveEventDetails(event);  // Assuming this is the method you created to save the event to DB

        // Add event to the list (if necessary)
        events.add(event);

        return event;
    }

    public List<String> getEventsByOrganizerEmail(String organizerEmail) {
        List<String> eventNames = new ArrayList<>();

        // Iterate through all events and check if the organizer's email matches
        for (Event event : events) {
            if (event.getOrganizerEmail().equalsIgnoreCase(organizerEmail)) {
                eventNames.add(event.getName());  // Add event name to the list
            }
        }

        return eventNames; // Return the list of event names
    }

    public void fetchAnnouncements() {

        Map<Integer, List<String>> announcementsMap = db.getAnnouncements();

        if (announcementsMap == null || announcementsMap.isEmpty()) {
            System.out.println("No announcements found in the database.");
            return;
        }

        // Assign announcements to the corresponding events
        for (Event event : events) {
            List<String> eventAnnouncements = announcementsMap.get(event.getEventId());
            if (eventAnnouncements != null) {
                event.setAnnouncements(eventAnnouncements); // Assuming Event has a setAnnouncements method
            }
        }

        System.out.println("Announcements successfully fetched and assigned to events.");
    }

    public Event getEventById(int eventId) {
        for (Event event : events) {
            if (event.getEventId()==eventId) {
                return event;
            }
        }
        return null; // Event not found
    }

    public Event getEventByName(String eventName) {
        for (Event event : events) {
            if (event.getName().equals(eventName)) {
                return event;
            }
        }
        return null; // Event not found
    }

    public List<String> generateFullReport() {
        List<String> reports = new ArrayList<>();

        for (Event event : events) {
            String report = generateEventReport(event.getEventId());
            reports.add(report);
        }

        return reports;
    }

    public String generateEventReport(int eventId) {
        Event event = getEventById(eventId);
        if (event == null) {
            return "Event not found!";
        }

        // Calculate the total registration fee
        int totalRegistrationFee = event.getVerifiedTeamsize() * event.getRegistrationFee();

        // Build the report string with better formatting
        StringBuilder report = new StringBuilder();
        report.append("Event Report:\n");
        report.append("  Name: ").append(event.getName()).append("\n");
        report.append("  Organizer: ").append(event.getOrganizerEmail()).append("\n");
        report.append("  Venue: ").append(event.getVenue()).append("\n");
        report.append("  Date and Time: ").append(event.getDateTime()).append("\n");
        report.append("  Registration Fee (Per Team): ").append(event.getRegistrationFee()).append("\n");
        report.append("  Total Registration Fee: ").append(totalRegistrationFee).append("\n");

        // Add team details if available
        report.append("  Teams Enrolled: ").append(event.getVerifiedTeamsize()).append("\n");
        if (event.getVerifiedTeamsize() > 0) {
            report.append("  Team Details:\n");
            for (Team team : event.getVerifiedTeams()) {
                report.append("    - Team Leader: ").append(team.getMembers().getFirst().getFirstName()).append("\n");
                for (Participant member : team.getMembers()) {
                    report.append("      Member: ").append(member.getFirstName()).append("\n");
                }
            }
        }

        return report.toString();
    }



    public boolean registerUser(String firstName, String lastName, String email, String password, String userType) {
        int currentUserId = db.fetchMaxUserIdFromDatabase();
        currentUserId += 1;

        if (userExists(email, password, userType)) {
            System.out.println("User already registered with this role. Registration skipped.");
            return false;
        }

        // Generate a new unique user ID
        String userId = String.valueOf(currentUserId);

        // Add user to the appropriate list and database
        switch (userType.toLowerCase()) {
            case "admin":
                Admin admin = new Admin(userId, firstName, lastName, email, password);
                admins.add(admin);
                db.registerUser(userId, firstName, lastName, email, password, userType);
                break;

            case "participant":
                Participant participant = new Participant(userId, firstName, lastName, email, password);
                participants.add(participant);
                db.registerUser(userId, firstName, lastName, email, password, userType);
                break;

            case "organizer":
                Organizer organizer = new Organizer(userId, firstName, lastName, email, password);
                organizers.add(organizer);
                db.registerUser(userId, firstName, lastName, email, password, userType);
                break;

            default:
                System.out.println("Invalid user type provided. Registration failed.");
                break;
        }
        return true;
    }

    private boolean userExists(String email, String password, String userType) {
        switch (userType.toLowerCase()) {
            case "admin":
                for (Admin admin : admins) {
                    if (admin.getEmail().equals(email)) {
                        return true;
                    }
                }
                break;

            case "participant":
                for (Participant participant : participants) {
                    if (participant.getEmail().equals(email)) {
                        return true;
                    }
                }
                break;

            case "organizer":
                for (Organizer organizer : organizers) {
                    if (organizer.getEmail().equals(email)) {
                        return true;
                    }
                }
                break;

            default:
                System.out.println("Invalid user type provided for existence check.");
                break;
        }

        return false; // User not found in the specific role
    }

    public boolean Login(String email, String password, String userType) {
        // Determine which list to check based on the user type
        switch (userType) {
            case "Admin":
                for (Admin admin : admins) {
                    if (admin.getEmail().equals(email) && admin.getPassword().equals(password)) {
                        return true; // User exists and credentials match
                    }
                }
                break;
            case "Participant":
                for (Participant participant : participants) {
                    if (participant.getEmail().equals(email) && participant.getPassword().equals(password)) {
                        return true; // User exists and credentials match
                    }
                }
                break;
            case "Organizer":
                for (Organizer organizer : organizers) {
                    if (organizer.getEmail().equals(email) && organizer.getPassword().equals(password)) {
                        return true; // User exists and credentials match
                    }
                }
                break;
            default:
                return false; // Invalid user type
        }

        return false; // User not found or credentials don't match
    }

    public List<String> getEventDetailsByName(String eventName) {
        for (Event event : events) {
            if (event.getName().equalsIgnoreCase(eventName)) { // Match event name (case-insensitive)
                List<String> eventDetails = new ArrayList<>();
                eventDetails.add(event.getDateTime().getStartTime()); // Start Time
                eventDetails.add(event.getDateTime().getEndTime());   // End Time
                eventDetails.add(event.getVenue());                  // Venue
                eventDetails.add(event.getDescription());            // Description
                return eventDetails;
            }
        }
        throw new IllegalArgumentException("Event with name '" + eventName + "' does not exist.");
    }
    public void updateEventDetails(String eventName, String startTime, String endTime, String venue, String description) {
        for (Event event : events) {
            if (event.getName().equalsIgnoreCase(eventName)) {
                event.getDateTime().setStartTime(startTime);
                event.getDateTime().setEndTime(endTime);
                event.setVenue(venue);
                event.setDescription(description);

                // Call the database update function
                Database db = new Database();
                db.updateEventInDatabase(event.getEventId(), startTime, endTime, venue, description);

                return;
            }
        }
        throw new IllegalArgumentException("Event with name '" + eventName + "' does not exist.");
    }

    public boolean addAnnouncement(String eventName, String organizerId, String message) {
        // Retrieve the event by name
        Event event = getEventByName(eventName);

        // Check if the event exists
        if (event == null) {
            System.out.println("Event not found!");
            return false;
        }

        // Add the announcement to the in-memory event object
        event.addAnnouncement(message);

        // Call the database method to update the announcements table

        boolean dbUpdateSuccessful = db.insertAnnouncement(event.getEventId(), organizerId, message);

        if (!dbUpdateSuccessful) {
            System.out.println("Failed to add the announcement to the database!");
            return false;
        }

        System.out.println("Announcement added successfully to the event: " + event.getName());
        return true;
    }

    public List<String> getAllEventNames() {
        List<String> eventNames = new ArrayList<>();

        // Loop through each event and add its name to the list
        for (Event event : events) {
            eventNames.add(event.getName());
        }

        return eventNames;
    }

    public int getTeamSizeByEventName(String eventName) {
        // Loop through the events to find the matching event by name
        for (Event event : events) {
            if (event.getName().equals(eventName)) {
                return event.getTeamSize();  // Return the team size of the found event
            }
        }

        // If the event is not found, return -1 or handle it as appropriate
        System.out.println("Event not found: " + eventName);
        return -1;  // Or throw an exception depending on how you want to handle this case
    }

    public void addTeamToEvent(String eventName, String teamName, List<String> memberEmails) {
        // Find the event by name
        Event event = getEventByName(eventName);

        if (event != null) {
            // Create a new team
            Team team = new Team(teamName);

            // Add members to the team
            for (String email : memberEmails) {
                // Assuming Participant class has an email constructor
                team.addMember(getParticipantByEmail(email));
            }

            // Add the created team to the event
            event.addTeam(team);

            // Persist team to database

            boolean isSaved = db.addTeamToDatabase(eventName, teamName, memberEmails);

            if (isSaved) {
                System.out.println("Team " + teamName + " added to event " + eventName + " and saved to database.");
            } else {
                System.out.println("Failed to save team to database.");
            }
        } else {
            System.out.println("Event not found: " + eventName);
        }
    }

    public Participant getParticipantByEmail(String email) {
        for (Participant participant : participants) {
            if (participant.getEmail().equals(email)) {
                return participant;  // Return the participant if the email matches
            }
        }
        return null;  // Return null if no participant with the given email is found
    }

    public boolean doesParticipantExist(String participantEmail) {
        // Check if the participant email exists in the list of participants
        for (Participant participant : participants) {
            if (participant.getEmail().equalsIgnoreCase(participantEmail)) {
                return true; // Participant exists
            }
        }
        return false; // Participant does not exist
    }

    public void initializeTeamsFromDatabase() {
        // Fetch all teams from the database
        List<Map<String, Object>> teamDataList = db.getAllTeams();

        // Iterate over the fetched team data
        for (Map<String, Object> teamData : teamDataList) {
            // Extract team name and member emails
            String teamName = (String) teamData.get("teamName");
            List<String> memberEmails = (List<String>) teamData.get("memberEmails");
            String eventName = (String) teamData.get("eventName");

            // Create a new Team instance
            Team team = new Team(teamName);

            // Add participants to the team
            for (String email : memberEmails) {
                Participant participant = getParticipantByEmail(email); // Helper method to find participant
                if (participant != null) {
                    team.getMembers().add(participant);
                }
            }

            teams.add(team);
            // Traverse the event list to find the event with the same name
            for (Event event : events) {
                if (event.getName().equals(eventName)) {
                    // Add the team to the event's team list
                    event.addTeam(team);
                    break; // Stop traversing once the matching event is found
                }
            }
        }
    }

    public List<Integer> getParticipantEventIds(String participantEmail) {

        List<Integer> enrolledEventIds = new ArrayList<>(); // To store the event IDs

        // Iterate through the events
        for (Event event : events) {
            boolean isParticipantInEvent = false;

            // Iterate through the teams in the event
            for (Team team : event.getVerifiedTeams()) {
                // Check if the participant is a member of the team
                for (Participant member : team.getMembers()) {
                    if (member.getEmail().equals(participantEmail)) {
                        isParticipantInEvent = true;
                        break;
                    }
                }

                if (isParticipantInEvent) {
                    enrolledEventIds.add(event.getEventId());
                    break; // No need to check further teams for this event
                }
            }
        }

        return enrolledEventIds;
    }

    public List<String> getParticipantAnnouncements(String participantEmail) {
        List<String> participantAnnouncements = new ArrayList<>();

        // Get all event IDs the participant is enrolled in
        List<Integer> eventIds = getParticipantEventIds(participantEmail);

        // Iterate through the event IDs
        for (int eventId : eventIds) {
            // Get the corresponding event from the events list
            Event event = null;
            for (Event e : events) {
                if (e.getEventId() == eventId) {
                    event = e;
                    break;
                }
            }

            if (event != null) {
                // Add the event name as a header
                participantAnnouncements.add("Event Name: " + event.getName());

                // Fetch announcements for the event
                List<String> announcements = db.getAnnouncementsByEventId(eventId);

                // Add announcements to the result, properly formatted
                if (!announcements.isEmpty()) {
                    for (String announcement : announcements) {
                        participantAnnouncements.add("- " + announcement);
                    }
                } else {
                    participantAnnouncements.add("  No announcements for this event.");
                }

                // Add a blank line for separation
                participantAnnouncements.add("");
            }
        }

        return participantAnnouncements;
    }


    public boolean submitFeedback(String eventName, String feedbackText, int rating, String participantEmail) {
        // Find the event by name
        Event event = getEventByName(eventName);

        if (event == null) {
            return false; // Event not found
        }

        int eventId = event.getEventId();

        // Call the Database class to insert feedback into the database
        boolean isFeedbackInserted = db.addFeedback(eventId, feedbackText, rating);

        if (isFeedbackInserted) {
            // Optionally: Add the feedback to the event's feedback list
            Feedback feedback = new Feedback(eventId, feedbackText, rating);
            event.addFeedback(feedback);
        }

        return isFeedbackInserted; // Return the result of feedback insertion
    }

    private int getEventIdByName(String eventName) {
        // Assuming you have a method that retrieves an event by name,
        // and returns its eventId
        Event event = getEventByName(eventName);
        if (event != null) {
            return event.getEventId();
        }
        return -1; // Return -1 if event is not found
    }

    // Method to get all feedbacks for an event by its name
    public List<String> getFeedbacksForEventByName(String eventName) {
        // Get the eventId for the given event name
        int eventId = getEventIdByName(eventName);

        // If eventId is invalid, return an empty list or handle it accordingly
        if (eventId == -1) {
            return List.of("Event not found");
        }

        // Call the Database method to retrieve feedbacks for the eventId
        return db.getFeedbackByEventId(eventId);
    }

}
