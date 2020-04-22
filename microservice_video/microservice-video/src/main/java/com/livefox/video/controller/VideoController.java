package com.livefox.video.controller;

import com.livefox.video.configurations.ApplicationPropertiesConfigurations;
import com.livefox.video.exception.VideoNotFoundException;
import com.livefox.video.model.Video;
import com.livefox.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class VideoController {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    ApplicationPropertiesConfigurations appProperties;

    @GetMapping(value = "/video")
    public List<Video> listVideo(){
        List<Video> video = videoRepository.findAll();
        if(video.isEmpty()) throw new VideoNotFoundException("Aucune video disponible");

        List<Video> listLimite = video.subList(0,appProperties.getLimitDeVideos());
        return listLimite ;
    }

    @GetMapping(value = "/autofilled")
    public void autofilled(){
        Video video1 = new Video(1,"Video drole","video drole path","https://i.pinimg.com/originals/cd/cd/df/cdcddfdd339538200ff675e47c62ab4a.jpg");
        Video video2 = new Video(2,"Video triste","video triste path","https://i.pinimg.com/originals/cd/cd/df/cdcddfdd339538200ff675e47c62ab4a.jpg");
        Video video3 = new Video(3,"Video romantique","video romantique path","https://i.pinimg.com/originals/cd/cd/df/cdcddfdd339538200ff675e47c62ab4a.jpg");
        Video video4 = new Video(4,"Video sympa","video sympa path","https://i.pinimg.com/originals/cd/cd/df/cdcddfdd339538200ff675e47c62ab4a.jpg");
        Video video5 = new Video(5,"Video horreur","video horreur path","https://i.pinimg.com/originals/cd/cd/df/cdcddfdd339538200ff675e47c62ab4a.jpg");
        videoRepository.save(video1);
        videoRepository.save(video2);
        videoRepository.save(video3);
        videoRepository.save(video4);
        videoRepository.save(video5);

    }

    @GetMapping(value = "/video/{id}")
    public Video showVideo(@PathVariable int id){
        Video video = videoRepository.findById(id);

        if(video == null) throw new VideoNotFoundException("Cette video n'existe pas");
        return video;
    }

    @PostMapping(value = "/video/add")
    public void addVideo (@Valid @RequestBody Video video){
        videoRepository.save(video);
    }

    @GetMapping(value = "/video/delete/{id]")
    public void deleteVideo(@PathVariable int id){
        videoRepository.deleteById(id);
    }

    @PostMapping(value = "/video/update/{id}")
    public void updateVideo (@RequestBody Video video){
        videoRepository.save(video);
    }
}
