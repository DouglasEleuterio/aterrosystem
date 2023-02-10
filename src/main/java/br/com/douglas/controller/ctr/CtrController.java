package br.com.douglas.controller.ctr;

import br.com.douglas.controller.core.BaseController;
import br.com.douglas.entity.entities.temp.CTR;
import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.mapper.ctr.CtrRequest;
import br.com.douglas.mapper.ctr.CtrResponse;
import br.com.douglas.service.interfaces.IBaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ctr")
public class CtrController extends BaseController<CTR, CtrRequest, CtrResponse> {
    protected CtrController(IBaseService<CTR> service, BaseMapper<CTR, CtrRequest, CtrResponse> responseMapper) {
        super(service, responseMapper);
    }
}
