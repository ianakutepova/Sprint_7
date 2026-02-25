package model;

import lombok.Data;

@Data
public class TrackModel {
    private String track;

    public TrackModel(String track) {
        this.track = track;
    }
}