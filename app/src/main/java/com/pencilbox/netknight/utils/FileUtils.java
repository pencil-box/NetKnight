package com.pencilbox.netknight.utils;

import com.pencilbox.netknight.io.BitInputStream;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * Created by pencil-box on 16/6/19.
 */
public class FileUtils {


    /**
     * 流转化成stringBuffer
     * @param in
     * @param charset
     * @return
     */
    public static StringBuffer stream2sb(InputStream in, String charset){

        StringBuffer buffer = new StringBuffer();
        try {


            BitInputStream bitInputStream = new BitInputStream(in);
            int i=0,c=1;
            while((i= bitInputStream.readBits(4))!=-1&&c<32){
                System.out.print(EncodeUtils.int2hex(i)+"");
                buffer.append(i);
                if(c%2==0){
                    buffer.append(" ");
                }

                c++;

            }


//            BufferedInputStream inputStream = new BufferedInputStream(in);
//            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream));


//            String line;
//            while((line=bufferReader.readLine())!=null){
//                sb.append(line);
//            }

//            Reader r = new InputStreamReader(in, charset);
//            int length = 0;
//            char[] c = new char[1024];
//            while ( (length = r.read(c)) != -1) {
//                sb.append(c, 0, length);
//            }
//            r.close();

            

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;

    }



//    public static String hex2
}
