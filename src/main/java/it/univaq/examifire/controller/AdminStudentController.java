package it.univaq.examifire.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.univaq.examifire.model.user.Role;
import it.univaq.examifire.model.user.Student;
import it.univaq.examifire.model.user.User;
import it.univaq.examifire.security.UserPrincipal;
import it.univaq.examifire.service.RoleService;
import it.univaq.examifire.service.StudentService;
import it.univaq.examifire.util.PasswordGeneratorUtils;
import it.univaq.examifire.util.Utils;
import it.univaq.examifire.validation.UserValidator;

@Controller()
@RequestMapping("/home/admin/student")
public class AdminStudentController {
   private static final Logger logger = LoggerFactory.getLogger(AdminStudentController.class);

   @Autowired
   private StudentService studentService;
   
   @Autowired
   private RoleService roleService;
   
   @Autowired
   private UserValidator userValidator;
   
   @Autowired
   private PasswordEncoder passwordEncoder;
   
   @GetMapping("/list")
   public String listAll(Authentication authentication, Model model) {
      logger.info("HTTP GET request received at URL /home/admin/student/list by the admin with email {}", ((UserPrincipal)authentication.getPrincipal()).getEmail());
      return "admin/user/student/list";
   }

   @RequestMapping(value ="/list-datatable", method = RequestMethod.GET)
   public @ResponseBody DataTablesOutput<Student> listAllPaginated (Authentication authentication, Model model, @Valid DataTablesInput input) {
      logger.info("Ajax request received at URL /home/admin/student/list-datatable by the admin with email {}", ((UserPrincipal)authentication.getPrincipal()).getEmail());
      return studentService.findAll(input);
   }
   
   @GetMapping("/add")
   public String showAdd(Authentication authentication, Model model) {
      logger.info("HTTP GET request received at URL /home/admin/student/add by the admin with email {}", ((UserPrincipal)authentication.getPrincipal()).getEmail());
      model.addAttribute("user", new Student());
      return "admin/user/student/add";
   }
   
   
   @Transactional
   @PostMapping("/add")
   public String add(Authentication authentication,
      @RequestParam(name = "save_option") String saveOption, 
      @RequestParam(name = "is_admin", required = false) boolean isAdmin,
      @Validated(User.CreateByAdmin.class) @ModelAttribute("user") Student student, 
      BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
      
      logger.info(
            "HTTP POST request received at URL /home/admin/student/add by the admin with email {}", ((UserPrincipal)authentication.getPrincipal()).getEmail());
      
      userValidator.validateDuplicatedEmail(student, bindingResult);
      
      if (bindingResult.hasErrors()) {
         bindingResult.getAllErrors().forEach((error) -> {
            logger.warn("Validation error: {}", error.getDefaultMessage());
         });
         
         model.addAttribute("is_admin", isAdmin);
         model.addAttribute("confirm_crud_operation", "add_failed");
         return "admin/user/student/add";
      }
      
      student.getRoles().add(roleService.findByName(Role.STUDENT_ROLE_NAME).get());
      
      if (isAdmin) {
         student.getRoles().add(roleService.findByName(Role.ADMIN_ROLE_NAME).get());
      }
      
      // automatically generate the password for the user 
      student.setPassword(passwordEncoder.encode(PasswordGeneratorUtils.generateCommonLangPassword()));
      studentService.update(student);
      logger.info("The {} has been added by the admin with email {}", student.toString(), ((UserPrincipal)authentication.getPrincipal()).getEmail());
         
      redirectAttributes.addFlashAttribute("confirm_crud_operation", "add_succeeded");
      switch (saveOption) {
         case "edit":
          return "redirect:/home/admin/student/edit/" + student.getId() + "/account-info";
         case "add_new":
          return "redirect:/home/admin/student/add";
         case "list_all":
          return "redirect:/home/admin/student/list";
         default:
             throw new IllegalArgumentException("Invalid save option: " + saveOption) ;
       }
   }
      
