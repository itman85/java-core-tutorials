package org.itman85.samples.streampattern;

import org.itman85.samples.spliterator.CreatingSpliterator;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Created by phannguyen on 9/7/18.
 */
public class FlatmapStreamEx {
    public static void main(String[] args){
        ClassLoader loader = FlatmapStreamEx.class.getClassLoader();
        try {
            Stream<String> stream1 = Files.lines(Paths.get(loader.getResource("TomSawyer_01.txt").toURI()));
            Stream<String> stream2 = Files.lines(Paths.get(loader.getResource("TomSawyer_02.txt").toURI()));
            Stream<String> stream3 = Files.lines(Paths.get(loader.getResource("TomSawyer_03.txt").toURI()));
            Stream<String> stream4 = Files.lines(Paths.get(loader.getResource("TomSawyer_04.txt").toURI()));

           // System.out.println("Stream 1" + stream1.count());//count will throw " stream has already been operated upon or closed"
           // System.out.println("Stream 2" + stream2.count());
           // System.out.println("Stream 3" + stream3.count());
           // System.out.println("Stream 4" + stream4.count());

            //merge stream
            Stream<Stream<String>> streamOfStreams = Stream.of(stream1,stream2,stream3,stream4);
           // System.out.println("# streams: " + streamOfStreams.count());//count will throw " stream has already been operated upon or closed"

            //Supplier< Stream<Stream<String>> > streamsSupplier
            //        = () -> Stream.of(stream1,stream2,stream3,stream4);

            //Stream<String> streamOfLines = streamsSupplier.get().flatMap(Function.identity());
            Stream<String> streamOfLines = streamOfStreams.flatMap(Function.identity());//slit stream by line
            //Function.identity() Same to Stream<String> streamOfLines = streamOfStreams.flatMap(stringStream -> stringStream);
            //System.out.println("# lines: " + streamOfLines.count());


            Function<String, Stream<String>> lineSplitter =
                    line -> Pattern.compile(" ").splitAsStream(line);// split line into word by space character

            Stream<String> streamOfWords =
                    streamOfLines.flatMap(lineSplitter)
                            .map(word -> word.toLowerCase())
                            .filter(word -> word.length() == 4)
                            .distinct();

            System.out.println("# words :" + streamOfWords.count());

        }catch (Exception ex){
            ex.printStackTrace();

        }


    }
}
