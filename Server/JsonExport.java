


import java.util.LinkedList;

public class JsonExport {

    int width, height, time;
    LinkedList<Source> sourceList;

    public JsonExport(int aheight, int awidth, int atime, LinkedList<Source> asourceList) {
        height = aheight;
        width = awidth;
        time = atime;
        sourceList = asourceList;
    }

    public String toString() {
        return "width : " + this.width + "\nheight : " + this.height + "\ntime : " + this.time + "\nliste : " + this.sourceList;
    }
}
