package com.example.velotixdemo.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "logs")
public class LogModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "level")
    private String level;

    @Column(name = "dateTime")
    private String dateTime;

    @Lob//Used Lob instead VARCHAR while table creating. Lob can handle more than 255 symbols
    @Column(name = "message")
    private String message;


    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "LogModel{" +
                "level='" + level + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
