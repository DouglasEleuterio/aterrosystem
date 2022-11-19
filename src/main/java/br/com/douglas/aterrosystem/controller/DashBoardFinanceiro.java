package br.com.douglas.aterrosystem.controller;

import br.com.douglas.aterrosystem.entity.AcumuladoMensalDto;
import br.com.douglas.aterrosystem.entity.CTR;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.service.DashBoardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/dashboard")
public class DashBoardFinanceiro {

    private final DashBoardService dashBoardService;

    public DashBoardFinanceiro(DashBoardService dashBoardService) {
        this.dashBoardService = dashBoardService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/acumulado-mensal")
    public AcumuladoMensalDto getDashAcumuladoMensam (){
        return dashBoardService.acumuladoMensal();
    }
}
