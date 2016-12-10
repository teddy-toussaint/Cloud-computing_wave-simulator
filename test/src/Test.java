import java.io.*;
import java.util.LinkedList;
import javax.json.*;
import javax.json.stream.*;

public class Test {
    public static void main(String [] args){
        LinkedList <Source> a=new LinkedList<Source>();
        Source a1=new Source(1,2,3);
        Source a2=new Source(4,5,6);
        Source a3=new Source(7,8,9);
        a.add(a1);
        a.add(a2);
        a.add(a3);
        int cur=0;
        StringWriter sw = new StringWriter();
        JsonGenerator jsonGen = Json.createGenerator(sw);
        jsonGen.writeStartArray();
        while(cur<a.size()){
            Source s=a.get(cur);
            jsonGen.writeStartObject()
                    .write("x",s.x)
                    .write("y",s.y)
                    .write("Amplitude",s.Amplitude)
                    .writeEnd();
            cur++;
        }
        jsonGen.writeEnd()
            .close();
        String doc = sw.toString();
        System.out.println(doc);
    }
}