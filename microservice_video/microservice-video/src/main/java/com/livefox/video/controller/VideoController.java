package com.livefox.video.controller;

import com.livefox.video.configuration.ApplicationPropertiesConfigurations;
import com.livefox.video.exception.VideoNotFoundException;
import com.livefox.video.model.Video;
import com.livefox.video.repository.VideoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class VideoController implements HealthIndicator {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    ApplicationPropertiesConfigurations appProperties;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/video")
    public List<Video> listVideo(){
        List<Video> video = videoRepository.findAll();
        if(video.isEmpty()) throw new VideoNotFoundException("Aucune video disponible");

        List<Video> listLimite = video.subList(0,appProperties.getLimitDeVideos());

        log.info("Find all videos");
        return listLimite ;
    }

    @GetMapping(value = "/autofilled")
    public void autofilled(){
        Video video1 = new Video(1,"Video drole","video drole path","pictDrole.jpg");
        Video video2 = new Video(2,"Video triste","video triste path","pictTriste.jpg");
        Video video3 = new Video(3,"Video romantique","video romantique path","pictRomantique.jpg");
        Video video4 = new Video(4,"Video sympa","video sympa path","pictSympa.jpg");
        Video video5 = new Video(5,"Video horreur","video horreur path","pictHorreur.jpg");
        videoRepository.save(video1);
        videoRepository.save(video2);
        videoRepository.save(video3);
        videoRepository.save(video4);
        videoRepository.save(video5);
        log.info("Autofilled MYSQL Databased");

    }

    @GetMapping(value = "/video/{id}")
    public Video showVideo(@PathVariable int id){
        Video video = videoRepository.findById(id);

        if(video == null) throw new VideoNotFoundException("Cette video n'existe pas");
        log.info("Request video by id: " + video.getId());
        return video;
    }

    @PostMapping(value = "/video/add")
    public void addVideo (@Valid @RequestBody Video video){
        videoRepository.save(video);
        log.info("add a video: " + video.toString());
    }

    @GetMapping(value = "/video/delete/{id]")
    public void deleteVideo(@PathVariable int id){
        log.info("delete the video id: " + id);
        videoRepository.deleteById(id);
    }

    @PostMapping(value = "/video/update/{id}")
    public void updateVideo (@RequestBody Video video){
        log.info("update a video: " + video.toString());
        videoRepository.save(video);
    }

    @Override
    public Health getHealth(boolean includeDetails) {
        return null;
    }

    @Override
    public Health health() {
        List<Video> videos = videoRepository.findAll();
        if(videos.isEmpty()){return Health.down().build();}
        return Health.up().build();
    }
}
