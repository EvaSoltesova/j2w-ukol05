package cz.czechitas.java2webapps.ukol5.controller;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

/**
 * Kontroler obsluhující registraci účastníků dětského tábora.
 */
@Controller
public class RegistraceController {

  @GetMapping("/")
  public ModelAndView formular() {
    ModelAndView modelAndView = new ModelAndView("formular");
    modelAndView.addObject("form", new RegistraceForm());
    return modelAndView;
  }

  @PostMapping("")
  public Object registruj(@ModelAttribute("form") @Valid RegistraceForm form, BindingResult bindingResult) {
    if(form.getDatumNarozeni() != null) {
      if (form.getVek() < 9 || form.getVek() > 15) {
        bindingResult.rejectValue("datumNarozeni", "", "Povolený věk je od 9 do 15 let.");
        return "formular";
      }
    }

    if(form.getSporty() != null) {
      if (form.getSporty().size() < 2) {
        bindingResult.rejectValue("sport", "", "Je nutné vybrat aspoň dva sporty.");
        return "formular";
      }
    }

    if (bindingResult.hasErrors()) {
      return "formular";
    }

    return new ModelAndView("rekapitulace")
            .addObject("formular", form);
    }

  @InitBinder    /* Converts empty strings into null when a form is submitted - used for email, telefon and web values */
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
  }