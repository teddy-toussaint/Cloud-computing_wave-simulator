import java.util.LinkedList;

public class JsonExport {

    int width, height, time;
    LinkedList<Source> sourceList;

    public JsonExport(int awidth, int aheight, int atime, LinkedList<Source> asourceList) {
        width = awidth;
        height = aheight;
        time = atime;
        sourceList = asourceList;
    }

    public String toString() {
        return "width : " + this.width + "\nheight : " + this.height + "\ntime : " + this.time + "\nliste : " + this.sourceList;
    }
}
