package info.johnnywinters.taskmaster;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class TaskController {

    private S3Client s3Client;

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskController(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    // Specify the route for this method
    @GetMapping("/hello")
    public String getHelloWorld(){
        return "Hello, World!";
    }


    @GetMapping("/tasks")
    public List<Task> getTasks(){
        return (List<Task>) taskRepository.findAll();
    }


    @GetMapping("/users/{name}/tasks")
    public List<Task> getUsersTasks(@PathVariable String name){
        return taskRepository.findByAssignee(name);
    }

    @PostMapping("/tasks")
    public @ResponseBody Task setTask(@ModelAttribute Task task){
        taskRepository.save(task);
        return taskRepository.findById(task.getId()).get();
    }

    @PostMapping("/tasks/{id}/images")
    public @ResponseBody Task uploadImage(@PathVariable String id, @RequestPart(value = "file") MultipartFile file){
        String pic = this.s3Client.uploadFile(file);
        Task task = taskRepository.findById(id).get();
        task.setPic(pic);
        taskRepository.save(task);
        return task;
    }

    @PutMapping("/tasks/{id}/state/{assignee}")
    public void assignTask(@PathVariable String id, @PathVariable String assignee){
        Task currentTask = taskRepository.findById(id).get();
        currentTask.setAssignee(assignee);
        taskRepository.save(currentTask);
    }

    @PutMapping("/tasks/{id}/state")
    public void advanceTaskStatus(@PathVariable String id){
        Task currentTask = taskRepository.findById(id).get();
        currentTask.incrementStatus();
        taskRepository.save(currentTask);
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable String id){
        taskRepository.deleteById(id);
    }
}
