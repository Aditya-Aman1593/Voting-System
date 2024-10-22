package com.voting.model;

import java.time.LocalDate;

public class Candidate {
    private int id;
    private String fullName;
    private LocalDate dob;
    private String nationality;
    private String partyName;
    private String partyLogo;
    private int votes;


    public Candidate() {
    }

    public Candidate(String fullName, LocalDate dob, String nationality, String partyName, String partyLogo) {
        this.fullName = fullName;
        this.dob = dob;
        this.nationality = nationality;
        this.partyName = partyName;
        this.partyLogo = partyLogo;
        this.votes = 0; // Default value
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyLogo() {
        return partyLogo;
    }

    public void setPartyLogo(String partyLogo) {
        this.partyLogo = partyLogo;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int calculateAge() {
        return LocalDate.now().getYear() - this.dob.getYear();
    }
   
}
