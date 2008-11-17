package org.jvyaml;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.Reader;
import java.util.Iterator;

import junit.framework.TestCase;

import org.yaml.snakeyaml.parser.Parser;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.scanner.ScannerImpl;

public class ParserImplTest extends TestCase {

    public void testParserImpl() throws Exception {
        main(new String[0]);
    }

    public static void tmain(final String[] args) throws Exception {
        String filename;
        if (args.length == 1) {
            filename = args[0];
        } else {
            filename = "src/test/resources/specification/example2_28.yaml";
        }
        System.out.println("Reading of file: \"" + filename + "\"");

        final StringBuffer input = new StringBuffer();
        final Reader reader = new FileReader(filename);
        char[] buff = new char[1024];
        int read = 0;
        while (true) {
            read = reader.read(buff);
            input.append(buff, 0, read);
            if (read < 1024) {
                break;
            }
        }
        reader.close();
        final String str = input.toString();
        final long before = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
            final Parser pars = new ParserImpl(new ScannerImpl(
                    new org.yaml.snakeyaml.reader.Reader(str)));
            for (final Iterator iter = new EventIterator(pars); iter.hasNext(); iter.next()) {
            }
        }
        final long after = System.currentTimeMillis();
        final long time = after - before;
        final double timeS = (after - before) / 1000.0;
        System.out.println("Walking through the events for the file: " + filename + " took " + time
                + "ms, or " + timeS + " seconds");
    }

    public static void main(final String[] args) throws Exception {
        String filename;
        if (args.length == 1) {
            filename = args[0];
        } else {
            filename = "src/test/resources/specification/example2_28.yaml";
        }
        final Parser pars = new ParserImpl(new ScannerImpl(new org.yaml.snakeyaml.reader.Reader(
                new FileInputStream(filename))));
        for (final Iterator iter = new EventIterator(pars); iter.hasNext();) {
            System.out.println(iter.next().getClass().getName());
        }
    }

    private static class EventIterator implements Iterator {
        Parser parser;

        public EventIterator(Parser parser) {
            this.parser = parser;
        }

        public boolean hasNext() {
            return null != parser.peekEvent();
        }

        public Object next() {
            return parser.getEvent();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
