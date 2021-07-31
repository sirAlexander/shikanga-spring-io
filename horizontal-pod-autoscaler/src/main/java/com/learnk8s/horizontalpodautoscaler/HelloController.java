package com.learnk8s.horizontalpodautoscaler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    private final QueueService queueService;

    @Value("${queue.name}")
    private String queueName;

    @Value("${worker.name}")
    private String workerName;

    @Value("${store.enabled}")
    private boolean storeEnabled;

    @Value("${worker.enabled}")
    private boolean workerEnabled;

    public HelloController(QueueService queueService) {
        this.queueService = queueService;
    }

    @GetMapping("/")
    public String home(Model model){
        int pendingMessages = queueService.pendingJobs(queueName);
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("pendingJobs", pendingMessages);
        model.addAttribute("completedJobs", queueService.completedJobs());
        model.addAttribute("isConnected", queueService.isUp() ? "yes" : "no");
        model.addAttribute("queueName", this.queueName);
        model.addAttribute("workerName", this.workerName);
        model.addAttribute("isStoreEnabled", this.storeEnabled);
        model.addAttribute("isWorkerEnabled", this.workerEnabled);
        return "home";
    }

    @RequestMapping(value = "/health")
    public ResponseEntity health() {
        HttpStatus status;
        if(queueService.isUp()){
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity(status);
    }
}