   @GetMapping("/edit/{studentId}/account-info")
   public String showEditAccountInfo(Authentication authentication, @PathVariable("studentId") Long studentId, Model model) {
      logger.info("HTTP GET request received at URL /home/admin/student/edit/{}/account-info by the admin with email {}", studentId, ((UserPrincipal)authentication.getPrincipal()).getEmail());
      Student persistentUser = studentService.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student Not Found with id: " + studentId));
      model.addAttribute("is_admin", Utils.hasAdminRole(persistentUser));
      model.addAttribute("persistentUser", persistentUser);
      model.addAttribute("user", persistentUser);
      return "admin/user/student/edit/account-info";
   }
   
   
   @Transactional
   @PostMapping("/edit/{studentId}/account-info")
   public String editAccountInfo(Authentication authentication,
      @PathVariable("studentId") Long studentId,
      @RequestParam(name = "is_admin", required = false) boolean isAdmin,
      @Validated(User.EditByAdmin.class) @ModelAttribute("user") Student student, BindingResult bindingResult,
      Model model, RedirectAttributes redirectAttributes) {
      
      logger.info("HTTP POST request received at URL /home/admin/student/edit/{}/account-info by the admin with email {}", studentId, ((UserPrincipal)authentication.getPrincipal()).getEmail());
      
      // we need to set the ID here since it is used by the validator
      student.setId(studentId);
      userValidator.validateDuplicatedEmail(student, bindingResult);
      if (bindingResult.hasErrors()) {
         bindingResult.getAllErrors().forEach((error) -> {
            logger.warn("Validation error: {}", error.getDefaultMessage());
         });
         
         model.addAttribute("persistentUser", studentService.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student Not Found with id: " + studentId)));
         model.addAttribute("is_admin", isAdmin);
         model.addAttribute("confirm_crud_operation", "update_failed");
         return "admin/user/student/edit/account-info";
      }
      
      Student persistentUser = studentService.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student Not Found with id: " + studentId));
      
      persistentUser.setFirstname(student.getFirstname());
      persistentUser.setLastname(student.getLastname());
      persistentUser.setEmail(student.getEmail());
      persistentUser.setIdentificationNumber(student.getIdentificationNumber());
      
      if (isAdmin && !Utils.hasAdminRole(persistentUser)) {
         persistentUser.getRoles().add(roleService.findByName(Role.ADMIN_ROLE_NAME).get());
      }else if (!isAdmin && Utils.hasAdminRole(persistentUser)) {
         persistentUser.getRoles().remove(roleService.findByName(Role.ADMIN_ROLE_NAME).get());
      }
      
      persistentUser.setAccountEnabled(student.isAccountEnabled());
      persistentUser.setPasswordNonExpired(student.isPasswordNonExpired());
      
      studentService.update(persistentUser);
      logger.info("The {} has been edited by the admin with email {}", persistentUser.toString(), ((UserPrincipal)authentication.getPrincipal()).getEmail());
      
      redirectAttributes.addFlashAttribute("confirm_crud_operation", "update_succeeded");
      return "redirect:/home/admin/student/edit/" + persistentUser.getId() + "/account-info";
   }
   
   @GetMapping("/edit/{studentId}/change-password")
   public String showChangePassword(Authentication authentication, @PathVariable("studentId") Long studentId, Model model) {
      
      logger.info("HTTP GET request received at URL /home/admin/student/edit/{}/change-password by the admin with email {}", studentId, ((UserPrincipal)authentication.getPrincipal()).getEmail());
      
      Student persistentUser = studentService.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student Not Found with id: " + studentId));
      model.addAttribute("persistentUser", persistentUser);
      model.addAttribute("user", persistentUser);
      return "admin/user/student/edit/change-password";
   }
   
   @Transactional
   @PostMapping("/edit/{studentId}/change-password")
   public String changePassword(
         Authentication authentication,
         @PathVariable("studentId") Long studentId,
         @RequestParam(name = "confirm_password") String confirmPassword,
         @Validated(User.ChangePassword.class) @ModelAttribute("user") Student student, BindingResult bindingResult, 
         Model model, RedirectAttributes redirectAttributes) {
      
      logger.info("HTTP POST request received at URL /home/admin/student/edit/{}/change-password by the admin with email {}", studentId, ((UserPrincipal)authentication.getPrincipal()).getEmail());
      
      Student persistentUser = studentService.findById(studentId)
            .orElseThrow(() -> new IllegalArgumentException("Student Not Found with id: " + studentId));
      
      userValidator.validateConfirmPassword(student.getPassword(), confirmPassword, bindingResult);
      if (bindingResult.hasErrors()) {
         bindingResult.getAllErrors().forEach((error) -> {
            logger.warn("Validation error: {}", error.getDefaultMessage());
         });
         
         model.addAttribute("persistentUser", studentService.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student Not Found with id: " + studentId)));
         model.addAttribute("confirm_crud_operation", "update_failed");
         return "admin/user/student/edit/change-password";
      }
      
      persistentUser.setPassword(passwordEncoder.encode(student.getPassword())); 
      studentService.update(persistentUser);
      logger.debug("The password of the student with email {} has been updated by the admin with email {}", persistentUser.getEmail(), ((UserPrincipal)authentication.getPrincipal()).getEmail());
      
      redirectAttributes.addFlashAttribute("confirm_crud_operation", "update_succeeded");
      return "redirect:/home/admin/student/edit/" + persistentUser.getId() + "/change-password";
      
   }
   
   @GetMapping("/delete/{studentId}")
   public String delete(
         Authentication authentication,
         @PathVariable("studentId") Long studentId, 
         Model model, RedirectAttributes redirectAttributes){
      logger.info("HTTP GET request received at URL /home/admin/student/delete/{} by the admin with email {}", studentId, ((UserPrincipal)authentication.getPrincipal()).getEmail());

      Student persistentUser = studentService.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student Not Found with id:" + studentId));
      studentService.delete(persistentUser);
      logger.info("The {} has been deleted by the admin with email {}", persistentUser.toString(), ((UserPrincipal)authentication.getPrincipal()).getEmail());
      
      redirectAttributes.addFlashAttribute("confirm_crud_operation", "delete_succeeded");
      return "redirect:/home/admin/student/list";
   }
   
   
}
