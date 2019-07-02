package info.johnnywinters.taskmaster;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class TaskController {
    private final String[] status = {"Available", "Assigned", "Accepted", "Finished"};


    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    TaskRepository taskRepository;

    // Specify the route for this method
    @GetMapping("/hello")
    public String getHelloWorld(){
        return "Hello, World!";
    }


    @GetMapping("/tasks")
    public List<Task> getTasks(){
        return (List<Task>) taskRepository.findAll();
    }

    @PostMapping("/tasks")
    public @ResponseBody Task setTask(@ModelAttribute Task task){
        task.setStatus(status[0]);
        taskRepository.save(task);
        return taskRepository.findById(task.getId()).get();
    }

    @PutMapping("/tasks/{id}/state")
    public void advanceTaskStatus(@PathVariable String id){
        Task currentTask = taskRepository.findById(id).get();
        int statusIndex = Arrays.asList(status).indexOf(currentTask.getStatus());
        if(statusIndex <= 2) currentTask.setStatus(status[statusIndex+1]);
        taskRepository.save(currentTask);
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable String id){
        taskRepository.deleteById(id);
    }
}
