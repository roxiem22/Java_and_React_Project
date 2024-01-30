package ro.tuc.ds2020.dtos;

import java.util.Objects;
import java.util.UUID;
public class UserDTO {
    private UUID id;
    private String name;
    private String password;
    private String mail;
    private String role;

    private String token;

    public UserDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDTO(UUID id, String name, String password, String mail, String role, String token) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.mail = mail;
        this.password = password;
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", role='" + role + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id) && Objects.equals(name, userDTO.name) && Objects.equals(password, userDTO.password) && Objects.equals(mail, userDTO.mail) && Objects.equals(role, userDTO.role) && Objects.equals(token, userDTO.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, mail, role, token);
    }
}