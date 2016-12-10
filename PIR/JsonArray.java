public class JsonArray {

    public double[][] ecran;
    
    public JsonArray(int awidth, int aheight){
        ecran = new double[awidth][aheight];
        for (int i = 0; i< awidth; i++){
            for (int j = 0; j < aheight;j++){
                ecran[i][j] = 0;
            }
        }
    }
    
}
