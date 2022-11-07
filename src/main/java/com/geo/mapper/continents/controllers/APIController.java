package com.geo.mapper.continents.controllers;

import com.geo.mapper.continents.models.ContinentInfoDto;
import com.geo.mapper.continents.services.DataService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@ApiOperation(value = "A Production Ready API for Geo Mapper API which deals with countries and its continent.")
public class APIController {

    @Autowired
    private DataService dataService;

    @PostMapping(value = "/get-countries-in-continent")
    @ApiOperation(value = "Get all geographical continent information for a given list of countries")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrieves the continent of given country along with other countries in the continent.") ,
            @ApiResponse(responseCode = "404", description = "Invalid Country in input."),
            @ApiResponse(responseCode = "500", description = "Please contact the support."),
            @ApiResponse(responseCode = "403", description = "User is unauthorized to trigger the API, Please login and try again.")})
    public ContinentInfoDto getCountriesFromContinent(@ApiParam(value = "Takes a list of countries as input. Example: [\"CA\",\"US\"]") @RequestBody List<String> countries,
                                                      HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        HttpSession session = httpServletRequest.getSession(true);
        String appKey = String.valueOf("geomapper");
        Bucket bucket = (Bucket) session.getAttribute("throttler-" + appKey);
        if (bucket == null) {
            bucket = createNewBucket();
            session.setAttribute("throttler-" + appKey, bucket);
        }
        boolean okToGo = bucket.tryConsume(1);
        if (okToGo) {
            return dataService.getOtherCountriesAlongWithInput(countries);
        } else {
            httpServletResponse.sendRedirect("/request-limit-reached");
            return null;
        }

    }

    public Bucket createNewBucket() {
        long capacity = 10;
        Refill refill = Refill.greedy(10, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(capacity, refill);
        return Bucket.builder().addLimit(limit).build();
    }

    @GetMapping(value="/request-limit-reached")
    public ResponseEntity<String> requestLimitReached(){
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
}
