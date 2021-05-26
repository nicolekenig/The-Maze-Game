package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {
    private InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    public int read() throws IOException {
        return this.in.read();
    }

    public int read(byte[] b) throws IOException {
        int index;
        int read;
        for(index = 0; index < 8; ++index) {
            read = this.in.read() & 255;
            b[index] = Integer.valueOf(read).byteValue();
        }

        index = 8;
        read = 0;

        while(read != -1 && index < b.length) {
            read = this.in.read() & 255;
            String toWrite = Integer.toBinaryString(read);
            char[] a = new char[8];
            char[] c = toWrite.toCharArray();
            int x;
            int i;
            if (b.length - index <= 8) {
                if (b.length - index == c.length) {
                    for(x = 0; x < c.length && index < b.length; ++x) {
                        b[index] = Integer.valueOf(Character.getNumericValue(c[x])).byteValue();
                        ++index;
                    }
                } else {
                    for(x = 0; x < 8; ++x) {
                        a[x] = '0';
                    }

                    x = 0;

                    for(i = b.length - index - c.length; i < a.length && x < c.length; ++i) {
                        a[i] = c[x];
                        ++x;
                    }

                    for(i = 0; i < a.length && index < b.length; ++i) {
                        b[index] = Integer.valueOf(Character.getNumericValue(a[i])).byteValue();
                        ++index;
                    }
                }
            } else {
                for(x = 0; x < 8; ++x) {
                    a[x] = '0';
                }

                x = 0;

                for(i = 8 - c.length; i < a.length && x < c.length; ++i) {
                    a[i] = c[x];
                    ++x;
                }

                for(i = 0; i < a.length && index < b.length; ++i) {
                    b[index] = Integer.valueOf(Character.getNumericValue(a[i])).byteValue();
                    ++index;
                }
            }
        }

        return b.length;
    }
}
