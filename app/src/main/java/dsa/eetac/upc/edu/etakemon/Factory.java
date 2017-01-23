package dsa.eetac.upc.edu.etakemon;

import java.util.HashMap;



public class Factory {
    private static Factory instance;
    private HashMap<String,Patterns> cache;
    private  Factory(){
        cache= new HashMap<String,Patterns>();
    }
    public static Factory getInstance(){
        if(instance==null){instance= new Factory();}
        return  instance;
    }//SINGLETON
    public Patterns getPattern(String cmd) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

            Patterns c = cache.get(cmd);
            if (c == null) {
                Class cl = Class.forName("pattern."+cmd);//LOG INFO CMD CREATED
                c= (Patterns) cl.newInstance();
                cache.put(cmd, c);//LOG INFO COMMAND IN CACHE
            }
            return c;

    }
}
