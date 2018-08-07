package org.itman85.samples.spliterator;

import org.itman85.samples.spliterator.model.Person;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by phannguyen on 8/7/18.
 */
public class CreatingSpliterator {
    public static void main(String[] args){
        ClassLoader loader = CreatingSpliterator.class.getClassLoader();
        File file = new File(loader.getResource("people.txt").getFile());
        System.out.println(file.toURI());
        Path path = Paths.get(file.toURI());

        try (Stream<String> lines = Files.lines(path);) {

            Spliterator<String> lineSpliterator = lines.spliterator();
            Spliterator<Person> peopleSpliterator = new PersonSpliterator(lineSpliterator);

            Stream<Person> people = StreamSupport.stream(peopleSpliterator, false);
            people.forEach(System.out::println);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


    }
}
