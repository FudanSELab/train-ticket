package contacts.service;

import contacts.entity.*;
import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import contacts.repository.ContactsRepository;

import java.util.ArrayList;
import java.util.UUID;


/**
 * @author fdse
 */
@Service
public class ContactsServiceImpl implements ContactsService {

    @Autowired
    private ContactsRepository contactsRepository;

    String success = "Success";

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactsServiceImpl.class);

    @Override
    public Response findContactsById(UUID id, HttpHeaders headers) {
        LOGGER.info("FIND CONTACTS BY ID: " + id);
        Contacts contacts = contactsRepository.findById(id);
        if (contacts != null) {
            return new Response<>(1, success, contacts);
        } else {
            LOGGER.error("No contacts according to contactsId: {}", id);
            return new Response<>(0, "No contacts according to contacts id", null);
        }
    }

    @Override
    public Response findContactsByAccountId(UUID accountId, HttpHeaders headers) {
        ArrayList<Contacts> arr = contactsRepository.findByAccountId(accountId);
        ContactsServiceImpl.LOGGER.info("[Contacts-Query-Service][Query-Contacts] Result Size: {}", arr.size());
        return new Response<>(1, success, arr);
    }

    @Override
    public Response createContacts(Contacts contacts, HttpHeaders headers) {
        Contacts contactsTemp = contactsRepository.findById(contacts.getId());
        if (contactsTemp != null) {
            ContactsServiceImpl.LOGGER.warn("[Contacts Service][Init Contacts] Already Exists Id: {}", contacts.getId());
            return new Response<>(0, "Already Exists", contactsTemp);
        } else {
            contactsRepository.save(contacts);
            return new Response<>(1, "Create Success", null);
        }
    }

    @Override
    public Response create(Contacts addContacts, HttpHeaders headers) {
        Contacts contacts = new Contacts();
        contacts.setId(UUID.randomUUID());
        contacts.setName(addContacts.getName());
        contacts.setPhoneNumber(addContacts.getPhoneNumber());
        contacts.setDocumentNumber(addContacts.getDocumentNumber());
        contacts.setAccountId(addContacts.getAccountId());
        contacts.setDocumentType(addContacts.getDocumentType());

        ArrayList<Contacts> accountContacts = contactsRepository.findByAccountId(addContacts.getAccountId());

        if (accountContacts.contains(contacts)) {
            ContactsServiceImpl.LOGGER.warn("[Contacts-Add&Delete-Service][AddContacts] Fail.Contacts already exists, contactId: {}", addContacts.getId());
            return new Response<>(0, "Contacts already exists", null);
        } else {
            contactsRepository.save(contacts);
            ContactsServiceImpl.LOGGER.info("[Contacts-Add&Delete-Service][AddContacts] Success.");
            return new Response<>(1, "Create contacts success", contacts);
        }
    }

    @Override
    public Response delete(UUID contactsId, HttpHeaders headers) {
        contactsRepository.deleteById(contactsId);
        Contacts contacts = contactsRepository.findById(contactsId);
        if (contacts == null) {
            ContactsServiceImpl.LOGGER.info("[Contacts-Add&Delete-Service][DeleteContacts] Success.");
            return new Response<>(1, "Delete success", contactsId);
        } else {
            ContactsServiceImpl.LOGGER.error("[Contacts-Add&Delete-Service][DeleteContacts] Fail.Reason not clear, contactsId: {}", contactsId);
            return new Response<>(0, "Delete failed", contactsId);
        }
    }

    @Override
    public Response modify(Contacts contacts, HttpHeaders headers) {
        headers = null;
        Response oldContactResponse = findContactsById(contacts.getId(), headers);
        LOGGER.info(oldContactResponse.toString());
        Contacts oldContacts = (Contacts) oldContactResponse.getData();
        if (oldContacts == null) {
            ContactsServiceImpl.LOGGER.error("[Contacts-Modify-Service][ModifyContacts] Fail.Contacts not found, contactId: {}", contacts.getId());
            return new Response<>(0, "Contacts not found", null);
        } else {
            oldContacts.setName(contacts.getName());
            oldContacts.setDocumentType(contacts.getDocumentType());
            oldContacts.setDocumentNumber(contacts.getDocumentNumber());
            oldContacts.setPhoneNumber(contacts.getPhoneNumber());
            contactsRepository.save(oldContacts);
            ContactsServiceImpl.LOGGER.info("[Contacts-Modify-Service][ModifyContacts] Success.");
            return new Response<>(1, "Modify success", oldContacts);
        }
    }

    @Override
    public Response getAllContacts(HttpHeaders headers) {
        ArrayList<Contacts> contacts = contactsRepository.findAll();
        if (contacts != null && !contacts.isEmpty()) {
            return new Response<>(1, success, contacts);
        } else {
            LOGGER.error("Get all contacts error, message: {}", "No content");
            return new Response<>(0, "No content", null);
        }
    }

}


