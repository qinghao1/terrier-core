import java.util.ArrayList;

public class VectorMath {

    /**
     * Computes the length of the vector
     */
    public static double getLength(ArrayList<Double> list){
        double sum = 0;

        for(Double num: list)
            sum += Math.pow(num, 2);

        return Math.sqrt(sum);
    }

    /**
     * Calculates the dot product of two vectors.
     * @return 0 if the length of vector is not same
     */
    public static double dotProduct(ArrayList<Double> m, ArrayList<Double> n){
        double sum = 0;

        if(m.size() != n.size())
            return sum;

        for(int i = 0; i < m.size(); i ++)
            sum += m.get(i) * n.get(i);

        return sum;
    }

    public static double cosSimilarity(ArrayList<Double> m, ArrayList<Double> n){
        double weight = VectorMath.dotProduct(m, n) / (VectorMath.getLength(m) * VectorMath.getLength(n));
        return weight;
    }
}
