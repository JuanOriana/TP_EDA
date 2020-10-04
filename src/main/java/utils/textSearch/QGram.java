package utils.textSearch;

import java.util.HashMap;
import java.util.Set;

public class QGram {

    int n;

    public QGram(int n) {
        this.n = n;
    }

    public HashMap<String,Integer> createToken(String string){
        HashMap<String, Integer> myMap = new HashMap<>();
        String numerales= "";
        for (int i = 0; i< n-1; i++){
            numerales=numerales.concat("#");
        }
        string = string.concat(numerales);
        string = numerales.concat(string);
        for (int i = 0; i < string.length()-n+1; i++){
            String substring = string.substring(i, i+n);
            myMap.putIfAbsent(substring,0);
            myMap.put(substring, myMap.get(substring)+1);

        }
        return myMap;
    }

    public void printToken(String string){
        Set<String> set = createToken(string).keySet();
        System.out.println(set);
    }

    public double compareTokens(String string1, String string2){
        int match=0, size1=0, size2=0;
        HashMap<String,Integer> map = createToken(string2);
        HashMap<String,Integer> map1 = createToken(string1);
        Set<String> set1 = map1.keySet();
        for (String string: map.keySet()){
            size2 += map.get(string);
        }
        for (String string: map1.keySet()){
            size1+= map1.get(string);
        }
        for (String string: set1){
            if (map.containsKey(string)){
                if (map.get(string)==1){
                    map.remove(string);
                }else{
                    map.put(string,map.get(string)-1);
                }
                match++;
            }
        }
        return (double)(size1+size2-(size1+size2-2*match))/(size1+size2);
    }

}
