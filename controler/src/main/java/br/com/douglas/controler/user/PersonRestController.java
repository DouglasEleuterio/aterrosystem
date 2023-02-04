package br.com.douglas.controler.user;

import br.com.douglas.controler.mapper.mappers.PersonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PersonRestController {

   @GetMapping("/person")
   public ResponseEntity<PersonResponse> getPerson() {
      return ResponseEntity.ok(new PersonResponse("John Doe", "john.doe@test.org"));
   }
}
