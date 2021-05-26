package IO;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {
    private OutputStream out;

    public MyCompressorOutputStream(OutputStream os) {
        this.out = os;
    }

    public void write(int b) throws IOException {
        this.out.write(b);
    }

    public void write(byte[] b) throws IOException {
        int counter;
        for(counter = 0; counter < 8; ++counter) {
            this.out.write(b[counter]);
        }

        counter = 0;
        String binary = "";

        for(int i = 8; i < b.length; ++i) {
            if (counter == 8) {
                this.out.write(Integer.valueOf(Integer.parseInt(binary, 2)).byteValue());
                binary = "";
                counter = 0;
            }

            binary = binary + b[i];
            ++counter;
            if (i == b.length - 1) {
                this.out.write(Integer.valueOf(Integer.parseInt(binary, 2)).byteValue());
            }
        }

    }
}