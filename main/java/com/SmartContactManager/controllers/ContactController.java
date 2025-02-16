package com.SmartContactManager.controllers;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.SmartContactManager.entities.Contact;
import com.SmartContactManager.entities.User;
import com.SmartContactManager.forms.ContactForm;
import com.SmartContactManager.forms.ContactSearchForm;
import com.SmartContactManager.helpers.AppConatants;
import com.SmartContactManager.helpers.Helper;
import com.SmartContactManager.helpers.Message;
import com.SmartContactManager.helpers.MessageType;
import com.SmartContactManager.services.ContactService;
import com.SmartContactManager.services.ImageServices;
import com.SmartContactManager.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
@RequestMapping("/user/contacts")
public class ContactController {


     private  Logger logger = LoggerFactory.getLogger(ContactController.class);



       @Autowired
       private ContactService contactService;

       @Autowired
       private ImageServices imageServices;

       @Autowired
       private UserService userService;
      // Adding contact page in profile
      @RequestMapping("/add")
      public String addContact(Model model)
      {
            ContactForm contactForm = new ContactForm();
            // contactForm.setName("Sahil raj");
            // contactForm.setFavorite(true);
            model.addAttribute("contactForm", contactForm);
           return "user/addcontact";
      }


        @RequestMapping(value = "/add", method=RequestMethod.POST)
        public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult bindingResult ,Authentication authentication , HttpSession httpSession) 
        {


             if(bindingResult.hasErrors())
             {
                httpSession.setAttribute("message", Message.builder()
                .content("Something went wrong")
                .type(MessageType.red)
                .build());
                 return "user/addcontact";
             }



              // process the form data 
              String username= Helper.getEmailOfLogedUser(authentication);


             User user=userService.getUserByEmail(username);
 
              // Image processing 
             
               logger.info("file information :{}" , contactForm.getContactImage().getOriginalFilename());
               String filename= UUID.randomUUID().toString();
               String fileUrl= imageServices.uploadImage(contactForm.getContactImage(),filename);


             Contact contact=new Contact();
             contact.setName(contactForm.getName());
             contact.setFavorite(contactForm.isFavorite());
             contact.setEmail(contactForm.getEmail());
             contact.setPhoneNumber(contactForm.getPhoneNumber());
             contact.setAddress(contactForm.getAddress());
             contact.setDescription(contactForm.getDescription());
             contact.setLinkedInLink(contactForm.getLinkedInLink());
             contact.setWebsiteLink(contactForm.getWebsiteLink());
             contact.setPicture(fileUrl);
             contact.setCloudinaryImagePublicId(filename);
             contact.setUser(user);

             contactService.save(contact);    
             System.out.println(contactForm);
               httpSession.setAttribute("message", Message.builder()
               .content("Contact Details add Successfull")
               .type(MessageType.green)
               .build());
            return "redirect:/user/contacts/add";
        }
        
        // view contact 

        @RequestMapping
        public String viewContacts(
        @RequestParam(value="page",defaultValue = "0")int page,
        @RequestParam(value="size",defaultValue =AppConatants.PAGE_SIZE+ "" ) int size,
        @RequestParam(value="sortBy",defaultValue = "name" ) String sortBy,
        @RequestParam(value="direction",defaultValue = "asc") String direction, Model model ,Authentication authentication)
        {
            String username = Helper.getEmailOfLogedUser(authentication);
            User user = userService.getUserByEmail(username);
             Page<Contact> pageContact= contactService.getByUser(user,page,size,sortBy,direction);
             model.addAttribute("pageContact", pageContact);
             model.addAttribute("pageSize", AppConatants.PAGE_SIZE);

             model.addAttribute("contactSearchForm", new ContactSearchForm());
             return "user/contacts";
        }


        @RequestMapping("/search")
        public String searchContacts(
        @ModelAttribute ContactSearchForm contactSearchForm,
        @RequestParam(value = "size" , defaultValue = AppConatants.PAGE_SIZE + "") int size,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
        @RequestParam(value = "direction", defaultValue = "asc") String direction,
        Model model , Authentication authentication)
        {
          

           logger.info("field {}  keywords :", contactSearchForm.getField() ,contactSearchForm.getValue() );

            var user= userService.getUserByEmail(Helper.getEmailOfLogedUser(authentication));
          
            Page<Contact> pageContact=null;
            if(contactSearchForm.getField().equalsIgnoreCase("name"))
            {
                pageContact = contactService.searchByName(contactSearchForm.getValue(), size , page , sortBy, direction,user);
            }
            else if(contactSearchForm.getField().equalsIgnoreCase("email"))
            {
              pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size , page , sortBy, direction,user);
            }
            else if(contactSearchForm.getField().equalsIgnoreCase("phone"))
            {
              pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size , page , sortBy, direction,user);
            }

            model.addAttribute("contactSearchForm", contactSearchForm);

            model.addAttribute("pageContact", pageContact);
           
            model.addAttribute("pageSize", AppConatants.PAGE_SIZE);
          
           return "user/search";
        

   }


         
         // detete contact
        @RequestMapping("/delete/{contactId}")
         public String deleteContact(
            @PathVariable("contactId") String contactId , HttpSession session) {
        contactService.delete(contactId);
        logger.info("contactId {} deleted", contactId);

        session.setAttribute("message",
                Message.builder()
                        .content("Contact is Deleted successfully !! ")
                        .type(MessageType.green)
                        .build()

        );

        return "redirect:/user/contacts";
    }

     
      
    // // update contact form view
    // @GetMapping("/view/{contactId}")
    // public String updateContactFormView(
    //         @PathVariable("contactId") String contactId,
    //         Model model) {

    //     var contact = contactService.getById(contactId);
    //     ContactForm contactForm = new ContactForm();
    //     contactForm.setName(contact.getName());
    //     contactForm.setEmail(contact.getEmail());
    //     contactForm.setPhoneNumber(contact.getPhoneNumber());
    //     contactForm.setAddress(contact.getAddress());
    //     contactForm.setDescription(contact.getDescription());
    //     contactForm.setFavorite(contact.isFavorite());
    //     contactForm.setWebsiteLink(contact.getWebsiteLink());
    //     contactForm.setLinkedInLink(contact.getLinkedInLink());
    //     contactForm.setPicture(contact.getPicture());
      
    //     model.addAttribute("contactForm", contactForm);
    //     model.addAttribute("contactId", contactId);

    //     return "user/updateContactView";
    // }


    // // edit contact form 

    //  @RequestMapping(value = "/update/{contactId}", method = RequestMethod.POST)
    //  public String updateContact(@PathVariable("contactId") String contactId,
    //          @Valid @ModelAttribute ContactForm contactForm,
    //        BindingResult bindingResult,
    //          Model model) {

    // //     // update the contact
    //     if (bindingResult.hasErrors()) {
    //         return "user/update_contact_view";
    //     }

    //     var con = contactService.getById(contactId);
    //     con.setId(contactId);
    //     con.setName(contactForm.getName());
    //     con.setEmail(contactForm.getEmail());
    //     con.setPhoneNumber(contactForm.getPhoneNumber());
    //     con.setAddress(contactForm.getAddress());
    //     con.setDescription(contactForm.getDescription());
    //     con.setFavorite(contactForm.isFavorite());
    //     con.setWebsiteLink(contactForm.getWebsiteLink());
    //     con.setLinkedInLink(contactForm.getLinkedInLink());
    //     con.setPicture(contactForm.getPicture());
    
    
    //     //     // process image:

    //      if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
    //          logger.info("file is not empty");
    //         String fileName = UUID.randomUUID().toString();
    //         String imageUrl = imageServices.uploadImage(contactForm.getContactImage(), fileName);
    //         con.setCloudinaryImagePublicId(fileName);
    //         con.setPicture(imageUrl);
    //         contactForm.setPicture(imageUrl);

    //     } else {
    //         logger.info("file is empty");
    //     }

    //        var updateCon = contactService.update(con);
    //        logger.info("updated contact {}", updateCon);

    //        model.addAttribute("message", Message.builder().content("Contact Updated !!").type(MessageType.green).build());

    //     return "redirect:/user/contacts/view/" + contactId;
    //  }



  }
