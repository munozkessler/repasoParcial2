package com.uca.capas.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.uca.capas.domain.Contribuyente;
import com.uca.capas.domain.Importancia;
import com.uca.capas.service.ContribuyenteService;
import com.uca.capas.service.ImportanciaService;

@Controller
public class MainController {
	
	@Autowired
	ImportanciaService importanciaService;
	
	@Autowired
	ContribuyenteService contribuyenteService;
	
	@RequestMapping("/inicio")
	public ModelAndView inicio() {
		ModelAndView mav = new ModelAndView();
		Contribuyente contribuyente =  new Contribuyente();
		List<Importancia> importancias= null;
		try {
			importancias =  importanciaService.findAll();
		}catch(Exception e) {
			e.printStackTrace();
		}
		mav.addObject("contribuyente",contribuyente);
		mav.addObject("importancias",importancias);
		mav.setViewName("index");
		return mav;

	}
	
	@RequestMapping("/formContribuyente")
	public ModelAndView formulario(@Valid @ModelAttribute Contribuyente contribuyente, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		
		if(result.hasErrors()) {

			List<Importancia> importancias= null;
			try {
				importancias =  importanciaService.findAll();
			}catch(Exception e) {
				e.printStackTrace();
			}
			mav.addObject("importancias",importancias);
			mav.setViewName("index");
		}
		else {
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date(System.currentTimeMillis());
		    contribuyente.setF_fecha_ingreso(date);
		    
		   
		    contribuyenteService.save(contribuyente);

			mav.setViewName("exito");
		}
		
		
		return mav;

	}
	
	@RequestMapping("/verContribuyentes")
	public ModelAndView findAll() {
		ModelAndView mav = new ModelAndView();
		List<Contribuyente> contribuyentes =null;
		
		contribuyentes=contribuyenteService.findAll();
		
		
		mav.addObject("contribuyentes", contribuyentes);
		mav.setViewName("contribuyentes");
		
		return mav;

	}

}