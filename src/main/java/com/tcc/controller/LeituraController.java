package com.tcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.tcc.apiTwilio.WhatsApp;
import com.tcc.model.Leitura;
import com.tcc.repository.LeituraRepository;

@CrossOrigin("*")
@RestController
public class LeituraController {

	@Autowired
	private LeituraRepository leituraRepository;
	
	@PostMapping("salvarLeitura")
	@ResponseBody
	public ResponseEntity<Leitura> salvarLeitura(@RequestBody Leitura leitura){
		WhatsApp wt = new WhatsApp();
		wt.Send();
		Leitura leituraArduino = leituraRepository.save(leitura);
		return new ResponseEntity<Leitura>(leituraArduino, HttpStatus.CREATED);
	}

	@GetMapping("/buscarLeituras")
	@ResponseBody
	public ResponseEntity<List<Leitura>> buscarLeituras(@RequestParam(name = "data") String data){
		System.out.println(data);
		List<Leitura> leituras = leituraRepository.buscarPorData(data.trim().toUpperCase());
		return new ResponseEntity<List<Leitura>>(leituras, HttpStatus.OK);
	}
}
