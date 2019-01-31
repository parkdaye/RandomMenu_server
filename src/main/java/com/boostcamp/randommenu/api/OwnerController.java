package com.boostcamp.randommenu.api;

import com.boostcamp.randommenu.model.Access;
import com.boostcamp.randommenu.dto.DefaultRes;
import com.boostcamp.randommenu.model.Login;
import com.boostcamp.randommenu.service.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Slf4j
@RestController
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(final OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping("login")
    public ResponseEntity<DefaultRes> login(@RequestBody @Valid final Login login) throws Exception {
        DefaultRes loginRes = ownerService.login(login.getOwnerId());

        if(loginRes == null) {
            throw new Exception();
        }

        return new ResponseEntity<>(loginRes, HttpStatus.OK);
    }

    @PostMapping("access")
    public ResponseEntity<DefaultRes> matchKey(@RequestBody @Valid final Access access) throws Exception {
        DefaultRes accessRes = ownerService.matchKey(access.getAccessKey());

        if(accessRes == null) {
            throw new Exception();
        }

        return new ResponseEntity<>(accessRes, HttpStatus.OK);
    }

}
