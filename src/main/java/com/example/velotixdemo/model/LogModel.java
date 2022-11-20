package com.example.velotixdemo.model;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "logs")
public class LogModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "level")
    private String level;

    @Column(name = "dateTime")
    private Timestamp dateTime;

    @Lob//Used Lob instead VARCHAR while table creating. Lob can handle more than 255 symbols
    @Column(name = "message")
    private String message;

    public LogModel() {
    }

    public LogModel(long id, String level, Timestamp dateTime, String message) {
        this.id = id;
        this.level = level;
        this.dateTime = dateTime;
        this.message = message;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
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
