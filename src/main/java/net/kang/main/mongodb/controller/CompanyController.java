package net.kang.main.mongodb.controller;

import net.kang.main.mongodb.domain.Company;
import net.kang.main.mongodb.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("company")
public class CompanyController {
    @Autowired CompanyService companyService;

    @GetMapping("all_company")
    public Flux<Company> allCompany(){
        return companyService.findAll();
    }

    @GetMapping("one_company/{id}")
    public Mono<ResponseEntity<Company>> oneCompany(@PathVariable("id") String id){
        return companyService.findOne(id).map(company -> {
            return ResponseEntity.ok(company);
        }).defaultIfEmpty(
            new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @PostMapping("create_company")
    public Mono<ResponseEntity<Company>> createCompany(@Valid @RequestBody Company company){
        return companyService.create(company).map(c -> {
            return new ResponseEntity<Company>(c, HttpStatus.CREATED);
        }).defaultIfEmpty(
            new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @PutMapping("update_company/{id}")
    public Mono<ResponseEntity<Company>> updateCompany(@PathVariable("id") String id, @Valid @RequestBody Company company){
        return companyService.update(id, company)
            .map(newCompany -> {
                return ResponseEntity.ok(newCompany);
            }).defaultIfEmpty(
                    new ResponseEntity<>(HttpStatus.NOT_FOUND)
            );
    }

    @DeleteMapping("delete_company/{id}")
    public Mono<ResponseEntity<Void>> deleteCompany(@PathVariable("id") String id){
        return companyService.delete(id).then(
            Mono.just(new ResponseEntity<Void>(HttpStatus.OK))
        ).defaultIfEmpty(
            new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }
}
