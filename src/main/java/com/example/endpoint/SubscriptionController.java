package com.example.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
class SubscriptionController {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(final SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @ExceptionHandler({Exception.class})
    ResponseEntity<SubscriptionResponse> unexpectedException(Exception e) {
        logger.error("Exception: ", e);
        return new ResponseEntity<>(new SubscriptionResponse("Unexpected error, check more info in log file"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/subscription", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<SubscriptionResponse> eventPost(@RequestBody @Valid SubscriptionDTO subscriptionDTO, BindingResult bindingResult) {
        logger.info(String.format("HTTP Request body: %s", subscriptionDTO));
        if (bindingResult.hasErrors()) {
            final List<ObjectError> allErrors = bindingResult.getAllErrors();
            final List<String> messages = allErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            return new ResponseEntity<>(new SubscriptionResponse(String.join(", ", messages)), HttpStatus.BAD_REQUEST);
        }
        Long subscriptionId = subscriptionService.publish(subscriptionDTO);
        return new ResponseEntity<>(new SubscriptionResponse(subscriptionId.toString()), HttpStatus.OK);
    }
}
