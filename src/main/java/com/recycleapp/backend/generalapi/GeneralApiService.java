package com.recycleapp.backend.generalapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;

@Service
public class GeneralApiService {

    String generalApiJsonPath = "src/main/resources/static/recycling_locs.json";

    public ObjectNode getRecyclingLocations() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return (ObjectNode) mapper.readTree(Paths.get(generalApiJsonPath).toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
