package contacts.controller;

import contacts.entity.Contacts;
import contacts.service.ContactsService;
import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/contactservice/admin")
public class AdminContactsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminContactsController.class);

    @Autowired
    private ContactsService contactsService;

    @GetMapping("/welcome")
    public String home(@RequestHeader HttpHeaders headers){
        return "Welcome to [ Admin Contacts Service ]!";
    }

    @GetMapping("/contacts")
    public ResponseEntity<Response> getAllContacts(@RequestHeader HttpHeaders headers){
        LOGGER.info("[getAllContacts][Admin get all contacts]");
        return ok(contactsService.getAllContacts(headers));
    }

    @PostMapping("/contacts")
    public ResponseEntity<Response> addContacts(@RequestBody Contacts contacts,@RequestHeader HttpHeaders headers){
        LOGGER.info("[addContacts][Admin add contact][name: {}]",contacts.getName());
        return ok(contactsService.create(contacts, headers));
    }

    @PutMapping("/contacts")
    public ResponseEntity<Response> modifyContacts(@RequestBody Contacts contacts,@RequestHeader HttpHeaders headers){
        LOGGER.info("[modifyContacts][Admin modify contact][id: {}]",contacts.getId());
        return ok(contactsService.modify(contacts, headers));
    }

    @DeleteMapping("/contacts/{contactsId}")
    public ResponseEntity<Response> deleteContacts(@PathVariable String contactsId,@RequestHeader HttpHeaders headers){
        LOGGER.info("[deleteContacts][Admin delete contact][id: {}]",contactsId);
        return ok(contactsService.delete(contactsId, headers));
    }
}
