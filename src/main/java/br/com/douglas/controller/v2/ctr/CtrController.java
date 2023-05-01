package br.com.douglas.controller.v2.ctr;

import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.mapper.ctr.CtrForInvoiceResponse;
import br.com.douglas.mapper.ctr.CtrMapper;
import br.com.douglas.service.ctr.CTRService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("ctrV2")
@RequestMapping("/api/v2/ctr")
public class CtrController {

    private final CTRService ctrService;
    private final CtrMapper mapper;
    public CtrController(CTRService ctrService, CtrMapper mapper) {
        this.ctrService = ctrService;
        this.mapper = mapper;
    }

    @GetMapping(path = "/{id}")
    public CtrForInvoiceResponse findById(@PathVariable(name = "id") String id) throws DomainException {
        return mapper.byCtrResponse(ctrService.findById(id));
    }
}
