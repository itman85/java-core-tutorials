package org.itman85.samples.spliterator;

import org.itman85.samples.spliterator.model.Person;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by phannguyen on 8/7/18.
 * This PersonSpliterator will help to build objects<Person> list from stream data
 */
public class PersonSpliterator implements Spliterator<Person> {
    private final Spliterator<String> lineSpliterator;
    private String name;
    private int age;
    private String city;

    public PersonSpliterator(Spliterator<String> lineSpliterator) {
        this.lineSpliterator = lineSpliterator ;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Person> action) {
        if (this.lineSpliterator.tryAdvance(line -> this.name = line) &&
                this.lineSpliterator.tryAdvance(line -> this.age = Integer.parseInt(line)) &&
                this.lineSpliterator.tryAdvance(line -> this.city = line)) {

            Person p = new Person(name, age, city);
            action.accept(p);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Spliterator<Person> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return lineSpliterator.estimateSize() / 3;
    }


    @Override
    public int characteristics() {
        return lineSpliterator.characteristics();
    }
}
