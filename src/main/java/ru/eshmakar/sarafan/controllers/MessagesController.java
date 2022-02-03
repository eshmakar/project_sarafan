package ru.eshmakar.sarafan.controllers;

import org.springframework.web.bind.annotation.*;
import ru.eshmakar.sarafan.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("messages")
public class MessagesController {
    private int counter = 4;
    private List<Map<String, String>> messages = new ArrayList<>() {{
        add(new HashMap<>() {{
            put("id", "1");
            put("text", "First message");
        }});
        add(new HashMap<>() {{
            put("id", "2");
            put("text", "Second message");
        }});
        add(new HashMap<>() {{
            put("id", "3");
            put("text", "Third message");
        }});
    }};

    @GetMapping
    public List<Map<String, String>> list() {
        System.out.println(messages);
        return messages;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getMessage(id);
    }

    private Map<String, String> getMessage(@PathVariable String id) {
        return messages.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping//adding message
    public Map<String, String> create(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(counter++));
        messages.add(message);
        return message;
    }

    @PutMapping("{id}")//updating message
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message) {
        Map<String, String> messageFromDB = getMessage(id);
        messageFromDB.putAll(message);
        messageFromDB.put("id", id);
        return messageFromDB;
    }


    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Map<String, String> message = getOne(id);
        messages.remove(message);
    }
}
