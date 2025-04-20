package com.example.sda_combine;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String teamName; // Name of the team
    private List<Participant> members; // Members of the team
    private boolean isVerified; // Verification status

    // Constructor
    public Team(String teamName) {
        this.teamName = teamName;
        this.members = new ArrayList<>();
        this.isVerified = false; // Default to unverified
    }

    // Overloaded constructor (if no team name is provided initially)
    public Team() {
        this("Unnamed Team");
    }

    // Getters and Setters
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Participant> getMembers() {
        return members;
    }

    public void setMembers(List<Participant> members) {
        this.members = members;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        this.isVerified = verified;
    }

    // Add a participant to the team
    public void addMember(Participant participant) {
        this.members.add(participant);
    }

    // Verify the team
    public void verifyTeam() {
        this.isVerified = true;
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamName='" + teamName + '\'' +
                ", members=" + members +
                ", isVerified=" + isVerified +
                '}';
    }
}
