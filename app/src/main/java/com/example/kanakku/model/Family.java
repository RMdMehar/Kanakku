package com.example.kanakku.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.List;
/**
 * Family POJO
 */
@IgnoreExtraProperties
public class Family {
    private String familyName;
    private int secretCode;
    private List<String> membersDocID;

    public Family() {}

    public Family(String familyName, int secretCode, List<String> membersDocID) {
        this.familyName = familyName;
        this.secretCode = secretCode;
        this.membersDocID = membersDocID;
    }

    public String getFamilyName() {
        return familyName;
    }

    public int getSecretCode() {
        return secretCode;
    }

    public List<String> getMembersDocID() {
        return membersDocID;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setSecretCode(int secretCode) {
        this.secretCode = secretCode;
    }

    public void setMembersDocID(List<String> membersDocID) {
        this.membersDocID = membersDocID;
    }
}
