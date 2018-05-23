package br.com.um.usermanager.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.um.usermanager.models.User;
import br.com.um.usermanager.repository.UserRepository;

@Controller
public class ManagerController {
	@Autowired
	private UserRepository ur;

	@RequestMapping(value = "/registerUser", method = RequestMethod.GET)
	public String form() {
		return "user/formUser";
	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public String form(@Valid User user, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Campos Obrigatórios Preencha Todos os campos!");
			return "redirect:/registerUser";
		}
		ur.save(user);
		attributes.addFlashAttribute("mensagem", "Usuario Adicionado com Sucesso!");
		return "redirect:/registerUser";
	}

	@RequestMapping("/users")
	public ModelAndView listManager() {
		ModelAndView mv = new ModelAndView("index");
		Iterable<User> users = ur.findAll();
		mv.addObject("users", users);
		return mv;
	}

	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public ModelAndView detailsUser(@PathVariable("code") long code) {
		User user = ur.findByCode(code);
		ModelAndView mv = new ModelAndView("user/detailsUser");
		mv.addObject("user", user);
		return mv;
	}

	@RequestMapping("/deleteUser")
	public String deleteUser(long code) {
		User evento = ur.findByCode(code);
		ur.delete(evento);
		return "redirect:/users";
	}

}
