package com.geo.mapper.continents.services;

import com.geo.mapper.continents.exceptions.InvalidCountryException;
import com.geo.mapper.continents.models.Continent;
import com.geo.mapper.continents.models.ContinentInfoDto;
import com.geo.mapper.continents.models.Country;
import com.geo.mapper.continents.models.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataService {

    private Logger logger  = LoggerFactory.getLogger(DataService.class);

    @Autowired
    RestTemplate restTemplate;

    private List<Continent> continentList;

    @PostConstruct
    public void init(){
        this.getExternalData();
    }

    public ContinentInfoDto getOtherCountriesAlongWithInput(List<String> inputCountries) {
        List<Continent> continents = getContinentList();
        ContinentInfoDto continentInfoDto = new ContinentInfoDto();
        List<Country> allCountriesOfContinent = new ArrayList<>();
        String continentName = "";
        for (String inputCountry : inputCountries) {
            if (Objects.nonNull(continents)) {
                Optional<Continent> matchedContinentData = continents.stream().filter(
                        continent -> continent.getCountries().stream().filter(country -> country.getCode().equals(inputCountry)).findFirst().isPresent()
                ).findFirst();
                if(matchedContinentData.isPresent()){
                    continentName = matchedContinentData.get().getName();
                    allCountriesOfContinent.addAll(matchedContinentData.get().getCountries());
                    continentInfoDto.setInputCountries(inputCountries);
                    allCountriesOfContinent.removeIf( country -> country.getCode().equals(inputCountry));
                    continentInfoDto.setOtherCountries(allCountriesOfContinent.stream().map(country -> country.getCode()).collect(Collectors.toList()));
                    continentInfoDto.setNameOfTheContinent(continentName);
                }else{
                    logger.error("Error in fetching details: Invalid country requested - " + LocalDate.now());
                    throw new InvalidCountryException("InvalidData");
                }
            }
        }
        return continentInfoDto;
    }


    @Cacheable("continent-list")
    public List<Continent> getContinentList() {
        return continentList;
    }

    public void setContinentList(List<Continent> continentList) {
        this.continentList = continentList;
    }

    public void getExternalData(){
        List<String> existingContinents = Arrays.asList(new String[]{"AS","EU","NA","SA","AN","AF","OC"});
        List<Continent> continents = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        for(String existingContinent: existingContinents){
            String rawBodyString = "{\n" +
                    "    \"query\": \"query($code: ID!) {\\n  continent(code: $code) {\\n    code\\n    name\\n    countries {\\n      code\\n      name\\n      native\\n      phone\\n      capital\\n      currency\\n      emoji\\n      emojiU\\n    }\\n  }\\n}\\n\",\n" +
                    "    \"variables\": {\n" +
                    "        \"code\": \"_code\"\n" +
                    "    }\n" +
                    "}";
            rawBodyString = rawBodyString.replace("_code",existingContinent);
            HttpEntity<String> request = new HttpEntity<>(rawBodyString, headers);
            ResponseEntity<ResponseData> response = restTemplate.exchange("https://countries.trevorblades.com/graphql", HttpMethod.POST, request, ResponseData.class);
            boolean isValidResponse = (Objects.nonNull(response.getStatusCode()) && response.getStatusCode().is2xxSuccessful())
                    && (Objects.nonNull(response.getBody().getData()) && Objects.nonNull(response.getBody().getData().getContinent()));
            if(isValidResponse){
                Continent continent = response.getBody().getData().getContinent();
                continents.add(continent);
            }
        }
        setContinentList(continents);
    }

}

