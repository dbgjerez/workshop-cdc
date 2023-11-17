package dev.ryanezil.camel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class CommonUser implements Serializable {

    @JsonProperty
    private String _id;

    @JsonProperty
    private String dni;

    @JsonProperty
    private Long remoteId;

    @JsonProperty
    private String firstName;

    @JsonProperty
    private String lastName;

    @JsonProperty
    private List<Audit> enriched = new ArrayList<>();

    @JsonProperty
    private String email;

    @JsonProperty
    private String phone;

    @JsonProperty
    private String gender;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Long getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(Long remoteId) {
        this.remoteId = remoteId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDni() {
        return this.dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public List<Audit> getEnriched() {
        return this.enriched;
    }

    public void setEnriched(List<Audit> enriched) {
        this.enriched = enriched;
    }

    @Override
    public String toString() {
        return "CommonUser [_id=" + _id + ", remoteId=" + remoteId + ", firstName=" + firstName + ", lastName="
                + lastName + ", enriched=" + enriched + ", email=" + email + ", phone=" + phone + ", gender=" + gender
                + "]";
    }

}
