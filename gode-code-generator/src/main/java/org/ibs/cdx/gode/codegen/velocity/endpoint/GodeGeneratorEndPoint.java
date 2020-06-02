package org.ibs.cdx.gode.codegen.velocity.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.ibs.cdx.gode.codegen.velocity.CodeManager;
import org.ibs.cdx.gode.codegen.velocity.app.AppVelocityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/generator")
@Api(tags={"Gode(e) build endpoints"})
public class GodeGeneratorEndPoint {

    @Autowired
    CodeManager manager;
    @PostMapping(path="/build")
    @ApiOperation(value = "Operation to build App")
    public Boolean build(@RequestBody AppVelocityModel model) throws IOException {
        return manager.build(model);
    }

    @PostMapping(path="/deploy")
    @ApiOperation(value = "Operation to deploy App")
    public Boolean deploy(@RequestBody DeploymentOpts options) throws IOException {
        return manager.deploy(options);
    }

}
