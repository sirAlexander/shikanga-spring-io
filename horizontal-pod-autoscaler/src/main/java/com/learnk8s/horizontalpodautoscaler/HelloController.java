package com.learnk8s.horizontalpodautoscaler;

import com.learnk8s.horizontalpodautoscaler.queue.QueueService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.LongStream;

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
    public String home(Model model) {
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

    @PostMapping("/submit")
    public String submit(@ModelAttribute Ticket ticket) {

        LongStream.rangeClosed(1, ticket.getQuantity())
                .forEach(i -> {
                    String id = UUID.randomUUID().toString();
                    queueService.send(queueName, id);
                });
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/metrics", produces = "text/plain")
    public String metrics() {
        int totalMessages = queueService.pendingJobs(queueName);
        return "# HELP messages Number of messages in the queueService\n"
                + "# TYPE messages gauge\n"
                + "messages " + totalMessages;
    }


    @RequestMapping(value = "/health")
    public ResponseEntity health() {
        HttpStatus status;
        if (queueService.isUp()) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity(status);
    }
}
