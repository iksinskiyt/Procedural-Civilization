public class HeightMap {
    public double[][] height;
    public double estimatedSeaLevel;
    public double estimatedMountainLevel;

    public HeightMap(){
        void estimateSeaLevel(double scaleFactor){
            double sum = 0;
            for(int i = 0;i < height.length/2;i++){
                for(int j = 0; i < height.length/2;i++){
                    sum += height[i][j];
                }
            }
            estimatedSeaLevel = (sum / height.length) * scaleFactor;
        }
        void estimateMountainLevel(double scaleFactor){
            double sum = 0;
            for(int i = 0;i < height.length/2;i++){
                for(int j = 0; i < height.length/2;i++){
                    sum += height[i][j];
                }
            }
            estimatedMountainLevel = (sum / height.length) * scaleFactor;
        }
    }

}
