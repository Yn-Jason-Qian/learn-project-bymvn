package net.stealthcat.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FileReadUtil {

    public static List<String> readAllLines(String resourceFilename) {
        try {
            return Files.readAllLines(Paths.get(Objects.requireNonNull(FileReadUtil.class.getClassLoader().getResource(resourceFilename)).toURI()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

}
