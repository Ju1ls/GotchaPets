package cz.jull.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.jull.logic.pet.Pet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class responsible for loading the static configuration data of all possible pets
 * from a JSON file into memory using Jackson.
 */
public class PetLoader {
    /**
     * Parses the JSON file mapping all available pets in the game.
     *
     * @param filePath The path to the JSON file containing the pet configurations.
     * @return A list of initialized Pet objects representing the entire game catalog.
     */
    public static List<Pet> loadPetsFromJson(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(filePath), new TypeReference<List<Pet>>() {});
        } catch (IOException e) {
            System.out.println("File couldn't be loaded: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
